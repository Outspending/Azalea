package me.outspending.protocol.packets.login.server;

import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record LoginAcknowledgedPacket() implements Packet {
    public static @NotNull LoginAcknowledgedPacket of(@NotNull PacketReader reader) {
        return new LoginAcknowledgedPacket();
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
    }

    @Override
    public int getID() {
        return 0x03;
    }
}
