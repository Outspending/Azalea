package me.outspending;

import me.outspending.block.BlockType;
import me.outspending.events.EventHandler;
import me.outspending.events.EventListener;
import me.outspending.events.event.PlayerJoinEvent;
import me.outspending.events.event.PlayerMoveEvent;
import me.outspending.generation.WorldGenerator;
import me.outspending.player.Player;
import me.outspending.position.Pos;
import me.outspending.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Testing implements EventListener {
    private static final World defaultWorld = World.builder("testing")
            .generator(WorldGenerator.create(chunkGenerator -> chunkGenerator.fillSection(4, BlockType.GRASS_BLOCK)))
            .build();
    private static final Logger log = LoggerFactory.getLogger(Testing.class);

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();

        player.setWorld(defaultWorld);
        player.setPosition(new Pos(0, 100, 0, 0, 0));
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        log.info("Player Position: {}", e.getTo());
    }

}
