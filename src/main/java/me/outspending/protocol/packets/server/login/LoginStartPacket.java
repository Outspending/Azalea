package me.outspending.protocol.packets.server.login;

import lombok.Getter;
import me.outspending.connection.ClientConnection;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record LoginStartPacket(ClientConnection connection, String name, UUID uuid) implements ServerPacket {
    public static LoginStartPacket read(ClientConnection connection, PacketReader reader) {
        return new LoginStartPacket(
                connection,
                reader.readString(),
                reader.readUUID()
        );
    }

    @Override
    public int id() {
        return 0x00;
    }

    @Override
    public @NotNull ClientConnection getSendingConnection() {
        return connection;
    }
}
