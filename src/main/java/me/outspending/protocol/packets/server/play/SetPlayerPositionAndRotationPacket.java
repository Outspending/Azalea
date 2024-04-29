package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.position.Pos;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

@Getter
public class SetPlayerPositionAndRotationPacket extends ServerPacket {
    private final Pos position;
    private final boolean onGround;

    public static SetPlayerPositionAndRotationPacket of(@NotNull PacketReader reader) {
        return new SetPlayerPositionAndRotationPacket(
                new Pos(
                        reader.readDouble(),
                        reader.readDouble(),
                        reader.readDouble(),
                        reader.readFloat(),
                        reader.readFloat()
                ),
                reader.readBoolean()
        );
    }

    public SetPlayerPositionAndRotationPacket(Pos position, boolean onGround) {
        super(0x18);
        this.position = position;
        this.onGround = onGround;
    }
}
