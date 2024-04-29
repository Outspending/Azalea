package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

@Getter
public class PlayerCommandPacket extends ServerPacket {
    private final int entityID;
    private final int actionID;
    private final int jumpBoost;

    public static PlayerCommandPacket of(@NotNull PacketReader reader) {
        return new PlayerCommandPacket(
                reader.readVarInt(),
                reader.readVarInt(),
                reader.readVarInt()
        );
    }

    public PlayerCommandPacket(int entityID, int actionID, int jumpBoost) {
        super(0x22);
        this.entityID = entityID;
        this.actionID = actionID;
        this.jumpBoost = jumpBoost;
    }
}
