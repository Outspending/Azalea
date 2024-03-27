package me.outspending.nbt.io.snbt;

import me.outspending.nbt.NBTTag;

import java.io.IOException;

public class SNBTUtil {
    public static String toSNBT(NBTTag<?> tag) throws IOException {
        return new SNBTSerializer().toString(tag);
    }

    public static NBTTag<?> fromSNBT(String string) throws IOException {
        return new SNBTDeserializer().fromString(string);
    }

    public static NBTTag<?> fromSNBT(String string, boolean lenient) throws IOException {
        return new SNBTParser(string).parse(NBTTag.DEFAULT_MAX_DEPTH, lenient);
    }
}
