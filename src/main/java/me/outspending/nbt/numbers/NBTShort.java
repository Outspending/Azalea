package me.outspending.nbt.numbers;

import me.outspending.nbt.NumberTag;

public class NBTShort extends NumberTag<Short> implements Comparable<NBTShort> {

    public static final byte ID = 2;
    public static final short ZERO_VALUE = 0;

    public NBTShort() {
        super(ZERO_VALUE);
    }

    public NBTShort(short value) {
        super(value);
    }

    @Override
    public byte getID() {
        return ID;
    }

    public void setValue(short value) {
        super.setValue(value);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && asShort() == ((NBTShort) other).asShort();
    }

    @Override
    public int compareTo(NBTShort other) {
        return getValue().compareTo(other.getValue());
    }

    @Override
    public NBTShort clone() {
        return new NBTShort(getValue());
    }
}
