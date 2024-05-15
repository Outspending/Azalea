package me.outspending.protocol.packets.server.play;

import me.outspending.connection.ClientConnection;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record EntityInteractPacket(ClientConnection connection, int entityID, int type, float targetX, float targetY, float targetZ, int hand, boolean sneaking) implements ServerPacket {
    public static EntityInteractPacket read(ClientConnection connection, @NotNull PacketReader reader) {
        int entityID = reader.readVarInt();
        int type = reader.readVarInt();

        float targetX = 0;
        float targetY = 0;
        float targetZ = 0;
        int hand = 0;

        if (type == 2) {
            targetX = reader.readFloat();
            targetY = reader.readFloat();
            targetZ = reader.readFloat();
        }

        if (type == 0 || type == 2) {
            hand = reader.readVarInt();
        }

        boolean sneaking = reader.readBoolean();
        return new EntityInteractPacket(connection, entityID, type, targetX, targetY, targetZ, hand, sneaking);
    }

    @Override
    public @NotNull ClientConnection getSendingConnection() {
        return connection;
    }

    @Override
    public int id() {
        return 0x13;
    }
}
