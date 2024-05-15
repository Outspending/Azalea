package me.outspending.protocol.writer;

import me.outspending.NamespacedID;
import me.outspending.item.ItemStack;
import me.outspending.position.Angle;
import me.outspending.position.Location;
import me.outspending.protocol.NetworkType;
import me.outspending.protocol.types.ClientPacket;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    <T> void write(@NotNull NetworkType<T> type, T value) throws IOException;
    boolean isCompressed();

    int getSize();
    ByteArrayOutputStream getStream();

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
    void writeTextComponent(@NotNull Component component);
    void writeJSONTextComponent(@NotNull Component component);
    void writeNamespacedKey(@NotNull NamespacedID id);
    void writeVarInt(int i);
    void writeVarLong(long l);
    // writeEntityMetaData
    // void writeSlot(@NotNull ItemStack itemStack);
    void writeNBTCompound(@NotNull CompoundBinaryTag tag);
    void writeLocation(@NotNull Location location);
    void writeAngle(@NotNull Angle angle);
    void writeUUID(@NotNull UUID uuid);
    void writeBitSet(@NotNull BitSet bitSet);
    // writeFixedBitSet(@NotNull BitSet bitSet)
    <T> void writeOptional(@NotNull T element, @NotNull NetworkType<T> type);
    <T> void writeArray(@NotNull T[] array, Consumer<T> consumer);
    <T extends Enum<?>> void writeEnum(@NotNull T e);

    void writeByteArray(byte[] array);
    void writeByteArray(byte[] array, int offset, int length);
    void writeLongArray(long[] array);

    void write(int b) throws IOException;

    byte[] toByteArray();
    void flush();
}
