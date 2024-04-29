package me.outspending.protocol.packets.client.play;

import lombok.Getter;
import me.outspending.position.Location;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@Getter
public class ClientBlockUpdatePacket extends ClientPacket {
    private final Location position;
    private final int blockID;

    public static ClientBlockUpdatePacket of(@NotNull PacketReader reader) {
        return new ClientBlockUpdatePacket(
                reader.readLocation(),
                reader.readVarInt()
        );
    }

    public ClientBlockUpdatePacket(Location position, int blockID) {
        super(0x09);

        this.position = position;
        this.blockID = blockID;
    }

    @Override
    public void write(PacketWriter writer) {
        writer.writeLocation(this.position);
        writer.writeVarInt(this.blockID);
    }
}
