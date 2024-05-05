package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record PlayerCommandPacket(int entityID, int actionID, int jumpBoost) implements ServerPacket {
    public static PlayerCommandPacket read(PacketReader reader) {
        return new PlayerCommandPacket(
                reader.readVarInt(),
                reader.readVarInt(),
                reader.readVarInt()
        );
    }

    @Override
    public int id() {
        return 0x22;
    }
}
