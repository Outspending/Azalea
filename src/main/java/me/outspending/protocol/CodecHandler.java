package me.outspending.protocol;

import me.outspending.MinecraftServer;
import me.outspending.connection.GameState;
import me.outspending.protocol.packets.HandshakePacket;
import me.outspending.protocol.packets.client.login.ClientLoginSuccessPacket;
import me.outspending.protocol.packets.server.play.*;
import me.outspending.protocol.packets.server.status.PingRequestPacket;
import me.outspending.protocol.packets.server.status.StatusRequestPacket;
import me.outspending.protocol.packets.server.configuration.AcknowledgeFinishConfigurationPacket;
import me.outspending.protocol.packets.server.configuration.ClientInformationPacket;
import me.outspending.protocol.packets.server.configuration.PluginMessageConfigurationPacket;
import me.outspending.protocol.packets.server.login.LoginAcknowledgedPacket;
import me.outspending.protocol.packets.server.login.LoginStartPacket;

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
                    0x02, ClientLoginSuccessPacket::of,
                    0x03, LoginAcknowledgedPacket::of
            ))
            .packetType(GameState.CONFIGURATION, Map.of(
                    0x00, ClientInformationPacket::of,
                    0x01, PluginMessageConfigurationPacket::of,
                    0x02, AcknowledgeFinishConfigurationPacket::of
            ))
            .packetType(GameState.PLAY, Map.ofEntries(
                    Map.entry(0x00, ConfirmTeleportPacket::of),
                    Map.entry(0x15, ServerKeepAlivePacket::of),
                    Map.entry(0x17, SetPlayerPositionPacket::of),
                    Map.entry(0x18, SetPlayerPositionAndRotationPacket::of),
                    Map.entry(0x19, PlayerRotationPacket::of),
                    Map.entry(0x20, PlayerAbilitiesPacket::of),
                    Map.entry(0x21, PlayerActionPacket::of),
                    Map.entry(0x22, PlayerCommandPacket::of),
                    Map.entry(0x33, SwingArmPacket::of),
                    Map.entry(0x35, UseItemOnPacket::of),
                    Map.entry(0x2F, SetCreativeModeSlotPacket::of),
                    Map.entry(0x2C, SetHeldItemPacket::of)
            ))
            .build();
}
