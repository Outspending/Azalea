package me.outspending.position;

public record Angle(float angle) {
    public static final Angle ZERO = new Angle(0.0F);

    public static Angle fromNetwork(byte value) {
        return new Angle(value * 360.0F / 256.0F);
    }

    public byte toNetwork() {
        return (byte) (angle * 256.0F / 360.0F);
    }
}
