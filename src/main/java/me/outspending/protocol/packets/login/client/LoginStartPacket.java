package me.outspending.protocol.packets.login.client;

import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record LoginStartPacket(String name, UUID uuid) implements Packet {
    public static LoginStartPacket of(PacketReader reader) {
        return of(
                reader.readString(),
                reader.readUUID()
        );
    }

    public static LoginStartPacket of(String name, UUID uuid) {
        return new LoginStartPacket(name, uuid);
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeString(name);
        writer.writeUUID(uuid);
    }

    @Override
    public int getID() {
        return 0x00;
    }
}
