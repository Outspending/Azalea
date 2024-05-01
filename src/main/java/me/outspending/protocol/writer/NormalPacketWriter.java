package me.outspending.protocol.writer;

import me.outspending.protocol.NetworkType;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.types.Packet;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Consumer;

public class NormalPacketWriter extends AbstractPacketWriter {

    private int getPacketLength(@NotNull ClientPacket packet) {
        PacketWriter writer = PacketWriter.createNormalWriter();

        writer.writeVarInt(packet.id());
        packet.write(writer);

        return writer.getSize();
    }

    public NormalPacketWriter(ClientPacket packet) {
        super(false);

        writeVarInt(getPacketLength(packet));
        writeVarInt(packet.id());
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
