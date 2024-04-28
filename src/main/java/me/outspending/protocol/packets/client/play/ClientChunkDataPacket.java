package me.outspending.protocol.packets.client.play;

import lombok.Getter;
import me.outspending.chunk.ChunkSection;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.nbt.CompoundBinaryTag;

import java.util.BitSet;

@Getter
public class ClientChunkDataPacket extends ClientPacket {
    public static final CompoundBinaryTag EMPTY_HEIGHTMAP = CompoundBinaryTag.builder()
            .put("MOTION_BLOCKING", CompoundBinaryTag.builder().putIntArray("MOTION_BLOCKING", new int[256]).build())
            .put("WORLD_SURFACE", CompoundBinaryTag.builder().putIntArray("WORLD_SURFACE", new int[256]).build())
            .build();

    private final int chunkX;
    private final int chunkZ;
    private final CompoundBinaryTag heightmaps;
    private final ChunkSection[] data;
    private final BlockEntity[] blockEntity;
    private final BitSet skyLightMask;
    private final BitSet blockLightMask;
    private final BitSet emptySkyLightMask;
    private final BitSet emptyBlockLightMask;
    private final Skylight[] skyLight;
    private final Blocklight[] blockLight;

    public ClientChunkDataPacket(int chunkX, int chunkZ, CompoundBinaryTag heightmaps, ChunkSection[] data, BlockEntity[] blockEntity, BitSet skyLightMask, BitSet blockLightMask, BitSet emptySkyLightMask, BitSet emptyBlockLightMask, Skylight[] skyLight, Blocklight[] blockLight) {
        super(0x25);

        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.heightmaps = heightmaps;
        this.data = data;
        this.blockEntity = blockEntity;
        this.skyLightMask = skyLightMask;
        this.blockLightMask = blockLightMask;
        this.emptySkyLightMask = emptySkyLightMask;
        this.emptyBlockLightMask = emptyBlockLightMask;
        this.skyLight = skyLight;
        this.blockLight = blockLight;
    }

    @Override
    public void write(PacketWriter writer) {
        writer.writeInt(this.chunkX);
        writer.writeInt(this.chunkZ);
        writer.writeNBTCompound(this.heightmaps);
        writer.writeVarInt(this.data.length);

        // Write the DATA field
        for (ChunkSection section : this.data) {
            section.write(writer);
        }

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

    public record BlockEntity(int packedXZ, short y, int type, CompoundBinaryTag data) {}
    public record Skylight(int length, byte[] skyLightArray) {
        public static final Skylight EMPTY = new Skylight(2048, new byte[2048]);
    }
    public record Blocklight(int length, byte[] blockLightArray) {
        public static final Blocklight EMPTY = new Blocklight(2048, new byte[2048]);
    }
}
