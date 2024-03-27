package me.outspending.nbt.io.snbt;

import me.outspending.nbt.NBTTag;
import me.outspending.nbt.io.StringDeserializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.stream.Collectors;

public class SNBTDeserializer implements StringDeserializer<NBTTag<?>> {
    @Override
    public NBTTag<?> fromReader(Reader reader) throws IOException {
        return fromReader(reader, NBTTag.DEFAULT_MAX_DEPTH);
    }

    public NBTTag<?> fromReader(Reader reader, int maxDepth) throws IOException {
        BufferedReader bufferedReader;
        if (reader instanceof BufferedReader) {
            bufferedReader = (BufferedReader) reader;
        } else {
            bufferedReader = new BufferedReader(reader);
        }
        return new SNBTParser(bufferedReader.lines().collect(Collectors.joining())).parse(maxDepth);
    }
}
