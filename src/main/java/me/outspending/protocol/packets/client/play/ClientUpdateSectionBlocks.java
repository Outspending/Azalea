package me.outspending.protocol.packets.client.play;

import me.outspending.block.Block;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;

public record ClientUpdateSectionBlocks(long chunkSectionPosition, @NotNull Collection<Block> blocks) implements ClientPacket {
    public ClientUpdateSectionBlocks(long chunkSectionPosition, @NotNull Block[] blocks) {
        this(chunkSectionPosition, Arrays.asList(blocks));
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeLong(this.chunkSectionPosition);
        writer.writeVarInt(this.blocks.size());
        for (Block block : blocks) {
            writer.writeVarLong(block.getType().getId());
        }
    }

    @Override
    public int id() {
        return 0x49;
    }
}
