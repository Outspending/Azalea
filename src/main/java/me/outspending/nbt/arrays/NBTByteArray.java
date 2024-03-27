package me.outspending.nbt.arrays;

import me.outspending.nbt.ArrayTag;

import java.util.Arrays;

public class NBTByteArray extends ArrayTag<byte[]> implements Comparable<NBTByteArray> {

    public static final byte ID = 7;
    public static final byte[] ZERO_VALUE = new byte[0];

    public NBTByteArray() {
        super(ZERO_VALUE);
    }

    public NBTByteArray(byte[] value) {
        super(value);
    }

    @Override
    public byte getID() {
        return ID;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && Arrays.equals(getValue(), ((NBTByteArray) other).getValue());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getValue());
    }

    @Override
    public int compareTo(NBTByteArray other) {
        return Integer.compare(length(), other.length());
    }

    @Override
    public NBTByteArray clone() {
        return new NBTByteArray(Arrays.copyOf(getValue(), length()));
    }
}
