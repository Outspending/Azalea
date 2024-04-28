package me.outspending.processes;

import me.outspending.connection.ClientConnection;
import me.outspending.entity.Player;

import java.util.*;

public class PlayerManager {
    private final Map<UUID, Player> players = new HashMap<>();

    public void addPlayer(Player player) {
        players.put(player.getUuid(), player);
    }

    public Player getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public Collection<Player> getAllPlayers() {
        return players.values();
    }
}
