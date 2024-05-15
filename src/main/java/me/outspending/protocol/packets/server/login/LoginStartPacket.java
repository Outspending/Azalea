package me.outspending.protocol.packets.server.login;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record LoginStartPacket(@NotNull ClientConnection connection, @NotNull String name, @NotNull UUID uuid) implements ServerPacket {
    public static LoginStartPacket read(@NotNull ClientConnection connection, @NotNull PacketReader reader) {
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
