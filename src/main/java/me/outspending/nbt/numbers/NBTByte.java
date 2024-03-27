package me.outspending.nbt.numbers;

import me.outspending.nbt.NumberTag;

public class NBTByte extends NumberTag<Byte> implements Comparable<NBTByte> {

    public static final byte ID = 1;
    public static final byte ZERO_VALUE = 0;

    public NBTByte() {
        super(ZERO_VALUE);
    }

    public NBTByte(byte value) {
        super(value);
    }

    public NBTByte(boolean value) {
        super((byte) (value ? 1 : 0));
    }

    @Override
    public byte getID() {
        return ID;
    }

    public void setValue(byte value) {
        super.setValue(value);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && asByte() == ((NBTByte) other).asByte();
    }

    @Override
    public int compareTo(NBTByte other) {
        return getValue().compareTo(other.getValue());
    }

    @Override
    public NBTByte clone() {
        return new NBTByte(getValue());
    }
}
