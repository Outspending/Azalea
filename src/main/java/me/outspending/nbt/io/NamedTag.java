package me.outspending.nbt.io;

import me.outspending.nbt.NBTTag;

public class NamedTag {
    private String name;
    private NBTTag<?> tag;

    public NamedTag(String name, NBTTag<?> tag) {
        this.name = name;
        this.tag = tag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(NBTTag<?> tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public NBTTag<?> getTag() {
        return tag;
    }
}
