package me.outspending.protocol;

import lombok.SneakyThrows;
import me.outspending.NamespacedID;
import me.outspending.messages.serializer.NBTComponentSerializer;
import me.outspending.position.Pos;
import net.kyori.adventure.nbt.*;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
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

public class NetworkTypes {
    private static final NBTComponentSerializer textComponentSerializer = NBTComponentSerializer.nbt();

    public static final NetworkType<Boolean> BOOLEAN = register((buffer) -> buffer.get() == 0x01, (stream, type) -> stream.writeByte(type ? (byte) 0x01 : (byte) 0x00));
    public static final NetworkType<Byte> BYTE = register(ByteBuffer::get, (Writer<Byte>) DataOutputStream::writeByte);
    public static final NetworkType<Short> SHORT = register(ByteBuffer::getShort, (Writer<Short>) DataOutputStream::writeShort);
    public static final NetworkType<Integer> INT = register(ByteBuffer::getInt, DataOutputStream::writeInt);
    public static final NetworkType<Long> LONG = register(ByteBuffer::getLong, DataOutputStream::writeLong);
    public static final NetworkType<Float> FLOAT = register(ByteBuffer::getFloat, DataOutputStream::writeFloat);
    public static final NetworkType<Double> DOUBLE = register(ByteBuffer::getDouble, DataOutputStream::writeDouble);
    public static final NetworkType<Integer> VARINT = register(VarNum::readVarInt, VarNum::writeVarInt);
    public static final NetworkType<Long> VARLONG = register(VarNum::readVarLong, VarNum::writeVarLong);
    public static final NetworkType<Pos> POSITION = register((buffer) -> Pos.fromNetwork(buffer.getLong()), (stream, type) -> stream.writeLong(type.toNetwork()));
    public static final NetworkType<String> STRING = register((buffer) -> {
        final int length = VARINT.read(buffer);
        final byte[] bytes = new byte[length];

        buffer.get(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }, (stream, type) -> {
        final byte[] bytes = type.getBytes(StandardCharsets.UTF_8);

        NetworkTypes.VARINT.write(stream, bytes.length);
        stream.write(bytes);
    });
    public static final NetworkType<NamespacedID> NAMESPACEDID = register((buffer) -> {
        final String read = STRING.read(buffer);
        if (read.contains(":")) {
            String[] split = read.split(":");
            return new NamespacedID(split[0], split[1]);
        }

        return null;
    }, (stream, type) -> {
        STRING.write(stream, type.toString());
    });
    public static final NetworkType<CompoundBinaryTag> NBTCOMPOUND = register((buffer) -> {
        try (ByteArrayInputStream stream = new ByteArrayInputStream(buffer.array())) {
            return BinaryTagIO.reader().readNameless(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }, ((stream, type) -> {
        BinaryTagIO.writer().writeNameless(type, (OutputStream) stream);
    }));
    public static final NetworkType<UUID> UUID = register((buffer) -> new UUID(buffer.getLong(), buffer.getLong()), (stream, type) -> {
        stream.writeLong(type.getMostSignificantBits());
        stream.writeLong(type.getLeastSignificantBits());
    });
    public static final NetworkType<BitSet> BITSET = register((buffer) -> {
        int length = VARINT.read(buffer);
        BitSet bitSet = new BitSet(length);
        for (int i = 0; i < length; i++) {
            bitSet.set(i, BOOLEAN.read(buffer));
        }

        return bitSet;
    }, (stream, type) -> {
        VARINT.write(stream, type.cardinality());
        for (int i = 0; i < type.length(); i++) {
            BOOLEAN.write(stream, type.get(i));
        }
    });
    public static final NetworkType<byte[]> BYTEARRAY = register((buffer) -> {
        int length = VARINT.read(buffer);
        byte[] bytes = new byte[length];
        return buffer.get(bytes).array();
    }, (stream, type) -> {
        stream.write(type);
    });
    public static final NetworkType<Component> TEXT_COMPONENT = register((buffer) -> {
        final int length = SHORT.read(buffer);
        final byte[] bytes = new byte[length];
        buffer.get(bytes);
        final String json = new String(bytes, StandardCharsets.UTF_8);
        return textComponentSerializer.deserialize(TagStringIO.get().asCompound(json));
    }, ((stream, type) -> {
        final BinaryTag tag = textComponentSerializer.serialize(type);
        final BinaryTagType<? extends BinaryTag> tagType = tag.type();

        if (tagType == BinaryTagTypes.STRING) {
            stream.write(tagType.id());
            BinaryTagTypes.STRING.write((StringBinaryTag) tag, stream);
        }
        else if (tagType == BinaryTagTypes.COMPOUND) {
            NBTCOMPOUND.write(stream, (CompoundBinaryTag) tag);
        }
    }));

    @Contract("_, _ -> new")
    private static <T> NetworkType<T> register(@NotNull Reader<@Nullable T> reader, @NotNull Writer<@Nullable T> writer) {
        return new NetworkType<>() {
            @Override
            @SneakyThrows
            public T read(ByteBuffer buffer) {
                return reader.read(buffer);
            }

            @Override
            @SneakyThrows
            public void write(DataOutputStream stream, T type) {
                writer.write(stream, type);
            }
        };
    }

    @FunctionalInterface
    interface Reader<T> {
        T read(@NotNull ByteBuffer buffer) throws IOException;
    }

    @FunctionalInterface
    interface Writer<T> {
        void write(@NotNull DataOutputStream stream, T type) throws IOException;
    }

}
