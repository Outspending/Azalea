package me.outspending;

import lombok.Getter;
import me.outspending.processes.PlayerManager;
import me.outspending.processes.WorldManager;

@Getter
public class ServerProcess {
    private final PlayerManager playerManager;
    private final WorldManager worldManager;

    public ServerProcess() {
        this.playerManager = new PlayerManager();
        this.worldManager = new WorldManager();
    }
}
