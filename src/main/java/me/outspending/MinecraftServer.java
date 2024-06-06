package me.outspending;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.outspending.connection.ServerConnection;
import me.outspending.player.Player;
import me.outspending.protocol.listener.PacketListener;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.thread.TickThread;
import me.outspending.utils.ResourceUtils;
import me.outspending.world.World;
import net.kyori.adventure.nbt.BinaryTagIO;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.zip.Deflater;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class MinecraftServer {
    private static final Logger logger = LoggerFactory.getLogger(MinecraftServer.class);

    public static MinecraftServer instance;

    public static final int PROTOCOL = 765;
    public static final int COMPRESSION_THRESHOLD = 256;
    public static final int COMPRESSION_LEVEL = Deflater.DEFAULT_COMPRESSION;
    public static final String VERSION = "Testing 1.20.4";

    private ServerConnection serverConnection;
    private final ServerProcess serverProcess;
    private final PacketListener<ServerPacket> packetListener = new ServerPacketListener();

    private int maxPlayers = 20;
    private String description = "Woah, an MOTD for my mc protocol!";

    public CompoundBinaryTag REGISTRY_NBT;

    public static @NotNull MinecraftServer getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Server not initialized");
        }

        return instance;
    }

    public static @NotNull MinecraftServer init() {
        final MinecraftServer server = new MinecraftServer(new ServerProcess());

        MinecraftServer.instance = server;
        server.loadRegistry();

        new TickThread().start();

        return server;
    }

    public MinecraftServer(@NotNull ServerProcess process) {
        this.serverProcess = process;
    }

    private void loadRegistry() {
        try (InputStream inputStream = ResourceUtils.getResourceAsStream("/networkCodec.nbt")) {
            Preconditions.checkNotNull(inputStream, "Couldn't find networkCodec.nbt");

            REGISTRY_NBT = BinaryTagIO.reader().read(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer(@NotNull String username) {
        return serverProcess.getPlayerCache().get(username);
    }

    public Player getPlayer(@NotNull UUID uuid) {
        return serverProcess.getPlayerCache().get(uuid);
    }

    public World getWorld(@NotNull String name) {
        return serverProcess.getWorldCache().get(name);
    }

    public Collection<Player> getAllPlayers() {
        return serverProcess.getPlayerCache().getAll();
    }

    public Collection<Player> getAllPlayers(Predicate<Player> playerPredicate) {
        return serverProcess.getPlayerCache().getAll()
                .stream()
                .filter(playerPredicate)
                .toList();
    }

    public boolean isRunning() {
        return getServerConnection().isRunning();
    }

    public void start(@NotNull String host, int port) {
        try {
            this.serverConnection = new ServerConnection(this);
            serverConnection.startTcpListener(host, port);
        } catch (IOException e) {
            logger.error("Unable to start Minecraft Server", e);
        }
    }
}
