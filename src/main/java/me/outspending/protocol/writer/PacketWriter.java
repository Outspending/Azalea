package me.outspending.protocol.writer;

import me.outspending.NamespacedID;
import me.outspending.Slot;
import me.outspending.block.ItemStack;
import me.outspending.position.Location;
import me.outspending.protocol.NetworkType;
import me.outspending.protocol.types.ClientPacket;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.UUID;
import java.util.function.Consumer;

public interface PacketWriter {
    static @NotNull PacketWriter createNormalWriter() {
        return new NormalPacketWriter();
    }

    static @NotNull PacketWriter createNormalWriter(ClientPacket packet) {
        return new NormalPacketWriter(packet);
    }

    <T> void write(@NotNull NetworkType<T> type, T value);
    boolean isCompressed();

    int getSize();
    ByteBuffer getBuffer();

    void writeBoolean(boolean b);
    void writeByte(byte b);
    void writeUnsignedByte(int b);
    void writeShort(short s);
    void writeUnsignedShort(int s);
    void writeInt(int i);
    void writeLong(long l); 
    void writeFloat(float f);
    void writeDouble(double d);
    void writeString(@NotNull String s);
    // writeTextComponent
    // writeJSONTextComponent
    void writeNamespacedKey(@NotNull NamespacedID id);
    void writeVarInt(int i);
    void writeVarLong(long l);
    // writeEntityMetaData
    void writeSlot(@NotNull ItemStack itemStack);
    void writeNBTCompound(@NotNull CompoundBinaryTag tag);
    void writeLocation(@NotNull Location location);
    // writeAngle
    void writeUUID(@NotNull UUID uuid);
    void writeBitSet(@NotNull BitSet bitSet);
    // writeFixedBitSet(@NotNull BitSet bitSet)
    <T> void writeOptional(@NotNull T element, @NotNull NetworkType<T> type);
    <T> void writeArray(@NotNull T[] array, Consumer<T> consumer);
    <T extends Enum<?>> void writeEnum(@NotNull T e);

    void writeByteArray(byte[] array);
    void writeByteBuffer(ByteBuffer buffer);
    void writeByteArray(byte[] array, int offset, int length);
    void writeLongArray(long[] array);

    void write(int b);

    void writeStream(ByteArrayOutputStream stream);

    void writePacket(@NotNull ClientPacket packet);
    void writeToStream(OutputStream stream);
    void clear();

    ByteBuffer get();
}
