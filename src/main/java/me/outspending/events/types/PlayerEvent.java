package me.outspending.events.types;

import lombok.Getter;
import me.outspending.entity.Player;

@Getter
public class PlayerEvent implements Event {
    private final Player player;

    public PlayerEvent(Player player) {
        this.player = player;
    }

}
