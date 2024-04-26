package me.outspending.protocol;

import me.outspending.MinecraftServer;
import me.outspending.connection.GameState;
import me.outspending.protocol.packets.configuration.server.AcknowledgeFinishConfigurationPacket;
import me.outspending.protocol.packets.configuration.server.ClientInformationPacket;
import me.outspending.protocol.packets.configuration.server.PluginMessageConfigurationPacket;
import me.outspending.protocol.packets.handshaking.HandshakePacket;
import me.outspending.protocol.packets.login.server.LoginAcknowledgedPacket;
import me.outspending.protocol.packets.login.server.LoginStartPacket;
import me.outspending.protocol.packets.login.client.LoginSuccessPacket;
import me.outspending.protocol.packets.status.client.PingRequestPacket;
import me.outspending.protocol.packets.status.client.StatusRequestPacket;

import java.util.Map;

public class CodecHandler {
    public static final PacketCodec<GameState> CLIENT_CODEC = PacketCodec.builder(GameState.class)
            .protocolVersion(MinecraftServer.PROTOCOL)
            .minecraftVersion("1.20.4")
            .packetType(GameState.HANDSHAKE, Map.of(
                    0x00, HandshakePacket::of
            ))
            .packetType(GameState.STATUS, Map.of(
                    0x00, StatusRequestPacket::of,
                    0x01, PingRequestPacket::of
            ))
            .packetType(GameState.LOGIN, Map.of(
                    0x00, LoginStartPacket::of,
                    0x02, LoginSuccessPacket::of,
                    0x03, LoginAcknowledgedPacket::of
            ))
            .packetType(GameState.CONFIGURATION, Map.of(
                    0x00, ClientInformationPacket::of,
                    0x01, PluginMessageConfigurationPacket::of,
                    0x02, AcknowledgeFinishConfigurationPacket::of
            ))
            .packetType(GameState.PLAY, Map.of(

            ))
            .build();
}
