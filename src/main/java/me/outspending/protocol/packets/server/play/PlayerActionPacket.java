package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.position.Location;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record PlayerActionPacket(int status, Location position, byte face, int sequence) implements ServerPacket {
    public static PlayerActionPacket read(PacketReader reader) {
        return new PlayerActionPacket(
                reader.readVarInt(),
                reader.readLocation(),
                reader.readByte(),
                reader.readVarInt()
        );
    }

    @Override
    public @NotNull GameState state() {
        return GameState.PLAY;
    }

    @Override
    public int id() {
        return 0x21;
    }
}
