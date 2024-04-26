package me.outspending;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.outspending.utils.ResourceUtils;
import net.kyori.adventure.nbt.BinaryTagIO;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.TagStringIO;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.URL;

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

    public CompoundBinaryTag REGISTRY_NBT;

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
            server.loadRegistry();

            return server;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void loadRegistry() {
        try (InputStream inputStream = ResourceUtils.getResourceAsStream("/networkCodec.nbt")) {
            if (inputStream == null) return;

            InputStream stream = ResourceUtils.getResourceAsStream("/networkCodec.nbt");
            REGISTRY_NBT = BinaryTagIO.reader().read(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        serverConnection.start();
    }
}
