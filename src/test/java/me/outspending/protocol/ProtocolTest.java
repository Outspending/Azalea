package me.outspending.protocol;

import me.outspending.protocol.packets.HandshakePacket;
import me.outspending.protocol.packets.client.configuration.ClientFinishConfigurationPacket;
import me.outspending.protocol.packets.server.login.LoginStartPacket;
import me.outspending.protocol.types.Packet;
import me.outspending.protocol.writer.PacketWriter;
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
        packets.add(new ClientFinishConfigurationPacket());
    }

    @Test
    public void writer() {
        PacketWriter writer = new PacketWriter();
        for (Packet packet : packets) {
            packet.write(writer);
        }
    }
}
