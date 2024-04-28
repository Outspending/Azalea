package me.outspending.protocol.packets.client.configuration;

import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public class ClientFinishConfigurationPacket extends ClientPacket {
    public static ClientFinishConfigurationPacket of(@NotNull PacketReader reader) {
        return new ClientFinishConfigurationPacket();
    }

    public ClientFinishConfigurationPacket() {
        super(0x02);
    }

    @Override
    public void write(PacketWriter writer) {}
}