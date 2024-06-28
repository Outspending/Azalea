package me.outspending.cache;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import me.outspending.player.Player;
import org.jetbrains.annotations.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

@ApiStatus.NonExtendable
public non-sealed class PlayerCache implements Cache<Player> {
    private final Map<String, Player> nameToPlayer = Maps.newConcurrentMap();
    private final Map<UUID, Player> uuidToPlayer = Maps.newConcurrentMap();

    private void safeAdd(@NotNull Player player) {
        nameToPlayer.put(player.getName(), player);
        uuidToPlayer.put(player.getUuid(), player);
    }

    private void safeRemove(@NotNull Player player) {
        nameToPlayer.remove(player.getName());
        uuidToPlayer.remove(player.getUuid());
    }

    @Override
    public void add(@UnknownNullability Player player) {
        Preconditions.checkNotNull(player, "Player cannot be null!");
        safeAdd(player);
    }

    @Override
    public void remove(@UnknownNullability Player player) {
        Preconditions.checkNotNull(player, "Player cannot be null!");
        safeRemove(player);
    }

    @Override
    public @Nullable Player get(@UnknownNullability String name) {
        if (name != null) {
            return nameToPlayer.get(name);
        }

        return null;
    }

    @Contract("null -> null")
    public @Nullable Player get(@UnknownNullability UUID uuid) {
        if (uuid != null) {
            return uuidToPlayer.get(uuid);
        }

        return null;
    }

    @Override
    public @NotNull Collection<Player> getAll() {
        return uuidToPlayer.values();
    }

}
