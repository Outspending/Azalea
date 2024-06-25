package me.outspending.events.event;

import lombok.Getter;
import me.outspending.events.types.CancelableEvent;
import me.outspending.protocol.types.ServerPacket;

@Getter
public class ServerPacketReceivedEvent extends CancelableEvent {
    private final ServerPacket packet;

    public ServerPacketReceivedEvent(ServerPacket packet) {
        this.packet = packet;
    }
}
