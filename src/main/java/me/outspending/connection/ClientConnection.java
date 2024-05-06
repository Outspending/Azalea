package me.outspending.connection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.outspending.MinecraftServer;
import me.outspending.chunk.Chunk;
import me.outspending.chunk.light.Blocklight;
import me.outspending.chunk.light.Skylight;
import me.outspending.entity.BlockEntity;
import me.outspending.protocol.listener.PacketListener;
import me.outspending.protocol.packets.client.configuration.ClientConfigurationDisconnectPacket;
import me.outspending.protocol.packets.client.login.ClientLoginDisconnectPacket;
import me.outspending.protocol.packets.client.play.ClientBundleDelimiterPacket;
import me.outspending.protocol.packets.client.play.ClientChunkDataPacket;
import me.outspending.protocol.packets.client.play.ClientPlayDisconnectPacket;
import me.outspending.protocol.packets.client.play.ClientPlayerInfoUpdatePacket;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.types.GroupedPacket;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;

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
                    kick("Connection closed by remote host");
                    return;
                }

                byte[] responseArray = Arrays.copyOf(BYTE_ARRAY, result);
                ByteBuffer buffer = ByteBuffer.wrap(responseArray);

                PacketReader reader = PacketReader.createNormalReader(buffer);
                packetListener.read(ClientConnection.this, reader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void kick(String reason) {
        kick(Component.text(reason));
    }

    public void kick(Component reason) {
        switch (state) {
            case LOGIN -> sendPacket(new ClientLoginDisconnectPacket(reason));
            case CONFIGURATION -> sendPacket(new ClientConfigurationDisconnectPacket(reason));
            case PLAY -> sendPacket(new ClientPlayDisconnectPacket(reason));
            default -> {
                // Do Nothing
            }
        }

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

    public void sendBundled() {
        sendPacket(new ClientBundleDelimiterPacket());
    }

    public void sendBundled(Runnable runnable) {
        sendBundled();
        runnable.run();
        sendBundled();
    }

    @SneakyThrows
    public void sendPacket(@NotNull ClientPacket packet) {
        if (!isOnline()) return;

        synchronized (socket) {
            PacketWriter writer = PacketWriter.createNormalWriter(packet);
            OutputStream stream = socket.getOutputStream();

            stream.write(writer.toByteArray());
            writer.flush();
        }
    }

    public void sendGroupedPacket(@NotNull GroupedPacket packet) {
        sendBundled(() -> {
            for (ClientPacket entryPacket : packet.getPackets()) {
                sendPacket(entryPacket);
            }
        });
    }


}
