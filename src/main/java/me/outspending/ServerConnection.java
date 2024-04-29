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
<<<<<<< Updated upstream
=======
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.*;
>>>>>>> Stashed changes

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class ServerConnection {
    private static final Logger logger = LoggerFactory.getLogger(ServerConnection.class);
<<<<<<< Updated upstream
=======
    private static final ScheduledExecutorService keepAliveExecutor = Executors.newSingleThreadScheduledExecutor();
    private static final ExecutorService clientExecutor = Executors.newCachedThreadPool();
>>>>>>> Stashed changes

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

                clientExecutor.submit(() -> {
                    new ClientConnection(clientSocket);
                });
            }
        } catch (IOException e) {
<<<<<<< Updated upstream
            e.printStackTrace();
=======
            logger.error("Failed to accept connection", e);
        } finally {
            clientExecutor.shutdown();
>>>>>>> Stashed changes
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
