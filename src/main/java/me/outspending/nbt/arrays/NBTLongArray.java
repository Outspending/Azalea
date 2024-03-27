package me.outspending.nbt.arrays;

import me.outspending.nbt.ArrayTag;

import java.util.Arrays;

public class NBTLongArray extends ArrayTag<long[]> implements Comparable<NBTLongArray> {

    public static final byte ID = 12;
    public static final long[] ZERO_VALUE = new long[0];

    public NBTLongArray() {
        super(ZERO_VALUE);
    }

    public NBTLongArray(long[] value) {
        super(value);
    }

    @Override
    public byte getID() {
        return ID;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && Arrays.equals(getValue(), ((NBTLongArray) other).getValue());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getValue());
    }

    @Override
    public int compareTo(NBTLongArray other) {
        return Integer.compare(length(), other.length());
    }

    @Override
    public NBTLongArray clone() {
        return new NBTLongArray(Arrays.copyOf(getValue(), length()));
    }
}
