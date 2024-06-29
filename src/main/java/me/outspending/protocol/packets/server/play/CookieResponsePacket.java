package me.outspending.protocol.packets.server.play;

import me.outspending.NamespacedID;
import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record CookieResponsePacket(@NotNull ClientConnection connection, @NotNull NamespacedID key, boolean hasPayload, byte @NotNull [] data) implements ServerPacket {
    public static CookieResponsePacket read(@NotNull ClientConnection connection, @NotNull PacketReader reader) {
        NamespacedID key = reader.readNamespacedID();
        boolean hasPayload = reader.readBoolean();
        byte[] data = hasPayload ? reader.readByteArray() : new byte[0];

        return new CookieResponsePacket(connection, key, hasPayload, data);
    }

    @Override
    public @NotNull ClientConnection getSendingConnection() {
        return this.connection;
    }

    @Override
    public int id() {
        return 0x11;
    }
}
