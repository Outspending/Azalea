package me.outspending;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@RequiredArgsConstructor
public class MinecraftServer {

    public static MinecraftServer instance;
    public static final int PROTOCOL = 765;
    public static final String VERSION = "Testing 1.20.4";

    private final String host;
    private final int port;
    private final ServerConnection serverConnection;

    private int maxPlayers = 20;
    private String description = "Woah, an MOTD for my custom mc protocol!";

    public static @NotNull MinecraftServer getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Server not initialized");
        }

        return instance;
    }

    public static @Nullable MinecraftServer init(@NotNull String address, int port) {
        try {
            final ServerConnection connection = new ServerConnection(address, port);
            final MinecraftServer server = new MinecraftServer(address, port, connection);

            MinecraftServer.instance = server;
            return server;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void start() {
        serverConnection.start();
    }
}
