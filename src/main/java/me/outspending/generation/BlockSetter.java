package me.outspending.generation;

@FunctionalInterface
public interface BlockSetter {
    void setBlock(int x, int y, int z, int blockID);
}
