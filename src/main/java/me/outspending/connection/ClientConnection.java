package me.outspending.connection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.outspending.MinecraftServer;
import me.outspending.events.EventExecutor;
import me.outspending.events.event.ClientPacketRecieveEvent;
import me.outspending.events.event.ServerPacketRecieveEvent;
import me.outspending.protocol.PacketHandler;
import me.outspending.protocol.PacketListener;
import me.outspending.protocol.codec.CodecHandler;
import me.outspending.protocol.packets.client.configuration.ClientConfigurationDisconnectPacket;
import me.outspending.protocol.packets.client.login.ClientLoginDisconnectPacket;
import me.outspending.protocol.packets.client.play.ClientBundleDelimiterPacket;
import me.outspending.protocol.packets.client.play.ClientPlayDisconnectPacket;
import me.outspending.protocol.reader.NormalPacketReader;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.types.GroupedPacket;
import me.outspending.protocol.types.ServerPacket;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.function.Function;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class ClientConnection {
    private static final Logger logger = LoggerFactory.getLogger(ClientConnection.class);
    private static final byte[] BYTE_ARRAY = new byte[32767];

    private final Socket socket;

    public final MinecraftServer server;
    public GameState state = GameState.HANDSHAKE;

    public boolean isRunning = true;

    private final PacketHandler handler = new PacketHandler();
    private PacketListener<ClientPacket> clientPacketListener;
    private PacketListener<ServerPacket> serverPacketListener;

    public ClientConnection(Socket socket) {
        this.socket = socket;
        this.server = MinecraftServer.getInstance();

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
                handlePacket(reader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handlePacket(@NotNull PacketReader reader) {
        try {
            int id = reader.getPacketID();

            Function<PacketReader, ServerPacket> packetFunction = CodecHandler.CLIENT_CODEC.getPacket(state, id);
            if (packetFunction == null) {
                logger.info(String.format("Unknown packet ID: %d, in state: %s", id, state.name()));
                return;
            }
            ServerPacket readPacket = packetFunction.apply(reader);
            handler.handle(this, readPacket);

            EventExecutor.emitEvent(new ServerPacketRecieveEvent(readPacket));
            if (serverPacketListener != null)
                serverPacketListener.onPacketReceive(readPacket);

            if (reader.hasAnotherPacket()) {
                byte[] remaining = reader.getRemainingBytes();
                ByteBuffer buffer = ByteBuffer.allocate(remaining.length);
                buffer.put(remaining);
                buffer.flip();

                handlePacket(new NormalPacketReader(buffer));
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
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

            EventExecutor.emitEvent(new ClientPacketRecieveEvent(packet, this));
            if (clientPacketListener != null)
                clientPacketListener.onPacketReceive(packet);

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
