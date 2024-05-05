package me.outspending.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.outspending.chunk.Chunk;
import me.outspending.connection.ClientConnection;
import me.outspending.position.Pos;
import me.outspending.world.World;

import java.util.List;
import java.util.UUID;

@Getter @Setter(AccessLevel.MODULE)
public class Player implements TickingEntity {
    private final ClientConnection connection;

    private final int entityID;
    private final String username;
    private final UUID uuid;

    private World world;
    private Pos position;

    public Player(ClientConnection connection, int entityID, String username, UUID uuid) {
        this.connection = connection;
        this.entityID = entityID;
        this.username = username;
        this.uuid = uuid;
    }

    public void setWorld(World world) {
        this.world = world;
        world.addEntity(this);
    }

    @Override
    public List<Chunk> getChunksInDistance(int distance) {
        return List.of();
    }

    @Override
    public List<Chunk> getLoadedChunks() {
        return List.of();
    }

    @Override
    public void tick(long time) {
    }
}
