package me.outspending.chunk.light;

public record Blocklight(int length, byte[] blockLightArray) {
    public static final Blocklight EMPTY = new Blocklight(2048, new byte[2048]);
}
