package me.outspending;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.outspending.connection.ClientConnection;
import me.outspending.connection.Connection;
import me.outspending.protocol.Packet;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class ServerConnection {
    private static final Logger logger = LoggerFactory.getLogger(ServerConnection.class);

    private boolean isRunning = false;

    private final String IPAddress;
    private final int port;

    private final ServerSocket mainSocket;

    public ServerConnection(@NotNull String IPAddress, int port) throws IOException {
        mainSocket = new ServerSocket();

        this.IPAddress = IPAddress;
        this.port = port;

        mainSocket.bind(new InetSocketAddress(IPAddress, port));
    }

    private void init() {
        try {
            while (isRunning) {
                Socket clientSocket = mainSocket.accept();
                logger.info("Client connected: " + clientSocket);

                new ClientConnection(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        if (isRunning) {
            return;
        }

        isRunning = true;
        init();
    }

    public boolean isRunning() {
        return !mainSocket.isClosed();
    }

    public void stop() {
        isRunning = false;
        try {
            mainSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
