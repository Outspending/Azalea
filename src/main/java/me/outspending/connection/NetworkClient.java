package me.outspending.connection;

import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import me.outspending.block.Block;
import me.outspending.block.BlockType;
import me.outspending.chunk.Chunk;
import me.outspending.chunk.light.Blocklight;
import me.outspending.chunk.light.Skylight;
import me.outspending.entity.BlockEntity;
import me.outspending.entity.Entity;
import me.outspending.player.Player;
import me.outspending.position.Angle;
import me.outspending.position.Pos;
import me.outspending.protocol.packets.client.play.*;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.registry.DefaultRegistries;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public interface NetworkClient {

    @ApiStatus.Internal
    private void sendBundled() {
        sendPacket(new ClientBundleDelimiterPacket());
    }

    @ApiStatus.Internal
    void sendPacket(@NotNull ClientPacket packet);

    @ApiStatus.Internal
    void handleConfigurationToPlay();

    @ApiStatus.Internal
    void handleMainLoginPackets();

    @ApiStatus.Internal
    void handleWorldEntityPackets();

    @ApiStatus.Internal
    void sendLoginPlayPacket();

    @ApiStatus.Internal
    void sendAddPlayerPacket(@NotNull Player player, boolean spawn);

    @ApiStatus.Internal
    void keepConnectionAlive(long time);

    @ApiStatus.Internal
    default void sendBundledPackets(@NotNull ClientPacket... packets) {
        sendBundledPackets(() -> {
            for (ClientPacket packet : packets) {
                sendPacket(packet);
            }
        });
    }

    @ApiStatus.Internal
    default void sendBundledPackets(@NotNull Runnable runnable) {
        sendBundled();
        runnable.run();
        sendBundled();
    }

    @ApiStatus.Internal
    default void sendChunkBatch(Chunk... chunks) {
        sendChunkBatch(Arrays.asList(chunks));
    }

    @ApiStatus.Internal
    default void sendChunkBatch(@NotNull Collection<Chunk> chunks) {
        sendPacket(new ClientChunkBatchStartPacket());
        for (Chunk chunk : chunks) {
            sendChunkData(chunk);
        }
        sendPacket(new ClientChunkBatchFinishedPacket(chunks.size()));
    }

    @ApiStatus.Internal
    default void sendChunkData(@NotNull Chunk chunk) {
        sendPacket(new ClientChunkDataPacket(
                chunk.getChunkX(), chunk.getChunkZ(),
                ClientChunkDataPacket.EMPTY_HEIGHTMAP,
                chunk, new BlockEntity[0],
                new BitSet(), new BitSet(), new BitSet(), new BitSet(),
                new Skylight[0], new Blocklight[0]
        ));
    }

    @ApiStatus.Internal
    default void sendRegistryPackets() {
        DefaultRegistries.sendRegistries(this);
    }

    @ApiStatus.Internal
    default void sendTickingPackets() {
        sendPacket(new ClientSetTickingStatePacket(20, false));
        sendPacket(new ClientStepTickPacket(0));
    }

    @ApiStatus.Internal
    default void sendEntityMovementPacket(@NotNull Entity entity, @NotNull Pos to, @NotNull Pos from) {
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
    default void sendRemoveEntitiesPacket(@NotNull Entity... entities) {
        IntList list = new IntArrayList(entities.length);
        for (Entity entity : entities) {
            list.add(entity.getEntityID());
        }

        sendPacket(new ClientRemoveEntitiesPacket(list));
    }

    @ApiStatus.Internal
    default void sendRemoveEntitiesPacket(@NotNull Collection<Entity> entities) {
        sendRemoveEntitiesPacket(entities.toArray(new Entity[0]));
    }

    @ApiStatus.Internal
    default void sendRemovePlayersPacket(@NotNull Player... players) {
        List<UUID> uuids = new ArrayList<>();
        for (Player player : players) {
            uuids.add(player.getUuid());
            for (Player playerViewer : player.getPlayerViewers()) {
                if (playerViewer.equals(player)) {
                    continue;
                }

                playerViewer.sendRemoveEntitiesPacket(player);
            }
        }

        sendPacket(new ClientPlayerInfoRemovePacket(players.length, uuids));
    }

    @ApiStatus.Internal
    default void sendRemovePlayersPacket(@NotNull Collection<Player> players) {
        sendRemoveEntitiesPacket(players.toArray(new Player[0]));
    }

    @ApiStatus.Internal
    default void sendBlockChange(@NotNull Pos pos, @NotNull BlockType blockType) {
        sendPacket(new ClientBlockUpdatePacket(pos, blockType));
    }

}
