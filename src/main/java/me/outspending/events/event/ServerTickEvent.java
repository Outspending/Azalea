package me.outspending.events.event;

import me.outspending.events.types.Event;

public record ServerTickEvent(long tickTime) implements Event {
}
