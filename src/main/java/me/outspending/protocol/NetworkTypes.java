package me.outspending.protocol;

import me.outspending.NamespacedID;
import me.outspending.position.Pos;
import net.kyori.adventure.nbt.BinaryTagIO;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.UUID;

public interface NetworkTypes {
    int SEGMENT_BITS = 0x7F;
    int CONTINUE_BIT = 0x80;

    NetworkType<Boolean> BOOLEAN_TYPE = new NetworkType<>() {
        @Override
        public Boolean read(ByteBuffer buffer) {
            return buffer.get() == 0x01;
        }

        @Override
        public void write(DataOutputStream stream, Boolean type) throws IOException {
            stream.writeByte(type ? (byte) 0x01 : (byte) 0x00);
        }
    };

    NetworkType<Byte> BYTE_TYPE = new NetworkType<>() {
        @Override
        public Byte read(ByteBuffer buffer) {
            return buffer.get();
        }

        @Override
        public void write(DataOutputStream stream, Byte type) throws IOException {
            stream.writeByte(type);
        }
    };

    NetworkType<Integer> UNSIGNED_BYTE_TYPE = new NetworkType<>() {
        @Override
        public Integer read(ByteBuffer buffer) {
            return buffer.get() & 0xFF;
        }

        @Override
        public void write(DataOutputStream stream, Integer type) throws IOException {
            stream.write(type & 0xFF);
        }
    };

    NetworkType<Short> SHORT_TYPE = new NetworkType<>() {
        @Override
        public Short read(ByteBuffer buffer) {
            return buffer.getShort();
        }

        @Override
        public void write(DataOutputStream stream, Short type) throws IOException {
            stream.writeShort(type);
        }
    };

    NetworkType<Integer> INT_TYPE = new NetworkType<>() {
        @Override
        public Integer read(ByteBuffer buffer) {
            return buffer.getInt();
        }

        @Override
        public void write(DataOutputStream stream, Integer type) throws IOException {
            stream.writeInt(type);
        }
    };

    NetworkType<Long> LONG_TYPE = new NetworkType<>() {
        @Override
        public Long read(ByteBuffer buffer) {
            return buffer.getLong();
        }

        @Override
        public void write(DataOutputStream stream, Long type) throws IOException {
            stream.writeLong(type);
        }
    };

    NetworkType<Float> FLOAT_TYPE = new NetworkType<>() {
        @Override
        public Float read(ByteBuffer buffer) {
            return buffer.getFloat();
        }

        @Override
        public void write(DataOutputStream stream, Float type) throws IOException {
            stream.writeFloat(type);
        }
    };

    NetworkType<Double> DOUBLE_TYPE = new NetworkType<>() {
        @Override
        public Double read(ByteBuffer buffer) {
            return buffer.getDouble();
        }

        @Override
        public void write(DataOutputStream stream, Double type) throws IOException {
            stream.writeDouble(type);
        }
    };

    NetworkType<String> STRING_TYPE = new NetworkType<>() {
        @Override
        public String read(ByteBuffer buffer) {
            int length = VARINT_TYPE.read(buffer);
            byte[] bytes = new byte[length];
            buffer.get(bytes);
            return new String(bytes, StandardCharsets.UTF_8);
        }

        @Override
        public void write(DataOutputStream stream, String type) throws IOException {
            byte[] bytes = type.getBytes(StandardCharsets.UTF_8);

            VARINT_TYPE.write(stream, bytes.length);
            stream.write(bytes);
        }
    };

    NetworkType<NamespacedID> NAMESPACEDID_TYPE = new NetworkType<>() {
        @Override
        public @Nullable NamespacedID read(ByteBuffer buffer) {
            String read = STRING_TYPE.read(buffer);
            if (read.contains(":")) {
                String[] split = read.split(":");
                return new NamespacedID(split[0], split[1]);
            }

            return null;
        }

        @Override
        public void write(DataOutputStream stream, NamespacedID type) throws IOException {
            STRING_TYPE.write(stream, type.toString());
        }
    };

    NetworkType<Integer> VARINT_TYPE = new NetworkType<>() {
        @Override
        public Integer read(ByteBuffer buffer) {
            int value = 0;
            int position = 0;
            byte currentByte;

            while (true) {
                currentByte = buffer.get();
                value |= (currentByte & SEGMENT_BITS) << position;

                if ((currentByte & CONTINUE_BIT) == 0) break;

                position += 7;

                if (position >= 32) throw new RuntimeException("VarInt is too big");
            }

            return value;
        }

        @Override
        public void write(DataOutputStream stream, Integer type) throws IOException {
            while (true) {
                if ((type & ~SEGMENT_BITS) == 0) {
                    stream.writeByte(type.byteValue());
                    return;
                }

                stream.writeByte((byte) ((byte) (type & SEGMENT_BITS) | CONTINUE_BIT));
                type >>>= 7;
            }
        }
    };

    NetworkType<Long> VARLONG_TYPE = new NetworkType<>() {
        @Override
        public Long read(ByteBuffer buffer) {
            long value = 0;
            int position = 0;
            byte currentByte;

            while (true) {
                currentByte = buffer.get();
                value |= (long) (currentByte & SEGMENT_BITS) << position;

                if ((currentByte & CONTINUE_BIT) == 0) break;

                position += 7;

                if (position >= 64) throw new RuntimeException("VarLong is too big");
            }

            return value;
        }

        @Override
        public void write(DataOutputStream stream, Long type) throws IOException {
            while (true) {
                if ((type & ~((long) SEGMENT_BITS)) == 0) {
                    stream.writeByte(type.byteValue());
                    return;
                }

                stream.writeByte((byte) ((byte) (type & SEGMENT_BITS) | CONTINUE_BIT));

                type >>>= 7;
            }
        }
    };

    NetworkType<CompoundBinaryTag> NBTCOMPOUND_TYPE = new NetworkType<>() {
        @Override
        public CompoundBinaryTag read(ByteBuffer buffer) {
            try (ByteArrayInputStream stream = new ByteArrayInputStream(buffer.array())) {
                return BinaryTagIO.reader().readNameless(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void write(DataOutputStream stream, CompoundBinaryTag type) throws IOException {
            BinaryTagIO.writer().writeNameless(type, (OutputStream) stream);
        }
    };

    NetworkType<Pos> LOCATION_TYPE = new NetworkType<>() {
        @Override
        public @NotNull Pos read(ByteBuffer buffer) {
            return Pos.fromNetwork(buffer.getLong());
        }

        @Override
        public void write(DataOutputStream stream, Pos type) throws IOException {
            stream.writeLong(type.toNetwork());
        }
    };

    NetworkType<UUID> UUID_TYPE = new NetworkType<>() {
        @Override
        public UUID read(ByteBuffer buffer) {
            return new UUID(buffer.getLong(), buffer.getLong());
        }

        @Override
        public void write(DataOutputStream stream, UUID type) throws IOException {
            stream.writeLong(type.getMostSignificantBits());
            stream.writeLong(type.getLeastSignificantBits());
        }
    };

    NetworkType<BitSet> BITSET_TYPE = new NetworkType<>() {
        @Override
        public @NotNull BitSet read(ByteBuffer buffer) {
            int length = VARINT_TYPE.read(buffer);
            BitSet bitSet = new BitSet(length);
            for (int i = 0; i < length; i++) {
                bitSet.set(i, BOOLEAN_TYPE.read(buffer));
            }

            return bitSet;
        }

        @Override
        public void write(DataOutputStream stream, BitSet type) throws IOException {
            VARINT_TYPE.write(stream, type.cardinality());
            for (int i = 0; i < type.length(); i++) {
                BOOLEAN_TYPE.write(stream, type.get(i));
            }
        }
    };

    NetworkType<byte[]> BYTEARRAY_TYPE = new NetworkType<>() {
        @Override
        public byte[] read(ByteBuffer buffer) {
            int length = VARINT_TYPE.read(buffer);
            byte[] bytes = new byte[length];
            return buffer.get(bytes).array();
        }

        @Override
        public void write(DataOutputStream stream, byte[] type) throws IOException {
            stream.write(type);
        }
    };

    NetworkType<Component> TEXT_COMPONENT_TYPE = new NetworkType<>() {
        @Override
        public @Nullable Component read(ByteBuffer buffer) {
            return null;
        }

        @Override
        public void write(DataOutputStream stream, Component type) throws IOException {
        }
    };

    NetworkType<Component> JSON_TEXT_COMPONENT_TYPE = new NetworkType<>() {
        @Override
        public @NotNull Component read(ByteBuffer buffer) {
            final String json = STRING_TYPE.read(buffer);
            return GsonComponentSerializer.gson().deserialize(json);
        }

        @Override
        public void write(DataOutputStream stream, Component type) throws IOException {
            final String json = GsonComponentSerializer.gson().serialize(type);
            STRING_TYPE.write(stream, json);
        }
    };
}
