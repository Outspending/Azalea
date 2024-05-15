package me.outspending.protocol.packets.client.status;

import com.google.gson.Gson;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

public record ClientStatusResponsePacket(@NotNull Players players, @NotNull Version version, @NotNull String description) implements ClientPacket {

    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeString(this.toJson());
    }

    @Override
    public int id() {
        return 0x00;
    }

    public record Players(int online, int max) {}
    public record Version(int protocol, String name) {}

}
