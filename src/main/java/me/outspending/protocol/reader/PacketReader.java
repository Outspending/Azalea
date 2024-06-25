package me.outspending.protocol.reader;

import me.outspending.NamespacedID;
import me.outspending.position.Angle;
import me.outspending.position.Pos;
import me.outspending.protocol.NetworkType;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.IntFunction;

public interface PacketReader {
    static @NotNull PacketReader createNormalReader(@NotNull ByteBuffer buffer) {
        return NormalPacketReader.create(buffer);
    }

    static @MonotonicNonNull PacketReader createNormalReader(byte @NotNull [] bytes) {
        return createNormalReader(ByteBuffer.wrap(bytes));
    }

    ByteBuffer getBuffer();

    <T> @Nullable T read(@NotNull NetworkType<T> type);
    boolean hasAnotherPacket();
    byte[] getRemainingBytes();
    byte[] getAllBytes();

    boolean readBoolean();
    byte readByte();
    int readUnsignedByte();
    short readShort();
    int readUnsignedShort();
    int readInt();
    long readLong();
    float readFloat();
    double readDouble();
    @Nullable String readString();
    @NotNull Component readTextComponent();
    @Nullable Component readJSONTextComponent();
    @Nullable NamespacedID readNamespacedKey();
    int readVarInt();
    long readVarLong();
    // readEntityMetaData
    // @Nullable ItemStack readSlot();
    @Nullable CompoundBinaryTag readNBTCompound();
    @Nullable Pos readPosition();
    @Nullable Angle readAngle();
    @Nullable UUID readUUID();
    // readBitSet
    // readFixedBitSet
    <T> @Nullable Optional<T> readOptional(@NotNull Function<PacketReader, T> elementReader);
    <T> @Nullable T[] readArray(@NotNull Function<PacketReader, T> elementReader, @NotNull IntFunction<T[]> arrayCreator);
    <T extends Enum<?>> @Nullable T readEnum(@NotNull Class<T> enumClass);
    byte[] readByteArray();
    byte[] readByteArray(int length);
}
