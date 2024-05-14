package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.connection.ClientConnection;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record PlayerAbilitiesPacket(ClientConnection connection, byte flags) implements ServerPacket {
    public static PlayerAbilitiesPacket read(ClientConnection connection, PacketReader reader) {
        return new PlayerAbilitiesPacket(connection, reader.readByte());
    }

    @Override
    public int id() {
        return 0x20;
    }

    @Override
    public @NotNull ClientConnection getSendingConnection() {
        return connection;
    }
}
