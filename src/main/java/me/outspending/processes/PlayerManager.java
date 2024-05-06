package me.outspending.processes;

import me.outspending.connection.ClientConnection;
import me.outspending.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public final class PlayerManager {
    private final Map<UUID, Player> players = new HashMap<>();

    public void addPlayer(Player player) {
        players.put(player.getUuid(), player);
    }

    public @Nullable Player getPlayer(String username) {
        return players.values().stream()
                .filter(player -> player.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public Player getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public Collection<Player> getAllPlayers(Predicate<Player> playerPredicate) {
        return players.values().stream()
                .filter(playerPredicate)
                .toList();
    }

    public Collection<Player> getAllPlayers() {
        return players.values();
    }
}
