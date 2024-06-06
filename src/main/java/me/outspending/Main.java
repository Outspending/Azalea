package me.outspending;

import com.google.common.base.Preconditions;
import me.outspending.events.EventExecutor;

public class Main {
    public static void main(String[] args) {
        MinecraftServer server = MinecraftServer.init();
        Preconditions.checkNotNull(server, "Server failed to initialize");

        EventExecutor.registerEvents(new Testing());

        server.start("127.0.0.1", 25565);
    }
}
