package me.outspending.protocol.packets.client.login;

import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public record ClientLoginDisconnectPacket(Component component) implements ClientPacket {
    public static ClientLoginDisconnectPacket of(@NotNull PacketReader reader) {
        return new ClientLoginDisconnectPacket(reader.readJSONTextComponent());
    }

    public ClientLoginDisconnectPacket(String message) {
        this(Component.text(message));
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeJSONTextComponent(component);
    }

    @Override
    public int id() {
        return 0x00;
    }
}
