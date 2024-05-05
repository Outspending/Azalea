package me.outspending.protocol.packets.client.play;

import me.outspending.entity.Player;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;

public record ClientAddPlayerInfoPacket(Player player) implements ClientPacket {
    @Override
    public void write(PacketWriter writer) {
        writer.writeByte((byte) 0x17);
        writer.writeVarInt(1);
        writer.writeUUID(player.getUuid());

        writer.writeString(player.getUsername());
        writer.writeVarInt(0);
    }

    @Override
    public int id() {
        return 0x3C;
    }
}
