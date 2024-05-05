package me.outspending.events.event;

import lombok.Getter;
import me.outspending.chunk.Chunk;
import me.outspending.events.types.Event;
import me.outspending.world.World;

public record ChunkLoadEvent(Chunk chunk) implements Event {
}
