package me.outspending.protocol;

import com.google.common.graph.Network;
import me.outspending.NamespacedID;
import me.outspending.position.Location;
import net.kyori.adventure.nbt.BinaryTagIO;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.Optional;
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
        public void write(ByteArrayOutputStream stream, Boolean type) {
            stream.write(type ? 0x01 : 0x00);
        }
    };

    NetworkType<Byte> BYTE_TYPE = new NetworkType<>() {
        @Override
        public Byte read(ByteBuffer buffer) {
            return buffer.get();
        }

        @Override
        public void write(ByteArrayOutputStream stream, Byte type) {
            stream.write(type);
        }
    };

    NetworkType<Integer> UNSIGNED_BYTE_TYPE = new NetworkType<>() {
        @Override
        public Integer read(ByteBuffer buffer) {
            return buffer.get() & 0xFF;
        }

        @Override
        public void write(ByteArrayOutputStream stream, Integer type) {
            stream.write((byte) (type & 0xFF));
        }
    };

    NetworkType<Short> SHORT_TYPE = new NetworkType<>() {
        @Override
        public Short read(ByteBuffer buffer) {
            return buffer.getShort();
        }

        @Override
        public void write(ByteArrayOutputStream stream, Short type) {
            byte[] bytes = ByteBuffer.allocate(2).putShort(type).array();
            stream.write(bytes, 0, bytes.length);
        }
    };

    NetworkType<Integer> UNSIGNED_SHORT_TYPE = new NetworkType<>() {
        @Override
        public Integer read(ByteBuffer buffer) {
            return buffer.getShort() & 0xFFFF;
        }

        @Override
        public void write(ByteArrayOutputStream stream, Integer type) {
            byte[] bytes = ByteBuffer.allocate(2).putShort((short) (type & 0xFFFF)).array();
            stream.write(bytes, 0, bytes.length);
        }
    };

    NetworkType<Integer> INT_TYPE = new NetworkType<>() {
        @Override
        public Integer read(ByteBuffer buffer) {
            return buffer.getInt();
        }

        @Override
        public void write(ByteArrayOutputStream stream, Integer type) {
            byte[] bytes = ByteBuffer.allocate(4).putInt(type).array();
            stream.write(bytes, 0, bytes.length);
        }
    };

    NetworkType<Long> LONG_TYPE = new NetworkType<>() {
        @Override
        public Long read(ByteBuffer buffer) {
            return buffer.getLong();
        }

        @Override
        public void write(ByteArrayOutputStream stream, Long type) {
            byte[] bytes = ByteBuffer.allocate(8).putLong(type).array();
            stream.write(bytes, 0, bytes.length);
        }
    };

    NetworkType<Float> FLOAT_TYPE = new NetworkType<>() {
        @Override
        public Float read(ByteBuffer buffer) {
            return buffer.getFloat();
        }

        @Override
        public void write(ByteArrayOutputStream stream, Float type) {
            byte[] bytes = ByteBuffer.allocate(4).putFloat(type).array();
            stream.write(bytes, 0, bytes.length);
        }
    };

    NetworkType<Double> DOUBLE_TYPE = new NetworkType<>() {
        @Override
        public Double read(ByteBuffer buffer) {
            return buffer.getDouble();
        }

        @Override
        public void write(ByteArrayOutputStream stream, Double type) {
            byte[] bytes = ByteBuffer.allocate(8).putDouble(type).array();
            stream.write(bytes, 0, bytes.length);
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
        public void write(ByteArrayOutputStream stream, String type) {
            byte[] bytes = type.getBytes(StandardCharsets.UTF_8);
            VARINT_TYPE.write(stream, bytes.length);
            stream.write(bytes, 0, bytes.length);
        }
    };

    NetworkType<NamespacedID> NAMESPACEDID_TYPE = new NetworkType<>() {
        @Override
        public @Nullable NamespacedID read(ByteBuffer buffer) {
            String[] parts = STRING_TYPE.read(buffer).split(":");
            return new NamespacedID(parts[0], parts[1]);
        }

        @Override
        public void write(ByteArrayOutputStream stream, NamespacedID type) {
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
        public void write(ByteArrayOutputStream stream, Integer type) {
            while (true) {
                if ((type & ~SEGMENT_BITS) == 0) {
                    stream.write(type);
                    return;
                }

                stream.write((byte) ((byte) (type & SEGMENT_BITS) | CONTINUE_BIT));
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
        public void write(ByteArrayOutputStream stream, Long type) {
            while (true) {
                if ((type & ~((long) SEGMENT_BITS)) == 0) {
                    stream.write(type.byteValue());
                    return;
                }

                stream.write((byte) ((byte) (type & SEGMENT_BITS) | CONTINUE_BIT));

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
        public void write(ByteArrayOutputStream stream, CompoundBinaryTag type) {
            try {
                BinaryTagIO.writer().writeNameless(type, stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    NetworkType<Location> LOCATION_TYPE = new NetworkType<>() {
        @Override
        public @NotNull Location read(ByteBuffer buffer) {
            long value = buffer.getLong();
            int x = (int) (value >> 38);
            int y = (int) (value << 52 >> 52);
            int z = (int) (value << 26 >> 38);
            return new Location(x, y, z);
        }

        @Override
        public void write(ByteArrayOutputStream stream, Location type) {
            stream.write(((type.x() & 0x3FFFFFF) << 38) | ((type.z() & 0x3FFFFFF) << 12) | (type.y() & 0xFFF));
        }
    };

    NetworkType<UUID> UUID_TYPE = new NetworkType<>() {
        @Override
        public UUID read(ByteBuffer buffer) {
            return new UUID(buffer.getLong(), buffer.getLong());
        }

        @Override
        public void write(ByteArrayOutputStream stream, UUID type) {
            ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
            buffer.putLong(type.getMostSignificantBits());
            buffer.putLong(type.getLeastSignificantBits());

            byte[] bytes = buffer.array();
            stream.write(bytes, 0, bytes.length);
        }
    };

    NetworkType<Optional<?>> OPTIONAL_TYPE = new NetworkType<>() {
        @Override
        public Optional<?> read(ByteBuffer buffer) {
            return Optional.ofNullable(buffer);
        }

        @Override
        public void write(ByteArrayOutputStream stream, Optional<?> type) {
            stream.write((byte) (type.isPresent() ? 0x01 : 0x00));
            type.ifPresent(o -> stream.write((byte) o));
        }
    };

    NetworkType<BitSet> BITSET_TYPE = new NetworkType<>() {
        @Override
        public @Nullable BitSet read(ByteBuffer buffer) {
            int length = VARINT_TYPE.read(buffer);
            BitSet bitSet = new BitSet(length);
            for (int i = 0; i < length; i++) {
                bitSet.set(i, BOOLEAN_TYPE.read(buffer));
            }

            return bitSet;
        }

        @Override
        public void write(ByteArrayOutputStream stream, BitSet type) {
            VARINT_TYPE.write(stream, type.length());
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
            for (int i = 0; i < length; i++) {
                bytes[i] = buffer.get();
            }
            return bytes;
        }

        @Override
        public void write(ByteArrayOutputStream stream, byte[] type) {
            for (Byte b : type) {
                stream.write(b);
            }
        }
    };
}
