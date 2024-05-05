package me.outspending.processes;

import lombok.Getter;
import me.outspending.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public final class WorldManager {
    private final Map<String, World> loadedWorlds = new HashMap<>();

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