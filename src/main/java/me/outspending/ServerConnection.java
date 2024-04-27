package me.outspending;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.outspending.connection.ClientConnection;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class ServerConnection {
    private static final Logger logger = LoggerFactory.getLogger(ServerConnection.class);

    private final String IPAddress;
    private final int port;

    private final AsynchronousServerSocketChannel mainSocket;

    public ServerConnection(@NotNull String IPAddress, int port) throws IOException {
        this.mainSocket = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(IPAddress, port));
        this.IPAddress = IPAddress;
        this.port = port;
    }

    private void init() {
        mainSocket.accept(null, new CompletionHandler<>() {
            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                logger.info("Client Connected: " + result);
                new ClientConnection(result);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                logger.error("Failed to accept connection", exc);
            }
        });
    }

    public void start() {
        if (isRunning()) return;
        init();
    }

    public boolean isRunning() {
        return mainSocket.isOpen();
    }

    public void stop() {
        try {
            mainSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
