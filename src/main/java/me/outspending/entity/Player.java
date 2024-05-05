package me.outspending.entity;

import lombok.Getter;
import me.outspending.chunk.Chunk;
import me.outspending.connection.ClientConnection;

import java.util.List;
import java.util.UUID;

@Getter
public class Player implements Entity {
    private final ClientConnection connection;
    private final String username;
    private final UUID uuid;

    public Player(ClientConnection connection, String username, UUID uuid) {
        this.connection = connection;
        this.username = username;
        this.uuid = uuid;
    }

    @Override
    public List<Chunk> getChunksInDistance(int distance) {
        return List.of();
    }

    @Override
    public List<Chunk> getLoadedChunks() {
        return List.of();
    }
}
