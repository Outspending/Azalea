package me.outspending.protocol.types;

import lombok.Getter;

import java.util.List;

@Getter
public final class GroupedPacket {
    private final List<ClientPacket> packets;

    public GroupedPacket(ClientPacket... packets) {
        this.packets = List.of(packets);
    }

    public GroupedPacket(List<ClientPacket> packets) {
        this.packets = packets;
    }

    public void addPacket(ClientPacket packet) {
        packets.add(packet);
    }
}
