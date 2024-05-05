package me.outspending;

import me.outspending.events.EventExecutor;
import me.outspending.events.event.ServerTickEvent;
import me.outspending.world.World;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TickHandler {
    private void setupTicks() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(this::scheduleTick, 50, 50, TimeUnit.MILLISECONDS);
    }

    protected TickHandler() {
        setupTicks();
    }

    public void scheduleTick() {
        final long tickTime = System.currentTimeMillis();

        // Execute the tick event
        EventExecutor.emitEvent(new ServerTickEvent());

        // Next tick all the loaded worlds
        final Collection<World> worlds = MinecraftServer.getInstance().getServerProcess().getWorldManager().getLoadedWorlds().values();
        for (World world : worlds) {
            world.tick(tickTime);
        }
    }
}
