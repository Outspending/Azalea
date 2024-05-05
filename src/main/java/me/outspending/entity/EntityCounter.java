package me.outspending.entity;

final class EntityCounter {
    private static int entityCount = 0;

    public static int getNextEntityID() {
        return entityCount++;
    }
}
