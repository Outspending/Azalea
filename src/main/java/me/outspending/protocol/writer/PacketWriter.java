package me.outspending.protocol.writer;

import me.outspending.NamespacedID;
import me.outspending.position.Angle;
import me.outspending.position.Pos;
import me.outspending.protocol.NetworkType;
import me.outspending.protocol.Writable;
import me.outspending.protocol.types.ClientPacket;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.StringBinaryTag;
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

    default void write(@NotNull Writable writable) {
        writable.write(this);
    }

    void writeBoolean(boolean b);

    void writeByte(byte b);

    void writeShort(short s);

    void writeInt(int i);

    void writeLong(long l);

    void writeFloat(float f);

    void writeDouble(double d);

    void writeString(@NotNull String s);

    void writeTextComponent(@NotNull Component component);

    void writeNamespacedKey(@NotNull NamespacedID id);

    void writeVarInt(int i);


    void writeVarLong(long l);

    void writeNBTCompound(@NotNull CompoundBinaryTag tag);

    void writePosition(@NotNull Pos pos);

    void writeAngle(@NotNull Angle angle);

    void writeUUID(@NotNull UUID uuid);

    void writeBitSet(@NotNull BitSet bitSet);

    void writeByteArray(byte[] array);

    void write(int b) throws IOException;

    byte[] toByteArray();

    void flush();

    int getSize();

    ByteArrayOutputStream getStream();

}
