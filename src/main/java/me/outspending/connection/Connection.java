package me.outspending.connection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.outspending.MinecraftServer;
import me.outspending.protocol.Packet;
import org.jetbrains.annotations.NotNull;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public abstract class Connection {
    public final MinecraftServer server;
    public GameState state;

    public Connection(MinecraftServer server, GameState defaultState) {
        this.server = server;
        this.state = defaultState;
    }

    public abstract void sendPacket(@NotNull Packet packet);
}
