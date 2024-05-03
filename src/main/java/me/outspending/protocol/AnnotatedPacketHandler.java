package me.outspending.protocol;

import me.outspending.MinecraftServer;
import me.outspending.NamespacedID;
import me.outspending.chunk.Chunk;
import me.outspending.connection.ClientConnection;
import me.outspending.connection.GameState;
import me.outspending.entity.Player;
import me.outspending.position.Pos;
import me.outspending.protocol.annotations.PacketReceiver;
import me.outspending.protocol.packets.server.HandshakePacket;
import me.outspending.protocol.packets.client.configuration.ClientFinishConfigurationPacket;
import me.outspending.protocol.packets.client.configuration.ClientRegistryDataPacket;
import me.outspending.protocol.packets.client.login.ClientLoginSuccessPacket;
import me.outspending.protocol.packets.client.play.*;
import me.outspending.protocol.packets.server.status.PingRequestPacket;
import me.outspending.protocol.packets.server.status.StatusRequestPacket;
import me.outspending.protocol.packets.server.configuration.AcknowledgeFinishConfigurationPacket;
import me.outspending.protocol.packets.server.login.LoginAcknowledgedPacket;
import me.outspending.protocol.packets.server.login.LoginStartPacket;
import me.outspending.protocol.packets.client.status.ClientPingResponsePacket;
import me.outspending.protocol.packets.client.status.ClientStatusResponsePacket;
import me.outspending.protocol.types.Packet;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public class AnnotatedPacketHandler {
    private static final Logger logger = LoggerFactory.getLogger(AnnotatedPacketHandler.class);
    private static final Map<Class<? extends Packet>, Method> PACKET_HANDLERS = new HashMap<>();

    static {
        for (Method method : AnnotatedPacketHandler.class.getMethods()) {
            if (method.isAnnotationPresent(PacketReceiver.class)) {
                Class<?>[] params = method.getParameterTypes();
                if (params.length == 2) {
                    Class<? extends Packet> packetClass = (Class<? extends Packet>) params[1];
                    PACKET_HANDLERS.put(packetClass, method);
                }
            }
        }
    }

    public void handle(@NotNull ClientConnection connection, @NotNull Packet packet) throws InvocationTargetException, IllegalAccessException {
        Method method = PACKET_HANDLERS.get(packet.getClass());
        if (method != null)
            method.invoke(this, connection, packet);
    }

    @PacketReceiver
    public void onHandshake(@NotNull ClientConnection client, @NotNull HandshakePacket packet) {
        client.setState(packet.nextState() == 2 ? GameState.LOGIN : GameState.STATUS);
    }

    @PacketReceiver
    public void onStatusRequest(@NotNull ClientConnection client, @NotNull StatusRequestPacket packet) {
        MinecraftServer server = client.getServer();
        client.sendPacket(new ClientStatusResponsePacket(
                new ClientStatusResponsePacket.Players(0, server.getMaxPlayers()),
                new ClientStatusResponsePacket.Version(MinecraftServer.PROTOCOL, MinecraftServer.VERSION),
                server.getDescription()
        ));
    }

    @PacketReceiver
    public void onPingRequest(@NotNull ClientConnection client, @NotNull PingRequestPacket packet) {
        client.sendPacket(new ClientPingResponsePacket(packet.payload()));
    }

    @PacketReceiver
    public void onLoginStart(@NotNull ClientConnection client, @NotNull LoginStartPacket packet) {
        MinecraftServer server = client.getServer();
        String name = packet.name();
        UUID uuid = packet.uuid();

        Player connectedPlayer = new Player(client, name, uuid);
        server.getServerProcess().getPlayerManager().addPlayer(connectedPlayer);

        // client.sendPacket(new ClientSetCompressionPacket(MinecraftServer.COMPRESSION_THRESHOLD));
        client.sendPacket(new ClientLoginSuccessPacket(uuid, name, new ClientLoginSuccessPacket.Property[0]));
    }

    @PacketReceiver
    public void onLoginAcknowledged(@NotNull ClientConnection client, @NotNull LoginAcknowledgedPacket packet) {
        client.setState(GameState.CONFIGURATION);

        client.sendPacket(new ClientRegistryDataPacket(client.getServer().REGISTRY_NBT));
        client.sendPacket(new ClientFinishConfigurationPacket());
    }

    @PacketReceiver
    public void onAcknowledgeConfiguration(@NotNull ClientConnection client, @NotNull AcknowledgeFinishConfigurationPacket packet) {
        logger.info("Configuration has finished!");
        client.setState(GameState.PLAY);

        final NamespacedID overworld = new NamespacedID("overworld");
        client.sendPacket(new ClientLoginPlayPacket(
                273, false, 1,
                new NamespacedID[]{overworld}, 20,
                10, 8, false,
                true, false,
                overworld, overworld,
                0L,
                (byte) 1, (byte) -1,
                false, false, false,
                null, null,
                0
        ));
        client.sendPacket(new ClientGameEventPacket((byte) 13, 0f));

        sendChunks(client);
        client.sendPacket(new ClientSynchronizePlayerPosition(new Pos(0, 64, 0, 0f, 0f), (byte) 0, 24));
    }

    private void sendChunks(@NotNull ClientConnection connection) {
        connection.sendPacket(new ClientCenterChunkPacket(0, 0));

        long time = System.currentTimeMillis();
        for (int x = -7; x < 7; x++) {
            for (int z = -7; z < 7; z++) {
                Chunk chunk = new Chunk(x, z);

                connection.sendChunkData(chunk);
            }
        }
        logger.info("Took " + (System.currentTimeMillis() - time) + "ms to send chunks");
    }
}
