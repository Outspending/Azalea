package me.outspending.protocol.packets.client.play;

import lombok.Getter;
import me.outspending.chunk.ChunkSection;
import me.outspending.connection.GameState;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import me.outspending.utils.MathUtils;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.BitSet;

public record ClientChunkDataPacket(
        int chunkX,
        int chunkZ,
        CompoundBinaryTag heightmaps,
        ChunkSection[] data,
        BlockEntity[] blockEntity,
        BitSet skyLightMask,
        BitSet blockLightMask,
        BitSet emptySkyLightMask,
        BitSet emptyBlockLightMask,
        Skylight[] skyLight,
        Blocklight[] blockLight
) implements ClientPacket {
    public static final int BITS_PER_BLOCK = (int) Math.ceil(MathUtils.log2(384 + 1));
    public static final CompoundBinaryTag EMPTY_HEIGHTMAP = CompoundBinaryTag.builder()
            .put("MOTION_BLOCKING", CompoundBinaryTag.builder().putIntArray("MOTION_BLOCKING", new int[256]).build())
            .put("WORLD_SURFACE", CompoundBinaryTag.builder().putIntArray("WORLD_SURFACE", new int[256]).build())
            .build();

    @Override
    public void write(PacketWriter writer) {
        PacketWriter dataWriter = PacketWriter.createNormalWriter();
        for (ChunkSection section : this.data) {
            section.write(dataWriter);
        }

        writer.writeInt(this.chunkX);
        writer.writeInt(this.chunkZ);
        writer.writeNBTCompound(this.heightmaps);
        writer.writeVarInt(dataWriter.getSize());
        writer.writeStream(dataWriter.getStream());

        writer.writeVarInt(this.blockEntity.length);
        writer.writeArray(this.blockEntity, blockEntity -> {
            writer.writeUnsignedByte(blockEntity.packedXZ);
            writer.writeShort(blockEntity.y);
            writer.writeVarInt(blockEntity.type);
            writer.writeNBTCompound(blockEntity.data);
        });
        writer.writeBitSet(this.skyLightMask);
        writer.writeBitSet(this.blockLightMask);
        writer.writeBitSet(this.emptySkyLightMask);
        writer.writeBitSet(this.emptyBlockLightMask);
        writer.writeVarInt(this.skyLight.length);
        writer.writeArray(this.skyLight, skyLight -> {
            writer.writeVarInt(skyLight.skyLightArray.length);
            writer.writeByteArray(skyLight.skyLightArray);
        });
        writer.writeVarInt(this.blockLight.length);
        writer.writeArray(this.blockLight, blockLight -> {
            writer.writeVarInt(blockLight.blockLightArray.length);
            writer.writeByteArray(blockLight.blockLightArray);
        });
    }

    @Override
    public @NotNull GameState state() {
        return GameState.PLAY;
    }

    @Override
    public int id() {
        return 0x25;
    }

    public record BlockEntity(int packedXZ, short y, int type, CompoundBinaryTag data) {}
    public record Skylight(int length, byte[] skyLightArray) {
        public static final Skylight EMPTY = new Skylight(2048, new byte[2048]);
    }
    public record Blocklight(int length, byte[] blockLightArray) {
        public static final Blocklight EMPTY = new Blocklight(2048, new byte[2048]);
    }
}
