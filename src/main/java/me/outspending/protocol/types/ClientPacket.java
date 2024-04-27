package me.outspending.protocol.types;

public abstract class ClientPacket extends Packet {
    public ClientPacket(int id) {
        super(id);
    }
}
