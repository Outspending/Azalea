package me.outspending.protocol;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public interface NetworkType<T> {
    @Contract("null -> fail")
    @Nullable T read(ByteBuffer buffer);

    @Contract("null, _ -> fail; _, null -> fail")
    void write(ByteArrayOutputStream stream, T type);
}
