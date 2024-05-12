package me.outspending.protocol;

import it.unimi.dsi.fastutil.Pair;
import me.outspending.MinecraftServer;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.zip.Deflater;

public class PacketEncoder {

    public static PacketWriter encode(@NotNull PacketWriter writer, @NotNull CompressionType type, @NotNull ClientPacket packet) {
        // Temporary PacketWriter to write the packet data
        PacketWriter packetWriter = PacketWriter.createNormalWriter();
        packetWriter.writeVarInt(packet.id());
        packet.write(packetWriter);

        byte[] packetData = packetWriter.toByteArray();
        switch (type) {
            case NONE -> encodeUncompressed(writer, packetData);
            case COMPRESSED -> encodeCompressed(writer, packetData);
        }

        return writer;
    }

    private static void encodeUncompressed(@NotNull PacketWriter writer, byte[] packetData) {
        writer.writeVarInt(packetData.length);
        writer.writeByteArray(packetData);
    }

    private static void encodeCompressed(@NotNull PacketWriter writer, byte[] packetData) {
        final int dataLength = packetData.length;

        if (dataLength >= MinecraftServer.COMPRESSION_THRESHOLD) {
            byte[] compressedData = compress(packetData);
            final int packetLength = getVarIntSize(dataLength) + compressedData.length;

            writer.writeVarInt(packetLength);
            writer.writeVarInt(dataLength);
            writer.writeByteArray(compressedData);
        } else {
            writer.writeVarInt(1 + dataLength);
            writer.writeVarInt(0);
            writer.writeByteArray(packetData);
        }
    }


    private static byte[] compress(byte[] data) {
        Deflater deflater = new Deflater(MinecraftServer.COMPRESSION_LEVEL, false);
        deflater.setInput(data);
        deflater.finish();
        byte[] compressedData = new byte[data.length];
        int compressedLength = deflater.deflate(compressedData);
        deflater.end();
        byte[] result = new byte[compressedLength];
        System.arraycopy(compressedData, 0, result, 0, compressedLength);
        return result;
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
