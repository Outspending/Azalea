package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public record ClientPlayDisconnectPacket(Component component) implements ClientPacket {
    public static ClientPlayDisconnectPacket of(@NotNull PacketReader reader) {
        return new ClientPlayDisconnectPacket(reader.readTextComponent());
    }

    public ClientPlayDisconnectPacket(String message) {
        this(Component.text(message));
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeTextComponent(component);
    }

    @Override
    public int id() {
        return 0x1B;
    }
}
