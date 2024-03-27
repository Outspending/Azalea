package me.outspending.nbt.io.nbt;

import me.outspending.nbt.*;
import me.outspending.nbt.arrays.NBTByteArray;
import me.outspending.nbt.arrays.NBTIntArray;
import me.outspending.nbt.arrays.NBTLongArray;
import me.outspending.nbt.io.MaxDepthIO;
import me.outspending.nbt.io.NamedTag;
import me.outspending.nbt.io.exceptions.ExceptionBiFunction;
import me.outspending.nbt.numbers.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LittleEndianNBTInputStream implements DataInput, NBTInput, MaxDepthIO, Closeable {
    private final DataInputStream input;

    private static Map<Byte, ExceptionBiFunction<LittleEndianNBTInputStream, Integer, ? extends NBTTag<?>, IOException>> readers = new HashMap<>();
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
        put(NBTList.ID, LittleEndianNBTInputStream::readNBTList, NBTList.class);
        put(NBTCompound.ID, LittleEndianNBTInputStream::readCompound, NBTCompound.class);
        put(NBTIntArray.ID, (i, d) -> readIntArray(i), NBTIntArray.class);
        put(NBTLongArray.ID, (i, d) -> readLongArray(i), NBTLongArray.class);
    }

    private static void put(byte id, ExceptionBiFunction<LittleEndianNBTInputStream, Integer, ? extends NBTTag<?>, IOException> reader, Class<?> clazz) {
        readers.put(id, reader);
        idClassMapping.put(id, clazz);
    }

    public LittleEndianNBTInputStream(InputStream in) {
        input = new DataInputStream(in);
    }

    public LittleEndianNBTInputStream(DataInputStream in) {
        input = in;
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
        ExceptionBiFunction<LittleEndianNBTInputStream, Integer, ? extends NBTTag<?>, IOException> f;
        if ((f = readers.get(type)) == null) {
            throw new IOException("invalid tag id \"" + type + "\"");
        }
        return f.accept(this, maxDepth);
    }

    private static NBTByte readByte(LittleEndianNBTInputStream in) throws IOException {
        return new NBTByte(in.readByte());
    }

    private static NBTShort readShort(LittleEndianNBTInputStream in) throws IOException {
        return new NBTShort(in.readShort());
    }

    private static NBTInt readInt(LittleEndianNBTInputStream in) throws IOException {
        return new NBTInt(in.readInt());
    }

    private static NBTLong readLong(LittleEndianNBTInputStream in) throws IOException {
        return new NBTLong(in.readLong());
    }

    private static NBTFloat readFloat(LittleEndianNBTInputStream in) throws IOException {
        return new NBTFloat(in.readFloat());
    }

    private static NBTDouble readDouble(LittleEndianNBTInputStream in) throws IOException {
        return new NBTDouble(in.readDouble());
    }

    private static NBTString readString(LittleEndianNBTInputStream in) throws IOException {
        return new NBTString(in.readUTF());
    }

    private static NBTByteArray readByteArray(LittleEndianNBTInputStream in) throws IOException {
        NBTByteArray bat = new NBTByteArray(new byte[in.readInt()]);
        in.readFully(bat.getValue());
        return bat;
    }

    private static NBTIntArray readIntArray(LittleEndianNBTInputStream in) throws IOException {
        int l = in.readInt();
        int[] data = new int[l];
        NBTIntArray iat = new NBTIntArray(data);
        for (int i = 0; i < l; i++) {
            data[i] = in.readInt();
        }
        return iat;
    }

    private static NBTLongArray readLongArray(LittleEndianNBTInputStream in) throws IOException {
        int l = in.readInt();
        long[] data = new long[l];
        NBTLongArray iat = new NBTLongArray(data);
        for (int i = 0; i < l; i++) {
            data[i] = in.readLong();
        }
        return iat;
    }

    private static NBTList<?> readNBTList(LittleEndianNBTInputStream in, int maxDepth) throws IOException {
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

    private static NBTCompound readCompound(LittleEndianNBTInputStream in, int maxDepth) throws IOException {
        NBTCompound comp = new NBTCompound();
        for (int id = in.readByte() & 0xFF; id != 0; id = in.readByte() & 0xFF) {
            String key = in.readUTF();
            NBTTag<?> element = in.readTag((byte) id, in.decrementMaxDepth(maxDepth));
            comp.put(key, element);
        }
        return comp;
    }

    @Override
    public void readFully(byte[] b) throws IOException {
        input.readFully(b);
    }

    @Override
    public void readFully(byte[] b, int off, int len) throws IOException {
        input.readFully(b, off, len);
    }

    @Override
    public int skipBytes(int n) throws IOException {
        return input.skipBytes(n);
    }

    @Override
    public boolean readBoolean() throws IOException {
        return input.readBoolean();
    }

    @Override
    public byte readByte() throws IOException {
        return input.readByte();
    }

    @Override
    public int readUnsignedByte() throws IOException {
        return input.readUnsignedByte();
    }

    @Override
    public short readShort() throws IOException {
        return Short.reverseBytes(input.readShort());
    }

    public int readUnsignedShort() throws IOException {
        return Short.toUnsignedInt(Short.reverseBytes(input.readShort()));
    }

    @Override
    public char readChar() throws IOException {
        return Character.reverseBytes(input.readChar());
    }

    @Override
    public int readInt() throws IOException {
        return Integer.reverseBytes(input.readInt());
    }

    @Override
    public long readLong() throws IOException {
        return Long.reverseBytes(input.readLong());
    }

    @Override
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(Integer.reverseBytes(input.readInt()));
    }

    @Override
    public double readDouble() throws IOException {
        return Double.longBitsToDouble(Long.reverseBytes(input.readLong()));
    }

    @Override
    @Deprecated
    public String readLine() throws IOException {
        return input.readLine();
    }

    @Override
    public void close() throws IOException {
        input.close();
    }

    @Override
    public String readUTF() throws IOException {
        byte[] bytes = new byte[readUnsignedShort()];
        readFully(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
