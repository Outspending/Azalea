package me.outspending;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.outspending.connection.ClientConnection;
import me.outspending.connection.GameState;
import me.outspending.entity.Player;
import me.outspending.processes.PlayerManager;
import me.outspending.protocol.packets.client.play.ClientKeepAlivePacket;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class ServerConnection {
    private static final Logger logger = LoggerFactory.getLogger(ServerConnection.class);
    private static final ScheduledExecutorService keepAliveExecutor = Executors.newSingleThreadScheduledExecutor();

    private boolean isRunning = true;

    private final String IPAddress;
    private final int port;

    private final ServerSocket mainSocket;

    public ServerConnection(@NotNull String IPAddress, int port) throws IOException {
        this.mainSocket = new ServerSocket();
        this.IPAddress = IPAddress;
        this.port = port;

        mainSocket.bind(new InetSocketAddress(IPAddress, port));
    }

    @SneakyThrows
    private void init() {
        PlayerManager manager = MinecraftServer.getInstance().getServerProcess().getPlayerManager();
        keepAliveExecutor.scheduleAtFixedRate(() -> {
            for (Player player : manager.getAllPlayers()) {
                ClientConnection connection = player.getConnection();
                if (connection.state == GameState.PLAY) {
                    connection.sendPacket(new ClientKeepAlivePacket(System.currentTimeMillis()));
                }
            }
        }, 10, 10, TimeUnit.SECONDS);

        try {
            while (isRunning) {
                Socket clientSocket = mainSocket.accept();
                logger.info("Client Connected: " + clientSocket);

                new ClientConnection(clientSocket);
            }
        } catch (IOException e) {
            logger.error("Failed to accept connection", e);
        }
    }

    public void start() {
        init();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void stop() {
        try {
            mainSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
