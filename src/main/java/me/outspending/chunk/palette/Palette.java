package me.outspending.chunk.palette;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.function.Consumer;

public interface Palette {
    int bitsPerEntry();
    void write(@NotNull PacketWriter writer, @NotNull Consumer<Palette> paletteConsumer);
}
