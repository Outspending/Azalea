package me.outspending.events.event;

import lombok.Getter;
import me.outspending.connection.ClientConnection;
import me.outspending.events.types.Event;
import me.outspending.protocol.types.ClientPacket;

public record ClientPacketRecieveEvent(ClientPacket packet, ClientConnection connection) implements Event {
}
