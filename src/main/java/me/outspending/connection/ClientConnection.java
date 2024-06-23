package me.outspending.connection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.outspending.MinecraftServer;
import me.outspending.player.Player;
import me.outspending.events.EventExecutor;
import me.outspending.events.event.ClientPacketRecieveEvent;
import me.outspending.events.event.ServerPacketRecieveEvent;
import me.outspending.protocol.CompressionType;
import me.outspending.protocol.PacketDecoder;
import me.outspending.protocol.PacketEncoder;
import me.outspending.protocol.listener.PacketListener;
import me.outspending.protocol.packets.client.play.ClientBundleDelimiterPacket;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.types.GroupedPacket;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class ClientConnection {
    private static final Logger logger = LoggerFactory.getLogger(ClientConnection.class);
    private static final byte[] BYTE_ARRAY = new byte[32767];

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    private MinecraftServer server;
    private ConnectionState state = ConnectionState.HANDSHAKE;
    private CompressionType compressionType = CompressionType.NONE;
    private Player player;

    private int packetsReceived = 0;
    private int packetsSent = 0;

    private final PacketListener<ClientPacket> packetListener = PacketListener.create(ClientPacket.class);

    public ClientConnection(Socket socket) {
        try {
            this.socket = socket;
            this.inputStream = socket.getInputStream();
            this.outputStream = socket.getOutputStream();
            this.server = MinecraftServer.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startTcpListener() {
        try {
            while (server.isRunning()) {
                int result = inputStream.read(BYTE_ARRAY);
                if (result == -1) {
                    return;
                }

                byte[] responseArray = Arrays.copyOf(BYTE_ARRAY, result);
                handlePacket(responseArray);
            }
        } catch (IOException e) {
            logger.error("Error while reading packet", e);
        }
    }

    private void handlePacket(byte @NotNull [] data) {
        final ByteBuffer buffer = ByteBuffer.wrap(data);
        final PacketReader reader = PacketReader.createNormalReader(buffer);

        ServerPacket readPacket = PacketDecoder.decode(this, reader, compressionType, state);
        if (readPacket != null) {
            logger.info("[{}] Received packet: {}", readPacket.id(), readPacket);

            EventExecutor.emitEvent(new ServerPacketRecieveEvent(readPacket));
            server.getPacketListener().onPacketReceived(readPacket);
            packetsReceived++;

            if (reader.hasAnotherPacket()) {
                handlePacket(reader.getRemainingBytes());
            }
        }
    }

    public boolean isOnline() {
        return socket.isConnected();
    }

    private void sendBundled() {
        sendPacket(new ClientBundleDelimiterPacket());
    }

    public void sendBundled(Runnable runnable) {
        sendBundled();
        runnable.run();
        sendBundled();
    }

    @SneakyThrows
    public void sendPackets(@NotNull ClientPacket... packets) {
        for (ClientPacket packet : packets) {
            sendPacket(packet);
        }
    }

    @SneakyThrows
    public void sendPacket(@NotNull ClientPacket packet) {
        if (isOnline()) {
            logger.info("[{}] Sending packet: {}", packet.id(), packet);
            PacketWriter writer = PacketEncoder.encode(PacketWriter.createNormalWriter(), compressionType, packet);

            EventExecutor.emitEvent(new ClientPacketRecieveEvent(packet, this));
            packetListener.onPacketReceived(packet);
            packetsSent++;

            outputStream.write(writer.toByteArray());
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
