package me.outspending.connection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.outspending.MinecraftServer;
import me.outspending.protocol.listener.PacketListener;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
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
    public boolean isRunning = false;

    public ClientConnection(Socket socket) {
        this.socket = socket;
        this.server = MinecraftServer.getInstance();
        this.packetListener = new PacketListener();

        run();
    }

    private void run() {
        try {
            InputStream stream = socket.getInputStream();

            while (true) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPacket(@NotNull ClientPacket packet) {
        try {
            PacketWriter writer = PacketWriter.createNormalWriter(packet);;
            writer.writeToStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
