package me.outspending;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.outspending.connection.ClientConnection;
import me.outspending.connection.GameState;
import me.outspending.connection.ServerConnection;
import me.outspending.entity.GameProfile;
import me.outspending.entity.Player;
import me.outspending.entity.Property;
import me.outspending.listeners.MovementPacketListener;
import me.outspending.position.Pos;
import me.outspending.protocol.CompressionType;
import me.outspending.protocol.listener.PacketListener;
import me.outspending.protocol.listener.PacketNode;
import me.outspending.protocol.packets.client.configuration.ClientFinishConfigurationPacket;
import me.outspending.protocol.packets.client.configuration.ClientRegistryDataPacket;
import me.outspending.protocol.packets.client.login.ClientLoginSuccessPacket;
import me.outspending.protocol.packets.client.login.ClientSetCompressionPacket;
import me.outspending.protocol.packets.client.play.ClientEntityAnimationPacket;
import me.outspending.protocol.packets.client.status.ClientPingResponsePacket;
import me.outspending.protocol.packets.client.status.ClientStatusResponsePacket;
import me.outspending.protocol.packets.server.HandshakePacket;
import me.outspending.protocol.packets.server.configuration.AcknowledgeFinishConfigurationPacket;
import me.outspending.protocol.packets.server.login.LoginAcknowledgedPacket;
import me.outspending.protocol.packets.server.login.LoginStartPacket;
import me.outspending.protocol.packets.server.play.PlayerRotationPacket;
import me.outspending.protocol.packets.server.play.SetPlayerPositionAndRotationPacket;
import me.outspending.protocol.packets.server.play.SetPlayerPositionPacket;
import me.outspending.protocol.packets.server.play.SwingArmPacket;
import me.outspending.protocol.packets.server.status.PingRequestPacket;
import me.outspending.protocol.packets.server.status.StatusRequestPacket;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.thread.TickThread;
import me.outspending.utils.ResourceUtils;
import me.outspending.world.World;
import net.kyori.adventure.nbt.BinaryTagIO;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
@RequiredArgsConstructor
public class MinecraftServer {
    private static final Logger logger = LoggerFactory.getLogger(MinecraftServer.class);

    public static MinecraftServer instance;

    public static final int PROTOCOL = 765;
    public static final int COMPRESSION_THRESHOLD = 256;
    public static final int COMPRESSION_LEVEL = Deflater.DEFAULT_COMPRESSION;
    public static final String VERSION = "Testing 1.20.4";

    private final String host;
    private final int port;
    private final ServerConnection serverConnection;
    private final ServerProcess serverProcess;
    private final PacketListener<ServerPacket> packetListener = PacketListener.create(ServerPacket.class);

    private int maxPlayers = 20;
    private String description = "Woah, an MOTD for my mc protocol!";

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
            final MinecraftServer server = new MinecraftServer(
                    address, port,
                    connection, new ServerProcess()
            );

            MinecraftServer.instance = server;
            server.loadRegistry();
            server.loadPacketNodes();

            new TickThread().start();

            return server;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void loadPacketNodes() {
        PacketNode<ServerPacket> node = PacketNode.create(ServerPacket.class)
                .addListener(HandshakePacket.class, packet -> {
                    final ClientConnection connection = packet.getSendingConnection();
                    connection.setState(packet.nextState() == 2 ? GameState.LOGIN : GameState.STATUS);
                })
                .addListener(StatusRequestPacket.class, packet -> {
                    final ClientConnection connection = packet.getSendingConnection();
                    final MinecraftServer server = connection.getServer();
                    connection.sendPacket(new ClientStatusResponsePacket(
                            new ClientStatusResponsePacket.Players(0, server.getMaxPlayers()),
                            new ClientStatusResponsePacket.Version(MinecraftServer.PROTOCOL, MinecraftServer.VERSION),
                            server.getDescription()
                    ));
                })
                .addListener(PingRequestPacket.class, packet -> {
                    final ClientConnection connection = packet.getSendingConnection();
                    connection.sendPacket(new ClientPingResponsePacket(packet.payload()));
                })
                .addListener(LoginStartPacket.class, packet -> {
                    final ClientConnection connection = packet.getSendingConnection();

                    String name = packet.name();
                    UUID uuid = packet.uuid();

                    GameProfile profile = new GameProfile(name, uuid, new Property[0]);
                    connection.setPlayer(new Player(connection, profile));

                    logger.info("Player {} ({}) has joined the server", name, uuid);
                    connection.sendPacket(new ClientSetCompressionPacket(MinecraftServer.COMPRESSION_THRESHOLD));
                    connection.setCompressionType(CompressionType.COMPRESSED);

                    connection.sendPacket(new ClientLoginSuccessPacket(profile));
                })
                .addListener(LoginAcknowledgedPacket.class, packet -> {
                    final ClientConnection connection = packet.getSendingConnection();
                    connection.setState(GameState.CONFIGURATION);

                    connection.sendPacket(new ClientRegistryDataPacket(connection.getServer().REGISTRY_NBT));
                    connection.sendPacket(new ClientFinishConfigurationPacket());
                })
                .addListener(AcknowledgeFinishConfigurationPacket.class, packet -> {
                    final ClientConnection connection = packet.getSendingConnection();
                    connection.getPlayer().handleConfigurationToPlay();
                })
                .addListener(SetPlayerPositionPacket.class, MovementPacketListener::handlePacket)
                .addListener(SetPlayerPositionAndRotationPacket.class, MovementPacketListener::handlePacket)
                .addListener(PlayerRotationPacket.class, MovementPacketListener::handlePacket)
                .addListener(SwingArmPacket.class, packet -> {
                    final Player player = packet.getSendingConnection().getPlayer();
                    packet.getSendingConnection().getPlayer().getViewers().forEach(viewer -> viewer.sendPacket(new ClientEntityAnimationPacket(player.getEntityID(), (byte) 0)));
                });

        packetListener.addNode(node);
    }

    private void loadRegistry() {
        try (InputStream inputStream = ResourceUtils.getResourceAsStream("/networkCodec.nbt")) {
            if (inputStream == null) return;

            InputStream stream = ResourceUtils.getResourceAsStream("/networkCodec.nbt");
            Preconditions.checkNotNull(stream, "Couldn't find networkCodec.nbt");

            REGISTRY_NBT = BinaryTagIO.reader().read(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer(@NotNull String username) {
        return serverProcess.getPlayerManager().getPlayer(username);
    }

    public Player getPlayer(@NotNull UUID uuid) {
        return serverProcess.getPlayerManager().getPlayer(uuid);
    }

    public World getWorld(@NotNull String name) {
        return serverProcess.getWorldManager().getWorld(name);
    }

    public Collection<Player> getAllPlayers() {
        return serverProcess.getPlayerManager().getAllPlayers();
    }

    public Collection<Player> getAllPlayers(Predicate<Player> playerPredicate) {
        return serverProcess.getPlayerManager().getAllPlayers(playerPredicate);
    }

    public void start() {
        serverConnection.start();
    }
}
