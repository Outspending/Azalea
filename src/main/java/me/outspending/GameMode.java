package me.outspending;

import org.jetbrains.annotations.Range;

public enum GameMode {
    SURVIVAL,
    CREATIVE,
    ADVENTURE,
    SPECTATOR;

    public byte getId() {
        return (byte) ordinal();
    }

    public static GameMode getById(@Range(from = 0, to = 4) byte id) {
        return values()[id];
    }
}
