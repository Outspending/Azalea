package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public record ClientPlayDisconnectPacket(@NotNull Component component) implements ClientPacket {
    public ClientPlayDisconnectPacket(@NotNull String message) {
        this(Component.text(message));
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeTextComponent(component);
    }

    @Override
    public int id() {
        return 0x1E;
    }

}
