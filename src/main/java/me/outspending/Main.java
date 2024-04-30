package me.outspending;

import com.google.common.base.Preconditions;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        MinecraftServer server = MinecraftServer.init("127.0.0.1", 25565);
        Preconditions.checkNotNull(server, "Server failed to initialize");

//        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
//        Runtime runtime = Runtime.getRuntime();
//        service.scheduleAtFixedRate(() -> {
//            long total = runtime.totalMemory();
//            long usedMemory = total - runtime.freeMemory();
//
//            System.out.println("Used: " + (usedMemory / 1024 / 1024) + "MB/" + (total / 1024 / 1024) + "MB");
//        }, 50, 50, TimeUnit.MILLISECONDS); // AKA per tick

        server.start();
    }
}
