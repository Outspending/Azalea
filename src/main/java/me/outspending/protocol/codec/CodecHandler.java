package me.outspending.protocol.codec;

import me.outspending.MinecraftServer;
import me.outspending.connection.ConnectionState;
import me.outspending.protocol.packets.server.HandshakePacket;
import me.outspending.protocol.packets.server.configuration.AcknowledgeFinishConfigurationPacket;
import me.outspending.protocol.packets.server.configuration.ClientInformationPacket;
import me.outspending.protocol.packets.server.configuration.ConfigurationPongPacket;
import me.outspending.protocol.packets.server.configuration.PluginMessageConfigurationPacket;
import me.outspending.protocol.packets.server.login.LoginAcknowledgedPacket;
import me.outspending.protocol.packets.server.login.LoginStartPacket;
import me.outspending.protocol.packets.server.play.*;
import me.outspending.protocol.packets.server.status.PingRequestPacket;
import me.outspending.protocol.packets.server.status.StatusRequestPacket;

import java.util.Map;

public class CodecHandler {
    public static final PacketCodec<ConnectionState> CLIENT_CODEC = PacketCodec.builder(ConnectionState.class)
            .protocolVersion(MinecraftServer.PROTOCOL)
            .minecraftVersion("1.20.4")
            .packetType(ConnectionState.HANDSHAKE, Map.ofEntries(
                    Map.entry(0x00, HandshakePacket::read)
            ))
            .packetType(ConnectionState.STATUS, Map.ofEntries(
                    Map.entry(0x00, StatusRequestPacket::read),
                    Map.entry(0x01, PingRequestPacket::read)
            ))
            .packetType(ConnectionState.LOGIN, Map.ofEntries(
                    Map.entry(0x00, LoginStartPacket::read),
                    Map.entry(0x03, LoginAcknowledgedPacket::read)
            ))
            .packetType(ConnectionState.CONFIGURATION, Map.ofEntries(
                    Map.entry(0x00, ClientInformationPacket::read),
                    Map.entry(0x02, PluginMessageConfigurationPacket::read),
                    Map.entry(0x03, AcknowledgeFinishConfigurationPacket::read),
                    Map.entry(0x05, ConfigurationPongPacket::read)
            ))
            .packetType(ConnectionState.PLAY, Map.ofEntries(
                    Map.entry(0x38, UseItemOnPacket::read),
                    Map.entry(0x36, SwingArmPacket::read),
                    Map.entry(0x1A, SetPlayerPositionPacket::read),
                    Map.entry(0x1B, SetPlayerPositionAndRotationPacket::read),
                    Map.entry(0x2F, SetHeldItemPacket::read),
                    Map.entry(0x18, ServerKeepAlivePacket::read),
                    Map.entry(0x1C, PlayerRotationPacket::read),
                    Map.entry(0x25, PlayerCommandPacket::read),
                    Map.entry(0x24, PlayerActionPacket::read),
                    Map.entry(0x23, PlayerAbilitiesPacket::read),
                    Map.entry(0x00, ConfirmTeleportPacket::read),
                    Map.entry(0x21, PingRequestPlayPacket::read),
                    Map.entry(0x08, ChunkBatchReceivedPacket::read),
                    Map.entry(0x16, EntityInteractPacket::read)
            ))
            .build();
}
