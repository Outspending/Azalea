package me.outspending.generation;

@FunctionalInterface
public interface BlockGetter {
    int getBlock(int x, int y, int z);
}
