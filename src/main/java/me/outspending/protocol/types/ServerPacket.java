package me.outspending.protocol.types;

import me.outspending.connection.ClientConnection;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface ServerPacket extends Packet {
    @Contract(pure = true)
    @NotNull ClientConnection getSendingConnection();
}
