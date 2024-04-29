package me.outspending.entity;

import lombok.Getter;
import me.outspending.connection.ClientConnection;

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
}
