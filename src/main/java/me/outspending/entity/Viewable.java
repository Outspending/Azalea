package me.outspending.entity;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.types.GroupedPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Viewable {

    void addViewer(@NotNull Player player);

    void removeViewer(@NotNull Player player);

    @NotNull List<Player> getViewers();

    void updateViewers();

    default boolean isViewer(@NotNull Player player) {
        return getViewers().contains(player);
    }

    default void sendPacketsToViewers(@NotNull ClientPacket... packets) {
        getViewers().forEach(player -> player.getConnection().sendGroupedPacket(new GroupedPacket(packets)));
    }

    default void sendPacketToViewers(@NotNull ClientPacket packet) {
        getViewers().forEach(player -> player.getConnection().sendPacket(packet));
    }

}
