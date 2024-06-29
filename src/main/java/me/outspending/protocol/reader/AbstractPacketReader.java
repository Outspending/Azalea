package me.outspending.protocol.reader;

import lombok.Getter;
import me.outspending.NamespacedID;
import me.outspending.position.Angle;
import me.outspending.position.Pos;
import me.outspending.protocol.NetworkType;
import me.outspending.protocol.NetworkTypes;
import me.outspending.protocol.exception.InvalidPacketException;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.util.UUID;

@Getter
public abstract class AbstractPacketReader implements PacketReader {
    protected final ByteBuffer buffer;

    public AbstractPacketReader(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public <T> @Nullable T read(@NotNull NetworkType<T> type) {
        return type.read(buffer);
    }

    @Override
    public boolean hasAnotherPacket() {
        return buffer.hasRemaining();
    }

    @Override
    public boolean readBoolean() {
        return NetworkTypes.BOOLEAN_TYPE.read(buffer);
    }

    @Override
    public byte readByte() {
        return NetworkTypes.BYTE_TYPE.read(buffer);
    }

    @Override
    public short readShort() {
        return NetworkTypes.SHORT_TYPE.read(buffer);
    }

    @Override
    public int readInt() {
        return NetworkTypes.INT_TYPE.read(buffer);
    }

    @Override
    public long readLong() {
        return NetworkTypes.LONG_TYPE.read(buffer);
    }

    @Override
    public float readFloat() {
        return NetworkTypes.FLOAT_TYPE.read(buffer);
    }

    @Override
    public double readDouble() {
        return NetworkTypes.DOUBLE_TYPE.read(buffer);
    }

    @Override
    public @Nullable String readString() {
        return NetworkTypes.STRING_TYPE.read(buffer);
    }

    @Override
    public int readVarInt() {
        return NetworkTypes.VARINT_TYPE.read(buffer);
    }

    @Override
    public @Nullable Angle readAngle() {
        return Angle.fromNetwork(readByte());
    }

    @Override
    public @Nullable CompoundBinaryTag readNBTCompound() {
        return NetworkTypes.NBTCOMPOUND_TYPE.read(buffer);
    }

    @Override
    public @Nullable NamespacedID readNamespacedID() {
        return NetworkTypes.NAMESPACEDID_TYPE.read(buffer);
    }

    @Override
    public @Nullable Pos readPosition() {
        return NetworkTypes.LOCATION_TYPE.read(buffer);
    }

    @Override
    public @Nullable UUID readUUID() {
        return NetworkTypes.UUID_TYPE.read(buffer);
    }

    @Override
    public byte[] readByteArray() {
        return NetworkTypes.BYTEARRAY_TYPE.read(buffer);
    }

    @Override
    public byte[] getRemainingBytes() {
        byte[] remaining = new byte[buffer.remaining()];
        buffer.get(remaining);
        return remaining;
    }

}
