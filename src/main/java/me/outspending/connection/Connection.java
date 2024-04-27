package me.outspending.connection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.outspending.MinecraftServer;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public abstract class Connection {
    public final MinecraftServer server;
    public GameState state;

    public Connection(MinecraftServer server, GameState defaultState) {
        this.server = server;
        this.state = defaultState;
    }
}
