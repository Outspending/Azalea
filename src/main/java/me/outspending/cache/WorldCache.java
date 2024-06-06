package me.outspending.cache;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import me.outspending.world.World;
import org.jetbrains.annotations.*;

import java.util.Collection;
import java.util.Map;

@ApiStatus.NonExtendable
public non-sealed class WorldCache implements Cache<World> {
    private Map<String, World> cachedWorlds = Maps.newConcurrentMap();

    @Override
    public void add(@UnknownNullability World key) {
        Preconditions.checkNotNull(key, "Player cannot be null!");

        cachedWorlds.put(key.getName(), key);
    }

    @Override
    public void remove(@UnknownNullability World key) {
        Preconditions.checkNotNull(key, "Player cannot be null!");

        cachedWorlds.remove(key.getName());
    }

    @Override
    public @Nullable World get(@UnknownNullability String name) {
        if (name != null) {
            return cachedWorlds.get(name);
        }

        return null;
    }

    @Override
    public @NotNull Collection<World> getAll() {
        return cachedWorlds.values();
    }

}
