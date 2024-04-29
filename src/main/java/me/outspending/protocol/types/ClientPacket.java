package me.outspending.protocol.types;

import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.Contract;

public interface ClientPacket extends Packet {
    @Contract("null -> fail")
    void write(PacketWriter writer);
}
