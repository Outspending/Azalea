package me.outspending.protocol.writer;

import lombok.SneakyThrows;
import me.outspending.NamespacedID;
import me.outspending.position.Angle;
import me.outspending.position.Pos;
import me.outspending.protocol.NetworkType;
import me.outspending.protocol.NetworkTypes;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.StringBinaryTag;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.UUID;

public abstract class AbstractPacketWriter implements PacketWriter {
    protected final ByteArrayOutputStream byteStream;
    protected final DataOutputStream stream;

    public AbstractPacketWriter() {
        this.byteStream = new ByteArrayOutputStream();
        this.stream = new DataOutputStream(byteStream);
    }

    @Override
    public <T> void write(@NotNull NetworkType<T> type, T value) throws IOException {
        type.write(stream, value);
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
    public void writeAngle(@NotNull Angle angle) {
        writeByte(angle.toNetwork());
    }

    @Override
    @SneakyThrows
    public void writeNBTCompound(@NotNull CompoundBinaryTag tag) {
        NetworkTypes.NBTCOMPOUND_TYPE.write(stream, tag);
    }

    @Override
    @SneakyThrows
    public void writePosition(@NotNull Pos pos) {
        NetworkTypes.LOCATION_TYPE.write(stream, pos);
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
    public void writeBitSet(@NotNull BitSet bitSet) {
        NetworkTypes.BITSET_TYPE.write(stream, bitSet);
    }

//    @Override
//    @SneakyThrows
//    public void writeSlot(@NotNull ItemStack itemStack) {
//        NetworkTypes.SLOT_TYPE.write(stream, itemStack);
//    }

    @Override
    @SneakyThrows
    public void writeJSONTextComponent(@NotNull Component component) {
        NetworkTypes.JSON_TEXT_COMPONENT_TYPE.write(stream, component);
    }

    @Override
    @SneakyThrows
    public void writeTextComponent(@NotNull Component component) {
        NetworkTypes.TEXT_COMPONENT_TYPE.write(stream, component);
    }

    @Override
    @SneakyThrows
    public void write(int b) throws IOException {
        stream.writeByte((byte) b);
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
