package me.outspending.nbt.arrays;

import me.outspending.nbt.ArrayTag;

import java.util.Arrays;

public class NBTIntArray extends ArrayTag<int[]> implements Comparable<NBTIntArray> {

    public static final byte ID = 11;
    public static final int[] ZERO_VALUE = new int[0];

    public NBTIntArray() {
        super(ZERO_VALUE);
    }

    public NBTIntArray(int[] value) {
        super(value);
    }

    @Override
    public byte getID() {
        return ID;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && Arrays.equals(getValue(), ((NBTIntArray) other).getValue());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getValue());
    }

    @Override
    public int compareTo(NBTIntArray other) {
        return Integer.compare(length(), other.length());
    }

    @Override
    public NBTIntArray clone() {
        return new NBTIntArray(Arrays.copyOf(getValue(), length()));
    }
}
