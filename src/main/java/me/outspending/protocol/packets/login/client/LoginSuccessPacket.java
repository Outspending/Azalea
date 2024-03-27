package me.outspending.protocol.packets.login.client;

import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public record LoginSuccessPacket(UUID uuid, String username, List<Property> properties) implements Packet {
    public static @NotNull LoginSuccessPacket of(@NotNull PacketReader reader) {
        return new LoginSuccessPacket(
                reader.readUUID(),
                reader.readString(),
                reader.readArray(propertyReader -> new Property(
                        propertyReader.readString(),
                        propertyReader.readString(),
                        propertyReader.readBoolean() ? propertyReader.readString() : null
                ), Property[]::new));
    }

    private void writeProperty(@NotNull Property property, @NotNull PacketWriter writer) {
        writer.writeString(property.name());
        writer.writeString(property.value());
        if (property.isSigned()) {
            writer.writeString(property.signature());
        }
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeUUID(uuid);
        writer.writeString(username);
        writer.writeVarInt(properties.size());
        writer.writeArray(properties, (property) -> writeProperty(property, writer));
    }

    @Override
    public int getID() {
        return 0x02;
    }

    public record Property(@NotNull String name, @NotNull String value, @Nullable String signature) {
        public boolean isSigned() {
            return signature != null;
        }
    }
}
