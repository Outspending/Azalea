package me.outspending.chunk.light;

public record Skylight(int length, byte[] skyLightArray) {
    public static final Skylight EMPTY = new Skylight(2048, new byte[2048]);
}
