package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.Slot;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

@Getter
public class SetCreativeModeSlotPacket extends ServerPacket {
    private final short slotNum;
    private final Slot slot;

    public static SetCreativeModeSlotPacket of(@NotNull PacketReader reader) {
        return new SetCreativeModeSlotPacket(
                reader.readShort(),
                reader.readSlot()
        );
    }

    public SetCreativeModeSlotPacket(short slotNum, Slot slot) {
        super(0x2F);
        this.slotNum = slotNum;
        this.slot = slot;
    }
}
