package me.outspending.events.event;

import lombok.Getter;
import me.outspending.player.Player;
import me.outspending.events.types.PlayerEvent;
import net.kyori.adventure.text.Component;

@Getter
public class PlayerDisconnectEvent extends PlayerEvent {
    private final Component reason;

    public PlayerDisconnectEvent(Player player, Component reason) {
        super(player);
        this.reason = reason;
    }
}
