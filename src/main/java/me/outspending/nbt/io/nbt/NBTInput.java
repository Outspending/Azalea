package me.outspending.nbt.io.nbt;

import me.outspending.nbt.NBTTag;
import me.outspending.nbt.io.NamedTag;

import java.io.IOException;

public interface NBTInput {
    NamedTag readTag(int maxDepth) throws IOException;

    NBTTag<?> readRawTag(int maxDepth) throws IOException;
}
