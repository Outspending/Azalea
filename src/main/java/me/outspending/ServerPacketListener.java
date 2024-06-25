package me.outspending;

import me.outspending.connection.ClientConnection;
import me.outspending.connection.ConnectionState;
import me.outspending.player.GameProfile;
import me.outspending.player.Player;
import me.outspending.player.Property;
import me.outspending.listeners.MovementPacketListener;
import me.outspending.protocol.CompressionType;
import me.outspending.protocol.listener.PacketListenerImpl;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.UUID;

final class ServerPacketListener extends PacketListenerImpl<ServerPacket> {
    private static final Logger logger = LoggerFactory.getLogger(ServerPacketListener.class);

    public ServerPacketListener() {
        handleHandshakePacket();
        handleStatusRequestPacket();
        handlePingRequestPacket();
        handleLoginStartPacket();
        handleLoginAcknowledgedPacket();
        handleAcknowledgeFinishConfigurationPacket();
        handleSwingArmPacket();

        handleMovementPackets();
    }

    private void handleHandshakePacket() {
        super.addListener(HandshakePacket.class, packet -> {
            final ClientConnection connection = packet.getSendingConnection();
            switch (packet.nextState()) {
                case 1 -> connection.setState(ConnectionState.STATUS);
                case 2 -> connection.setState(ConnectionState.LOGIN);
                case 3 -> connection.setState(ConnectionState.TRANSFER);
                default -> {}
            }
        });
    }

    private void handleStatusRequestPacket() {
        super.addListener(StatusRequestPacket.class, packet -> {
            final ClientConnection connection = packet.getSendingConnection();
            final MinecraftServer server = connection.getServer();
            final Collection<Player> players = server.getAllPlayers();
            connection.sendPacket(new ClientStatusResponsePacket(
                    new ClientStatusResponsePacket.Players(players.size(), server.getMaxPlayers()),
                    new ClientStatusResponsePacket.Version(MinecraftServer.PROTOCOL, MinecraftServer.VERSION),
                    server.getDescription()
            ));
        });
    }

    private void handlePingRequestPacket() {
        super.addListener(PingRequestPacket.class, packet -> {
            final ClientConnection connection = packet.getSendingConnection();
            connection.sendPacket(new ClientPingResponsePacket(packet.payload()));
        });
    }

    private void handleLoginStartPacket() {
        super.addListener(LoginStartPacket.class, packet -> {
            final ClientConnection connection = packet.getSendingConnection();

            String name = packet.name();
            UUID uuid = packet.uuid();

            GameProfile profile = new GameProfile(name, uuid, new Property[0]);
            connection.setPlayer(new Player(connection, profile));

            logger.info("Player {} ({}) has joined the server", name, uuid);
            connection.sendPacket(new ClientSetCompressionPacket(MinecraftServer.COMPRESSION_THRESHOLD));
            connection.setCompressionType(CompressionType.COMPRESSED);

            connection.sendPacket(new ClientLoginSuccessPacket(profile));
        });
    }

    private void handleLoginAcknowledgedPacket() {
        super.addListener(LoginAcknowledgedPacket.class, packet -> {
            final ClientConnection connection = packet.getSendingConnection();
            connection.setState(ConnectionState.CONFIGURATION);

            connection.getPlayer().sendRegistryPacket();
            connection.sendPacket(new ClientFinishConfigurationPacket());
        });
    }

    private void handleAcknowledgeFinishConfigurationPacket() {
        super.addListener(AcknowledgeFinishConfigurationPacket.class, packet -> {
            final ClientConnection connection = packet.getSendingConnection();
            connection.getPlayer().handleConfigurationToPlay();
        });
    }

    private void handleMovementPackets() {
        super.addListener(SetPlayerPositionPacket.class, MovementPacketListener::handlePacket);
        super.addListener(SetPlayerPositionAndRotationPacket.class, MovementPacketListener::handlePacket);
        super.addListener(PlayerRotationPacket.class, MovementPacketListener::handlePacket);
    }

    private void handleSwingArmPacket() {
        super.addListener(SwingArmPacket.class, packet -> {
            final Player player = packet.getSendingConnection().getPlayer();
            packet.getSendingConnection().getPlayer().getViewers().forEach(viewer -> viewer.sendPacket(new ClientEntityAnimationPacket(player.getEntityID(), (byte) 0)));
        });
    }
}
