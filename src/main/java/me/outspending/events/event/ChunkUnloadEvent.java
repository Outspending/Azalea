package me.outspending.events.event;

import me.outspending.chunk.Chunk;
import me.outspending.events.types.Event;

public record ChunkUnloadEvent(Chunk chunk) implements Event {
}
