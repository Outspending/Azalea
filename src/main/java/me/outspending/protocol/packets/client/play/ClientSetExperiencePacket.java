package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public record ClientSetExperiencePacket(@Range(from = 0, to = 1) float experienceBar, int level, int totalExperience) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeFloat(experienceBar);
        writer.writeVarInt(level);
        writer.writeVarInt(totalExperience);
    }

    @Override
    public int id() {
        return 0x5C;
    }

}
