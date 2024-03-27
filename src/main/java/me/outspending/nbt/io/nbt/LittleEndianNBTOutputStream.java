package me.outspending.nbt.io.nbt;

import me.outspending.nbt.*;
import me.outspending.nbt.arrays.NBTByteArray;
import me.outspending.nbt.arrays.NBTIntArray;
import me.outspending.nbt.arrays.NBTLongArray;
import me.outspending.nbt.io.MaxDepthIO;
import me.outspending.nbt.io.NamedTag;
import me.outspending.nbt.io.exceptions.ExceptionTriConsumer;
import me.outspending.nbt.numbers.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LittleEndianNBTOutputStream implements DataOutput, NBTOutput, MaxDepthIO, Closeable {
    private final DataOutputStream output;

    private static Map<Byte, ExceptionTriConsumer<LittleEndianNBTOutputStream, NBTTag<?>, Integer, IOException>> writers = new HashMap<>();
    private static Map<Class<?>, Byte> classIdMapping = new HashMap<>();

    static {
        put(NBTEnd.ID, (o, t, d) -> {}, NBTEnd.class);
        put(NBTByte.ID, (o, t, d) -> writeByte(o, t), NBTByte.class);
        put(NBTShort.ID, (o, t, d) -> writeShort(o, t), NBTShort.class);
        put(NBTInt.ID, (o, t, d) -> writeInt(o, t), NBTInt.class);
        put(NBTLong.ID, (o, t, d) -> writeLong(o, t), NBTLong.class);
        put(NBTFloat.ID, (o, t, d) -> writeFloat(o, t), NBTFloat.class);
        put(NBTDouble.ID, (o, t, d) -> writeDouble(o, t), NBTDouble.class);
        put(NBTByteArray.ID, (o, t, d) -> writeByteArray(o, t), NBTByteArray.class);
        put(NBTString.ID, (o, t, d) -> writeString(o, t), NBTString.class);
        put(NBTList.ID, LittleEndianNBTOutputStream::writeList, NBTList.class);
        put(NBTCompound.ID, LittleEndianNBTOutputStream::writeCompound, NBTCompound.class);
        put(NBTIntArray.ID, (o, t, d) -> writeIntArray(o, t), NBTIntArray.class);
        put(NBTLongArray.ID, (o, t, d) -> writeLongArray(o, t), NBTLongArray.class);
    }

    private static void put(byte id, ExceptionTriConsumer<LittleEndianNBTOutputStream, NBTTag<?>, Integer, IOException> f, Class<?> clazz) {
        writers.put(id, f);
        classIdMapping.put(clazz, id);
    }

    public LittleEndianNBTOutputStream(OutputStream out) {
        output = new DataOutputStream(out);
    }

    public LittleEndianNBTOutputStream(DataOutputStream out) {
        output = out;
    }

    public void writeTag(NamedTag tag, int maxDepth) throws IOException {
        writeByte(tag.getTag().getID());
        if (tag.getTag().getID() != 0) {
            writeUTF(tag.getName() == null ? "" : tag.getName());
        }
        writeRawTag(tag.getTag(), maxDepth);
    }

    public void writeTag(NBTTag<?> tag, int maxDepth) throws IOException {
        writeByte(tag.getID());
        if (tag.getID() != 0) {
            writeUTF("");
        }
        writeRawTag(tag, maxDepth);
    }

    public void writeRawTag(NBTTag<?> tag, int maxDepth) throws IOException {
        ExceptionTriConsumer<LittleEndianNBTOutputStream, NBTTag<?>, Integer, IOException> f;
        if ((f = writers.get(tag.getID())) == null) {
            throw new IOException("invalid tag \"" + tag.getID() + "\"");
        }
        f.accept(this, tag, maxDepth);
    }

    static byte idFromClass(Class<?> clazz) {
        Byte id = classIdMapping.get(clazz);
        if (id == null) {
            throw new IllegalArgumentException("unknown Tag class " + clazz.getName());
        }
        return id;
    }

    private static void writeByte(LittleEndianNBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeByte(((NBTByte) tag).asByte());
    }

    private static void writeShort(LittleEndianNBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeShort(((NBTShort) tag).asShort());
    }

    private static void writeInt(LittleEndianNBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeInt(((NBTInt) tag).asInt());
    }

    private static void writeLong(LittleEndianNBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeLong(((NBTLong) tag).asLong());
    }

    private static void writeFloat(LittleEndianNBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeFloat(((NBTFloat) tag).asFloat());
    }

    private static void writeDouble(LittleEndianNBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeDouble(((NBTDouble) tag).asDouble());
    }

    private static void writeString(LittleEndianNBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeUTF(((NBTString) tag).getValue());
    }

    private static void writeByteArray(LittleEndianNBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeInt(((NBTByteArray) tag).length());
        out.write(((NBTByteArray) tag).getValue());
    }

    private static void writeIntArray(LittleEndianNBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeInt(((NBTIntArray) tag).length());
        for (int i : ((NBTIntArray) tag).getValue()) {
            out.writeInt(i);
        }
    }

    private static void writeLongArray(LittleEndianNBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeInt(((NBTLongArray) tag).length());
        for (long l : ((NBTLongArray) tag).getValue()) {
            out.writeLong(l);
        }
    }

    private static void writeList(LittleEndianNBTOutputStream out, NBTTag<?> tag, int maxDepth) throws IOException {
        out.writeByte(idFromClass(((NBTList<?>) tag).getTypeClass()));
        out.writeInt(((NBTList<?>) tag).size());
        for (NBTTag<?> t : ((NBTList<?>) tag)) {
            out.writeRawTag(t, out.decrementMaxDepth(maxDepth));
        }
    }

    private static void writeCompound(LittleEndianNBTOutputStream out, NBTTag<?> tag, int maxDepth) throws IOException {
        for (Map.Entry<String, NBTTag<?>> entry : (NBTCompound) tag) {
            if (entry.getValue().getID() == 0) {
                throw new IOException("end tag not allowed");
            }
            out.writeByte(entry.getValue().getID());
            out.writeUTF(entry.getKey());
            out.writeRawTag(entry.getValue(), out.decrementMaxDepth(maxDepth));
        }
        out.writeByte(0);
    }

    @Override
    public void close() throws IOException {
        output.close();
    }

    @Override
    public void flush() throws IOException {
        output.flush();
    }

    @Override
    public void write(int b) throws IOException {
        output.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        output.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        output.write(b, off, len);
    }

    @Override
    public void writeBoolean(boolean v) throws IOException {
        output.writeBoolean(v);
    }

    @Override
    public void writeByte(int v) throws IOException {
        output.writeByte(v);
    }

    @Override
    public void writeShort(int v) throws IOException {
        output.writeShort(Short.reverseBytes((short) v));
    }

    @Override
    public void writeChar(int v) throws IOException {
        output.writeChar(Character.reverseBytes((char) v));
    }

    @Override
    public void writeInt(int v) throws IOException {
        output.writeInt(Integer.reverseBytes(v));
    }

    @Override
    public void writeLong(long v) throws IOException {
        output.writeLong(Long.reverseBytes(v));
    }

    @Override
    public void writeFloat(float v) throws IOException {
        output.writeInt(Integer.reverseBytes(Float.floatToIntBits(v)));
    }

    @Override
    public void writeDouble(double v) throws IOException {
        output.writeLong(Long.reverseBytes(Double.doubleToLongBits(v)));
    }

    @Override
    public void writeBytes(String s) throws IOException {
        output.writeBytes(s);
    }

    @Override
    public void writeChars(String s) throws IOException {
        output.writeChars(s);
    }

    @Override
    public void writeUTF(String s) throws IOException {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        writeShort(bytes.length);
        write(bytes);
    }
}
