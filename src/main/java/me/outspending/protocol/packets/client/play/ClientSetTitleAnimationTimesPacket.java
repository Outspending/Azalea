package me.outspending.protocol.packets.client.play;

import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public record ClientSetTitleAnimationTimesPacket(int fadeIn, int stay, int fadeOut) implements ClientPacket {
    public ClientSetTitleAnimationTimesPacket(Duration fadeIn, Duration stay, Duration fadeOut) {
        this(
                ((int) (fadeIn.toMillis() / 50)),
                ((int) (stay.toMillis() / 50)),
                ((int) (fadeOut.toMillis() / 50))
        );
    }

    private int ticksFromDuration(Duration duration) {
        return (int) (duration.toMillis() / 50);
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeInt(this.fadeIn);
        writer.writeInt(this.stay);
        writer.writeInt(this.fadeOut);
    }

    @Override
    public int id() {
        return 0x66;
    }

}
