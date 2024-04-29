package me.outspending.protocol.reader;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.zip.Deflater;

public class CompressedPacketReader extends AbstractPacketReader {
    public static @NotNull PacketReader create(@NotNull ByteBuffer buffer) {
        Deflater deflater = new Deflater();
        deflater.setInput(buffer);
        deflater.finish();

        byte[] compressedData = new byte[buffer.remaining()];
        deflater.deflate(compressedData);
        deflater.end();

        ByteBuffer compressedBuffer = ByteBuffer.wrap(compressedData);
        System.out.println(Arrays.toString(compressedData));
        return new CompressedPacketReader(compressedBuffer);
    }

    public CompressedPacketReader(@NotNull ByteBuffer buffer) {
        super(buffer, true);
    }

    @Override
    public @Nullable <T> Optional<T> readOptional(@NotNull Function<PacketReader, T> elementReader) {
        return Optional.empty();
    }

    @Override
    public <T> @Nullable T[] readArray(@NotNull Function<PacketReader, T> elementReader, @NotNull IntFunction<T[]> arrayCreator) {
        return null;
    }

    @Override
    public <T extends Enum<?>> @Nullable T readEnum(@NotNull Class<T> enumClass) {
        return null;
    }
}
