package me.outspending;

import com.github.steveice10.opennbt.NBTIO;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.outspending.utils.ResourceUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

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

    public CompoundTag REGISTRY_NBT;

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

            DataInputStream stream = new DataInputStream(new GZIPInputStream(inputStream));
            REGISTRY_NBT = (CompoundTag) NBTIO.readTag((DataInput) stream);

            System.out.println(REGISTRY_NBT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        serverConnection.start();
    }
}
