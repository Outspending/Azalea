package me.outspending.protocol;

import com.google.common.graph.Network;
import me.outspending.NamespacedID;
import me.outspending.Slot;
import me.outspending.block.ItemStack;
import me.outspending.position.Location;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.writer.PacketWriter;
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
            int length = VARINT_TYPE.read(buffer);
            byte[] bytes = new byte[length];
            buffer.get(bytes);
            return new String(bytes, StandardCharsets.UTF_8);
        }

        @Override
        public void write(ByteBuffer buffer, String type) {
            byte[] bytes = type.getBytes(StandardCharsets.UTF_8);
            VARINT_TYPE.write(buffer, bytes.length);
            buffer.put(bytes);
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
        public void write(ByteBuffer buffer, NamespacedID type) {
            STRING_TYPE.write(buffer, type.toString());
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
            try {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                BinaryTagIO.writer().writeNameless(type, stream);

                buffer.put(stream.toByteArray());
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
        public void write(ByteBuffer buffer, Location type) {
            LONG_TYPE.write(buffer, (((long) (type.x() & 0x3FFFFFF) << 38) | ((long) (type.z() & 0x3FFFFFF) << 12) | (type.y() & 0xFFF)));
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
        public void write(ByteBuffer buffer, BitSet type) {
            VARINT_TYPE.write(buffer, type.cardinality());
            for (int i = 0; i < type.length(); i++) {
                BOOLEAN_TYPE.write(buffer, type.get(i));
            }
        }
    };

    NetworkType<ItemStack> SLOT_TYPE = new NetworkType<>() {
        @Override
        public ItemStack read(ByteBuffer buffer) {
            if (BOOLEAN_TYPE.read(buffer)) {
                return new ItemStack(
                        VARINT_TYPE.read(buffer),
                        BYTE_TYPE.read(buffer),
                        NBTCOMPOUND_TYPE.read(buffer)
                );
            }

            return null;
        }

        @Override
        public void write(ByteBuffer buffer, ItemStack type) {
            BOOLEAN_TYPE.write(buffer, true);
            VARINT_TYPE.write(buffer, type.getCount());
            NBTCOMPOUND_TYPE.write(buffer, type.getItemNBT());
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
        public void write(ByteBuffer buffer, byte[] type) {
            buffer.put(type);
        }
    };
}
