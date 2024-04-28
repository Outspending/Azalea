package me.outspending.protocol.writer;

import me.outspending.position.Location;
import me.outspending.protocol.NetworkType;
import me.outspending.protocol.types.ClientPacket;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.function.Consumer;

public interface PacketWriter {
    static @NotNull PacketWriter createNormalWriter() {
        return new NormalPacketWriter();
    }

    static @NotNull PacketWriter createNormalWriter(ClientPacket packet) {
        return new NormalPacketWriter(packet);
    }

    default int getPacketLength(ClientPacket packet) {
        PacketWriter writer = new NormalPacketWriter();
        writer.writeVarInt(packet.getId());
        packet.write(writer);

        return writer.getSize();
    }

    <T> void write(@NotNull NetworkType<T> type, T value);
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

    void writeToStream(OutputStream stream);
}