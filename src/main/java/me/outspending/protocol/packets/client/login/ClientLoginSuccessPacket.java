package me.outspending.protocol.packets.client.login;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.entity.Property;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public record ClientLoginSuccessPacket(UUID uuid, String username, Property[] properties) implements ClientPacket {
    public static @NotNull ClientLoginSuccessPacket of(@NotNull PacketReader reader) {
        return new ClientLoginSuccessPacket(
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
    public void write(PacketWriter writer) {
        writer.writeUUID(this.uuid);
        writer.writeString(this.username);

        writer.writeVarInt(this.properties.length);
        writer.writeArray(this.properties, property -> writeProperty(property, writer));

    }

    @Override
    public int id() {
        return 0x02;
    }
}
