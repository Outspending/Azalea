package me.outspending.protocol.packets.client.play;

import lombok.Getter;
import me.outspending.position.Pos;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;

@Getter
public class ClientSynchronizePlayerPosition extends ClientPacket {
    private final Pos position;
    private final byte flags;
    private final int teleportID;

    public static ClientSynchronizePlayerPosition of(PacketReader reader) {
        return new ClientSynchronizePlayerPosition(
                new Pos(
                        reader.readDouble(),
                        reader.readDouble(),
                        reader.readDouble(),
                        reader.readFloat(),
                        reader.readFloat()
                ),
                reader.readByte(),
                reader.readVarInt()
        );
    }

    public ClientSynchronizePlayerPosition(Pos position, byte flags, int teleportID) {
        super(0x3E);
        this.position = position;
        this.flags = flags;
        this.teleportID = teleportID;
    }

    @Override
    public void write(PacketWriter writer) {
        writer.writeDouble(this.position.x());
        writer.writeDouble(this.position.y());
        writer.writeDouble(this.position.z());
        writer.writeFloat(this.position.yaw());
        writer.writeFloat(this.position.pitch());
        writer.writeByte(this.flags);
        writer.writeVarInt(this.teleportID);
    }
}
