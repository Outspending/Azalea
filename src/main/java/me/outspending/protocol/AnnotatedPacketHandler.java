package me.outspending.protocol;

import com.google.common.base.Charsets;
import me.outspending.GameMode;
import me.outspending.MinecraftServer;
import me.outspending.connection.Connection;
import me.outspending.connection.GameState;
import me.outspending.position.Location;
import me.outspending.protocol.annotations.PacketReceiver;
import me.outspending.protocol.packets.configuration.server.AcknowledgeFinishConfigurationPacket;
import me.outspending.protocol.packets.configuration.client.FinishConfigurationPacket;
import me.outspending.protocol.packets.configuration.client.RegistryDataPacket;
import me.outspending.protocol.packets.handshaking.HandshakePacket;
import me.outspending.protocol.packets.login.server.LoginAcknowledgedPacket;
import me.outspending.protocol.packets.login.server.LoginStartPacket;
import me.outspending.protocol.packets.login.client.LoginSuccessPacket;
import me.outspending.protocol.packets.login.client.SetCompressionPacket;
import me.outspending.protocol.packets.play.client.GameEventPacket;
import me.outspending.protocol.packets.play.client.LoginPlayPacket;
import me.outspending.protocol.packets.status.client.PingRequestPacket;
import me.outspending.protocol.packets.status.client.StatusRequestPacket;
import me.outspending.protocol.packets.status.server.PingResponsePacket;
import me.outspending.protocol.packets.status.server.StatusResponsePacket;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public void handle(@NotNull Connection connection, @NotNull Packet packet) throws InvocationTargetException, IllegalAccessException {
        Method method = PACKET_HANDLERS.get(packet.getClass());
        if (method != null)
            method.invoke(this, connection, packet);
    }

    @PacketReceiver
    public void onHandshake(@NotNull Connection client, @NotNull HandshakePacket packet) {
        client.setState(packet.nextState() == 2 ? GameState.LOGIN : GameState.STATUS);
    }

    @PacketReceiver
    public void onStatusRequest(@NotNull Connection client, @NotNull StatusRequestPacket packet) {
        MinecraftServer server = client.getServer();
        client.sendPacket(new StatusResponsePacket(
                new StatusResponsePacket.Players(0, server.getMaxPlayers()),
                new StatusResponsePacket.Version(MinecraftServer.PROTOCOL, MinecraftServer.VERSION),
                server.getDescription()
        ));
    }

    @PacketReceiver
    public void onPingRequest(@NotNull Connection client, @NotNull PingRequestPacket packet) {
        client.sendPacket(new PingResponsePacket(packet.payload()));
    }

    @PacketReceiver
    public void onLoginStart(@NotNull Connection client, @NotNull LoginStartPacket packet) {
        client.sendPacket(new SetCompressionPacket(-1));
        client.sendPacket(new LoginSuccessPacket(packet.uuid(), packet.name(), new ArrayList<>()));
    }

    @PacketReceiver
    public void onLoginAcknowledged(@NotNull Connection client, @NotNull LoginAcknowledgedPacket packet) {
        client.setState(GameState.CONFIGURATION);

        client.sendPacket(new RegistryDataPacket(client.getServer().REGISTRY_NBT));
        client.sendPacket(new FinishConfigurationPacket());
    }

    @PacketReceiver
    public void onAcknowledgeConfiguration(@NotNull Connection client, @NotNull AcknowledgeFinishConfigurationPacket packet) {
        logger.info("Configuration has finished!");
        client.setState(GameState.PLAY);

        client.sendPacket(new LoginPlayPacket(
                0,
                false,
                1,
                List.of("minecraft:overworld"),
                20,
                10,
                8,
                false,
                false,
                false,
                "minecraft:overworld",
                "overworld",
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
        client.sendPacket(new GameEventPacket((byte) 0,0f));
    }
}
