package me.outspending.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.outspending.chunk.Chunk;
import me.outspending.connection.ClientConnection;
import me.outspending.position.Pos;
import me.outspending.world.World;
import net.kyori.adventure.text.Component;

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

    public Player(ClientConnection connection, String username, UUID uuid) {
        this.connection = connection;
        this.entityID = EntityCounter.getNextEntityID();
        this.username = username;
        this.uuid = uuid;
    }

    public Player(ClientConnection connection, GameProfile profile) {
        this(connection, profile.getUsername(), profile.getUuid());
    }

    public void setWorld(World world) {
        this.world = world;
        world.addEntity(this);
    }

    public void kick(String reason) {
        connection.kick(reason);
    }

    public void kick(Component component) {
        connection.kick(component);
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
