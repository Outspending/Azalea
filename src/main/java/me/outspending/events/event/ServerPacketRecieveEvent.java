package me.outspending.events.event;

import me.outspending.events.types.Event;
import me.outspending.protocol.types.ServerPacket;

public record ServerPacketRecieveEvent(ServerPacket packet) implements Event {
}
