package me.outspending.protocol.packets.server.play;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record PlayerRotationPacket(float yaw, float pitch, boolean onGround) implements ServerPacket {

    public static PlayerRotationPacket read(@NotNull PacketReader reader) {
        return new PlayerRotationPacket(
                reader.readFloat(),
                reader.readFloat(),
                reader.readBoolean()
        );
    }

    @Override
    public int id() {
        return 0x19;
    }

}
