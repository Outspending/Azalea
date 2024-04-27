package me.outspending.protocol;

import com.google.common.base.Charsets;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.outspending.position.Location;
import me.outspending.protocol.packets.configuration.client.RegistryDataPacket;
import net.kyori.adventure.nbt.BinaryTagIO;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.function.BiConsumer;
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

        return writer.size();
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
        byte[] bytes = value.getBytes(Charsets.UTF_8);

        writeVarInt(value.length());
        write(bytes, 0, bytes.length);
    }

    public void writeBoolean(boolean value) {
        write(value ? 0x01 : 0x00);
    }

    public void writeShort(short value) {
        try {
            byte[] bytes = ByteBuffer.allocate(2).putShort(value).array();
            write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFloat(float value) {
        try {
            byte[] bytes = ByteBuffer.allocate(4).putFloat(value).array();
            write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeInt(int value) {
        try {
            byte[] bytes = ByteBuffer.allocate(4).putInt(value).array();
            write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDouble(double value) {
        try {
            byte[] bytes = ByteBuffer.allocate(8).putDouble(value).array();
            write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLong(long value) {
        try {
            byte[] bytes = ByteBuffer.allocate(8).putLong(value).array();
            write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeUUID(@NotNull UUID uuid) {
        try {
            ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
            buffer.putLong(uuid.getMostSignificantBits());
            buffer.putLong(uuid.getLeastSignificantBits());
            write(buffer.array());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> void writeArray(@NotNull T[] array, @NotNull Consumer<T> consumer) {
        for (T element : array) {
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
        writeVarLong(((long) (location.x() & 0x3FFFFFF) << 38) | ((long) (location.z() & 0x3FFFFFF) << 12) | (location.y() & 0xFFF));
    }

    public void writeNBTCompound(@NotNull CompoundBinaryTag tag) {
        try {
            BinaryTagIO.writer().writeNameless(tag, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
