package me.outspending.protocol;

import lombok.Getter;
import me.outspending.connection.GameState;
import me.outspending.protocol.codec.CodecHandler;
import me.outspending.protocol.exception.UnknownPacketException;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.function.Function;

public record PacketDecoder(@NotNull ByteBuffer buffer, @NotNull GameState gameState) {

    public @Nullable ServerPacket decode() {
        PacketReader reader = PacketReader.createNormalReader(buffer);
        int packetID = reader.getPacketID();

        Function<PacketReader, ServerPacket> function = CodecHandler.CLIENT_CODEC.getPacket(gameState, packetID);
        if (function == null) {
            throw new UnknownPacketException(gameState, packetID);
        }

        return function.apply(reader);
    }

}
