package me.outspending;

import com.google.common.base.Preconditions;

public class Main {
    public static void main(String[] args) {
        MinecraftServer server = MinecraftServer.init("127.0.0.1", 25565);
        Preconditions.checkNotNull(server, "Server failed to initialize");

        server.start();
    }
}
