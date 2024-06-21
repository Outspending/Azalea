package me.outspending;

import com.google.common.base.Preconditions;
import me.outspending.events.EventExecutor;
import me.outspending.registry.DefaultRegistries;
import me.outspending.registry.DefaultedRegistry;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        MinecraftServer server = MinecraftServer.init();
        Preconditions.checkNotNull(server, "Server failed to initialize");

        EventExecutor.registerEvents(new Testing());

        server.start("127.0.0.1", 25565);
    }
}
