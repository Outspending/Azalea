package me.outspending.protocol.types;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class GroupedPacket {
    private final List<ClientPacket> packets = new ArrayList<>();

    public GroupedPacket(ClientPacket... packets) {
        this.packets.addAll(List.of(packets));
    }

    public GroupedPacket(List<ClientPacket> packets) {
        this.packets.addAll(packets);
    }

    public void addPacket(ClientPacket packet) {
        packets.add(packet);
    }
}
