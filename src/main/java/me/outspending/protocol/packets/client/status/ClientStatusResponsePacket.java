package me.outspending.protocol.packets.client.status;

import com.google.gson.Gson;
import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;

public record ClientStatusResponsePacket(Players players, Version version, String description) implements ClientPacket {
    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public void write(PacketWriter writer) {
        writer.writeString(this.toJson());
    }

    @Override
    public int id() {
        return 0x00;
    }

    public record Players(int online, int max) {}
    public record Version(int protocol, String name) {}
}
