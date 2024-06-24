package me.outspending.protocol.packets.client.play;

import me.outspending.attribute.AttributeType;
import me.outspending.entity.Entity;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientUpdateAttributesPacket(@NotNull Entity entity, @NotNull AttributeValue... values) implements ClientPacket {
    public ClientUpdateAttributesPacket(@NotNull Entity entity, @NotNull AttributeType type, double value) {
        this(entity, new AttributeValue(type, value));
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeVarInt(entity.getEntityID());
        writer.writeVarInt(values.length);
        for (AttributeValue value : values) {
            writer.writeVarInt(value.type.getId());
            writer.writeDouble(value.value);
            writer.writeVarInt(0); // Skip Modifiers (Not Implemented)
        }
    }

    @Override
    public int id() {
        return 0x75;
    }

    public record AttributeValue(@NotNull AttributeType type, double value) {}
}
