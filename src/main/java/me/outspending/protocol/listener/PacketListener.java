package me.outspending.protocol.listener;

import lombok.AccessLevel;
import lombok.Getter;
import me.outspending.connection.Connection;
import me.outspending.protocol.AnnotatedPacketHandler;
import me.outspending.protocol.CodecHandler;
import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.connection.GameState;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

@Getter(AccessLevel.PUBLIC)
public class PacketListener {
    private static final AnnotatedPacketHandler packetHandler = new AnnotatedPacketHandler();

    public void write(@NotNull Connection connection, @NotNull Packet packet) {
        connection.sendPacket(packet);
    }

    public void read(@NotNull Connection connection, @NotNull PacketReader reader) throws InvocationTargetException, IllegalAccessException {
        int id = reader.getPacketId();
        GameState state = connection.getState();

        Function<PacketReader, Packet> packetFunction = CodecHandler.GAMESTATE_CODEC.getPacket(state, id);
        if (packetFunction == null) {
            System.out.println("Unknown packet ID: " + reader.getPacketId());
            return;
        }

        Packet readPacket = packetFunction.apply(reader);
        packetHandler.handle(connection, readPacket);

        read(connection, readPacket);
    }

    public void read(@NotNull Connection connection, @NotNull Packet packet) {}
}
