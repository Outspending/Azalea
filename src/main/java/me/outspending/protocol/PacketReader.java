package me.outspending.protocol;

import com.github.steveice10.opennbt.NBTIO;
import com.github.steveice10.opennbt.NBTInputStream;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.IntFunction;

@Getter(AccessLevel.PUBLIC)
public class PacketReader extends ByteArrayInputStream {
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

    private final int length;
    private final int packetID;

    public PacketReader(byte[] buffer) {
        super(buffer);

        this.length = readVarInt();
        this.packetID = readVarInt();
    }

    public int readVarInt() {
        int value = 0;
        int size = 0;
        int b;

        do {
            b = read();
            value |= (b & SEGMENT_BITS) << (size++ * 7);
        } while ((b & CONTINUE_BIT) != 0);

        return value;
    }

    public long readVarLong() {
        long value = 0;
        int size = 0;
        int b;

        do {
            b = read();
            value |= (b & SEGMENT_BITS) << (size++ * 7);
        } while ((b & CONTINUE_BIT) != 0);

        return value;
    }

    public @Nullable String readString() {
        int length = readVarInt();
        byte[] bytes = new byte[length];
        read(bytes, 0, length);
        return new String(bytes);
    }

    public byte[] readByteArray() {
        int length = readVarInt();
        byte[] bytes = new byte[length];
        read(bytes, 0, length);
        return bytes;
    }

    public byte readByte() {
        return (byte) read();
    }

    public int readUnsignedByte() {
        return readByte() & 0xFF;
    }

    public boolean readBoolean() {
        return read() == 1;
    }

    public short readShort() {
        return (short) ((read() << 8) | read());
    }

    public int readInt() {
        return (read() << 24) | (read() << 16) | (read() << 8) | read();
    }

    public long readLong() {
        return ((long) read() << 56) | ((long) read() << 48) | ((long) read() << 40) | ((long) read() << 32) | ((long) read() << 24) | ((long) read() << 16) | ((long) read() << 8) | read();
    }

    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }

    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    public @Nullable UUID readUUID() {
        return new UUID(readLong(), readLong());
    }

    public <T> @Nullable List<T> readArray(@NotNull Function<PacketReader, T> reader, @NotNull IntFunction<T[]> generator) {
        int length = readVarInt();
        T[] array = generator.apply(length);
        System.out.println("Length: " + length);
        for (int i = 0; i < length; i++) {
            array[i] = reader.apply(this);
        }

        return Arrays.asList(array);
    }

    public <T extends Tag> @Nullable T readAnyTag(Class<T> expected) {
        try {
            Tag tag = NBTIO.readAnyTag(this);

            if (tag == null) {
                return null;
            }

            if (!expected.isInstance(tag)) {
                throw new IOException("Expected " + expected.getName() + " but got " + tag.getClass().getName());
            }

            return expected.cast(tag);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
