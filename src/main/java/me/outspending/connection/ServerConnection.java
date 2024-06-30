package me.outspending.connection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.outspending.MinecraftServer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class ServerConnection {
    private static final Logger logger = LoggerFactory.getLogger(ServerConnection.class);
    private static final ScheduledExecutorService keepAliveExecutor = Executors.newSingleThreadScheduledExecutor();
    private static final ExecutorService clientExecutor = Executors.newCachedThreadPool();

    private boolean running = false;
    private String IPAddress;
    private int port;

    private final ServerSocket mainSocket;
    private final MinecraftServer server;

    public ServerConnection(@NotNull MinecraftServer server) throws IOException {
        this.mainSocket = new ServerSocket();
        this.server = server;
    }

    public void startTcpListener(@NotNull String IPAddress, int port) {
        try {
            this.IPAddress = IPAddress;
            this.port = port;
            this.running = true;
            mainSocket.bind(new InetSocketAddress(IPAddress, port));

            keepConnectionsAlive();
            UNSAFE_startTcpListener();
        } catch (IOException e) {
            logger.error("Failed to accept minecraft connection", e);
        }
    }

    private void UNSAFE_startTcpListener() throws IOException {
        while (running) {
            Socket clientSocket = mainSocket.accept();
            logger.info("Client Connected: {}", clientSocket);

            clientExecutor.submit(() -> {
                final ClientConnection connection = new ClientConnection(clientSocket);
                connection.startTcpListener();
            });
        }
    }

    private void keepConnectionsAlive() {
        keepAliveExecutor.scheduleAtFixedRate(() -> {
            final long time = System.currentTimeMillis();
            server.getAllPlayers().forEach(player -> player.keepConnectionAlive(time));
        }, 10, 10, TimeUnit.SECONDS);
    }

    public void stop() {
        try {
            mainSocket.close();
        } catch (IOException e) {
            logger.error("Failed to close server socket", e);
        }
    }
}
