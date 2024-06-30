package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public record ClientActionBarTextPacket(@NotNull Component text) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeTextComponent(this.text);
    }

    @Override
    public int id() {
        return 0x4C;
    }

}
