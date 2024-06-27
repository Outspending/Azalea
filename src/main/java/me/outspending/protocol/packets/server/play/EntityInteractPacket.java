package me.outspending.protocol.packets.server.play;

import me.outspending.connection.ClientConnection;
import me.outspending.entity.Entity;
import me.outspending.position.Pos;
import me.outspending.protocol.InteractType;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record EntityInteractPacket(@NotNull ClientConnection connection, int entityID, InteractType type, Pos targetPos, Entity.Hand hand, boolean sneaking) implements ServerPacket {

    public static EntityInteractPacket read(@NotNull ClientConnection connection, @NotNull PacketReader reader) {
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
        return new EntityInteractPacket(connection, entityID, InteractType.getById(type), new Pos(targetX, targetY, targetZ), Entity.Hand.getById(hand), sneaking);
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
