package me.outspending.protocol.packets.client.login;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public record ClientLoginDisconnectPacket(@NotNull Component component) implements ClientPacket {
    public ClientLoginDisconnectPacket(@NotNull String message) {
        this(Component.text(message));
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeTextComponent(this.component);
    }

    @Override
    public int id() {
        return 0x00;
    }

}
