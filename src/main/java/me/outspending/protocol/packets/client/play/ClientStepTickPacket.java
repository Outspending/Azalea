package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientStepTickPacket(int tickSteps) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(tickSteps);
    }

    @Override
    public int id() {
        return 0x6F;
    }

}
