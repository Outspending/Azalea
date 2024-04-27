package me.outspending.protocol.packets.client.status;

import com.google.gson.Gson;
import lombok.Getter;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import me.outspending.utils.AdventureUtils;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

@Getter
public class ClientStatusResponsePacket extends ClientPacket {
    private final Players players;
    private final Version version;
    private final String description;

    public ClientStatusResponsePacket(@NotNull Players players, @NotNull Version version, @NotNull Component description) {
        this(players, version, AdventureUtils.serializeJson(description));
    }

    public ClientStatusResponsePacket(Players players, Version version, String description) {
        super(0x00);
        this.players = players;
        this.version = version;
        this.description = description;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public void write(PacketWriter writer) {
        writer.writeString(this.toJson());
    }

    public record Players(int online, int max) {}
    public record Version(int protocol, String name) {}
}
