package me.outspending.protocol.reader;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;

public class NormalPacketReader extends AbstractPacketReader {
    public static @NotNull PacketReader create(@NotNull ByteBuffer buffer) {
        return new NormalPacketReader(buffer);
    }

    public NormalPacketReader(@NotNull ByteBuffer buffer) {
        super(buffer, false);
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
