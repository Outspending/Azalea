package me.outspending.connection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.outspending.MinecraftServer;
import me.outspending.protocol.Packet;
import org.jetbrains.annotations.NotNull;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public abstract class Connection {
    public final MinecraftServer server;

    public GameState state = GameState.HANDSHAKE;

    public abstract void sendPacket(@NotNull Packet packet);
}
