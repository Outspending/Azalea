package me.outspending.protocol.writer;

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
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;
import java.util.UUID;

public abstract class AbstractPacketWriter implements PacketWriter {
    private final ByteBuffer buffer;
    private final boolean isCompressed;

    public AbstractPacketWriter(boolean isCompressed) {
        this.buffer = ByteBuffer.allocateDirect(32000);
        this.isCompressed = isCompressed;
    }

    @Override
    public <T> void write(@NotNull NetworkType<T> type, T value) {
        type.write(buffer, value);
    }

    @Override
    public boolean isCompressed() {
        return isCompressed;
    }

    @Override
    public int getSize() {
        return buffer.capacity();
    }

    @Override
    public ByteBuffer getBuffer() {
        return buffer;
    }

    @Override
    public void writeBoolean(boolean b) {
        NetworkTypes.BOOLEAN_TYPE.write(buffer, b);
    }

    @Override
    public void writeByte(byte b) {
        NetworkTypes.BYTE_TYPE.write(buffer, b);
    }

    @Override
    public void writeUnsignedByte(int b) {
        NetworkTypes.UNSIGNED_BYTE_TYPE.write(buffer, b);
    }

    @Override
    public void writeShort(short s) {
        NetworkTypes.SHORT_TYPE.write(buffer, s);
    }

    @Override
    public void writeUnsignedShort(int s) {
        NetworkTypes.UNSIGNED_SHORT_TYPE.write(buffer, s);
    }

    @Override
    public void writeInt(int i) {
        NetworkTypes.INT_TYPE.write(buffer, i);
    }

    @Override
    public void writeLong(long l) {
        NetworkTypes.LONG_TYPE.write(buffer, l);
    }

    @Override
    public void writeFloat(float f) {
        NetworkTypes.FLOAT_TYPE.write(buffer, f);
    }

    @Override
    public void writeDouble(double d) {
        NetworkTypes.DOUBLE_TYPE.write(buffer, d);
    }

    @Override
    public void writeString(@NotNull String s) {
        NetworkTypes.STRING_TYPE.write(buffer, s);
    }

    @Override
    public void writeNamespacedKey(@NotNull NamespacedID id) {
        NetworkTypes.NAMESPACEDID_TYPE.write(buffer, id);
    }

    @Override
    public void writeVarInt(int i) {
        NetworkTypes.VARINT_TYPE.write(buffer, i);
    }

    @Override
    public void writeVarLong(long l) {
        NetworkTypes.VARLONG_TYPE.write(buffer, l);
    }

    @Override
    public void writeNBTCompound(@NotNull CompoundBinaryTag tag) {
        NetworkTypes.NBTCOMPOUND_TYPE.write(buffer, tag);
    }

    @Override
    public void writeLocation(@NotNull Location location) {
        NetworkTypes.LOCATION_TYPE.write(buffer, location);
    }

    @Override
    public void writeUUID(@NotNull UUID uuid) {
        NetworkTypes.UUID_TYPE.write(buffer, uuid);
    }

    @Override
    public void writeByteArray(byte[] array) {
        NetworkTypes.BYTEARRAY_TYPE.write(buffer, array);
    }

    @Override
    public void writeByteArray(byte[] array, int offset, int length) {
        buffer.put(array, offset, length);
    }

    @Override
    public void writeBitSet(@NotNull BitSet bitSet) {
        NetworkTypes.BITSET_TYPE.write(buffer, bitSet);
    }

    @Override
    public void writeSlot(@NotNull ItemStack itemStack) {
        NetworkTypes.SLOT_TYPE.write(buffer, itemStack);
    }

    @Override
    public void write(int b) {
        buffer.put((byte) b);
    }

    @Override
    public void writeLongArray(long[] array) {
        for (long value : array) {
            writeLong(value);
        }
    }

    @Override
    public void writeStream(ByteArrayOutputStream stream) {
        buffer.put(stream.toByteArray());
    }

    @Override
    public void writeByteBuffer(ByteBuffer buffer) {
        this.buffer.put(buffer);
    }

    @Override
    public void writeToStream(OutputStream stream) {
        try {
            byte[] array = new byte[buffer.remaining()];
            buffer.get(array);
            stream.write(buffer.array());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writePacket(@NotNull ClientPacket packet) {
        int currentPosition = buffer.position();
        buffer.position(currentPosition + 3);

        int idPosition = buffer.position();
        writeVarInt(packet.id());
        packet.write(this);

        int packetLength = buffer.position() - idPosition;
        buffer.position(currentPosition);
        writeVarInt(packetLength);
        buffer.position(currentPosition + 3 + packetLength);

        System.out.println(packetLength);
        PacketReader reader = PacketReader.createNormalReader(buffer);
        System.out.println("Packet ID: " + reader.getPacketID());
        System.out.println("Packet Length: " + reader.getPacketLength());
    }

    @Override
    public void clear() {
        buffer.reset();
    }
}
