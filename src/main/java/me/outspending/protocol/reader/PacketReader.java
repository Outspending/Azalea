package me.outspending.protocol.reader;

import me.outspending.NamespacedID;
import me.outspending.position.Angle;
import me.outspending.position.Pos;
import me.outspending.protocol.NetworkType;
import me.outspending.protocol.NetworkTypes;
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

    static @NotNull PacketReader createNormalReader(byte @NotNull [] bytes) {
        return createNormalReader(ByteBuffer.wrap(bytes));
    }

    <T> @Nullable T read(@NotNull NetworkType<T> type);

    default boolean hasAnotherPacket() {
        return this.getRemainingBytes().length > 0;
    }

    byte[] getRemainingBytes();

    boolean readBoolean();

    byte readByte();

    short readShort();

    int readInt();

    long readLong();

    float readFloat();

    double readDouble();

    @Nullable String readString();

    int readVarInt();

    @Nullable CompoundBinaryTag readNBTCompound();

    @Nullable NamespacedID readNamespacedID();

    @Nullable Pos readPosition();

    @Nullable Angle readAngle();

    @Nullable UUID readUUID();

    byte[] readByteArray();

}
