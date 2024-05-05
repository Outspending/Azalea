package me.outspending.protocol.packets.client.configuration;

import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public record ClientConfigurationDisconnectPacket(Component component) implements ClientPacket {
    public static ClientConfigurationDisconnectPacket of(@NotNull PacketReader reader) {
        return new ClientConfigurationDisconnectPacket(reader.readTextComponent());
    }

    public ClientConfigurationDisconnectPacket(String message) {
        this(Component.text(message));
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeTextComponent(component);
    }

    @Override
    public int id() {
        return 0x01;
    }
}
