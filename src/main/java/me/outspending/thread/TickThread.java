package me.outspending.thread;

import me.outspending.MinecraftServer;
import me.outspending.events.EventExecutor;
import me.outspending.events.event.ServerTickEvent;
import me.outspending.world.World;

import java.util.Collection;

public class TickThread extends Thread {
    private static final long TICK_TIME_NANOS = 1_000_000_000L / 20;
    private static final int SERVER_MAX_TICK_CATCH_UP = 5;
    private static final long SLEEP_THRESHOLD = System.getProperty("os.name", "")
            .toLowerCase().startsWith("windows") ? 17 : 2;

    public TickThread() {
        super("TickThread-1");
    }

    @Override
    public void run() {
        long ticks = 0;
        long baseTime = System.nanoTime();
        while (true) {
            final long tickStart = System.nanoTime();

            EventExecutor.emitEvent(new ServerTickEvent(tickStart));

            Collection<World> loadedWorlds = MinecraftServer.getInstance().getServerProcess().getWorldCache().getAll();
            for (World world : loadedWorlds) {
                world.tick(tickStart);
            }

            ticks++;
            long nextTickTime = baseTime + ticks * TICK_TIME_NANOS;
            waitUntilNextTick(nextTickTime);

            if (System.nanoTime() > nextTickTime + TICK_TIME_NANOS * SERVER_MAX_TICK_CATCH_UP) {
                baseTime = System.nanoTime();
                ticks = 0;
            }
        }
    }

    public void waitUntilNextTick(long nextTickTimeNanos) {
        long currentTime;
        while((currentTime = System.nanoTime()) < nextTickTimeNanos) {
            long remainingTime = nextTickTimeNanos - currentTime;
            long remainingMilliseconds = remainingTime / 1_000_000L;

            if (remainingMilliseconds >= SLEEP_THRESHOLD) {
                sleepThread(remainingMilliseconds / 2);
            }
        }
    }

    private void sleepThread(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
