package me.outspending.protocol;

import me.outspending.protocol.packets.configuration.client.FinishConfigurationPacket;
import me.outspending.protocol.packets.handshaking.HandshakePacket;
import me.outspending.protocol.packets.login.client.LoginSuccessPacket;
import me.outspending.protocol.packets.login.server.LoginStartPacket;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class ProtocolTest {
    private final Collection<Packet> packets = new HashSet<>();

    @Test
    public void test() {
        packets.add(new HandshakePacket(765, "localhost", (short) 25565, 1));
        packets.add(new LoginStartPacket("Outspending", UUID.randomUUID()));
        packets.add(new FinishConfigurationPacket());
    }

    @Test
    public void writer() {
        PacketWriter writer = new PacketWriter();
        for (Packet packet : packets) {
            packet.write(writer);
        }
    }
}
