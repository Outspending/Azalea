package me.outspending.protocol.packets.client.play;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.position.Location;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientBlockUpdatePacket(Location position, int blockID) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeLocation(this.position);
        writer.writeVarInt(this.blockID);
    }

    @Override
    public int id() {
        return 0x09;
    }

}
