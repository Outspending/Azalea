package me.outspending.events.event;

import me.outspending.player.Player;
import me.outspending.events.types.PlayerEvent;

public class PlayerJoinEvent extends PlayerEvent {
    public PlayerJoinEvent(Player player) {
        super(player);
    }
}
