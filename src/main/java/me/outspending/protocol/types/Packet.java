package me.outspending.protocol.types;

import me.outspending.connection.GameState;
import org.jetbrains.annotations.NotNull;

public interface Packet {
    @NotNull GameState state();
    int id();
}
