package me.outspending.nbt.numbers;

import me.outspending.nbt.NumberTag;

public class NBTInt extends NumberTag<Integer> implements Comparable<NBTInt> {

    public static final byte ID = 3;
    public static final int ZERO_VALUE = 0;

    public NBTInt() {
        super(ZERO_VALUE);
    }

    public NBTInt(int value) {
        super(value);
    }

    @Override
    public byte getID() {
        return ID;
    }

    public void setValue(int value) {
        super.setValue(value);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && asInt() == ((NBTInt) other).asInt();
    }

    @Override
    public int compareTo(NBTInt other) {
        return getValue().compareTo(other.getValue());
    }

    @Override
    public NBTInt clone() {
        return new NBTInt(getValue());
    }
}
