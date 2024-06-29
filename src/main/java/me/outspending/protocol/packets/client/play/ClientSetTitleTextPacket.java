package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public record ClientSetTitleTextPacket(@NotNull Component title) implements ClientPacket {
    public ClientSetTitleTextPacket(@NotNull String title) {
        this(Component.text(title));
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeTextComponent(this.title);
    }

    @Override
    public int id() {
        return 0x65;
    }
}
