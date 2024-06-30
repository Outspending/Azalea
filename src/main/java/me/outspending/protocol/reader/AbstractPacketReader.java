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
    public boolean readBoolean() {
        return NetworkTypes.BOOLEAN.read(buffer);
    }

    @Override
    public byte readByte() {
        return NetworkTypes.BYTE.read(buffer);
    }

    @Override
    public short readShort() {
        return NetworkTypes.SHORT.read(buffer);
    }

    @Override
    public int readInt() {
        return NetworkTypes.INT.read(buffer);
    }

    @Override
    public long readLong() {
        return NetworkTypes.LONG.read(buffer);
    }

    @Override
    public float readFloat() {
        return NetworkTypes.FLOAT.read(buffer);
    }

    @Override
    public double readDouble() {
        return NetworkTypes.DOUBLE.read(buffer);
    }

    @Override
    public @Nullable String readString() {
        return NetworkTypes.STRING.read(buffer);
    }

    @Override
    public int readVarInt() {
        return NetworkTypes.VARINT.read(buffer);
    }

    @Override
    public @Nullable Angle readAngle() {
        return Angle.fromNetwork(readByte());
    }

    @Override
    public @Nullable CompoundBinaryTag readNBTCompound() {
        return NetworkTypes.NBTCOMPOUND.read(buffer);
    }

    @Override
    public @Nullable NamespacedID readNamespacedID() {
        return NetworkTypes.NAMESPACEDID.read(buffer);
    }

    @Override
    public @Nullable Pos readPosition() {
        return NetworkTypes.POSITION.read(buffer);
    }

    @Override
    public @Nullable UUID readUUID() {
        return NetworkTypes.UUID.read(buffer);
    }

    @Override
    public byte[] readByteArray() {
        return NetworkTypes.BYTEARRAY.read(buffer);
    }

    @Override
    public byte[] getRemainingBytes() {
        byte[] remaining = new byte[buffer.remaining()];
        buffer.get(remaining);
        return remaining;
    }

}
