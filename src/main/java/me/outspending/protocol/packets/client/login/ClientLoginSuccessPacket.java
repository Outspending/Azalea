package me.outspending.protocol.packets.client.login;

import lombok.Getter;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Getter
public class ClientLoginSuccessPacket extends ClientPacket {
    private final UUID uuid;
    private final String username;
    private final Property[] properties;

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

    public ClientLoginSuccessPacket(UUID uuid, String username, Property[] properties) {
        super(0x02);
        this.uuid = uuid;
        this.username = username;
        this.properties = properties;
    }

    private void writeProperty(@NotNull Property property, @NotNull PacketWriter writer) {
        writer.writeString(property.name());
        writer.writeString(property.value());
        if (property.isSigned()) {
            writer.writeString(property.signature());
        }
    }

    public record Property(@NotNull String name, @NotNull String value, @Nullable String signature) {
        public boolean isSigned() {
            return signature != null;
        }
    }
}
