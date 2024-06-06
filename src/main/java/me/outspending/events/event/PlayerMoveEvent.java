package me.outspending.events.event;

import lombok.Getter;
import me.outspending.player.Player;
import me.outspending.events.types.PlayerEvent;
import me.outspending.position.Pos;

@Getter
public class PlayerMoveEvent extends PlayerEvent {
    private final Pos from;
    private final Pos to;

    public PlayerMoveEvent(Player player, Pos to) {
        super(player);

        this.from = player.getPosition();
        this.to = to;
    }
}
