package me.outspending.entity;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import me.outspending.GameMode;
import me.outspending.MinecraftServer;
import me.outspending.NamespacedID;
import me.outspending.block.Material;
import me.outspending.chunk.Chunk;
import me.outspending.chunk.light.Blocklight;
import me.outspending.chunk.light.Skylight;
import me.outspending.connection.ClientConnection;
import me.outspending.connection.GameState;
import me.outspending.events.EventExecutor;
import me.outspending.events.event.PlayerJoinEvent;
import me.outspending.generation.WorldGenerator;
import me.outspending.position.Pos;
import me.outspending.protocol.packets.client.play.*;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.world.World;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Getter @Setter
public class Player implements LivingEntity, TickingEntity {
    private static final Logger logger = LoggerFactory.getLogger(Player.class);

    private final ClientConnection connection;

    private final List<Player> viewers = new ArrayList<>();
    private final GameMode gameMode = GameMode.CREATIVE;

    private final int entityID;
    private final GameProfile profile;

    private boolean isHardcore = false;
    private int viewDistance = 12;
    private int simulationDistance = 8 * 5;
    private boolean onGround = false;

    private World world;
    private Pos position;

    public Player(ClientConnection connection, boolean isHardcore, int viewDistance, int simulationDistance, GameProfile profile) {
        this.connection = connection;
        this.entityID = EntityCounter.getNextEntityID();
        this.isHardcore = isHardcore;
        this.viewDistance = viewDistance;
        this.simulationDistance = simulationDistance;
        this.profile = profile;
    }

    public Player(ClientConnection connection, GameProfile profile) {
        this.connection = connection;
        this.entityID = EntityCounter.getNextEntityID();
        this.profile = profile;
    }

    public void setWorld(World world) {
        this.world = world;
        world.addEntity(this);
    }

    public void kick(String reason) {
        connection.kick(reason);
    }

    public void kick(Component component) {
        connection.kick(component);
    }

    public MinecraftServer getServer() {
        return connection.getServer();
    }

    @Override
    public void tick(long time) {
        updateViewers();
    }

    @Override
    public void updateViewers() {
        world.getPlayers().forEach(player -> {
            if (this.equals(player)) return;

            boolean isViewer = this.isViewer(player);
            double distance = this.distance(player);
            if (distance <= simulationDistance && !isViewer) {
                this.addViewer(player);
                logger.info("Adding viewer: {}", player.getName());
            } else if (distance >= simulationDistance && isViewer) {
                logger.info("Removing viewer: {}", player.getName());
                this.removeViewer(player);
                sendRemoveEntityPacket(player);
            }
        });
    }

    @Override
    public void addViewer(@NotNull Player player) {
        viewers.add(player);
    }

    @Override
    public void removeViewer(@NotNull Player player) {
        viewers.remove(player);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            return ((Player) obj).getEntityID() == this.getEntityID();
        }

        return false;
    }

    @Override
    public @NotNull List<Player> getViewers() {
        return viewers;
    }

    @Override
    public int compareTo(@NotNull Entity o) {
        return this.getEntityID() == o.getEntityID() ? 0 : -1;
    }

    @ApiStatus.Internal
    public void sendPacket(@NotNull ClientPacket packet) {
        connection.sendPacket(packet);
    }

    public void sendBundled(Runnable runnable) {
        connection.sendBundled(runnable);
    }

    public void sendActionBar(@NotNull Component component) {
        sendPacket(new ClientActionBarTextPacket(component));
    }

    @ApiStatus.Internal
    public void sendBundledPacket() {
        connection.sendBundled();
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
        Preconditions.checkArgument(connection.getState() == GameState.CONFIGURATION, "Player is not in configuration state");

        connection.setState(GameState.PLAY);
        EventExecutor.emitEvent(new PlayerJoinEvent(this));
        if (this.getWorld() == null) {
            logger.error("Player's world is null, cannot spawn in. Make sure to set the world on PlayerJoinEvent!");
            this.kick("Failed to join world");
            return;
        }

        this.getServer().getServerProcess().getPlayerManager().addPlayer(this);
        sendMainLoginPackets();
    }

    @ApiStatus.Internal
    public void sendMainLoginPackets() {
        sendLoginPlayPacket();
        sendPacket(new ClientSynchronizePlayerPosition(position, (byte) 0, 24));
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
                generator.generate(chunk, chunkGenerator -> {
                    chunkGenerator.fillSection(4, Material.STONE);
                });

                chunks.add(chunk);
            }
        }

        long start = System.currentTimeMillis();
        sendChunkBatch(chunks);
        logger.info("Finished sending {} chunks in: {}MS", 28*28, System.currentTimeMillis() - start);

        handleWorldEntityPackets();
    }

    @ApiStatus.Internal
    public void handleWorldEntityPackets() {
        getViewers().forEach(viewer -> {
            sendAddPlayerPacket(viewer);
            viewer.sendAddPlayerPacket(this);
        });
    }

    @ApiStatus.Internal
    public void sendTickingPackets() {
        sendPacket(new ClientSetTickingStatePacket(20, false));
        sendPacket(new ClientStepTickPacket(0));
    }

    @ApiStatus.Internal
    public void sendLoginPlayPacket() {
        final NamespacedID overworld = new NamespacedID("overworld");
        sendPacket(new ClientLoginPlayPacket(
                273, false, 1,
                new NamespacedID[]{overworld}, 20,
                10, 8, false,
                true, false,
                overworld, overworld,
                0L,
                gameMode.getId(), (byte) -1,
                false, false, false,
                null, null,
                0
        ));
    }

    @ApiStatus.Internal
    public void sendAddPlayerPacket(@NotNull Player player) {
        final Pos position = player.getPosition();
        sendPacket(new ClientPlayerInfoUpdatePacket(
                (byte) 0x01,
                new ClientPlayerInfoUpdatePacket.Players(
                        player.getUUID(),
                        new ClientPlayerInfoUpdatePacket.Action.AddPlayer(
                                player.getName(),
                                0, new Property[0])
                )));

        sendPacket(new ClientSpawnEntityPacket(
                player.getEntityID(), player.getUUID(),
                124, position.x(), position.y(), position.z(),
                (byte) position.pitch(), (byte) position.yaw(), (byte) 0,
                0, (short) 0, (short) 0, (short) 0
        ));
    }

    @ApiStatus.Internal
    public void sendPlayerMovementPacket(@NotNull Player player, @NotNull Pos to, @NotNull Pos from) {
        short deltaX = (short) ((to.x() - from.x()) * 32 * 128);
        short deltaY = (short) ((to.y() - from.y()) * 32 * 128);
        short deltaZ = (short) ((to.z() - from.z()) * 32 * 128);

        sendPacket(new ClientUpdateEntityPositionAndRotationPacket(
                player.getEntityID(),
                deltaX,
                deltaY,
                deltaZ,
                (byte) to.yaw(),
                (byte) to.pitch(),
                player.isOnGround()
        ));
    }

    @ApiStatus.Internal
    public void sendRemoveEntityPacket(@NotNull Player player) {
        sendPacket(new ClientPlayerInfoRemovePacket(1, player.getUUID()));
        sendRemoveEntityPacket(player);
    }

    @ApiStatus.Internal
    public void sendRemoveEntityPacket(@NotNull Entity entity) {
        sendPacket(new ClientRemoveEntitiesPacket(1, entity.getEntityID()));
    }

    public String getName() {
        return profile.getUsername();
    }

    public UUID getUUID() {
        return profile.getUuid();
    }

}
