package me.outspending.nbt;

import me.outspending.nbt.NBTTag;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;

public abstract class ArrayTag<T> extends NBTTag<T> {
    public ArrayTag(@NotNull T value) {
        super(value);
        if (!value.getClass().isArray()) {
            throw new UnsupportedOperationException("Value must be an array");
        }
    }

    public int length() {
        return Array.getLength(getValue());
    }

    @Override
    public T getValue() {
        return super.getValue();
    }

    @Override
    public void setValue(@NotNull T value) {
        super.setValue(value);
    }

    @Override
    public String valueToString(int maxDepth) {
        return arrayToString("", "");
    }

    protected String arrayToString(@NotNull String prefix, @NotNull String suffix) {
        StringBuffer sb = new StringBuffer("[").append(prefix).append("".equals(prefix) ? "" : ";");
        for (int i = 0; i < length(); i++) {
            sb.append(i == 0 ? "" : ",").append(Array.get(getValue(), i)).append(suffix);
        }
        sb.append("]");
        return sb.toString();
    }
}
