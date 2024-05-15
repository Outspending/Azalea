package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public record ClientSetTabListHeaderAndFooterPacket(Component header, Component footer) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeTextComponent(header);
        writer.writeTextComponent(footer);
    }

    @Override
    public int id() {
        return 0x6A;
    }

}
