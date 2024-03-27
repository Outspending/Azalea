package me.outspending.nbt.numbers;

import me.outspending.nbt.NumberTag;

public class NBTLong extends NumberTag<Long> implements Comparable<NBTLong> {

    public static final byte ID = 4;
    public static final long ZERO_VALUE = 0L;

    public NBTLong() {
        super(ZERO_VALUE);
    }

    public NBTLong(long value) {
        super(value);
    }

    @Override
    public byte getID() {
        return ID;
    }

    public void setValue(long value) {
        super.setValue(value);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && asLong() == ((NBTLong) other).asLong();
    }

    @Override
    public int compareTo(NBTLong other) {
        return getValue().compareTo(other.getValue());
    }

    @Override
    public NBTLong clone() {
        return new NBTLong(getValue());
    }
}
