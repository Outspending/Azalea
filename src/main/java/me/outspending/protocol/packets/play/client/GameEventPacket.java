package me.outspending.protocol.packets.play.client;

import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record GameEventPacket(byte event, float value) implements Packet {
    public static GameEventPacket of(@NotNull PacketReader reader) {
        return new GameEventPacket(
                reader.readByte(),
                reader.readFloat()
        );
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.write(this.event);
        writer.writeFloat(this.value);
    }

    @Override
    public int getID() {
        return 0x20;
    }
}
