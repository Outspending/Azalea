package me.outspending.events.event;

import lombok.Getter;
import me.outspending.connection.ClientConnection;
import me.outspending.events.types.CancelableEvent;
import me.outspending.protocol.types.ClientPacket;

@Getter
public class ClientPacketReceivedEvent extends CancelableEvent {
    private final ClientPacket packet;
    private final ClientConnection connection;

    public ClientPacketReceivedEvent(ClientPacket packet, ClientConnection connection) {
        this.packet = packet;
        this.connection = connection;
    }
}
