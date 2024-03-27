package me.outspending.nbt.numbers;

import me.outspending.nbt.NumberTag;

public class NBTDouble extends NumberTag<Double> implements Comparable<NBTDouble> {

    public static final byte ID = 6;
    public static final double ZERO_VALUE = 0.0D;

    public NBTDouble() {
        super(ZERO_VALUE);
    }

    public NBTDouble(double value) {
        super(value);
    }

    @Override
    public byte getID() {
        return ID;
    }

    public void setValue(double value) {
        super.setValue(value);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && asDouble() == ((NBTDouble) other).asDouble();
    }

    @Override
    public int compareTo(NBTDouble other) {
        return getValue().compareTo(other.getValue());
    }

    @Override
    public NBTDouble clone() {
        return new NBTDouble(getValue());
    }
}
