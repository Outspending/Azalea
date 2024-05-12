package me.outspending.protocol.packets.client.play;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.position.Pos;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientSynchronizePlayerPosition(Pos position, byte flags, int teleportID) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeDouble(this.position.x());
        writer.writeDouble(this.position.y());
        writer.writeDouble(this.position.z());
        writer.writeFloat(this.position.yaw());
        writer.writeFloat(this.position.pitch());
        writer.writeByte(this.flags);
        writer.writeVarInt(this.teleportID);
    }

    @Override
    public int id() {
        return 0x3E;
    }

}
