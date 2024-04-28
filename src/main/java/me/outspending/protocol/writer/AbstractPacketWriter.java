package me.outspending.protocol.writer;

import me.outspending.NamespacedID;
import me.outspending.position.Location;
import me.outspending.protocol.NetworkType;
import me.outspending.protocol.NetworkTypes;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.BitSet;
import java.util.UUID;

public abstract class AbstractPacketWriter implements PacketWriter {
    private final ByteArrayOutputStream stream;
    private final boolean isCompressed;

    public AbstractPacketWriter(boolean isCompressed) {
        this.stream = new ByteArrayOutputStream();
        this.isCompressed = isCompressed;
    }

    @Override
    public <T> void write(@NotNull NetworkType<T> type, T value) {
        type.write(stream, value);
    }

    @Override
    public boolean isCompressed() {
        return isCompressed;
    }

    @Override
    public int getSize() {
        return stream.size();
    }

    @Override
    public ByteArrayOutputStream getStream() {
        return stream;
    }

    @Override
    public void writeBoolean(boolean b) {
        NetworkTypes.BOOLEAN_TYPE.write(stream, b);
    }

    @Override
    public void writeByte(byte b) {
        NetworkTypes.BYTE_TYPE.write(stream, b);
    }

    @Override
    public void writeUnsignedByte(int b) {
        NetworkTypes.UNSIGNED_BYTE_TYPE.write(stream, b);
    }

    @Override
    public void writeShort(short s) {
        NetworkTypes.SHORT_TYPE.write(stream, s);
    }

    @Override
    public void writeUnsignedShort(int s) {
        NetworkTypes.UNSIGNED_SHORT_TYPE.write(stream, s);
    }

    @Override
    public void writeInt(int i) {
        NetworkTypes.INT_TYPE.write(stream, i);
    }

    @Override
    public void writeLong(long l) {
        NetworkTypes.LONG_TYPE.write(stream, l);
    }

    @Override
    public void writeFloat(float f) {
        NetworkTypes.FLOAT_TYPE.write(stream, f);
    }

    @Override
    public void writeDouble(double d) {
        NetworkTypes.DOUBLE_TYPE.write(stream, d);
    }

    @Override
    public void writeString(@NotNull String s) {
        NetworkTypes.STRING_TYPE.write(stream, s);
    }

    @Override
    public void writeNamespacedKey(@NotNull NamespacedID id) {
        NetworkTypes.NAMESPACEDID_TYPE.write(stream, id);
    }

    @Override
    public void writeVarInt(int i) {
        NetworkTypes.VARINT_TYPE.write(stream, i);
    }

    @Override
    public void writeVarLong(long l) {
        NetworkTypes.VARLONG_TYPE.write(stream, l);
    }

    @Override
    public void writeNBTCompound(@NotNull CompoundBinaryTag tag) {
        NetworkTypes.NBTCOMPOUND_TYPE.write(stream, tag);
    }

    @Override
    public void writeLocation(@NotNull Location location) {
        NetworkTypes.LOCATION_TYPE.write(stream, location);
    }

    @Override
    public void writeUUID(@NotNull UUID uuid) {
        NetworkTypes.UUID_TYPE.write(stream, uuid);
    }

    @Override
    public void writeByteArray(byte[] array) {
        NetworkTypes.BYTEARRAY_TYPE.write(stream, array);
    }

    @Override
    public void writeBitSet(@NotNull BitSet bitSet) {
        NetworkTypes.BITSET_TYPE.write(stream, bitSet);
    }

    @Override
    public void writeToStream(OutputStream stream) {
        try {
            this.stream.writeTo(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
