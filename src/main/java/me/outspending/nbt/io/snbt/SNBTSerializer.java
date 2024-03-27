package me.outspending.nbt.io.snbt;

import me.outspending.nbt.NBTTag;
import me.outspending.nbt.io.StringSerializer;

import java.io.IOException;
import java.io.Writer;

public class SNBTSerializer implements StringSerializer<NBTTag<?>> {
    @Override
    public void toWriter(NBTTag<?> tag, Writer writer) throws IOException {
        SNBTWriter.write(tag, writer);
    }

    public void toWriter(NBTTag<?> tag, Writer writer, int maxDepth) throws IOException {
        SNBTWriter.write(tag, writer, maxDepth);
    }
}
