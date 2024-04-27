package me.outspending.protocol.packets.client.login;

import lombok.Getter;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@Getter
public class ClientSetCompressionPacket extends ClientPacket {
    private final int threshold;

    public static @NotNull ClientSetCompressionPacket of(@NotNull PacketReader reader) {
        return new ClientSetCompressionPacket(reader.readVarInt());
    }

    public ClientSetCompressionPacket(int threshold) {
        super(0x03);
        this.threshold = threshold;
    }

    @Override
    public void write(PacketWriter writer) {
        writer.writeVarInt(this.threshold);
    }
}
