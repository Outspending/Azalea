package me.outspending;

public enum GameMode {
    SURVIVAL,
    CREATIVE,
    ADVENTURE,
    SPECTATOR;

    public byte getId() {
        return (byte) ordinal();
    }

    public static GameMode getById(byte id) {
        return values()[id];
    }
}
