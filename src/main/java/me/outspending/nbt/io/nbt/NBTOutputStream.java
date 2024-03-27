package me.outspending.nbt.io.nbt;

import me.outspending.nbt.*;
import me.outspending.nbt.arrays.NBTByteArray;
import me.outspending.nbt.arrays.NBTIntArray;
import me.outspending.nbt.arrays.NBTLongArray;
import me.outspending.nbt.io.MaxDepthIO;
import me.outspending.nbt.io.NamedTag;
import me.outspending.nbt.io.exceptions.ExceptionTriConsumer;
import me.outspending.nbt.numbers.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class NBTOutputStream extends DataOutputStream implements NBTOutput, MaxDepthIO {
    private static Map<Byte, ExceptionTriConsumer<NBTOutputStream, NBTTag<?>, Integer, IOException>> writers = new HashMap<>();
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
        put(NBTList.ID, NBTOutputStream::writeList, NBTList.class);
        put(NBTCompound.ID, NBTOutputStream::writeCompound, NBTCompound.class);
        put(NBTIntArray.ID, (o, t, d) -> writeIntArray(o, t), NBTIntArray.class);
        put(NBTLongArray.ID, (o, t, d) -> writeLongArray(o, t), NBTLongArray.class);
    }

    private static void put(byte id, ExceptionTriConsumer<NBTOutputStream, NBTTag<?>, Integer, IOException> f, Class<?> clazz) {
        writers.put(id, f);
        classIdMapping.put(clazz, id);
    }

    public NBTOutputStream(OutputStream out) {
        super(out);
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

    @Override
    public void flush() throws IOException {
        // Do nothing
    }

    public void writeRawTag(NBTTag<?> tag, int maxDepth) throws IOException {
        ExceptionTriConsumer<NBTOutputStream, NBTTag<?>, Integer, IOException> f;
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

    private static void writeByte(NBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeByte(((NBTByte) tag).asByte());
    }

    private static void writeShort(NBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeShort(((NBTShort) tag).asShort());
    }

    private static void writeInt(NBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeInt(((NBTInt) tag).asInt());
    }

    private static void writeLong(NBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeLong(((NBTLong) tag).asLong());
    }

    private static void writeFloat(NBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeFloat(((NBTFloat) tag).asFloat());
    }

    private static void writeDouble(NBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeDouble(((NBTDouble) tag).asDouble());
    }

    private static void writeString(NBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeUTF(((NBTString) tag).getValue());
    }

    private static void writeByteArray(NBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeInt(((NBTByteArray) tag).length());
        out.write(((NBTByteArray) tag).getValue());
    }

    private static void writeIntArray(NBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeInt(((NBTIntArray) tag).length());
        for (int i : ((NBTIntArray) tag).getValue()) {
            out.writeInt(i);
        }
    }

    private static void writeLongArray(NBTOutputStream out, NBTTag<?> tag) throws IOException {
        out.writeInt(((NBTLongArray) tag).length());
        for (long l : ((NBTLongArray) tag).getValue()) {
            out.writeLong(l);
        }
    }

    private static void writeList(NBTOutputStream out, NBTTag<?> tag, int maxDepth) throws IOException {
        out.writeByte(idFromClass(((NBTList<?>) tag).getTypeClass()));
        out.writeInt(((NBTList<?>) tag).size());
        for (NBTTag<?> t : ((NBTList<?>) tag)) {
            out.writeRawTag(t, out.decrementMaxDepth(maxDepth));
        }
    }

    private static void writeCompound(NBTOutputStream out, NBTTag<?> tag, int maxDepth) throws IOException {
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
}
