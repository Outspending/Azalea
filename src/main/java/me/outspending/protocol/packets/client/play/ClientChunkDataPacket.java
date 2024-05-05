package me.outspending.protocol.packets.client.play;

import me.outspending.chunk.Chunk;
import me.outspending.chunk.light.Blocklight;
import me.outspending.chunk.light.Skylight;
import me.outspending.entity.BlockEntity;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.nbt.CompoundBinaryTag;

import java.util.BitSet;

public record ClientChunkDataPacket(
        int chunkX,
        int chunkZ,
        CompoundBinaryTag heightmaps,
        Chunk chunk,
        BlockEntity[] blockEntity,
        BitSet skyLightMask,
        BitSet blockLightMask,
        BitSet emptySkyLightMask,
        BitSet emptyBlockLightMask,
        Skylight[] skyLight,
        Blocklight[] blockLight
) implements ClientPacket {
    public static final CompoundBinaryTag EMPTY_HEIGHTMAP = CompoundBinaryTag.builder()
            .put("MOTION_BLOCKING", CompoundBinaryTag.builder().putIntArray("MOTION_BLOCKING", new int[256]).build())
            .put("WORLD_SURFACE", CompoundBinaryTag.builder().putIntArray("WORLD_SURFACE", new int[256]).build())
            .build();

    @Override
    public void write(PacketWriter writer) {
        PacketWriter dataWriter = PacketWriter.createNormalWriter();

        chunk.write(dataWriter);

        writer.writeInt(this.chunkX);
        writer.writeInt(this.chunkZ);
        writer.writeNBTCompound(this.heightmaps);
        writer.writeVarInt(dataWriter.getSize());
        writer.writeByteArray(dataWriter.getStream().toByteArray());

        writer.writeVarInt(this.blockEntity.length);
        writer.writeArray(this.blockEntity, blockEntity -> {
            writer.writeUnsignedByte(blockEntity.packedXZ());
            writer.writeShort(blockEntity.y());
            writer.writeVarInt(blockEntity.type());
            writer.writeNBTCompound(blockEntity.data());
        });
        writer.writeBitSet(this.skyLightMask);
        writer.writeBitSet(this.blockLightMask);
        writer.writeBitSet(this.emptySkyLightMask);
        writer.writeBitSet(this.emptyBlockLightMask);
        writer.writeVarInt(this.skyLight.length);
        writer.writeArray(this.skyLight, skyLight -> {
            writer.writeVarInt(skyLight.skyLightArray().length);
            writer.writeByteArray(skyLight.skyLightArray());
        });
        writer.writeVarInt(this.blockLight.length);
        writer.writeArray(this.blockLight, blockLight -> {
            writer.writeVarInt(blockLight.blockLightArray().length);
            writer.writeByteArray(blockLight.blockLightArray());
        });
    }

    @Override
    public int id() {
        return 0x25;
    }
}
