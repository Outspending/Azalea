package me.outspending.player;

import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import lombok.Getter;
import lombok.Setter;
import me.outspending.GameMode;
import me.outspending.MinecraftServer;
import me.outspending.NamespacedID;
import me.outspending.chat.Chatable;
import me.outspending.chunk.Chunk;
import me.outspending.chunk.light.Blocklight;
import me.outspending.chunk.light.Skylight;
import me.outspending.connection.ClientConnection;
import me.outspending.connection.ConnectionState;
import me.outspending.connection.NetworkClient;
import me.outspending.entity.BlockEntity;
import me.outspending.entity.Entity;
import me.outspending.entity.EntityType;
import me.outspending.entity.LivingEntity;
import me.outspending.events.EventExecutor;
import me.outspending.events.event.ChunkSwitchEvent;
import me.outspending.events.event.PlayerDisconnectEvent;
import me.outspending.events.event.PlayerJoinEvent;
import me.outspending.events.event.PlayerMoveEvent;
import me.outspending.generation.WorldGenerator;
import me.outspending.position.Angle;
import me.outspending.position.Pos;
import me.outspending.protocol.GameEvent;
import me.outspending.protocol.packets.client.configuration.ClientConfigurationDisconnectPacket;
import me.outspending.protocol.packets.client.login.ClientLoginDisconnectPacket;
import me.outspending.protocol.packets.client.play.*;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.types.GroupedPacket;
import me.outspending.registry.DefaultRegistries;
import me.outspending.registry.dimension.Dimension;
import me.outspending.registry.dimension.DimensionType;
import me.outspending.world.World;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

@Getter @Setter
public class Player extends LivingEntity implements NetworkClient, Chatable {
    private static final Logger logger = LoggerFactory.getLogger(Player.class);

    private final ClientConnection connection;
    private final GameProfile profile;

    private GameMode gameMode = GameMode.CREATIVE;
    private Pos lastPosition = Pos.ZERO;

    private boolean isHardcore;
    private int viewDistance;
    private int simulationDistance;
    private boolean onGround = false;

    public Player(ClientConnection connection, boolean isHardcore, int viewDistance, int simulationDistance, GameProfile profile) {
        super(EntityType.PLAYER, profile.getUuid());

        this.connection = connection;
        this.isHardcore = isHardcore;
        this.viewDistance = viewDistance;
        this.simulationDistance = simulationDistance;
        this.profile = profile;
    }

    public Player(ClientConnection connection, GameProfile profile) {
        this(connection, false, 12, 10, profile);
    }

    public void setWorld(@UnknownNullability World world) {
        if (world == null) return;

        this.world = world;
        world.addEntity(this);
    }

    public void kick(@NotNull String reason) {
        this.kick(Component.text(reason));
    }

    public void kick(@NotNull Component reason) {
        final ClientConnection clientConnection = this.getConnection();
        final Socket clientSocket = clientConnection.getSocket();
        switch (clientConnection.getState()) {
            case LOGIN -> sendPacket(new ClientLoginDisconnectPacket(reason));
            case CONFIGURATION -> sendPacket(new ClientConfigurationDisconnectPacket(reason));
            case PLAY -> sendPacket(new ClientPlayDisconnectPacket(reason));
            default -> {}
        }

        EventExecutor.emitEvent(new PlayerDisconnectEvent(this));
        this.getPlayerViewers().forEach(viewer -> viewer.sendRemovePlayersPacket(this));

        try {
            logger.info("Client disconnected: {}", clientSocket.getInetAddress());
            clientSocket.close();
        } catch (IOException e) {
            logger.error("Error while closing socket", e);
        }
    }

    public MinecraftServer getServer() {
        return connection.getServer();
    }

    @Override
    public void tick(long time) {
        super.tick(time);
        handleMovement();

        if (position.x() != lastPosition.x() || position.y() != lastPosition.z() || position.z() != lastPosition.z()) {
            lastPosition = position;
        }
    }

    private void handleMovement() {
        final Pos to = position;
        final Pos from = lastPosition;

        if (!to.equals(from)) {
            EventExecutor.emitEvent(new PlayerMoveEvent(this, to));

            this.getPlayerViewers().forEach(viewer -> viewer.sendEntityMovementPacket(this, to, from));

            Chunk fromChunk = world.getChunk(from);
            Chunk toChunk = world.getChunk(to);
            if (!fromChunk.equals(toChunk)) {
                EventExecutor.emitEvent(new ChunkSwitchEvent(this, to, fromChunk, toChunk));

                // TODO: Send chunks when player moves to a new chunk
            }
        }
    }

    public void sendActionBar(@NotNull Component component) {
        sendPacket(new ClientActionBarTextPacket(component));
    }

    public void setGameMode(@NotNull GameMode gameMode) {
        this.gameMode = gameMode;
        sendPacket(new ClientGameEventPacket(GameEvent.CHANGE_GAME_MODE, gameMode.getId()));
    }

    public void showDemoScreen() {
        sendPacket(new ClientGameEventPacket(GameEvent.DEMO_EVENT, 0));
    }

    public void showCredits() {
        sendPacket(new ClientGameEventPacket(GameEvent.WIN_GAME, 1));
    }

    public void showHurtAnimation(@NotNull Pos pos) {
        showHurtAnimation(pos.yaw());
    }

    public void showHurtAnimation(float yaw) {
        sendPacket(new ClientHurtAnimationPacket(this, yaw));
    }

    public boolean canSee(@NotNull Entity entity) {
        return getViewers().contains(entity);
    }

    @ApiStatus.Internal
    @Override
    public void sendPacket(@NotNull ClientPacket packet) {
        this.connection.sendPacket(packet);
    }

    @ApiStatus.Internal
    @Override
    public void handleConfigurationToPlay() {
        Preconditions.checkArgument(this.connection.getState() == ConnectionState.CONFIGURATION, "Player is not in configuration state");

        this.connection.setState(ConnectionState.PLAY);
        EventExecutor.emitEvent(new PlayerJoinEvent(this));
        if (this.getWorld() == null) {
            logger.error("Player's world is null, cannot spawn in. Make sure to set the world on PlayerJoinEvent!");
            this.kick("Failed to join world");
            return;
        }

        this.getServer().getServerProcess().getPlayerCache().add(this);
        handleMainLoginPackets();
    }

    @ApiStatus.Internal
    @Override
    public void handleMainLoginPackets() {
        sendLoginPlayPacket();

        sendPacket(new ClientSynchronizePlayerPosition(position, (byte) 0, 24));

        updateViewers();
        for (Player player : MinecraftServer.getInstance().getAllPlayers()) {
            if (this.getUuid() == player.getUuid()) {
                continue;
            }

            logger.info("{}", player);
            boolean spawn = player.canSee(player);

            this.sendAddPlayerPacket(player, spawn);
            player.sendAddPlayerPacket(this, spawn);
        }
        this.sendAddPlayerPacket(this, false);

        sendPacket(new ClientGameEventPacket(GameEvent.START_WAITING_FOR_CHUNKS, 0f));

        sendTickingPackets();

        // send chunks
        Chunk positionChunk = world.getChunk(position);
        sendPacket(new ClientCenterChunkPacket(positionChunk.getChunkX(), positionChunk.getChunkZ()));

        Set<Chunk> chunks = new HashSet<>();
        WorldGenerator generator = world.getGenerator();
        this.sendBundledPackets();
        for (int x = -14; x < 14; x++) {
            for (int z = -14; z < 14; z++) {
                Chunk chunk = world.getChunk(x, z);
                generator.generate(chunk);

                chunks.add(chunk);
            }
        }

        long start = System.currentTimeMillis();
        sendChunkBatch(chunks);
        logger.info("Finished sending {} chunks in: {}MS", 28*28, System.currentTimeMillis() - start);

        handleWorldEntityPackets();
        this.world.addEntity(this);
    }

    @ApiStatus.Internal
    @Override
    public void handleWorldEntityPackets() {
        this.getViewers().forEach(viewer -> {
            viewer.spawn(this);
        });
    }

    @ApiStatus.Internal
    @Override
    public void sendLoginPlayPacket() {
        final NamespacedID[] dimensionNames = Arrays.stream(DimensionType.getDimensions())
                .map(Dimension::getBiomeKey)
                .toArray(NamespacedID[]::new);

        sendPacket(new ClientLoginPlayPacket(
                this.entityID, false,
                dimensionNames, 20,
                10, 8, false,
                true, false,
                0, dimensionNames[0],
                0L,
                this.gameMode.getId(), (byte) -1,
                false, false, false,
                null, null,
                0, false
        ));
    }

    @ApiStatus.Internal
    @Override
    public void sendAddPlayerPacket(@NotNull Player player, boolean spawn) {
        this.sendPacket(new ClientPlayerInfoUpdatePacket(
                new ClientPlayerInfoUpdatePacket.Players(
                        player.getUuid(),
                        new ClientPlayerInfoUpdatePacket.Action.AddPlayer(
                                player.getName(),
                                0, new Property[0]),
                        new ClientPlayerInfoUpdatePacket.Action.UpdateListed(true)
                )
        ));

        if (spawn) player.spawn(this);
    }

    @ApiStatus.Internal
    @Override
    public void keepConnectionAlive(long time) {
        if (this.connection.getState() == ConnectionState.PLAY) {
            sendPacket(new ClientKeepAlivePacket(time));
        }
    }

    public String getName() {
        return profile.getUsername();
    }

    @Override
    public void chat(@NotNull String message) {

    }

    @Override
    public void chat(java.awt.@NotNull Component message) {

    }

    @Override
    public void sendMessage(@NotNull String message) {

    }

    @Override
    public void sendMessage(java.awt.@NotNull Component message) {

    }

    @Override
    public void sendMessages(@NotNull String... messages) {

    }

    @Override
    public void sendMessages(java.awt.@NotNull Component... messages) {

    }

    public void setLevel(int level) {

    }

    public void resetLevel() {

    }

    public void setExp(float exp) {

    }

    public byte @NotNull [] grabCookieData(@NotNull NamespacedID key) {
        sendPacket(new ClientRequestCookiePacket(key));
        return new byte[0]; // TODO: Return the cookie data from the response packet!
    }

    public void storeCookie(@NotNull NamespacedID key, byte @NotNull [] value) {
        sendPacket(new ClientStoreCookiePacket(key, value));
    }

}
