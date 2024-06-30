package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public record ClientSetSubtitleTextPacket(@NotNull Component text) implements ClientPacket {
    public ClientSetSubtitleTextPacket(@NotNull String text) {
        this(Component.text(text));
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeTextComponent(this.text);
    }

    @Override
    public int id() {
        return 0x63;
    }
}
