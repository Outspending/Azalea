package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.position.Location;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

@Getter
public class PlayerActionPacket extends ServerPacket {
    private final int status;
    private final Location position;
    private final byte face;
    private final int sequence;

    public static PlayerActionPacket of(@NotNull PacketReader reader) {
        return new PlayerActionPacket(
                reader.readVarInt(),
                reader.readLocation(),
                reader.readByte(),
                reader.readVarInt()
        );
    }

    public PlayerActionPacket(int status, Location position, byte face, int sequence) {
        super(0x21);

        this.status = status;
        this.position = position;
        this.face = face;
        this.sequence = sequence;
    }
}
