package me.outspending.connection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.outspending.MinecraftServer;
import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import me.outspending.protocol.listener.PacketListener;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.Arrays;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class ClientConnection extends Connection {
    private static final Logger logger = LoggerFactory.getLogger(ClientConnection.class);
    private static final byte[] BYTE_ARRAY = new byte[32767];

    private final Socket socket;

    public PacketListener packetListener;
    public boolean isRunning = false;

    public ClientConnection(Socket socket) throws IOException {
        super(MinecraftServer.getInstance(), GameState.HANDSHAKE);

        this.socket = socket;
        this.packetListener = new PacketListener();

        run();
    }

    private void run() {
        isRunning = true;
        synchronized (socket) {
            try {
                InputStream stream = socket.getInputStream();

                while (isRunning) {
                    int bytesRead = stream.read(BYTE_ARRAY);
                    if (bytesRead == -1) {
                        break;
                    }

                    byte[] responseArray = Arrays.copyOf(BYTE_ARRAY, bytesRead);
                    PacketReader reader = new PacketReader(responseArray);
                    packetListener.read(this, reader);
                }

                logger.info("Client disconnected: " + socket);
                socket.close();
            } catch (IOException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendPacket(@NotNull Packet packet) {
        try {
            PacketWriter writer = new PacketWriter(packet);
            OutputStream stream = socket.getOutputStream();

            stream.write(writer.toByteArray());
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
