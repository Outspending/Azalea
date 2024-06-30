package me.outspending;

import me.outspending.block.BlockType;
import me.outspending.entity.Entity;
import me.outspending.events.EventHandler;
import me.outspending.events.EventListener;
import me.outspending.events.event.EntityWorldAddEvent;
import me.outspending.events.event.PlayerJoinEvent;
import me.outspending.generation.WorldGenerator;
import me.outspending.player.Player;
import me.outspending.position.Pos;
import me.outspending.world.World;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;

import java.time.Duration;

public class Testing implements EventListener {
    private static final World defaultWorld = World.builder("testing")
            .generator(WorldGenerator.create(chunkGenerator -> chunkGenerator.fillSection(4, BlockType.GRASS_BLOCK)))
            .build();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();

        player.setWorld(defaultWorld);
        player.setPosition(new Pos(0, 100, 0, 0, 0));
    }

    @EventHandler
    public void onEntityAdd(EntityWorldAddEvent e) {
        final Entity entity = e.getEntity();
        if (entity instanceof Player player) {
            player.sendTitle(
                    Title.title(
                            Component.text("wowie"),
                            Component.text("Is this a... subtitle?"),
                            Title.Times.times(
                                    Duration.ofSeconds(1),
                                    Duration.ofSeconds(3),
                                    Duration.ofSeconds(1)
                            )
                    )
            );
        }
    }

}
