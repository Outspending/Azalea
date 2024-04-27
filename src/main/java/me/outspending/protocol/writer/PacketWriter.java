package me.outspending.protocol.writer;

import me.outspending.position.Location;
import me.outspending.protocol.NetworkType;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public interface PacketWriter {
    <T> T write(@NotNull NetworkType<T> type);
    boolean isCompressed();

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
    // writeIdentifier
    void writeVarInt(int i);
    void writeVarLong(long l);
    // writeEntityMetaData
    // writeSlot
    void writeNBTCompound(@NotNull CompoundBinaryTag tag);
    void writeLocation(@NotNull Location location);
    // writeAngle
    void writeUUID(@NotNull UUID uuid);
    // writeBitSet
    // writeFixedBitSet
    <T> void writeOptional(@NotNull T element, @NotNull NetworkType<T> type);
    <T> void writeArray(@NotNull T[] array, Consumer<T> consumer);
    <T extends Enum<?>> void writeEnum(@NotNull T e);
    void writeByteArray(Byte[] array);
}
