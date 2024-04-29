package me.outspending.protocol.packets.client.play;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

public record ClientDisconnectPlayPacket(CompoundBinaryTag reason) implements ClientPacket {
    public static @NotNull ClientDisconnectPlayPacket of(@NotNull PacketReader reader) {
        return new ClientDisconnectPlayPacket(reader.readNBTCompound());
    }

    @Override
    public void write(PacketWriter writer) {
        writer.writeNBTCompound(this.reason);
    }

    @Override
    public @NotNull GameState state() {
        return GameState.PLAY;
    }

    @Override
    public int id() {
        return 0x1B;
    }
}
