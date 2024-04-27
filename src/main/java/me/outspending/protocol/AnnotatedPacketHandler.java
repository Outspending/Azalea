package me.outspending.protocol;

import me.outspending.MinecraftServer;
import me.outspending.connection.ClientConnection;
import me.outspending.connection.GameState;
import me.outspending.position.Location;
import me.outspending.protocol.annotations.PacketReceiver;
import me.outspending.protocol.packets.HandshakePacket;
import me.outspending.protocol.packets.client.configuration.ClientFinishConfigurationPacket;
import me.outspending.protocol.packets.client.configuration.ClientRegistryDataPacket;
import me.outspending.protocol.packets.client.login.ClientLoginSuccessPacket;
import me.outspending.protocol.packets.client.play.ClientGameEventPacket;
import me.outspending.protocol.packets.client.play.ClientLoginPlayPacket;
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
import java.util.HashMap;
import java.util.Map;

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
        client.setState(packet.getNextState() == 2 ? GameState.LOGIN : GameState.STATUS);
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
        client.sendPacket(new ClientPingResponsePacket(packet.getPayload()));
    }

    @PacketReceiver
    public void onLoginStart(@NotNull ClientConnection client, @NotNull LoginStartPacket packet) {
        client.sendPacket(new ClientLoginSuccessPacket(packet.getUuid(), packet.getName(), new ClientLoginSuccessPacket.Property[0]));
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

        client.sendPacket(new ClientLoginPlayPacket(
                273,
                false,
                1,
                new String[]{"minecraft:overworld"},
                20,
                10,
                8,
                false,
                true,
                false,
                "minecraft:overworld",
                "minecraft:overworld",
                0L,
                (byte) 1,
                (byte) -1,
                false,
                false,
                false,
                "",
                Location.ZERO,
                0
        ));
        client.sendPacket(new ClientGameEventPacket((byte) 0,0f));
    }
}
