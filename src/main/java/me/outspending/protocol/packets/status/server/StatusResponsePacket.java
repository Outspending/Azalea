package me.outspending.protocol.packets.status.server;

import com.google.gson.Gson;
import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketWriter;
import me.outspending.utils.AdventureUtils;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public record StatusResponsePacket(@NotNull Players players, @NotNull Version version, @NotNull String description) implements Packet {
    public StatusResponsePacket(@NotNull Players players, @NotNull Version version, @NotNull Component description) {
        this(players, version, AdventureUtils.serializeJson(description));
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeString(toJson());
    }

    @Override
    public int getID() {
        return 0x00;
    }

    public record Players(int online, int max) {}
    public record Version(int protocol, String name) {}
}
