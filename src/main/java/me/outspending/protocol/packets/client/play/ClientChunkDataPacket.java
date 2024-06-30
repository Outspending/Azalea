package me.outspending.protocol.packets.client.play;

import me.outspending.chunk.Chunk;
import me.outspending.chunk.light.Blocklight;
import me.outspending.chunk.light.Skylight;
import me.outspending.entity.BlockEntity;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

import java.util.BitSet;

public record ClientChunkDataPacket(
        int chunkX,
        int chunkZ,
        @NotNull CompoundBinaryTag heightmaps,
        @NotNull Chunk chunk,
        @NotNull BlockEntity[] blockEntity,
        @NotNull BitSet skyLightMask,
        @NotNull BitSet blockLightMask,
        @NotNull BitSet emptySkyLightMask,
        @NotNull BitSet emptyBlockLightMask,
        @NotNull Skylight[] skyLight,
        @NotNull Blocklight[] blockLight
) implements ClientPacket {
    public static final CompoundBinaryTag EMPTY_HEIGHTMAP = CompoundBinaryTag.builder()
            .put("MOTION_BLOCKING", CompoundBinaryTag.builder().putIntArray("MOTION_BLOCKING", new int[256]).build())
            .put("WORLD_SURFACE", CompoundBinaryTag.builder().putIntArray("WORLD_SURFACE", new int[256]).build())
            .build();

    @Override
    public void write(@NotNull PacketWriter writer) {
        PacketWriter dataWriter = PacketWriter.createNormalWriter();

        chunk.write(dataWriter);

        writer.writeInt(this.chunkX);
        writer.writeInt(this.chunkZ);
        writer.writeNBTCompound(this.heightmaps);
        writer.writeVarInt(dataWriter.getSize());
        writer.writeByteArray(dataWriter.getStream().toByteArray());

        writer.writeVarInt(this.blockEntity.length);
        for (BlockEntity entity : this.blockEntity) {
            writer.writeByte(entity.packedXZ());
            writer.writeShort(entity.y());
            writer.writeVarInt(entity.type());
            writer.writeNBTCompound(entity.data());
        }
        writer.writeBitSet(this.skyLightMask);
        writer.writeBitSet(this.blockLightMask);
        writer.writeBitSet(this.emptySkyLightMask);
        writer.writeBitSet(this.emptyBlockLightMask);
        writer.writeVarInt(this.skyLight.length);
        for (Skylight light : this.skyLight) {
            writer.writeVarInt(light.skyLightArray().length);
            writer.writeByteArray(light.skyLightArray());
        }
        writer.writeVarInt(this.blockLight.length);
        for (Blocklight light : this.blockLight) {
            writer.writeVarInt(light.blockLightArray().length);
            writer.writeByteArray(light.blockLightArray());
        }
    }

    @Override
    public int id() {
        return 0x27;
    }

}
