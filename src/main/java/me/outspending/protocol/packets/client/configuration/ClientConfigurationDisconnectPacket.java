package me.outspending.protocol.packets.client.configuration;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public record ClientConfigurationDisconnectPacket(@NotNull Component component) implements ClientPacket {
    public ClientConfigurationDisconnectPacket(@NotNull String message) {
        this(Component.text(message));
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeTextComponent(component);
    }

    @Override
    public int id() {
        return 0x02;
    }

}
