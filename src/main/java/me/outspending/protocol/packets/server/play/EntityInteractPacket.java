package me.outspending.protocol.packets.server.play;

import me.outspending.connection.ClientConnection;
import me.outspending.entity.LivingEntity;
import me.outspending.position.Pos;
import me.outspending.protocol.InteractType;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;

public record EntityInteractPacket(int entityID, InteractType type, Pos targetPos, LivingEntity.Hand hand, boolean sneaking) implements ServerPacket {

    public static EntityInteractPacket read(@NotNull PacketReader reader) {
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
        return new EntityInteractPacket(entityID, InteractType.getById(type), new Pos(targetX, targetY, targetZ), LivingEntity.Hand.getById(hand), sneaking);
    }

    @Override
    public int id() {
        return 0x13;
    }

}
