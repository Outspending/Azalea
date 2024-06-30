package me.outspending.protocol.writer;

import it.unimi.dsi.fastutil.Pair;
import lombok.SneakyThrows;
import me.outspending.protocol.NetworkType;
import me.outspending.protocol.types.ClientPacket;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

final class NormalPacketWriter extends AbstractPacketWriter {

    private Pair<Integer, PacketWriter> getPacketLength(@NotNull ClientPacket packet) {
        PacketWriter writer = PacketWriter.createNormalWriter();

        writer.writeVarInt(packet.id());
        packet.write(writer);

        return Pair.of(writer.getSize(), writer);
    }

    @SneakyThrows
    public NormalPacketWriter(ClientPacket packet) {
        Pair<Integer, PacketWriter> pair = getPacketLength(packet);
        writeVarInt(pair.left());
        writeByteArray(pair.right().toByteArray());
    }

    public NormalPacketWriter() {}

}
