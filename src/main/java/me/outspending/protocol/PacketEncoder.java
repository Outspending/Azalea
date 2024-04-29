package me.outspending.protocol;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.io.OutputStream;

public record PacketEncoder(@NotNull ClientPacket packet) {

    public void encode(@NotNull OutputStream output) {
        PacketWriter writer = PacketWriter.createNormalWriter(packet);
        writer.writeToStream(output);
    }

}
