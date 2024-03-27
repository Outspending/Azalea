package me.outspending.protocol;

import lombok.AccessLevel;
import lombok.Getter;
import me.nullicorn.nedit.NBTReader;
import me.nullicorn.nedit.type.NBTCompound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.IntFunction;

@Getter(AccessLevel.PUBLIC)
public class PacketReader {
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

    private final int length;
    private final int packetId;

    private final ByteArrayInputStream inputStream;

    public PacketReader(byte[] buffer) {
        inputStream = new ByteArrayInputStream(buffer);

        this.length = readVarInt();
        this.packetId = readVarInt();
    }

    public int readVarInt() {
        int value = 0;
        int size = 0;
        int b;

        do {
            b = inputStream.read();
            value |= (b & SEGMENT_BITS) << (size++ * 7);
        } while ((b & CONTINUE_BIT) != 0);

        return value;
    }

    public long readVarLong() {
        long value = 0;
        int size = 0;
        int b;

        do {
            b = inputStream.read();
            value |= (b & SEGMENT_BITS) << (size++ * 7);
        } while ((b & CONTINUE_BIT) != 0);

        return value;
    }

    public @Nullable String readString() {
        int length = readVarInt();
        byte[] bytes = new byte[length];
        inputStream.read(bytes, 0, length);
        return new String(bytes);
    }

    public boolean readBoolean() {
        return inputStream.read() == 1;
    }

    public short readShort() {
        return (short) ((inputStream.read() << 8) | inputStream.read());
    }

    public int readInt() {
        return (inputStream.read() << 24) | (inputStream.read() << 16) | (inputStream.read() << 8) | inputStream.read();
    }

    public long readLong() {
        return ((long) inputStream.read() << 56) | ((long) inputStream.read() << 48) | ((long) inputStream.read() << 40) | ((long) inputStream.read() << 32) | ((long) inputStream.read() << 24) | ((long) inputStream.read() << 16) | ((long) inputStream.read() << 8) | inputStream.read();
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

    public @Nullable NBTCompound readNBTCompound() {
        NBTCompound compound;
        try {
            compound = NBTReader.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read NBT compound", e);
        }

        return compound;
    }
}
