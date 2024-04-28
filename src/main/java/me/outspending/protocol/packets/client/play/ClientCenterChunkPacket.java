package me.outspending.protocol.packets.client.play;

import lombok.Getter;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@Getter
public class ClientCenterChunkPacket extends ClientPacket {
    private final int chunkX;
    private final int chunkZ;

    public static ClientCenterChunkPacket of(@NotNull PacketReader reader) {
        return new ClientCenterChunkPacket(
                reader.readVarInt(),
                reader.readVarInt()
        );
    }

    public ClientCenterChunkPacket(int chunkX, int chunkZ) {
        super(0x52);

        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    @Override
    public void write(PacketWriter writer) {
        writer.writeVarInt(this.chunkX);
        writer.writeVarInt(this.chunkZ);
    }
}
