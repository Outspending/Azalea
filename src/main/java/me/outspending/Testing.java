package me.outspending;

import me.outspending.block.Block;
import me.outspending.block.BlockType;
import me.outspending.entity.Entity;
import me.outspending.events.EventHandler;
import me.outspending.events.EventListener;
import me.outspending.events.event.EntityWorldAddEvent;
import me.outspending.events.event.PlayerJoinEvent;
import me.outspending.events.event.PlayerMoveEvent;
import me.outspending.events.event.ServerTickEvent;
import me.outspending.generation.WorldGenerator;
import me.outspending.player.Player;
import me.outspending.position.Pos;
import me.outspending.world.World;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;

import java.time.Duration;

public class Testing implements EventListener {
    private static final MiniMessage message = MiniMessage.miniMessage();
    private static final Runtime runtime = Runtime.getRuntime();
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
    public void onEntitySpawn(EntityWorldAddEvent e) {
        final Entity entity = e.getEntity();
        if (entity instanceof Player player) {
            player.setPlayerListHeaderAndFooter(
                    message.deserialize("<gradient:#ff0000:#00ff00>Header</gradient>"),
                    message.deserialize("<gradient:#ff0000:#00ff00>Footer</gradient>")
            );
        }
    }

    @EventHandler
    public void onTick(ServerTickEvent e) {
        defaultWorld.getPlayers().forEach(player -> {
            final int usedMemory = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1048576);
            final int maxMemory = (int) (runtime.maxMemory() / 1048576);
            final int freeMemory = (int) (runtime.freeMemory() / 1048576);

            player.sendActionBar(Component.text("Memory: " + usedMemory + "MB/" + maxMemory + "MB (" + freeMemory + "MB free)"));
        });
    }

}
