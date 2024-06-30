package me.outspending.protocol.reader;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;

final class NormalPacketReader extends AbstractPacketReader {

    static @NotNull PacketReader create(@NotNull ByteBuffer buffer) {
        return new NormalPacketReader(buffer);
    }

    public NormalPacketReader(@NotNull ByteBuffer buffer) {
        super(buffer);
    }

}
