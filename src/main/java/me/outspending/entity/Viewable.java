package me.outspending.entity;

import me.outspending.player.Player;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.types.GroupedPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Viewable {

    void addViewer(@NotNull Entity entity);

    void removeViewer(@NotNull Entity entity);

    @NotNull List<Entity> getViewers();

    default @NotNull List<Player> getPlayerViewers() {
        return getViewers().stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .toList();
    }

    void updateViewers();

    default boolean isViewer(@NotNull Entity entity) {
        return getViewers().contains(entity);
    }

    default void sendPacketsToViewers(@NotNull ClientPacket... packets) {
        getPlayerViewers().forEach(player -> player.sendBundledPackets(packets));
    }

}
