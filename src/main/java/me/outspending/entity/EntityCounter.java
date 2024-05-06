package me.outspending.entity;

import java.util.concurrent.atomic.AtomicInteger;

final class EntityCounter {
    private static final AtomicInteger entityCounter = new AtomicInteger(0);

    public static int getNextEntityID() {
        return entityCounter.incrementAndGet();
    }
}
