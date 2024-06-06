package me.outspending.utils;

import me.outspending.MinecraftServer;
import me.outspending.player.Player;
import me.outspending.position.Pos;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.world.World;

import java.util.Collection;
import java.util.function.Predicate;

public class PacketUtils {
    public static void sendPacketToAll(ClientPacket packet) {
        final Collection<Player> players = MinecraftServer.getInstance().getAllPlayers();
        players.forEach(player -> player.getConnection().sendPacket(packet));
    }

    public static void sendGroupedPacket(Collection<Player> players, ClientPacket packet) {
        players.forEach(player -> player.getConnection().sendPacket(packet));
    }

    public static void sendGroupedPacket(Collection<Player> players, ClientPacket packet, Predicate<Player> predicate) {
        players.stream()
                .filter(predicate)
                .forEach(player -> player.getConnection().sendPacket(packet));
    }

    public static void sendGroupedPacket(World world, ClientPacket packet, Predicate<Player> predicate) {
        sendGroupedPacket(world.getPlayers(), packet, predicate);
    }

    public static void sendGroupedPacket(ClientPacket packet, Predicate<Player> predicate) {
        Collection<Player> allPlayers = MinecraftServer.getInstance().getServerProcess().getPlayerCache().getAll();
        sendGroupedPacket(allPlayers, packet, predicate);
    }

    public static void sendPacketWithinDistance(ClientPacket packet, Pos position, long distance) {
        sendGroupedPacket(packet, player -> player.distanceFrom(position) <= distance);
    }

public static void sendPacketWithinDistance(ClientPacket packet, Pos position, long distance, Predicate<Player> predicate) {
        sendGroupedPacket(MinecraftServer.getInstance().getAllPlayers(), packet, player -> player.distanceFrom(position) <= distance && predicate.test(player));
    }
}
