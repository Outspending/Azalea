package me.outspending;

public class Main {
    public static void main(String[] args) {
        MinecraftServer server = MinecraftServer.init("127.0.0.1", 25565);
        if (server != null) {
            server.start();
        }
    }
}
