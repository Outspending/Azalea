package me.outspending.connection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.outspending.MinecraftServer;
import me.outspending.chunk.Chunk;
import me.outspending.protocol.listener.PacketListener;
import me.outspending.protocol.packets.client.play.ClientChunkDataPacket;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.types.GroupedPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.Arrays;
import java.util.BitSet;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class ClientConnection {
    private static final Logger logger = LoggerFactory.getLogger(ClientConnection.class);
    private static final byte[] BYTE_ARRAY = new byte[32767];

    private final Socket socket;
    private final WritableByteChannel writableChannel;

    public final MinecraftServer server;
    public GameState state = GameState.HANDSHAKE;

    public PacketListener packetListener;
    public boolean isRunning = true;

    @SneakyThrows
    public ClientConnection(Socket socket) {
        this.socket = socket;
        this.writableChannel = Channels.newChannel(socket.getOutputStream());
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
                packetListener.read(ClientConnection.this, reader);
            }
        } catch (IOException e) {
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

    public void sendChunkData(Chunk chunk) {
        sendPacket(new ClientChunkDataPacket(
                chunk.getChunkX(), chunk.getChunkZ(),
                ClientChunkDataPacket.EMPTY_HEIGHTMAP,
                chunk, new ClientChunkDataPacket.BlockEntity[0],
                new BitSet(), new BitSet(), new BitSet(), new BitSet(),
                new ClientChunkDataPacket.Skylight[0], new ClientChunkDataPacket.Blocklight[0]
        ));
    }

    public void sendChunkData(Chunk[] chunks) {
        for (Chunk chunk : chunks) {
            sendChunkData(chunk);
        }
    }

    @SneakyThrows
    public void sendPacket(@NotNull ClientPacket packet) {
        if (!isOnline()) return;

        synchronized (writableChannel) {
            PacketWriter writer = PacketWriter.createNormalWriter(packet);
            writableChannel.write(writer.get());
        }
    }

    public void sendGroupedPacket(@NotNull GroupedPacket packet) {
        for (ClientPacket entryPacket : packet.getPackets()) {
            sendPacket(entryPacket);
        }
    }
}
