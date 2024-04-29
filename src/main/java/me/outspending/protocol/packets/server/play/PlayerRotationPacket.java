package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record PlayerRotationPacket(float yaw, float pitch, boolean onGround) implements ServerPacket {
    public static PlayerRotationPacket read(PacketReader reader) {
        return new PlayerRotationPacket(
                reader.readFloat(),
                reader.readFloat(),
                reader.readBoolean()
        );
    }

    @Override
    public @NotNull GameState state() {
        return GameState.PLAY;
    }

    @Override
    public int id() {
        return 0x19;
    }
}
