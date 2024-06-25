package me.outspending.player;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import me.outspending.GameMode;
import me.outspending.MinecraftServer;
import me.outspending.NamespacedID;
import me.outspending.block.BlockType;
import me.outspending.chunk.Chunk;
import me.outspending.chunk.light.Blocklight;
import me.outspending.chunk.light.Skylight;
import me.outspending.connection.ClientConnection;
import me.outspending.connection.ConnectionState;
import me.outspending.entity.BlockEntity;
import me.outspending.entity.Entity;
import me.outspending.entity.EntityType;
import me.outspending.entity.LivingEntity;
import me.outspending.events.EventExecutor;
import me.outspending.events.event.*;
import me.outspending.generation.WorldGenerator;
import me.outspending.position.Angle;
import me.outspending.position.Pos;
import me.outspending.protocol.packets.client.configuration.ClientConfigurationDisconnectPacket;
import me.outspending.protocol.packets.client.login.ClientLoginDisconnectPacket;
import me.outspending.protocol.packets.client.play.*;
import me.outspending.protocol.types.ClientPacket;
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
public class Player extends LivingEntity {
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
            default -> {
                // Do Nothing
            }
        }

        EventExecutor.emitEvent(new PlayerDisconnectEvent(this, reason));
        this.getViewers().forEach(viewer -> viewer.sendRemoveEntityPacket(this));

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
        updateViewers();
        handleMovement();

        lastPosition = position;
    }

    private void handleMovement() {
        final Pos to = position;
        final Pos from = lastPosition;

        if (!to.equals(from)) {
            EventExecutor.emitEvent(new PlayerMoveEvent(this, to));

            getViewers().forEach(viewer -> viewer.sendEntityMovementPacket(this, to, from));

            Chunk fromChunk = world.getChunk(from);
            Chunk toChunk = world.getChunk(to);
            if (!fromChunk.equals(toChunk)) {
                EventExecutor.emitEvent(new ChunkSwitchEvent(this, to, fromChunk, toChunk));

                // dont send chunks yet
            }
        }
    }

    public void sendActionBar(@NotNull Component component) {
        sendPacket(new ClientActionBarTextPacket(component));
    }

    public void setGameMode(@NotNull GameMode gameMode) {
        this.gameMode = gameMode;
        sendPacket(new ClientGameEventPacket((byte) 3, gameMode.getId()));
    }

    @ApiStatus.Internal
    public void sendPacket(@NotNull ClientPacket packet) {
        connection.sendPacket(packet);
    }

    @ApiStatus.Internal
    public void sendBundledPacket(Runnable runnable) {
        connection.sendBundled(runnable);
    }

    @ApiStatus.Internal
    public void sendChunkBatch(Chunk... chunks) {
        sendChunkBatch(Arrays.asList(chunks));
    }

    @ApiStatus.Internal
    public void sendChunkBatch(Collection<Chunk> chunks) {
        sendPacket(new ClientChunkBatchStartPacket());
        for (Chunk chunk : chunks) {
            sendChunkData(chunk);
        }
        sendPacket(new ClientChunkBatchFinishedPacket(chunks.size()));
    }

    @ApiStatus.Internal
    public void sendChunkData(Chunk chunk) {
        chunk.setIsLoaded(true);
        sendPacket(new ClientChunkDataPacket(
                chunk.getChunkX(), chunk.getChunkZ(),
                ClientChunkDataPacket.EMPTY_HEIGHTMAP,
                chunk, new BlockEntity[0],
                new BitSet(), new BitSet(), new BitSet(), new BitSet(),
                new Skylight[0], new Blocklight[0]
        ));
    }

    @ApiStatus.Internal
    public void handleConfigurationToPlay() {
        Preconditions.checkArgument(connection.getState() == ConnectionState.CONFIGURATION, "Player is not in configuration state");

        connection.setState(ConnectionState.PLAY);
        EventExecutor.emitEvent(new PlayerJoinEvent(this));
        if (this.getWorld() == null) {
            logger.error("Player's world is null, cannot spawn in. Make sure to set the world on PlayerJoinEvent!");
            this.kick("Failed to join world");
            return;
        }

        this.getServer().getServerProcess().getPlayerCache().add(this);
        sendMainLoginPackets();
    }

    @ApiStatus.Internal
    private void sendMainLoginPackets() {
        sendLoginPlayPacket();

        sendPacket(new ClientSynchronizePlayerPosition(position, (byte) 0, 24));
        handleWorldEntityPackets();
        sendPacket(new ClientGameEventPacket((byte) 13, 0f));

        sendTickingPackets();

        // send chunks
        Chunk positionChunk = world.getChunk(position);
        sendPacket(new ClientCenterChunkPacket(positionChunk.getChunkX(), positionChunk.getChunkZ()));

        Set<Chunk> chunks = new HashSet<>();
        WorldGenerator generator = world.getGenerator();
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

        EventExecutor.emitEvent(new EntitySpawnEvent(this));
    }

    @ApiStatus.Internal
    private void handleWorldEntityPackets() {
        getViewers().forEach(viewer -> {
            sendAddPlayerPacket(viewer);
            viewer.sendAddPlayerPacket(this);
        });
    }

    @ApiStatus.Internal
    public void sendRegistryPacket() {
        DefaultRegistries.sendRegistries(this.connection);
    }

    @ApiStatus.Internal
    private void sendTickingPackets() {
        sendPacket(new ClientSetTickingStatePacket(20, false));
        sendPacket(new ClientStepTickPacket(0));
    }

    @ApiStatus.Internal
    private void sendLoginPlayPacket() {
        final NamespacedID[] dimensionNames = Arrays.stream(DimensionType.values())
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
    public void sendAddPlayerPacket(@NotNull Player player) {
        sendPacket(new ClientPlayerInfoUpdatePacket(
                new ClientPlayerInfoUpdatePacket.Players(
                        player.getUUID(),
                        new ClientPlayerInfoUpdatePacket.Action.AddPlayer(
                                player.getName(),
                                0, new Property[0]),
                        new ClientPlayerInfoUpdatePacket.Action.UpdateListed(true)
                )));
        sendAddEntityPacket(player);
    }

    @ApiStatus.Internal
    public void sendAddEntityPacket(@NotNull Entity entity) {
        final Pos position = entity.getPosition();
        sendPacket(new ClientSpawnEntityPacket(
                entity.getEntityID(), entity.getEntityUUID(),
                entity.getType().getId(), position.x(), position.y(), position.z(),
                new Angle(position.yaw()), new Angle(position.pitch()), Angle.ZERO,
                0, (short) 0, (short) 0, (short) 0
        ));
    }

    @ApiStatus.Internal
    public void sendEntityMovementPacket(@NotNull Entity entity, @NotNull Pos to, @NotNull Pos from) {
        short deltaX = (short) ((to.x() - from.x()) * 32 * 128);
        short deltaY = (short) ((to.y() - from.y()) * 32 * 128);
        short deltaZ = (short) ((to.z() - from.z()) * 32 * 128);

        Angle yaw = new Angle(to.yaw());
        int entityID = entity.getEntityID();

        sendPacket(new ClientUpdateEntityPositionAndRotationPacket(
                entityID,
                deltaX,
                deltaY,
                deltaZ,
                yaw,
                new Angle(to.pitch()),
                entity.isOnGround()
        ));
        sendPacket(new ClientSetHeadRotationPacket(entityID, yaw));
    }

    @ApiStatus.Internal
    public void sendRemoveEntityPacket(@NotNull Entity entity) {
        if (entity instanceof Player player) {
            sendPacket(new ClientPlayerInfoRemovePacket(1, player.getUUID()));
        } else {
            sendPacket(new ClientRemoveEntitiesPacket(1, entity.getEntityID()));
        }
    }

    @ApiStatus.Internal
    public void keepAliveConnection(long time) {
        if (getConnection().getState() == ConnectionState.PLAY) {
            sendPacket(new ClientKeepAlivePacket(time));
        }
    }

    public String getName() {
        return profile.getUsername();
    }

    public UUID getUUID() {
        return profile.getUuid();
    }

}
