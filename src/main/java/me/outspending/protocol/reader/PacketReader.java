package me.outspending.protocol.reader;

import me.outspending.NamespacedID;
import me.outspending.Slot;
import me.outspending.block.ItemStack;
import me.outspending.position.Location;
import me.outspending.protocol.CompressionType;
import me.outspending.protocol.NetworkType;
import net.kyori.adventure.nbt.CompoundBinaryTag;
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

    static @NotNull PacketReader createCompressedReader(@NotNull ByteBuffer buffer) {
        return CompressedPacketReader.create(buffer);
    }

    static @NotNull PacketReader createReader(@NotNull ByteBuffer buffer, int threshold) {
        if (buffer.remaining() < threshold) {
            return createNormalReader(buffer);
        }
        return createCompressedReader(buffer);
    }

    static @NotNull PacketReader createReader(@NotNull ByteBuffer buffer, @NotNull CompressionType type) {
        return switch (type) {
            case NONE -> createNormalReader(buffer);
            case ZLIB -> createCompressedReader(buffer);
        };
    }

    int getPacketLength();
    int getPacketID();

    ByteBuffer getBuffer();

    <T> @Nullable T read(@NotNull NetworkType<T> type);
    boolean hasAnotherPacket();
    boolean isCompressed();
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
    // readTextComponent
    // readJSONTextComponent
    @Nullable NamespacedID readNamespacedKey();
    int readVarInt();
    long readVarLong();
    // readEntityMetaData
    @Nullable ItemStack readSlot();
    @Nullable CompoundBinaryTag readNBTCompound();
    @Nullable Location readLocation();
    // readAngle
    @Nullable UUID readUUID();
    // readBitSet
    // readFixedBitSet
    <T> @Nullable Optional<T> readOptional(@NotNull Function<PacketReader, T> elementReader);
    <T> @Nullable T[] readArray(@NotNull Function<PacketReader, T> elementReader, @NotNull IntFunction<T[]> arrayCreator);
    <T extends Enum<?>> @Nullable T readEnum(@NotNull Class<T> enumClass);
    byte[] readByteArray();
}
