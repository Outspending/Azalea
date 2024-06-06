package me.outspending.utils;

import me.outspending.MinecraftServer;
import me.outspending.player.Player;

import java.util.Collection;
import java.util.List;

public class PlayerUtils {
    private static Collection<Player> getAllPlayers() {
        return MinecraftServer.getInstance().getServerProcess().getPlayerCache().getAll();
    }

    public static List<Player> getPlayersWithinDistance(Player player, long distance) {
        return getAllPlayers().stream()
                .filter(loopedPlayer -> player.distanceFrom(loopedPlayer) <= distance)
                .toList();
    }
}
