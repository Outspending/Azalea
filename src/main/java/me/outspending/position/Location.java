package me.outspending.position;

public record Location(double x, double y, double z) {
    public static Location ZERO = new Location(0, 0, 0);
    public static Location ONE = new Location(1, 1, 1);
}
