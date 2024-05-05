package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.Slot;
import me.outspending.block.ItemStack;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record SetCreativeModeSlotPacket(short slotNum, ItemStack slot) implements ServerPacket {
    public static SetCreativeModeSlotPacket read(PacketReader reader) {
        return new SetCreativeModeSlotPacket(
                reader.readShort(),
                reader.readSlot()
        );
    }

    @Override
    public int id() {
        return 0x2F;
    }
}
