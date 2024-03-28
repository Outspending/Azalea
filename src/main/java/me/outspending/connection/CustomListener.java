package me.outspending.connection;

import me.outspending.protocol.Packet;
import me.outspending.protocol.listener.PacketListener;
import me.outspending.protocol.packets.handshaking.HandshakePacket;
import org.jetbrains.annotations.NotNull;

public class CustomListener extends PacketListener {
    @Override
    public void read(@NotNull Connection connection, @NotNull Packet packet) {
        if (packet instanceof HandshakePacket) {
            System.out.println("Handshake packet received");
        } else {
            System.out.println(packet);
        }

        super.read(connection, packet);
    }
}
