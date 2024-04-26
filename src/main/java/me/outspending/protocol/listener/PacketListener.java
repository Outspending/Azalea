package me.outspending.protocol.listener;

import me.outspending.connection.Connection;
import me.outspending.connection.GameState;
import me.outspending.protocol.AnnotatedPacketHandler;
import me.outspending.protocol.CodecHandler;
import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public class PacketListener {
    private static final AnnotatedPacketHandler packetHandler = new AnnotatedPacketHandler();

    public void write(@NotNull Connection connection, @NotNull Packet packet) {
        connection.sendPacket(packet);
    }

    public void read(@NotNull Connection connection, @NotNull PacketReader reader) throws InvocationTargetException, IllegalAccessException {
        int id = reader.getPacketID();
        GameState state = connection.getState();

        System.out.println("Received packet ID: " + id + ", in state: " + state.name());

        Function<PacketReader, Packet> packetFunction = CodecHandler.CLIENT_CODEC.getPacket(state, id);
        if (packetFunction == null) {
            System.out.printf("Unknown packet ID: %d, in state: %s%n", id, state.name());
            return;
        }

        Packet readPacket = packetFunction.apply(reader);
        packetHandler.handle(connection, readPacket);

        read(connection, readPacket);
    }

    public void read(@NotNull Connection connection, @NotNull Packet packet) {
        System.out.println("Handled packet: " + packet);
    }
}
