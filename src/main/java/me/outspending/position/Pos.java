package me.outspending.position;

import org.jetbrains.annotations.UnknownNullability;

public record Pos(double x, double y, double z, float yaw, float pitch) {
    public static final Pos ZERO = new Pos(0, 0, 0, 0f, 0f);
    public static final Pos ONE = new Pos(1, 1, 1, 0f, 0f);

    public Pos(double x, double y, double z) {
        this(x, y, z, 0, 0);
    }

    public static @UnknownNullability Pos fromNetwork(long l) {
        int x = (int) (l >> 38);
        int y = (int) (l << 52 >> 52);
        int z = (int) (l << 26 >> 38);

        return new Pos(x, y, z, 0f, 0f);
    }

    public long toNetwork() {
        return (((long) ((int) this.x & 0x3FFFFFF) << 38) | ((long) ((int) this.z & 0x3FFFFFF) << 12) | ((int) this.y & 0xFFF));
    }

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
