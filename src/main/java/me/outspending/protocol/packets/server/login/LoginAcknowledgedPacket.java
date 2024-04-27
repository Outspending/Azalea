package me.outspending.protocol.packets.server.login;

import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;


public class LoginAcknowledgedPacket extends ServerPacket {
    public static @NotNull LoginAcknowledgedPacket of(@NotNull PacketReader reader) {
        return new LoginAcknowledgedPacket();
    }

    public LoginAcknowledgedPacket() {
        super(0x03);
    }

    @Override
    public void write(@NotNull PacketWriter writer) {}
}
