package me.outspending.protocol.packets.client.play;

import lombok.Getter;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@Getter
public class ClientKeepAlivePacket extends ClientPacket {
    private final long keepAliveID;

    public static ClientKeepAlivePacket of(@NotNull PacketReader reader) {
        return new ClientKeepAlivePacket(reader.readLong());
    }

    public ClientKeepAlivePacket(long keepAliveID) {
        super(0x24);
        this.keepAliveID = keepAliveID;
    }

    @Override
    public void write(PacketWriter writer) {
        writer.writeLong(keepAliveID);
    }
}
