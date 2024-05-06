package me.outspending.entity;

import lombok.Getter;
import lombok.Setter;
import me.outspending.GameMode;
import me.outspending.chunk.Chunk;
import me.outspending.chunk.light.Blocklight;
import me.outspending.chunk.light.Skylight;
import me.outspending.connection.ClientConnection;
import me.outspending.position.Pos;
import me.outspending.protocol.packets.client.play.*;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.world.World;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class Player implements TickingEntity {
    private final ClientConnection connection;

    private final List<Player> viewers = new ArrayList<>();
    private final GameMode gameMode = GameMode.CREATIVE;

    private final int entityID;
    private final String username;
    private final UUID uuid;

    private boolean isHardcore = false;
    private int viewDistance = 10 * 5;
    private int simulationDistance = 8 * 5;
    private boolean onGround = false;

    private World world;
    private Pos position;

    public Player(ClientConnection connection, boolean isHardcore, int viewDistance, int simulationDistance, String username, UUID uuid) {
        this.connection = connection;
        this.entityID = EntityCounter.getNextEntityID();
        this.isHardcore = isHardcore;
        this.viewDistance = viewDistance;
        this.simulationDistance = simulationDistance;
        this.username = username;
        this.uuid = uuid;
    }

    public Player(ClientConnection connection, String username, UUID uuid) {
        this.connection = connection;
        this.entityID = EntityCounter.getNextEntityID();
        this.username = username;
        this.uuid = uuid;
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

    @Override
    public void tick(long time) {
        updateViewers();
    }

    @Override
    public void updateViewers() {
        world.getPlayers().forEach(player -> {
            if (this.equals(player)) return;

            if (this.distance(player) <= simulationDistance) {
                this.addViewer(player);
            } else if (this.isViewer(player)) {
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
    public @NotNull List<Player> getViewers() {
        return viewers;
    }

    @ApiStatus.Internal
    public void sendPacket(@NotNull ClientPacket packet) {
        connection.sendPacket(packet);
    }

    @ApiStatus.Internal
    public void sendChunkData(Chunk chunk) {
        sendPacket(new ClientChunkDataPacket(
                chunk.getChunkX(), chunk.getChunkZ(),
                ClientChunkDataPacket.EMPTY_HEIGHTMAP,
                chunk, new BlockEntity[0],
                new BitSet(), new BitSet(), new BitSet(), new BitSet(),
                new Skylight[0], new Blocklight[0]
        ));
    }

    @ApiStatus.Internal
    public void sendAddPlayerPacket(@NotNull Player player) {
        final Pos position = player.getPosition();
        sendPacket(new ClientPlayerInfoUpdatePacket(
                (byte) 0x01,
                new ClientPlayerInfoUpdatePacket.Players(
                        player.uuid,
                        new ClientPlayerInfoUpdatePacket.Action.AddPlayer(
                                player.username,
                                0, new Property[0])
                )));

        sendPacket(new ClientSpawnEntityPacket(
                player.getEntityID(), player.getUuid(),
                124, position.x(), position.y(), position.z(),
                (byte) position.pitch(), (byte) position.yaw(), (byte) 0,
                0, (short) 0, (short) 0, (short) 0
        ));
    }

    @ApiStatus.Internal
    public void sendPlayerMovementPacket(@NotNull Player player, @NotNull Pos to, @NotNull Pos from) {
        short deltaX = (short) ((short) (to.x() * 32 - from.x() * 32) * 128);
        short deltaY = (short) ((short) (to.y() * 32 - from.y() * 32) * 128);
        short deltaZ = (short) ((short) (to.z() * 32 - from.z() * 32) * 128);
        player.sendPacket(new ClientUpdateEntityPositionAndRotationPacket(
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
        sendPacket(new ClientPlayerInfoRemovePacket(1, player.getUuid()));
        sendRemoveEntityPacket(player);
    }

    @ApiStatus.Internal
    public void sendRemoveEntityPacket(@NotNull Entity entity) {
        sendPacket(new ClientRemoveEntitiesPacket(1, entity.getEntityID()));
    }
}
