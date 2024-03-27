package me.outspending.protocol;

import lombok.NoArgsConstructor;
import me.nullicorn.nedit.NBTWriter;
import me.nullicorn.nedit.type.NBTCompound;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.function.Consumer;

@NoArgsConstructor
public class PacketWriter {
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    public PacketWriter(@NotNull Packet packet, int length) {
        writeVarInt(length);
        writeVarInt(packet.getID());
        packet.write(this);
    }

    public void writeVarInt(int value) {
        do {
            int temp = value & SEGMENT_BITS;
            value >>>= 7;
            if (value != 0) {
                temp |= CONTINUE_BIT;
            }
            outputStream.write(temp);
        } while (value != 0);
    }

    public void writeVarLong(long value) {
        do {
            int temp = (int) (value & SEGMENT_BITS);
            value >>>= 7;
            if (value != 0) {
                temp |= CONTINUE_BIT;
            }
            outputStream.write(temp);
        } while (value != 0);
    }

    public void writeString(@NotNull String value) {
        byte[] bytes = value.getBytes();
        writeVarInt(bytes.length);
        outputStream.write(bytes, 0, bytes.length);
    }

    public void writeBoolean(boolean value) {
        outputStream.write(value ? 1 : 0);
    }

    public void writeShort(short value) {
        outputStream.write(value >> 8);
        outputStream.write(value);
    }

    public void writeInt(int value) {
        outputStream.write(value >> 24);
        outputStream.write(value >> 16);
        outputStream.write(value >> 8);
        outputStream.write(value);
    }

    public void writeLong(long value) {
        outputStream.write((int) (value >> 56));
        outputStream.write((int) (value >> 48));
        outputStream.write((int) (value >> 40));
        outputStream.write((int) (value >> 32));
        outputStream.write((int) (value >> 24));
        outputStream.write((int) (value >> 16));
        outputStream.write((int) (value >> 8));
        outputStream.write((int) value);
    }

    public void writeUUID(@NotNull UUID uuid) {
        writeLong(uuid.getMostSignificantBits());
        writeLong(uuid.getLeastSignificantBits());
    }

    public <T> void writeArray(@NotNull Iterable<T> array, @NotNull Consumer<T> consumer) {
        for (T element : array) {
            System.out.println("Writing element: " + element);
            consumer.accept(element);
        }
    }

    public void writeByteArray(byte[] bytes) {
        outputStream.write(bytes, 0, bytes.length);
    }

    public void writePacketWriter(@NotNull PacketWriter writer) {
        writeByteArray(writer.toByteArray());
    }

    public void writeNBTCompound(@NotNull NBTCompound compound) {
        try {
            NBTWriter.write(compound, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] toByteArray() {
        return outputStream.toByteArray();
    }

    public int size() {
        return outputStream.size();
    }

    public void reset() {
        outputStream.reset();
    }

    public void close() {
        try {
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
