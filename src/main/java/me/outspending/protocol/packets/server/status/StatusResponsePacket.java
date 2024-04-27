package me.outspending.protocol.packets.server.status;

import com.google.gson.Gson;
import lombok.Getter;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.protocol.writer.PacketWriter;
import me.outspending.utils.AdventureUtils;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

@Getter
public class StatusResponsePacket extends ServerPacket {
    private final Players players;
    private final Version version;
    private final String description;

    public StatusResponsePacket(@NotNull Players players, @NotNull Version version, @NotNull Component description) {
        this(players, version, AdventureUtils.serializeJson(description));
    }

    public StatusResponsePacket(Players players, Version version, String description) {
        super(0x00);
        this.players = players;
        this.version = version;
        this.description = description;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeString(toJson());
    }

    public record Players(int online, int max) {}
    public record Version(int protocol, String name) {}
}
