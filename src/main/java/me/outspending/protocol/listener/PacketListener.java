package me.outspending.protocol.listener;

import lombok.SneakyThrows;
import me.outspending.connection.ClientConnection;
import me.outspending.connection.GameState;
import me.outspending.protocol.AnnotatedPacketHandler;
import me.outspending.protocol.codec.CodecHandler;
import me.outspending.protocol.reader.NormalPacketReader;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.Packet;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.function.Function;

public class PacketListener {
    private static final Logger logger = LoggerFactory.getLogger(PacketListener.class);
    private static final AnnotatedPacketHandler packetHandler = new AnnotatedPacketHandler();

    @SneakyThrows
    public void read(@NotNull ClientConnection connection, @NotNull PacketReader reader) {
        int id = reader.getPacketID();
        GameState state = connection.getState();

        Function<PacketReader, ServerPacket> packetFunction = CodecHandler.CLIENT_CODEC.getPacket(state, id);
        if (packetFunction == null) {
            logger.info(String.format("Unknown packet ID: %d, in state: %s", id, state.name()));
//            logger.info("Disconnecting client from server: Invalid Packet");
//            connection.kick();
            return;
        }
        Packet readPacket = packetFunction.apply(reader);
        packetHandler.handle(connection, readPacket);

        read(connection, readPacket);

        if (reader.hasAnotherPacket()) {
            logger.info("Reading remaining bytes for packet: " + id);
            byte[] remaining = reader.getRemainingBytes();
            ByteBuffer buffer = ByteBuffer.allocate(remaining.length);
            buffer.put(remaining);
            buffer.flip();

            read(connection, new NormalPacketReader(buffer));
        }
    }

    public void read(@NotNull ClientConnection connection, @NotNull Packet packet) {

    }
}
