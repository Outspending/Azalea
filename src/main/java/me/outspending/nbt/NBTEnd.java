package me.outspending.nbt;

import org.jetbrains.annotations.NotNull;

public class NBTEnd extends NBTTag<Void> {

    public static final byte ID = 0;
    public static final NBTEnd INSTANCE = new NBTEnd();

    private NBTEnd() {
        super(null);
    }

    @Override
    public byte getID() {
        return ID;
    }

    @Override
    protected Void checkValue(@NotNull Void value) {
        return value;
    }

    @Override
    public String valueToString(int maxDepth) {
        return "\"end\"";
    }

    @Override
    public NBTEnd clone() {
        return INSTANCE;
    }

}
