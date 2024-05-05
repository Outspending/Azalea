package me.outspending.protocol;

import lombok.Getter;
import lombok.SneakyThrows;
import me.outspending.protocol.reader.PacketReader;

import java.nio.ByteBuffer;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

@Getter
public class CompressionDecoder {
    private final Inflater inflater;
    private final boolean validateDecompressed;

    public CompressionDecoder(boolean validateDecompressed) {
        this.validateDecompressed = validateDecompressed;
        this.inflater = new Inflater();
    }

    public void decode(PacketReader reader, ByteBuffer buffer) {
        if (buffer.remaining() != 0) {
            int length = reader.readVarInt();
            if (this.validateDecompressed) {
                this.setupInput(buffer);
                ByteBuffer inflated = this.inflate(buffer);
                this.inflater.reset();
            }
        }
    }

    @SneakyThrows
    private void setupInput(ByteBuffer buffer) {
        ByteBuffer input;
        if (buffer.remaining() > 0) {
            input = ByteBuffer.wrap(buffer.array());
        } else {
            input = ByteBuffer.allocateDirect(buffer.remaining());
            buffer.put(input);
            input.flip();
        }

        this.inflater.setInput(input);
    }

    private ByteBuffer inflate(ByteBuffer buffer) {
        try {
            this.inflater.inflate(buffer);
        } catch (DataFormatException e) {
            e.printStackTrace();
        }

        return buffer;
    }
}
