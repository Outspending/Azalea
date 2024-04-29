package me.outspending.protocol.packets.client.play;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.position.Location;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientBlockUpdatePacket(Location position, int blockID) implements ClientPacket {
    public static ClientBlockUpdatePacket of(@NotNull PacketReader reader) {
        return new ClientBlockUpdatePacket(
                reader.readLocation(),
                reader.readVarInt()
        );
    }

    @Override
    public void write(PacketWriter writer) {
        writer.writeLocation(this.position);
        writer.writeVarInt(this.blockID);
    }

    @Override
    public @NotNull GameState state() {
        return GameState.PLAY;
    }

    @Override
    public int id() {
        return 0x09;
    }
}
