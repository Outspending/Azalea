package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record PlayerAbilitiesPacket(byte flags) implements ServerPacket {
    public static PlayerAbilitiesPacket read(PacketReader reader) {
        return new PlayerAbilitiesPacket(reader.readByte());
    }

    @Override
    public int id() {
        return 0x20;
    }
}
