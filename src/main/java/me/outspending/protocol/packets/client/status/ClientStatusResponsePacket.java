package me.outspending.protocol.packets.client.status;

import com.google.gson.Gson;
import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import me.outspending.utils.AdventureUtils;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public record ClientStatusResponsePacket(Players players, Version version, String description) implements ClientPacket {
    public ClientStatusResponsePacket(@NotNull Players players, @NotNull Version version, @NotNull Component description) {
        this(players, version, AdventureUtils.serializeJson(description));
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public void write(PacketWriter writer) {
        writer.writeString(this.toJson());
    }

    @Override
    public @NotNull GameState state() {
        return GameState.STATUS;
    }

    @Override
    public int id() {
        return 0x00;
    }

    public record Players(int online, int max) {}
    public record Version(int protocol, String name) {}
}
