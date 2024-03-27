package me.outspending.protocol;

import me.nullicorn.nedit.type.NBTCompound;
import me.outspending.MinecraftServer;
import me.outspending.connection.Connection;
import me.outspending.protocol.annotations.PacketReceiver;
import me.outspending.protocol.packets.status.client.StatusRequestPacket;
import me.outspending.protocol.packets.configuration.client.AcknowledgeFinishConfigurationPacket;
import me.outspending.protocol.packets.login.client.LoginAcknowledgedPacket;
import me.outspending.protocol.packets.login.client.LoginStartPacket;
import me.outspending.protocol.packets.login.client.LoginSuccessPacket;
import me.outspending.connection.GameState;
import me.outspending.protocol.packets.handshaking.HandshakePacket;
import me.outspending.protocol.packets.status.server.StatusResponsePacket;
import me.outspending.protocol.packets.configuration.server.FinishConfigurationPacket;
import me.outspending.protocol.packets.configuration.server.RegistryDataPacket;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class AnnotatedPacketHandler {
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
    public void onLoginStart(@NotNull Connection client, @NotNull LoginStartPacket packet) {
        client.sendPacket(new LoginSuccessPacket(packet.uuid(), packet.name(), new ArrayList<>()));
    }

    @PacketReceiver
    public void onLoginAcknowledged(@NotNull Connection client, @NotNull LoginAcknowledgedPacket packet) {
        client.setState(GameState.CONFIGURATION);

//        JsonObject registryJson = ResourceUtils.getResourceJson("/registry_data.json");
//        NBTCompound compound = NBTJson.parse(registryJson);
//
//        System.out.println(compound);
        client.sendPacket(new RegistryDataPacket(new NBTCompound()));
        client.sendPacket(new FinishConfigurationPacket());
    }

    @PacketReceiver
    public void onConfigurationFinished(@NotNull Connection client, @NotNull AcknowledgeFinishConfigurationPacket packet) {
        client.setState(GameState.PLAY);
        System.out.println("Configuration has finished!");
    }
}
