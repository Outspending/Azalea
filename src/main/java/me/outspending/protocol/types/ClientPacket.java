package me.outspending.protocol.types;

import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.Contract;

public abstract class ClientPacket extends Packet {
    public ClientPacket(int id) {
        super(id);
    }

    @Contract("null -> fail")
    public abstract void write(PacketWriter writer);
}
