package me.outspending.protocol;

import me.outspending.MinecraftServer;
import me.outspending.NamespacedID;
import me.outspending.chunk.Chunk;
import me.outspending.chunk.ChunkMap;
import me.outspending.connection.ClientConnection;
import me.outspending.connection.GameState;
import me.outspending.entity.GameProfile;
import me.outspending.entity.Player;
import me.outspending.entity.Property;
import me.outspending.events.EventExecutor;
import me.outspending.events.event.ChunkSwitchEvent;
import me.outspending.events.event.PlayerJoinEvent;
import me.outspending.events.event.PlayerMoveEvent;
import me.outspending.events.types.Event;
import me.outspending.position.Pos;
import me.outspending.protocol.annotations.PacketReceiver;
import me.outspending.protocol.packets.client.configuration.ClientFinishConfigurationPacket;
import me.outspending.protocol.packets.client.configuration.ClientRegistryDataPacket;
import me.outspending.protocol.packets.client.login.ClientLoginSuccessPacket;
import me.outspending.protocol.packets.client.play.*;
import me.outspending.protocol.packets.client.status.ClientPingResponsePacket;
import me.outspending.protocol.packets.client.status.ClientStatusResponsePacket;
import me.outspending.protocol.packets.server.HandshakePacket;
import me.outspending.protocol.packets.server.configuration.AcknowledgeFinishConfigurationPacket;
import me.outspending.protocol.packets.server.login.LoginAcknowledgedPacket;
import me.outspending.protocol.packets.server.login.LoginStartPacket;
import me.outspending.protocol.packets.server.play.SetPlayerPositionAndRotationPacket;
import me.outspending.protocol.packets.server.play.SetPlayerPositionPacket;
import me.outspending.protocol.packets.server.status.PingRequestPacket;
import me.outspending.protocol.packets.server.status.StatusRequestPacket;
import me.outspending.protocol.types.Packet;
import me.outspending.world.World;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PacketHandler {
    private static final Logger logger = LoggerFactory.getLogger(PacketHandler.class);
    private static final Map<Class<? extends Packet>, Method> PACKET_HANDLERS = new HashMap<>();

    private Player loadedPlayer;

    static {
        for (Method method : PacketHandler.class.getMethods()) {
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
        logger.info("Sending status response..");
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
        String name = packet.name();
        UUID uuid = packet.uuid();

        loadedPlayer = new Player(client, name, uuid);

        // client.sendPacket(new ClientSetCompressionPacket(MinecraftServer.COMPRESSION_THRESHOLD));
        client.sendPacket(new ClientLoginSuccessPacket(uuid, name, new Property[0]));
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

        client.getServer().getServerProcess().getPlayerManager().addPlayer(loadedPlayer);
        EventExecutor.emitEvent(new PlayerJoinEvent(loadedPlayer));

        if (loadedPlayer.getWorld() == null) {
            logger.error("Player's world is null, cannot spawn in. Make sure to set the world on PlayerJoinEvent!");
            loadedPlayer.kick("Failed to join world");
            return;
        }

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
        client.sendPacket(new ClientSynchronizePlayerPosition(new Pos(0, 64, 0, 0f, 0f), (byte) 0, 24));
        client.sendPacket(new ClientGameEventPacket((byte) 13, 0f));

        client.sendPacket(new ClientSetTickingStatePacket(20, false));
        client.sendPacket(new ClientStepTickPacket(0));

        sendChunks(client);
    }

    @PacketReceiver
    public void onPlayerMove(@NotNull ClientConnection connection, @NotNull SetPlayerPositionPacket packet) {
        handleMove(packet.position(), loadedPlayer.getPosition());
    }

    @PacketReceiver
    public void onPlayerMoveAndRotation(@NotNull ClientConnection connection, @NotNull SetPlayerPositionAndRotationPacket packet) {
        handleMove(packet.position(), loadedPlayer.getPosition());
    }

    private void handleMove(Pos to, Pos from) {
        final World world = loadedPlayer.getWorld();

        EventExecutor.emitEvent(new PlayerMoveEvent(loadedPlayer, to));
        loadedPlayer.setPosition(to);

        // Check if the player is moving within chunks
        Chunk fromChunk = world.getChunk(from);
        Chunk toChunk = world.getChunk(to);
        if (!fromChunk.equals(toChunk)) {
            EventExecutor.emitEvent(new ChunkSwitchEvent(loadedPlayer, to, fromChunk, toChunk));
        }
    }

    private void sendChunks(@NotNull ClientConnection connection) {
        connection.sendPacket(new ClientCenterChunkPacket(0, 0));

        long start = System.currentTimeMillis();
        World world = loadedPlayer.getWorld();
        for (int x = -14; x < 14; x++) {
            for (int z = -14; z < 14; z++) {
                Chunk chunk = world.getChunk(x, z);
                chunk.getSections()[5].fill(1);

                connection.sendChunkData(chunk);
            }
        }

        logger.info("Finished sending {} chunks in: {}MS", 28*28, System.currentTimeMillis() - start);
    }
}
