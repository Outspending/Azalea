package me.outspending;

import me.outspending.events.EventHandler;
import me.outspending.events.EventListener;
import me.outspending.events.event.PlayerJoinEvent;
import me.outspending.player.Player;
import me.outspending.position.Pos;
import me.outspending.world.World;

public class Testing implements EventListener {
    private static final World defaultWorld = World.create("testing");

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();

        player.setWorld(defaultWorld);
        player.setPosition(new Pos(0, 20, 0, 0, 0));
    }
}
