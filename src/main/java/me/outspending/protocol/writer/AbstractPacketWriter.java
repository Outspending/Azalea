package me.outspending.protocol.writer;

import it.unimi.dsi.fastutil.Pair;
import lombok.SneakyThrows;
import me.outspending.NamespacedID;
import me.outspending.Slot;
import me.outspending.block.ItemStack;
import me.outspending.position.Location;
import me.outspending.protocol.NetworkType;
import me.outspending.protocol.NetworkTypes;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;
import java.util.UUID;

public abstract class AbstractPacketWriter implements PacketWriter {
    protected final ByteArrayOutputStream byteStream;
    protected final DataOutputStream stream;

    private final boolean isCompressed;

    public AbstractPacketWriter(boolean isCompressed) {
        this.byteStream = new ByteArrayOutputStream();
        this.stream = new DataOutputStream(byteStream);
        this.isCompressed = isCompressed;
    }

    @Override
    public <T> void write(@NotNull NetworkType<T> type, T value) throws IOException {
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
        return byteStream;
    }

    @Override
    @SneakyThrows
    public void writeBoolean(boolean b) {
        NetworkTypes.BOOLEAN_TYPE.write(stream, b);
    }

    @Override
    @SneakyThrows
    public void writeByte(byte b) {
        NetworkTypes.BYTE_TYPE.write(stream, b);
    }

    @Override
    @SneakyThrows
    public void writeUnsignedByte(int b) {
        NetworkTypes.UNSIGNED_BYTE_TYPE.write(stream, b);
    }

    @Override
    @SneakyThrows
    public void writeShort(short s) {
        NetworkTypes.SHORT_TYPE.write(stream, s);
    }

    @Override
    @SneakyThrows
    public void writeUnsignedShort(int s) {
        NetworkTypes.UNSIGNED_SHORT_TYPE.write(stream, s);
    }

    @Override
    @SneakyThrows
    public void writeInt(int i) {
        NetworkTypes.INT_TYPE.write(stream, i);
    }

    @Override
    @SneakyThrows
    public void writeLong(long l) {
        NetworkTypes.LONG_TYPE.write(stream, l);
    }

    @Override
    @SneakyThrows
    public void writeFloat(float f) {
        NetworkTypes.FLOAT_TYPE.write(stream, f);
    }

    @Override
    @SneakyThrows
    public void writeDouble(double d) {
        NetworkTypes.DOUBLE_TYPE.write(stream, d);
    }

    @Override
    @SneakyThrows
    public void writeString(@NotNull String s) {
        NetworkTypes.STRING_TYPE.write(stream, s);
    }

    @Override
    @SneakyThrows
    public void writeNamespacedKey(@NotNull NamespacedID id) {
        NetworkTypes.NAMESPACEDID_TYPE.write(stream, id);
    }

    @Override
    @SneakyThrows
    public void writeVarInt(int i) {
        NetworkTypes.VARINT_TYPE.write(stream, i);
    }

    @Override
    @SneakyThrows
    public void writeVarLong(long l) {
        NetworkTypes.VARLONG_TYPE.write(stream, l);
    }

    @Override
    @SneakyThrows
    public void writeNBTCompound(@NotNull CompoundBinaryTag tag) {
        NetworkTypes.NBTCOMPOUND_TYPE.write(stream, tag);
    }

    @Override
    @SneakyThrows
    public void writeLocation(@NotNull Location location) {
        NetworkTypes.LOCATION_TYPE.write(stream, location);
    }

    @Override
    @SneakyThrows
    public void writeUUID(@NotNull UUID uuid) {
        NetworkTypes.UUID_TYPE.write(stream, uuid);
    }

    @Override
    @SneakyThrows
    public void writeByteArray(byte[] array) {
        NetworkTypes.BYTEARRAY_TYPE.write(stream, array);
    }

    @Override
    @SneakyThrows
    public void writeByteArray(byte[] array, int offset, int length) {
        stream.write(array, offset, length);
    }

    @Override
    @SneakyThrows
    public void writeBitSet(@NotNull BitSet bitSet) {
        NetworkTypes.BITSET_TYPE.write(stream, bitSet);
    }

    @Override
    @SneakyThrows
    public void writeSlot(@NotNull ItemStack itemStack) {
        NetworkTypes.SLOT_TYPE.write(stream, itemStack);
    }

    @Override
    @SneakyThrows
    public void write(int b) throws IOException {
        stream.writeByte((byte) b);
    }

    @Override
    @SneakyThrows
    public void writeLongArray(long[] array) {
        for (long value : array) {
            writeLong(value);
        }
    }

    @Override
    @SneakyThrows
    public void flush() {
        stream.flush();
    }

    @Override
    public byte[] toByteArray() {
        return byteStream.toByteArray();
    }
}
