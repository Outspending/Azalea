package me.outspending.protocol.packets.client.login;

import me.outspending.player.GameProfile;
import me.outspending.player.Property;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientLoginSuccessPacket(@NotNull GameProfile profile) implements ClientPacket {

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
