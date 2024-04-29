package me.outspending.protocol.packets.client.play;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientCenterChunkPacket(int chunkX, int chunkZ) implements ClientPacket {
    public static ClientCenterChunkPacket of(@NotNull PacketReader reader) {
        return new ClientCenterChunkPacket(
                reader.readVarInt(),
                reader.readVarInt()
        );
    }

    @Override
    public void write(PacketWriter writer) {
        writer.writeVarInt(this.chunkX);
        writer.writeVarInt(this.chunkZ);
    }

    @Override
    public @NotNull GameState state() {
        return GameState.PLAY;
    }

    @Override
    public int id() {
        return 0x52;
    }
}
