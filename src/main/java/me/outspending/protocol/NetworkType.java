package me.outspending.protocol;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public interface NetworkType<T> {
    @Contract("null -> fail")
    @Nullable T read(ByteBuffer buffer);

    @Contract("null, _ -> fail; _, null -> fail")
    void write(DataOutputStream stream, T type) throws IOException;
}
