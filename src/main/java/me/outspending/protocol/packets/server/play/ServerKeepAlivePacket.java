package me.outspending.protocol.packets.server.play;

import lombok.Getter;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

@Getter
public class ServerKeepAlivePacket extends ServerPacket {
    private final long keepAliveID;

    public static ServerKeepAlivePacket of(@NotNull PacketReader reader) {
        return new ServerKeepAlivePacket(reader.readLong());
    }

    public ServerKeepAlivePacket(long keepAliveID) {
        super(0x15);
        this.keepAliveID = keepAliveID;
    }
}
