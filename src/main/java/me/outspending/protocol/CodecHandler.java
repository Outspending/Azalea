package me.outspending.protocol;

import me.outspending.MinecraftServer;
import me.outspending.protocol.packets.status.client.StatusRequestPacket;
import me.outspending.protocol.packets.configuration.client.AcknowledgeFinishConfigurationPacket;
import me.outspending.protocol.packets.login.client.LoginAcknowledgedPacket;
import me.outspending.protocol.packets.login.client.LoginSuccessPacket;
import me.outspending.connection.GameState;
import me.outspending.protocol.packets.handshaking.HandshakePacket;
import me.outspending.protocol.packets.login.client.LoginStartPacket;
import me.outspending.protocol.packets.configuration.server.FinishConfigurationPacket;
import me.outspending.protocol.packets.configuration.server.RegistryDataPacket;

import java.util.Map;

public class CodecHandler {
    public static final PacketCodec<GameState> GAMESTATE_CODEC = PacketCodec.builder(GameState.class)
            .protocolVersion(MinecraftServer.PROTOCOL)
            .minecraftVersion("1.20.4")
            .packetType(GameState.HANDSHAKE, Map.of(
                    0x00, HandshakePacket::of
            ))
            .packetType(GameState.STATUS, Map.of(
                    0x00, StatusRequestPacket::of
            ))
            .packetType(GameState.LOGIN, Map.of(
                    0x00, LoginStartPacket::of,
                    0x02, LoginSuccessPacket::of,
                    0x03, LoginAcknowledgedPacket::of
            ))
            .packetType(GameState.CONFIGURATION, Map.of(
                    0x02, FinishConfigurationPacket::of,
                    0x05, RegistryDataPacket::of
            ))
            .packetType(GameState.PLAY, Map.of(
                    0x02, AcknowledgeFinishConfigurationPacket::of
            ))
            .build();
}
