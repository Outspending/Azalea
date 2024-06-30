package me.outspending.listeners;

import com.google.common.base.Preconditions;
import me.outspending.connection.ClientConnection;
import me.outspending.player.Player;
import me.outspending.position.Pos;
import me.outspending.protocol.packets.server.play.PlayerRotationPacket;
import me.outspending.protocol.packets.server.play.SetPlayerPositionAndRotationPacket;
import me.outspending.protocol.packets.server.play.SetPlayerPositionPacket;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MovementPacketListener {

    private static final Logger log = LoggerFactory.getLogger(MovementPacketListener.class);

    @Contract("_, null -> fail")
    public static void handlePacket(@NotNull ClientConnection connection, @UnknownNullability ServerPacket packet) {
        Preconditions.checkNotNull(packet, "packet cannot be null");

        final Player recievedPlayer = connection.getPlayer();
        switch (packet) {
            case SetPlayerPositionPacket positionPacket -> handlePosition(recievedPlayer, positionPacket);
            case SetPlayerPositionAndRotationPacket positionAndRotationPacket ->
                    handlePositionAndRotation(recievedPlayer, positionAndRotationPacket);
            case PlayerRotationPacket rotationPacket -> handleRotation(recievedPlayer, rotationPacket);
            default -> throw new IllegalArgumentException("Unknown packet type: " + packet.getClass().getSimpleName());
        }

        recievedPlayer.handleMovement();
    }

    private static void handlePosition(@NotNull Player player, @NotNull SetPlayerPositionPacket packet) {
        final Pos playerPos = player.getPosition();
        final Pos packetPos = packet.position();

        player.setLastPosition(playerPos);
        player.setPosition(new Pos(packetPos.x(), packetPos.y(), packetPos.z(), playerPos.yaw(), playerPos.pitch()));
    }

    private static void handleRotation(@NotNull Player player, @NotNull PlayerRotationPacket packet) {
        player.setRotation(packet.yaw(), packet.pitch());
    }

    private static void handlePositionAndRotation(@NotNull Player player, @NotNull SetPlayerPositionAndRotationPacket packet) {
        player.setLastPosition(player.getPosition());
        player.setPosition(packet.position());
    }

}
