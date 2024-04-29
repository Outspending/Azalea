package me.outspending.protocol.packets.server.login;

import lombok.Getter;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
public class LoginStartPacket extends ServerPacket {
    private final String name;
    private final UUID uuid;

    public static LoginStartPacket of(PacketReader reader) {
        return new LoginStartPacket(
                reader.readString(),
                reader.readUUID()
        );
    }

    public LoginStartPacket(String name, UUID uuid) {
        super(0x00);
        this.name = name;
        this.uuid = uuid;
    }
}
