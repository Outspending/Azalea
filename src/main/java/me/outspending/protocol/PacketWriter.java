package me.outspending.protocol;

import com.github.steveice10.opennbt.NBTIO;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

        return writer.size();
    }

    public PacketWriter(@NotNull Packet packet) {
        this(packet, getPacketLength(packet));
    }

    public PacketWriter(@NotNull Packet packet, int length) {
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

    public void writeNBTCompound(@NotNull CompoundTag compound) {
        try {
            PacketWriter instance = this;
            NBTIO.writeAnyTag(new ByteArrayOutputStream() {
                @Override
                public void write(int b) {
                    instance.write(b);
                }
            }, compound);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
