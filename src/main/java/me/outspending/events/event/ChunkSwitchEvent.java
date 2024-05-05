package me.outspending.events.event;

import lombok.Getter;
import me.outspending.chunk.Chunk;
import me.outspending.entity.Player;
import me.outspending.events.types.PlayerEvent;
import me.outspending.position.Pos;

@Getter
public class ChunkSwitchEvent extends PlayerEvent {
    private final Pos position;
    private final Chunk from;
    private final Chunk to;

    public ChunkSwitchEvent(Player player, Pos position, Chunk from, Chunk to) {
        super(player);

        this.position = position;
        this.from = from;
        this.to = to;
    }
}
