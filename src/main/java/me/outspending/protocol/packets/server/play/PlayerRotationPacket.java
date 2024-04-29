package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

@Getter
public class PlayerRotationPacket extends ServerPacket {
    private final float yaw;
    private final float pitch;
    private final boolean onGround;

    public static PlayerRotationPacket of(@NotNull PacketReader reader) {
        return new PlayerRotationPacket(
                reader.readFloat(),
                reader.readFloat(),
                reader.readBoolean()
        );
    }

    public PlayerRotationPacket(float yaw, float pitch, boolean onGround) {
        super(0x19);
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }
}
