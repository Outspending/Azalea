package me.outspending.protocol;

import me.outspending.MinecraftServer;
import me.outspending.connection.ClientConnection;
import me.outspending.connection.ConnectionState;
import me.outspending.protocol.codec.CodecHandler;
import me.outspending.protocol.exception.InvalidPacketException;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ServerPacket;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiFunction;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class PacketDecoder {
    private static final Logger logger = LoggerFactory.getLogger(PacketDecoder.class);

    public static ServerPacket decode(@NotNull ClientConnection connection, @NotNull PacketReader reader, @NotNull CompressionType type, @NotNull ConnectionState state) {
        int packetID;
        byte[] packetData;

        try {
            int dataLength = reader.readVarInt();

            if (type == CompressionType.COMPRESSED) {
                int uncompressedLength = reader.readVarInt();
                packetID = reader.readVarInt();

                if (dataLength >= MinecraftServer.COMPRESSION_THRESHOLD) {
                    byte[] compressedData = reader.getRemainingBytes();
                    packetData = decompress(compressedData, uncompressedLength);
                } else {
                    packetData = reader.getRemainingBytes();
                }
            } else {
                packetID = reader.readVarInt();
                packetData = reader.getRemainingBytes();
            }

            BiFunction<ClientConnection, PacketReader, ServerPacket> packetFunction = CodecHandler.CLIENT_CODEC.getPacket(state, packetID);
            if (packetFunction == null) {
                logger.info("Unknown packet ID: {}, in state: {}", packetID, state.name());
                return null;
            }

            return packetFunction.apply(connection, PacketReader.createNormalReader(packetData));
        } catch (InvalidPacketException e) {
            logger.error("Error decoding packet:", e);
            throw new InvalidPacketException("Failed to decode packet", e);
        }
    }

    private static byte[] decompress(byte[] compressedData, int uncompressedLength) {
        try {
            final Inflater inflater = new Inflater();
            inflater.setInput(compressedData);
            byte[] result = new byte[uncompressedLength];

            int bytesDecompressed = inflater.inflate(result);
            if (bytesDecompressed != uncompressedLength) {
                throw new InvalidPacketException("Decompression failed: incorrect length");
            }

            return result;
        } catch (DataFormatException e) {
            throw new InvalidPacketException("Decompression failed: invalid data", e);
        }
    }

    private static int getVarIntSize(int value) {
        int size = 0;
        do {
            value >>>= 7;
            size++;
        } while (value != 0);
        return size;
    }
}
