package me.outspending.connection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.outspending.MinecraftServer;
import me.outspending.protocol.*;
import me.outspending.protocol.listener.PacketListener;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.Arrays;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class ClientConnection extends Connection {
    private static final byte[] BYTE_ARRAY = new byte[1024];
    private final Socket socket;

    private PacketListener packetListener;
    private boolean isRunning = false;

    public ClientConnection(Socket socket) throws IOException {
        super(MinecraftServer.getInstance());

        this.socket = socket;
        this.packetListener = new PacketListener();

        run();
    }

    private void run() {
        isRunning = true;
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

            System.out.println("Client disconnected: " + socket);
            socket.close();
        } catch (IOException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void sendPacket(@NotNull Packet packet) {
        PacketWriter packetLength = new PacketWriter();
        packetLength.writeVarInt(packet.getID());
        packet.write(packetLength);

        PacketWriter writer = new PacketWriter();
        writer.writeVarInt(packetLength.size());
        writer.writePacketWriter(packetLength);

        try {
            socket.getOutputStream().write(writer.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
