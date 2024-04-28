package me.outspending.connection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.outspending.MinecraftServer;
import me.outspending.NamespacedID;
import me.outspending.protocol.listener.PacketListener;
import me.outspending.protocol.packets.client.play.ClientLoginPlayPacket;
import me.outspending.protocol.packets.client.status.ClientStatusResponsePacket;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.types.GroupedPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Arrays;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class ClientConnection {
    private static final Logger logger = LoggerFactory.getLogger(ClientConnection.class);
    private static final byte[] BYTE_ARRAY = new byte[32767];

    private final Socket socket;

    public final MinecraftServer server;
    public GameState state = GameState.HANDSHAKE;

    public PacketListener packetListener;
    public boolean isRunning = true;

    public ClientConnection(Socket socket) {
        this.socket = socket;
        this.server = MinecraftServer.getInstance();
        this.packetListener = new PacketListener();

        run();
    }

    private void run() {
        try {
            InputStream stream = socket.getInputStream();

            while (isRunning) {
                int result = stream.read(BYTE_ARRAY);
                if (result == -1) {
                    kick();
                    return;
                }

                byte[] responseArray = Arrays.copyOf(BYTE_ARRAY, result);
                ByteBuffer buffer = ByteBuffer.wrap(responseArray);

                PacketReader reader = PacketReader.createNormalReader(buffer);
                logger.info(Arrays.toString(reader.getAllBytes()));

                packetListener.read(ClientConnection.this, reader);
            }
        } catch (IOException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void kick() {
        try {
            logger.info("Client disconnected: " + socket.getInetAddress());
            socket.close();
            isRunning = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isOnline() {
        return socket.isConnected() && isRunning;
    }

    public void sendPacket(@NotNull ClientPacket packet) {
        if (!isOnline()) return;

        try {
            PacketWriter writer = PacketWriter.createNormalWriter(packet);
            writer.writeToStream(socket.getOutputStream());

            ByteArrayOutputStream stream = writer.getStream();
            ByteBuffer buffer = ByteBuffer.wrap(stream.toByteArray());
            PacketReader reader = PacketReader.createNormalReader(buffer);

            logger.info("Sent Length: " + reader.getPacketLength());
            logger.info("Sent ID: " + reader.getPacketID());
            logger.info("Current State: " + state.name());

            logger.info(Arrays.toString(reader.getAllBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendGroupedPacket(@NotNull GroupedPacket packet) {
        for (ClientPacket entryPacket : packet.getPackets()) {
            sendPacket(entryPacket);
        }
    }
}
