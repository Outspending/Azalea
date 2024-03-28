package me.outspending.connection;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.outspending.MinecraftServer;
import me.outspending.protocol.Packet;
import me.outspending.protocol.PacketReader;
import me.outspending.protocol.PacketWriter;
import me.outspending.protocol.listener.PacketListener;
import me.outspending.protocol.packets.configuration.server.RegistryDataPacket;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.Arrays;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class ClientConnection extends Connection {
    private static final byte[] BYTE_ARRAY = new byte[1024];
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
        PacketWriter writer = new PacketWriter(packet);
        PacketReader reader = new PacketReader(writer.toByteArray());

        System.out.println(writer.getLength());
        System.out.println(writer.getPacketID());

        if (packet instanceof RegistryDataPacket)
            System.out.println(reader.readAnyTag(CompoundTag.class));

        try {
            socket.getOutputStream().write(writer.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
