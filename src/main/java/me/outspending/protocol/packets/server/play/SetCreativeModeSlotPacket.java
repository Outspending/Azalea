package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.Slot;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record SetCreativeModeSlotPacket(short slotNum, Slot slot) implements ServerPacket {
    public static SetCreativeModeSlotPacket read(PacketReader reader) {
        return new SetCreativeModeSlotPacket(
                reader.readShort(),
                reader.readSlot()
        );
    }

    @Override
    public @NotNull GameState state() {
        return GameState.PLAY;
    }

    @Override
    public int id() {
        return 0x2F;
    }
}
