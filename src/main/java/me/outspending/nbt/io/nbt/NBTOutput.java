package me.outspending.nbt.io.nbt;

import me.outspending.nbt.NBTTag;
import me.outspending.nbt.io.NamedTag;

import java.io.IOException;

public interface NBTOutput {
    void writeTag(NamedTag tag, int maxDepth) throws IOException;

    void writeTag(NBTTag<?> tag, int maxDepth) throws IOException;

    void flush() throws IOException;
}
