package me.outspending.protocol;

import org.jetbrains.annotations.NotNull;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class VarNum {
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

    public static int readVarInt(@NotNull ByteBuffer buffer) {
        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = buffer.get();
            value |= (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 32) throw new RuntimeException("VarInt is too big");
        }

        return value;
    }

    public static long readVarLong(@NotNull ByteBuffer buffer) {
        long value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = buffer.get();
            value |= (long) (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 64) throw new RuntimeException("VarLong is too big");
        }

        return value;
    }

    public static void writeVarInt(@NotNull DataOutputStream stream, int value) throws IOException {
        int newValue = value;
        while (true) {
            if ((newValue & ~SEGMENT_BITS) == 0) {
                stream.writeByte((byte) newValue);
                return;
            }

            stream.writeByte((byte) ((byte) (newValue & SEGMENT_BITS) | CONTINUE_BIT));
            newValue >>>= 7;
        }
    }

    public static void writeVarLong(@NotNull DataOutputStream stream, long value) throws IOException {
        long newValue = value;
        while (true) {
            if ((newValue & ~((long) SEGMENT_BITS)) == 0) {
                stream.writeByte((byte) newValue);
                return;
            }

            stream.writeByte((byte) ((byte) (newValue & SEGMENT_BITS) | CONTINUE_BIT));
            newValue >>>= 7;
        }
    }

    public static int getVarIntSize(int value) {
        int size = 0;
        do {
            value >>>= 7;
            size++;
        } while (value != 0);
        return size;
    }

    public static int getVarLongSize(long value) {
        int size = 0;
        do {
            value >>>= 7;
            size++;
        } while (value != 0);
        return size;
    }

}
