package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientSetTickingStatePacket(float tickRate, boolean frozen) implements ClientPacket {
    public static ClientSetTickingStatePacket of(@NotNull PacketReader reader) {
        return new ClientSetTickingStatePacket(reader.readFloat(), reader.readBoolean());
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeFloat(tickRate);
        writer.writeBoolean(frozen);
    }

    @Override
    public int id() {
        return 0x6E;
    }
}
