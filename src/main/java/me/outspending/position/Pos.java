package me.outspending.position;

public record Pos(double x, double y, double z, float yaw, float pitch) {
    public static final Pos ZERO = new Pos(0, 0, 0, 0f, 0f);
    public static final Pos ONE = new Pos(1, 1, 1, 0f, 0f);

    public double distance(Pos pos) {
        return Math.sqrt(Math.pow(x - pos.x, 2) + Math.pow(y - pos.y, 2) + Math.pow(z - pos.z, 2));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pos pos = (Pos) obj;
        return Double.compare(pos.x, x) == 0 && Double.compare(pos.y, y) == 0 && Double.compare(pos.z, z) == 0 && Float.compare(pos.yaw, yaw) == 0 && Float.compare(pos.pitch, pitch) == 0;
    }
}
