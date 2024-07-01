package me.outspending.player;

import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import lombok.Getter;
import lombok.Setter;
import me.outspending.GameMode;
import me.outspending.MinecraftServer;
import me.outspending.NamespacedID;
import me.outspending.chunk.Chunk;
import me.outspending.connection.ClientConnection;
import me.outspending.connection.ConnectionState;
import me.outspending.connection.NetworkClient;
import me.outspending.entity.Entity;
import me.outspending.entity.EntityType;
import me.outspending.entity.LivingEntity;
import me.outspending.events.EventExecutor;
import me.outspending.events.event.ChunkSwitchEvent;
import me.outspending.events.event.PlayerDisconnectEvent;
import me.outspending.events.event.PlayerJoinEvent;
import me.outspending.events.event.PlayerMoveEvent;
import me.outspending.generation.WorldGenerator;
import me.outspending.messages.Chatable;
import me.outspending.messages.TitleSender;
import me.outspending.position.Pos;
import me.outspending.protocol.GameEvent;
import me.outspending.protocol.packets.client.configuration.ClientConfigurationDisconnectPacket;
import me.outspending.protocol.packets.client.login.ClientLoginDisconnectPacket;
import me.outspending.protocol.packets.client.play.*;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.registry.dimension.Dimension;
import me.outspending.registry.dimension.DimensionType;
import me.outspending.world.World;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

@Getter @Setter
public class Player extends LivingEntity implements NetworkClient, Chatable, TitleSender {
    private static final Logger logger = LoggerFactory.getLogger(Player.class);

    private final ClientConnection connection;
    private final GameProfile profile;
    private final List<Chunk> loadedChunks = new ArrayList<>();

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

    @ApiStatus.Internal
    public void handleMovement() {
        EventExecutor.emitEvent(new PlayerMoveEvent(this, this.position));

        this.getPlayerViewers().forEach(viewer -> viewer.sendEntityMovementPacket(this, this.position, this.lastPosition));

        Chunk fromChunk = world.getChunk(this.lastPosition);
        Chunk toChunk = world.getChunk(this.position);
        if (!fromChunk.equals(toChunk)) {
            EventExecutor.emitEvent(new ChunkSwitchEvent(this, this.position, fromChunk, toChunk));

            final List<Chunk> clientLoadedChunks = this.getLoadedChunks();
            world.getChunksInRange(this.getChunkAt(), (this.viewDistance / 2)).thenAcceptAsync(chunksInRange -> {
                long time = System.currentTimeMillis();

                // Determine new chunks to load
                List<Chunk> newChunksToLoad = chunksInRange.stream()
                        .filter(chunk -> !clientLoadedChunks.contains(chunk))
                        .toList();

                // Determine old chunks to unload
                List<Chunk> chunksToUnload = clientLoadedChunks.stream()
                        .filter(chunk -> !chunksInRange.contains(chunk))
                        .toList();

                if (!newChunksToLoad.isEmpty() || !chunksToUnload.isEmpty()) {
                    for (Chunk chunk : newChunksToLoad) {
                        chunk.load();
                    }

                    sendBundledPackets(() -> {
                        // Add new chunks to the loaded list
                        this.addLoadedChunks(newChunksToLoad);
                        this.sendChunkBatch(newChunksToLoad);

                        // Send unload packets for old chunks
                        for (Chunk chunk : chunksToUnload) {
                            this.sendPacket(new ClientChunkUnloadPacket(chunk));
                            this.removeLoadedChunk(chunk);
                        }
                    });

                    // Ensure border chunks are updated
                    updateBorderChunks(toChunk, this.viewDistance);
                    sendMessage(String.format("Loaded %s Chunks, Unloaded %s Chunks (%s ms)", newChunksToLoad.size(), chunksToUnload.size(), (System.currentTimeMillis() - time)));
                }
            }).exceptionally(e -> {
                logger.error("Error while sending chunk batch", e);
                return null;
            });
        }
    }

    private Set<Chunk> updateBorderChunks(Chunk currentChunk, int viewDistance) {
        int chunkX = currentChunk.getChunkX();
        int chunkZ = currentChunk.getChunkZ();

        Set<Chunk> borderChunks = new HashSet<>();
        for (int dx = -viewDistance; dx <= viewDistance; dx++) {
            for (int dz = -viewDistance; dz <= viewDistance; dz++) {
                if (dx == -viewDistance || dx == viewDistance || dz == -viewDistance || dz == viewDistance) {
                    int newChunkX = chunkX + dx;
                    int newChunkZ = chunkZ + dz;
                    Chunk borderChunk = world.getChunk(newChunkX, newChunkZ);
                    if (borderChunk != null) {
                        borderChunks.add(borderChunk);
                    }
                }
            }
        }

        return borderChunks;
    }

    public void sendActionBar(@NotNull Component message) {
        sendPacket(new ClientActionBarTextPacket(message));
    }

    public void sendActionBar(@NotNull String message) {
        sendActionBar(Component.text(message));
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

    public Chunk getChunkAt() {
        return this.world.getChunk(this.position);
    }

    public boolean canSeeChunk(@NotNull Chunk chunk) {
        return this.loadedChunks.contains(chunk);
    }

    @Override
    public void chat(@NotNull Component message) {

    }

    @Override
    public void sendMessage(@NotNull Component message) {
        sendPacket(new ClientSystemChatMessagePacket(message, false));
    }

    @Override
    public void sendTitle(@NotNull Title title) {
        final Title.Times titleTimes = title.times();
        sendBundledPackets(() -> {
            sendPacket(new ClientSetTitleTextPacket(title.title()));
            sendPacket(new ClientSetSubtitleTextPacket(title.subtitle()));
            if (titleTimes != null) {
                sendPacket(new ClientSetTitleAnimationTimesPacket(titleTimes.fadeIn(), titleTimes.stay(), titleTimes.fadeOut()));
            }
        });
    }

    @Override
    public void sendTitle(@NotNull Component component) {
        sendPacket(new ClientSetTitleTextPacket(component));
    }

    @Override
    public void sendSubtitle(@NotNull Component component) {
        sendPacket(new ClientSetSubtitleTextPacket(component));
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

    public void addLoadedChunk(@NotNull Chunk chunk) {
        this.loadedChunks.add(chunk);
    }

    public void addLoadedChunks(@NotNull Collection<Chunk> chunks) {
        this.loadedChunks.addAll(chunks);
    }

    public void removeLoadedChunk(@NotNull Chunk chunk) {
        this.loadedChunks.remove(chunk);
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
        for (int x = -2; x < 2; x++) {
            for (int z = -2; z < 2; z++) {
                chunks.add(world.getChunk(x, z));
            }
        }

        long start = System.currentTimeMillis();
        addLoadedChunks(chunks);
        sendChunkBatch(chunks);
        logger.info("Finished sending {} chunks in: {}MS", 28*28, System.currentTimeMillis() - start);

        handleWorldEntityPackets();
        this.world.addEntity(this);
        this.getServer().getServerProcess().getPlayerCache().add(this);
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

}
