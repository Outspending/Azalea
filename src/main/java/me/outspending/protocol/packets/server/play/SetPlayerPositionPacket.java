package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.position.Pos;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

@Getter
public class SetPlayerPositionPacket extends ServerPacket {
    private final Pos position;
    private final boolean isGround;

    public static SetPlayerPositionPacket of(@NotNull PacketReader reader) {
        return new SetPlayerPositionPacket(
                new Pos(
                        reader.readDouble(),
                        reader.readDouble(),
                        reader.readDouble(),
                        0f, 0f
                ),
                reader.readBoolean()
        );
    }

    public SetPlayerPositionPacket(Pos position, boolean isGround) {
        super(0x17);
        this.position = position;
        this.isGround = isGround;
    }
}
