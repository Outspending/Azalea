package me.outspending.protocol;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.outspending.position.Location;
import me.outspending.protocol.packets.configuration.server.RegistryDataPacket;
import net.kyori.adventure.nbt.BinaryTagIO;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.UUID;
import java.util.function.Consumer;

@Getter(AccessLevel.PUBLIC)
@NoArgsConstructor
public class PacketWriter extends ByteArrayOutputStream {
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

    private int length;
    private int packetID;

    public static int getPacketLength(@NotNull Packet packet) {
        PacketWriter writer = new PacketWriter();
        writer.writeVarInt(packet.getID());
        packet.write(writer);

        return packet instanceof RegistryDataPacket ? writer.size() + 8 : writer.size(); // VERY janky way to fix the issue but idgaf
    }

    public PacketWriter(@NotNull Packet packet) {
        this(packet, getPacketLength(packet));
    }

    public PacketWriter(@NotNull Packet packet, int length) {
        super(length);

        this.length = length;
        this.packetID = packet.getID();

        writeVarInt(this.length);
        writeVarInt(this.packetID);
        packet.write(this);
    }

    public void writeVarInt(int value) {
        do {
            int temp = value & SEGMENT_BITS;
            value >>>= 7;
            if (value != 0) {
                temp |= CONTINUE_BIT;
            }
            write(temp);
        } while (value != 0);
    }

    public void writeVarLong(long value) {
        do {
            int temp = (int) (value & SEGMENT_BITS);
            value >>>= 7;
            if (value != 0) {
                temp |= CONTINUE_BIT;
            }
            write(temp);
        } while (value != 0);
    }

    public void writeString(@NotNull String value) {
        byte[] bytes = value.getBytes();
        writeVarInt(bytes.length);
        write(bytes, 0, bytes.length);
    }

    public void writeBoolean(boolean value) {
        write(value ? 1 : 0);
    }

    public void writeShort(short value) {
        write(value >> 8);
        write(value);
    }

    public void writeInt(int value) {
        write(value >> 24);
        write(value >> 16);
        write(value >> 8);
        write(value);
    }

    public void writeDouble(double value) {
        writeLong(Double.doubleToLongBits(value));
    }

    public void writeLong(long value) {
        write((int) (value >> 56));
        write((int) (value >> 48));
        write((int) (value >> 40));
        write((int) (value >> 32));
        write((int) (value >> 24));
        write((int) (value >> 16));
        write((int) (value >> 8));
        write((int) value);
    }

    public void writeUUID(@NotNull UUID uuid) {
        writeLong(uuid.getMostSignificantBits());
        writeLong(uuid.getLeastSignificantBits());
    }

    public <T> void writeArray(@NotNull Iterable<T> array, @NotNull Consumer<T> consumer) {
        for (T element : array) {
            System.out.println("Writing element: " + element);
            consumer.accept(element);
        }
    }

    public void writeByteArray(byte[] bytes) {
        write(bytes, 0, bytes.length);
    }

    public void writePacketWriter(@NotNull PacketWriter writer) {
        writeByteArray(writer.toByteArray());
    }

    public void writeLocation(@NotNull Location location) {
        writeDouble(location.x());
        writeDouble(location.y());
        writeDouble(location.z());
    }

    public void writeNBTCompound(@NotNull CompoundBinaryTag tag) {
        try {
            BinaryTagIO.writer().writeNameless(tag, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
