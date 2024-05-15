package me.outspending.processes;

import lombok.Getter;
import me.outspending.world.World;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public final class WorldManager {
    private final Map<String, World> loadedWorlds = new ConcurrentHashMap<>();

    public void addWorld(World world) {
        loadedWorlds.put(world.getName(), world);
    }

    public void removeWorld(World world) {
        loadedWorlds.remove(world.getName());
    }

    public World getWorld(String name) {
        return loadedWorlds.get(name);
    }
}
