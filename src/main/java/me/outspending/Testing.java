package me.outspending;

import me.outspending.entity.Player;
import me.outspending.events.EventHandler;
import me.outspending.events.EventListener;
import me.outspending.events.event.PlayerJoinEvent;
import me.outspending.position.Pos;
import me.outspending.protocol.listener.PacketNode;
import me.outspending.protocol.packets.client.play.ClientBundleDelimiterPacket;
import me.outspending.protocol.packets.client.play.ClientChunkDataPacket;
import me.outspending.protocol.packets.server.play.SetPlayerPositionAndRotationPacket;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.world.World;

public class Testing implements EventListener {
    private static final World defaultWorld = World.create("testing");

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();

        player.setWorld(defaultWorld);
        player.setPosition(new Pos(0, 64, 0, 0, 0));
    }

}
