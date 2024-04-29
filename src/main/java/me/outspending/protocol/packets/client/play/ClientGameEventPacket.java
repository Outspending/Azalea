package me.outspending.protocol.packets.client.play;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientGameEventPacket(byte event, float value) implements ClientPacket {
    public static ClientGameEventPacket of(@NotNull PacketReader reader) {
        return new ClientGameEventPacket(
                reader.readByte(),
                reader.readFloat()
        );
    }

    @Override
    public void write(PacketWriter writer) {
        writer.writeByte(this.event);
        writer.writeFloat(this.value);
    }

    @Override
    public @NotNull GameState state() {
        return GameState.PLAY;
    }

    @Override
    public int id() {
        return 0x20;
    }
}
