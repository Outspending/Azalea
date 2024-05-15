package me.outspending.processes;

import me.outspending.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public final class PlayerManager {
    private final Map<UUID, Player> players = new ConcurrentHashMap<>();

    public void addPlayer(Player player) {
        players.put(player.getUUID(), player);
    }

    public @Nullable Player getPlayer(String username) {
        return players.values().stream()
                .filter(player -> player.getName().equals(username))
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
