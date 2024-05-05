package me.outspending.world;

import me.outspending.Tickable;
import me.outspending.chunk.Chunk;
import me.outspending.entity.Entity;
import me.outspending.entity.Player;
import me.outspending.position.Pos;

import java.util.List;
import java.util.function.Predicate;

public interface World extends Tickable {
    static World create(String name) {
        return new WorldImpl(name);
    }

    List<Entity> getAllEntities();

    default List<Entity> getAllEntities(Predicate<Entity> predicate) {
        return getAllEntities().stream()
                .filter(predicate)
                .toList();
    }

    String getName();

    List<Player> getPlayers();

    void addEntity(Entity entity);

    void removeEntity(Entity entity);

    List<Chunk> getLoadedChunks();

    List<Chunk> getChunksInDistance(Pos position, int distance);

    Chunk getChunk(int x, int z);

    default Chunk getChunk(Pos position) {
        return getChunk((int) position.x(), (int) position.z());
    }
}
