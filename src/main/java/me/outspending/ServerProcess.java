package me.outspending;

import lombok.Getter;
import me.outspending.processes.PlayerManager;

@Getter
public class ServerProcess {
    private final PlayerManager playerManager;

    public ServerProcess() {
        this.playerManager = new PlayerManager();
    }
}
