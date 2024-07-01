package me.outspending.protocol.packets.client.play;

import me.outspending.block.BlockType;
import me.outspending.position.Pos;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientBlockUpdatePacket(@NotNull Pos pos, @NotNull BlockType type) implements ClientPacket {

    public ClientBlockUpdatePacket(int x, int y, int z, @NotNull BlockType type) {
        this(new Pos(x, y, z), type);
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeLong(this.pos.toNetwork());
        writer.writeVarInt(this.type.getId());
    }

    @Override
    public int id() {
        return 0x09;
    }

}
