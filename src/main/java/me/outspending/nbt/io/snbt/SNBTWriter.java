package me.outspending.nbt.io.snbt;

import me.outspending.nbt.*;
import me.outspending.nbt.arrays.NBTByteArray;
import me.outspending.nbt.arrays.NBTIntArray;
import me.outspending.nbt.arrays.NBTLongArray;
import me.outspending.nbt.io.MaxDepthIO;
import me.outspending.nbt.numbers.*;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.Map;
import java.util.regex.Pattern;

public final class SNBTWriter implements MaxDepthIO {

    private static final Pattern NON_QUOTE_PATTERN = Pattern.compile("[a-zA-Z_.+\\-]+");

    private Writer writer;

    private SNBTWriter(Writer writer) {
        this.writer = writer;
    }

    public static void write(NBTTag<?> tag, Writer writer, int maxDepth) throws IOException {
        new SNBTWriter(writer).writeAnything(tag, maxDepth);
    }

    public static void write(NBTTag<?> tag, Writer writer) throws IOException {
        write(tag, writer, NBTTag.DEFAULT_MAX_DEPTH);
    }

    private void writeAnything(NBTTag<?> tag, int maxDepth) throws IOException {
        switch (tag.getID()) {
            case NBTEnd.ID:
                //do nothing
                break;
            case NBTByte.ID:
                writer.append(Byte.toString(((NBTByte) tag).asByte())).write('b');
                break;
            case NBTShort.ID:
                writer.append(Short.toString(((NBTShort) tag).asShort())).write('s');
                break;
            case NBTInt.ID:
                writer.write(Integer.toString(((NBTInt) tag).asInt()));
                break;
            case NBTLong.ID:
                writer.append(Long.toString(((NBTLong) tag).asLong())).write('l');
                break;
            case NBTFloat.ID:
                writer.append(Float.toString(((NBTFloat) tag).asFloat())).write('f');
                break;
            case NBTDouble.ID:
                writer.append(Double.toString(((NBTDouble) tag).asDouble())).write('d');
                break;
            case NBTByteArray.ID:
                writeArray(((NBTByteArray) tag).getValue(), ((NBTByteArray) tag).length(), "B");
                break;
            case NBTString.ID:
                writer.write(escapeString(((NBTString) tag).getValue()));
                break;
            case NBTList.ID:
                writer.write('[');
                for (int i = 0; i < ((NBTList<?>) tag).size(); i++) {
                    writer.write(i == 0 ? "" : ",");
                    writeAnything(((NBTList<?>) tag).get(i), decrementMaxDepth(maxDepth));
                }
                writer.write(']');
                break;
            case NBTCompound.ID:
                writer.write('{');
                boolean first = true;
                for (Map.Entry<String, NBTTag<?>> entry : (NBTCompound) tag) {
                    writer.write(first ? "" : ",");
                    writer.append(escapeString(entry.getKey())).write(':');
                    writeAnything(entry.getValue(), decrementMaxDepth(maxDepth));
                    first = false;
                }
                writer.write('}');
                break;
            case NBTIntArray.ID:
                writeArray(((NBTIntArray) tag).getValue(), ((NBTIntArray) tag).length(), "I");
                break;
            case NBTLongArray.ID:
                writeArray(((NBTLongArray) tag).getValue(), ((NBTLongArray) tag).length(), "L");
                break;
            default:
                throw new IOException("unknown tag with id \"" + tag.getID() + "\"");
        }
    }

    private void writeArray(Object array, int length, String prefix) throws IOException {
        writer.append('[').append(prefix).write(';');
        for (int i = 0; i < length; i++) {
            writer.append(i == 0 ? "" : ",").write(Array.get(array, i).toString());
        }
        writer.write(']');
    }

    public static String escapeString(String s) {
        if (!NON_QUOTE_PATTERN.matcher(s).matches()) {
            StringBuilder sb = new StringBuilder();
            sb.append('"');
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '\\' || c == '"') {
                    sb.append('\\');
                }
                sb.append(c);
            }
            sb.append('"');
            return sb.toString();
        }
        return s;
    }
}
