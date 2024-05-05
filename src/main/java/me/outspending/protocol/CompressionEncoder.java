package me.outspending.protocol;

import lombok.Getter;
import me.outspending.MinecraftServer;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.zip.Deflater;

@Getter
public class CompressionEncoder {
    private final byte[] encodeBuf = new byte[8192];
    private final Deflater deflater;

    public CompressionEncoder(Deflater deflater) {
        this.deflater = deflater;
    }

    public void encode(@NotNull PacketWriter writer, ByteBuffer buffer) {
        PacketWriter lengthBuffer = PacketWriter.createNormalWriter();

        int readable = buffer.remaining();
        if (readable < MinecraftServer.COMPRESSION_THRESHOLD) {
            lengthBuffer.writeVarInt(0);
            lengthBuffer.writeByteArray(buffer.array());
        } else {
            byte[] data = new byte[readable];
            buffer.get(data);
            lengthBuffer.writeVarInt(data.length);

            this.deflater.setInput(data, 0, readable);
            this.deflater.finish();

            while (!this.deflater.finished()) {
                int length = this.deflater.deflate(this.encodeBuf);
                lengthBuffer.writeByteArray(this.encodeBuf, 0, length);
            }

            this.deflater.reset();
        }
    }
}
