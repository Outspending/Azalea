package me.outspending.protocol.packets.client.play;

import lombok.Getter;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import org.jetbrains.annotations.NotNull;

@Getter
public class ClientGameEventPacket extends ClientPacket {
    private final byte event;
    private final float value;

    public static ClientGameEventPacket of(@NotNull PacketReader reader) {
        return new ClientGameEventPacket(
                reader.readByte(),
                reader.readFloat()
        );
    }

    public ClientGameEventPacket(byte event, float value) {
        super(0x20);
        this.event = event;
        this.value = value;
    }
}
