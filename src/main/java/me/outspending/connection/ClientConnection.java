package me.outspending.connection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.outspending.MinecraftServer;
import me.outspending.protocol.listener.PacketListener;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class ClientConnection {
    private static final Logger logger = LoggerFactory.getLogger(ClientConnection.class);

    private final ByteBuffer buffer = ByteBuffer.allocate(32767);
    private final AsynchronousSocketChannel socket;

    public final MinecraftServer server;
    public GameState state = GameState.HANDSHAKE;

    public PacketListener packetListener;
    public boolean isRunning = false;

    public ClientConnection(AsynchronousSocketChannel socket) {
        this.socket = socket;
        this.server = MinecraftServer.getInstance();
        this.packetListener = new PacketListener();

        run();
    }

    private void run() {
        socket.read(buffer, null, new CompletionHandler<>() {
            @Override
            @SneakyThrows
            public void completed(Integer result, Object attachment) {
                if (result == -1) {
                    socket.close();
                    return;
                }

                buffer.flip();
                ByteBuffer serializedBuffer = ByteBuffer.allocate(buffer.remaining());
                serializedBuffer.put(buffer);
                serializedBuffer.flip();

                PacketReader reader = PacketReader.createNormalReader(serializedBuffer);
                packetListener.read(ClientConnection.this, reader);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                logger.error("Failed to read data", exc);
            }
        });
    }

    public void sendPacket(@NotNull ClientPacket packet) {

    }
}
