package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.position.Location;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

@Getter
public class UseItemOnPacket extends ServerPacket {
    private final int hand;
    private final Location position;
    private final int face;
    private final float cursorPosX;
    private final float cursorPosY;
    private final float cursorPosZ;
    private final boolean insideBlock;
    private final int sequence;

    public static UseItemOnPacket of(@NotNull PacketReader reader) {
        return new UseItemOnPacket(
                reader.readVarInt(),
                reader.readLocation(),
                reader.readVarInt(),
                reader.readFloat(),
                reader.readFloat(),
                reader.readFloat(),
                reader.readBoolean(),
                reader.readVarInt()
        );
    }

    public UseItemOnPacket(int hand, Location position, int face, float cursorPosX, float cursorPosY, float cursorPosZ, boolean insideBlock, int sequence) {
        super(0x35);
        this.hand = hand;
        this.position = position;
        this.face = face;
        this.cursorPosX = cursorPosX;
        this.cursorPosY = cursorPosY;
        this.cursorPosZ = cursorPosZ;
        this.insideBlock = insideBlock;
        this.sequence = sequence;
    }
}
