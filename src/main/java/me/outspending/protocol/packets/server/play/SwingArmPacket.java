package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

@Getter
public class SwingArmPacket extends ServerPacket {
    private final int hand;

    public static SwingArmPacket of(@NotNull PacketReader reader) {
        return new SwingArmPacket(reader.readVarInt());
    }

    public SwingArmPacket(int hand) {
        super(0x33);
        this.hand = hand;
    }
}
