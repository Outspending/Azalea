package me.outspending.protocol.types;

import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.Contract;

public abstract class ServerPacket extends Packet {
    public ServerPacket(int id) {
        super(id);
    }

    @Contract("null -> fail")
    public abstract void write(PacketWriter writer);
}
