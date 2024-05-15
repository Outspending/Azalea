package me.outspending.protocol.packets.client.play;

import me.outspending.position.Pos;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientBlockUpdatePacket(@NotNull Pos pos, int blockID) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeLong(this.pos.toNetwork());
        writer.writeVarInt(this.blockID);
    }

    @Override
    public int id() {
        return 0x09;
    }

}
