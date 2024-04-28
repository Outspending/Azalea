package me.outspending.protocol.writer;

import me.outspending.protocol.NetworkType;
import me.outspending.protocol.types.ClientPacket;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

public class NormalPacketWriter extends AbstractPacketWriter {

    public NormalPacketWriter(ClientPacket packet) {
        super(false);

        writeVarInt(getPacketLength(packet));
        writeVarInt(packet.getId());
        packet.write(this);
    }

    public NormalPacketWriter() {
        super(false);
    }

    @Override
    public <T> void writeOptional(@NotNull T element, @NotNull NetworkType<T> type) {

    }

    @Override
    public <T> void writeArray(@NotNull T[] array, Consumer<T> consumer) {
        for (T element : array) {
            consumer.accept(element);
        }
    }

    @Override
    public <T extends Enum<?>> void writeEnum(@NotNull T e) {

    }
}
