package me.outspending.nbt.io.nbt;

import me.outspending.nbt.*;
import me.outspending.nbt.arrays.NBTByteArray;
import me.outspending.nbt.arrays.NBTIntArray;
import me.outspending.nbt.arrays.NBTLongArray;
import me.outspending.nbt.io.MaxDepthIO;
import me.outspending.nbt.io.NamedTag;
import me.outspending.nbt.io.exceptions.ExceptionBiFunction;
import me.outspending.nbt.numbers.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class NBTInputStream extends DataInputStream implements NBTInput, MaxDepthIO {
    private static Map<Byte, ExceptionBiFunction<NBTInputStream, Integer, ? extends NBTTag<?>, IOException>> readers = new HashMap<>();
    private static Map<Byte, Class<?>> idClassMapping = new HashMap<>();

    static {
        put(NBTEnd.ID, (i, d) -> NBTEnd.INSTANCE, NBTEnd.class);
        put(NBTByte.ID, (i, d) -> readByte(i), NBTByte.class);
        put(NBTShort.ID, (i, d) -> readShort(i), NBTShort.class);
        put(NBTInt.ID, (i, d) -> readInt(i), NBTInt.class);
        put(NBTLong.ID, (i, d) -> readLong(i), NBTLong.class);
        put(NBTFloat.ID, (i, d) -> readFloat(i), NBTFloat.class);
        put(NBTDouble.ID, (i, d) -> readDouble(i), NBTDouble.class);
        put(NBTByteArray.ID, (i, d) -> readByteArray(i), NBTByteArray.class);
        put(NBTString.ID, (i, d) -> readString(i), NBTString.class);
        put(NBTList.ID, NBTInputStream::readNBTList, NBTList.class);
        put(NBTCompound.ID, NBTInputStream::readCompound, NBTCompound.class);
        put(NBTIntArray.ID, (i, d) -> readIntArray(i), NBTIntArray.class);
        put(NBTLongArray.ID, (i, d) -> readLongArray(i), NBTLongArray.class);
    }

    private static void put(byte id, ExceptionBiFunction<NBTInputStream, Integer, ? extends NBTTag<?>, IOException> reader, Class<?> clazz) {
        readers.put(id, reader);
        idClassMapping.put(id, clazz);
    }

    public NBTInputStream(InputStream in) {
        super(in);
    }

    public NamedTag readTag(int maxDepth) throws IOException {
        byte id = readByte();
        return new NamedTag(readUTF(), readTag(id, maxDepth));
    }

    public NBTTag<?> readRawTag(int maxDepth) throws IOException {
        byte id = readByte();
        return readTag(id, maxDepth);
    }

    private NBTTag<?> readTag(byte type, int maxDepth) throws IOException {
        ExceptionBiFunction<NBTInputStream, Integer, ? extends NBTTag<?>, IOException> f;
        if ((f = readers.get(type)) == null) {
            throw new IOException("invalid tag id \"" + type + "\"");
        }
        return f.accept(this, maxDepth);
    }

    private static NBTByte readByte(NBTInputStream in) throws IOException {
        return new NBTByte(in.readByte());
    }

    private static NBTShort readShort(NBTInputStream in) throws IOException {
        return new NBTShort(in.readShort());
    }

    private static NBTInt readInt(NBTInputStream in) throws IOException {
        return new NBTInt(in.readInt());
    }

    private static NBTLong readLong(NBTInputStream in) throws IOException {
        return new NBTLong(in.readLong());
    }

    private static NBTFloat readFloat(NBTInputStream in) throws IOException {
        return new NBTFloat(in.readFloat());
    }

    private static NBTDouble readDouble(NBTInputStream in) throws IOException {
        return new NBTDouble(in.readDouble());
    }

    private static NBTString readString(NBTInputStream in) throws IOException {
        return new NBTString(in.readUTF());
    }

    private static NBTByteArray readByteArray(NBTInputStream in) throws IOException {
        NBTByteArray bat = new NBTByteArray(new byte[in.readInt()]);
        in.readFully(bat.getValue());
        return bat;
    }

    private static NBTIntArray readIntArray(NBTInputStream in) throws IOException {
        int l = in.readInt();
        int[] data = new int[l];
        NBTIntArray iat = new NBTIntArray(data);
        for (int i = 0; i < l; i++) {
            data[i] = in.readInt();
        }
        return iat;
    }

    private static NBTLongArray readLongArray(NBTInputStream in) throws IOException {
        int l = in.readInt();
        long[] data = new long[l];
        NBTLongArray iat = new NBTLongArray(data);
        for (int i = 0; i < l; i++) {
            data[i] = in.readLong();
        }
        return iat;
    }

    private static NBTList<?> readNBTList(NBTInputStream in, int maxDepth) throws IOException {
        byte listType = in.readByte();
        NBTList<?> list = NBTList.createUnchecked(idClassMapping.get(listType));
        int length = in.readInt();
        if (length < 0) {
            length = 0;
        }
        for (int i = 0; i < length; i++) {
            list.addUnchecked(in.readTag(listType, in.decrementMaxDepth(maxDepth)));
        }
        return list;
    }

    private static NBTCompound readCompound(NBTInputStream in, int maxDepth) throws IOException {
        NBTCompound comp = new NBTCompound();
        for (int id = in.readByte() & 0xFF; id != 0; id = in.readByte() & 0xFF) {
            String key = in.readUTF();
            NBTTag<?> element = in.readTag((byte) id, in.decrementMaxDepth(maxDepth));
            comp.put(key, element);
        }
        return comp;
    }
}
