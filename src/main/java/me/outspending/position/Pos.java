package me.outspending.position;

public record Pos(double x, double y, double z, float yaw, float pitch) {
    public double distance(Pos pos) {
        return Math.sqrt(Math.pow(x - pos.x, 2) + Math.pow(y - pos.y, 2) + Math.pow(z - pos.z, 2));
    }
}
