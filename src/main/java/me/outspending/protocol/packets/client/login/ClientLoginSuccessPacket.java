package me.outspending.protocol.packets.client.login;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.entity.GameProfile;
import me.outspending.entity.Property;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public record ClientLoginSuccessPacket(GameProfile profile) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeUUID(profile.getUuid());
        writer.writeString(profile.getUsername());

        Property[] properties = profile.getProperties();
        writer.writeVarInt(properties.length);
        for (Property property : properties) {
            property.write(writer);
        }
    }

    @Override
    public int id() {
        return 0x02;
    }

}
