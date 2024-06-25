package me.outspending;

import lombok.Getter;
import me.outspending.cache.PlayerCache;
import me.outspending.cache.WorldCache;

@Getter
public class ServerProcess {
    private final PlayerCache playerCache = new PlayerCache();
    private final WorldCache worldCache = new WorldCache();

    ServerProcess() {}

}
