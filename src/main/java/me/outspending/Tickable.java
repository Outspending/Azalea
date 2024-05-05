package me.outspending;

@FunctionalInterface
public interface Tickable {
    void tick(long time);
}
