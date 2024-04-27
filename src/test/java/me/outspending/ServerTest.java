package me.outspending;

import org.junit.jupiter.api.Test;

public class ServerTest {

    @Test
    public void test() {
        MinecraftServer.init("127.0.0.1", 25565);
    }
}
