package me.outspending.protocol;

import me.outspending.position.Location;
import net.kyori.adventure.nbt.BinaryTagIO;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
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
        public void write(ByteBuffer buffer, Boolean type) {
            buffer.put(type ? (byte) 0x01 : (byte) 0x00);
        }
    };

    NetworkType<Byte> BYTE_TYPE = new NetworkType<>() {
        @Override
        public Byte read(ByteBuffer buffer) {
            return buffer.get();
        }

        @Override
        public void write(ByteBuffer buffer, Byte type) {
            buffer.put(type);
        }
    };

    NetworkType<Integer> UNSIGNED_BYTE_TYPE = new NetworkType<>() {
        @Override
        public Integer read(ByteBuffer buffer) {
            return buffer.get() & 0xFF;
        }

        @Override
        public void write(ByteBuffer buffer, Integer type) {
            buffer.put((byte) (type & 0xFF));
        }
    };

    NetworkType<Short> SHORT_TYPE = new NetworkType<>() {
        @Override
        public Short read(ByteBuffer buffer) {
            return buffer.getShort();
        }

        @Override
        public void write(ByteBuffer buffer, Short type) {
            buffer.putShort(type);
        }
    };

    NetworkType<Integer> UNSIGNED_SHORT_TYPE = new NetworkType<>() {
        @Override
        public Integer read(ByteBuffer buffer) {
            return buffer.getShort() & 0xFFFF;
        }

        @Override
        public void write(ByteBuffer buffer, Integer type) {
            buffer.putShort((short) (type & 0xFFFF));
        }
    };

    NetworkType<Integer> INT_TYPE = new NetworkType<>() {
        @Override
        public Integer read(ByteBuffer buffer) {
            return buffer.getInt();
        }

        @Override
        public void write(ByteBuffer buffer, Integer type) {
            buffer.putInt(type);
        }
    };

    NetworkType<Long> LONG_TYPE = new NetworkType<>() {
        @Override
        public Long read(ByteBuffer buffer) {
            return buffer.getLong();
        }

        @Override
        public void write(ByteBuffer buffer, Long type) {
            buffer.putLong(type);
        }
    };

    NetworkType<Float> FLOAT_TYPE = new NetworkType<>() {
        @Override
        public Float read(ByteBuffer buffer) {
            return buffer.getFloat();
        }

        @Override
        public void write(ByteBuffer buffer, Float type) {
            buffer.putFloat(type);
        }
    };

    NetworkType<Double> DOUBLE_TYPE = new NetworkType<>() {
        @Override
        public Double read(ByteBuffer buffer) {
            return buffer.getDouble();
        }

        @Override
        public void write(ByteBuffer buffer, Double type) {
            buffer.putDouble(type);
        }
    };

    NetworkType<String> STRING_TYPE = new NetworkType<>() {
        @Override
        public String read(ByteBuffer buffer) {
            int length = UNSIGNED_SHORT_TYPE.read(buffer);
            byte[] bytes = new byte[length];
            buffer.get(bytes);
            return new String(bytes);
        }

        @Override
        public void write(ByteBuffer buffer, String type) {
            byte[] bytes = type.getBytes();
            UNSIGNED_SHORT_TYPE.write(buffer, bytes.length);
            buffer.put(bytes);
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
        public void write(ByteBuffer buffer, Integer type) {
            while (true) {
                if ((type & ~SEGMENT_BITS) == 0) {
                    buffer.put(type.byteValue());
                    return;
                }

                buffer.put((byte) ((byte) (type & SEGMENT_BITS) | CONTINUE_BIT));
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
        public void write(ByteBuffer buffer, Long type) {
            while (true) {
                if ((type & ~((long) SEGMENT_BITS)) == 0) {
                    buffer.put(type.byteValue());
                    return;
                }

                buffer.put((byte) ((byte) (type & SEGMENT_BITS) | CONTINUE_BIT));

                // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
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
        public void write(ByteBuffer buffer, CompoundBinaryTag type) {
            try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
                BinaryTagIO.writer().writeNameless(type, stream);

                byte[] serializedData = stream.toByteArray();

                if (buffer.remaining() < serializedData.length) {
                    ByteBuffer newBuffer = ByteBuffer.allocate(buffer.position() + serializedData.length);
                    buffer.flip();
                    newBuffer.put(buffer);
                    buffer = newBuffer;
                }

                buffer.put(serializedData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    NetworkType<Location> LOCATION_TYPE = new NetworkType<>() {
        @Override
        public @Nullable Location read(ByteBuffer buffer) {
            long value = buffer.getLong();
            int x = (int) (value >> 38);
            int y = (int) (value << 52 >> 52);
            int z = (int) (value << 26 >> 38);
            return new Location(x, y, z);
        }

        @Override
        public void write(ByteBuffer buffer, Location type) {
            buffer.putInt(((type.x() & 0x3FFFFFF) << 38) | ((type.z() & 0x3FFFFFF) << 12) | (type.y() & 0xFFF));
        }
    };

    NetworkType<UUID> UUID_TYPE = new NetworkType<>() {
        @Override
        public UUID read(ByteBuffer buffer) {
            return new UUID(buffer.getLong(), buffer.getLong());
        }

        @Override
        public void write(ByteBuffer buffer, UUID type) {
            buffer.putLong(type.getMostSignificantBits());
            buffer.putLong(type.getLeastSignificantBits());
        }
    };

    NetworkType<Optional<?>> OPTIONAL_TYPE = new NetworkType<>() {
        @Override
        public Optional<?> read(ByteBuffer buffer) {
            return Optional.ofNullable(buffer);
        }

        @Override
        public void write(ByteBuffer buffer, Optional<?> type) {
            buffer.put((byte) (type.isPresent() ? 0x01 : 0x00));
            type.ifPresent(o -> buffer.put((byte) o));
        }
    };

    NetworkType<Byte[]> BYTEARRAY_TYPE = new NetworkType<>() {
        @Override
        public Byte[] read(ByteBuffer buffer) {
            int length = UNSIGNED_SHORT_TYPE.read(buffer);
            Byte[] bytes = new Byte[length];
            for (int i = 0; i < length; i++) {
                bytes[i] = buffer.get();
            }
            return bytes;
        }

        @Override
        public void write(ByteBuffer buffer, Byte[] type) {
            UNSIGNED_SHORT_TYPE.write(buffer, type.length);
            for (Byte b : type) {
                buffer.put(b);
            }
        }
    };
}
