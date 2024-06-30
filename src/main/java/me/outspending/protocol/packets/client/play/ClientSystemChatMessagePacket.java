package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public record ClientSystemChatMessagePacket(Component message, boolean overlay) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeTextComponent(this.message);
        writer.writeBoolean(this.overlay);
    }

    @Override
    public int id() {
        return 0x6C;
    }

}
