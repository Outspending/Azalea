package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

@Getter
public class SetHeldItemPacket extends ServerPacket {
    private final short slot;

    public static SetHeldItemPacket of(@NotNull PacketReader reader) {
        return new SetHeldItemPacket(reader.readShort());
    }

    public SetHeldItemPacket(short slot) {
        super(0x2C);
        this.slot = slot;
    }
}
