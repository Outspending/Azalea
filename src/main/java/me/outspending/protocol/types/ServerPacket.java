package me.outspending.protocol.types;

public abstract class ServerPacket extends Packet {
    public ServerPacket(int id) {
        super(id);
    }
}
