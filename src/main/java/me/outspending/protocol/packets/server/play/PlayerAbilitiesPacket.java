package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

@Getter
public class PlayerAbilitiesPacket extends ServerPacket {
    private final byte flags;

    public static PlayerAbilitiesPacket of(@NotNull PacketReader reader) {
        return new PlayerAbilitiesPacket(reader.readByte());
    }

    public PlayerAbilitiesPacket(byte flags) {
        super(0x20);
        this.flags = flags;
    }
}
