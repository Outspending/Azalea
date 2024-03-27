package me.outspending.nbt.numbers;

import me.outspending.nbt.NumberTag;

public class NBTFloat extends NumberTag<Float> implements Comparable<NBTFloat> {

    public static final byte ID = 5;
    public static final float ZERO_VALUE = 0.0F;

    public NBTFloat() {
        super(ZERO_VALUE);
    }

    public NBTFloat(float value) {
        super(value);
    }

    @Override
    public byte getID() {
        return ID;
    }

    public void setValue(float value) {
        super.setValue(value);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && getValue().equals(((NBTFloat) other).getValue());
    }

    @Override
    public int compareTo(NBTFloat other) {
        return getValue().compareTo(other.getValue());
    }

    @Override
    public NBTFloat clone() {
        return new NBTFloat(getValue());
    }

}
