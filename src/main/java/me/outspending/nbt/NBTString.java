package me.outspending.nbt;

import org.jetbrains.annotations.NotNull;

public class NBTString extends NBTTag<String> implements Comparable<NBTString> {

    public static final byte ID = 8;
    public static final String ZERO_VALUE = "";

    public NBTString() {
        this(ZERO_VALUE);
    }

    public NBTString(@NotNull String value) {
        super(value);
    }

    @Override
    public byte getID() {
        return ID;
    }

    @Override
    public String getValue() {
        return super.getValue();
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
    }

    @Override
    public int compareTo(NBTString o) {
        return getValue().compareTo(o.getValue());
    }

    @Override
    public String valueToString(int maxDepth) {
        return escapeString(getValue(), false);
    }

    @Override
    public NBTTag<String> clone() {
        return new NBTString(getValue());
    }
}
