package me.outspending.entity;

import me.outspending.protocol.Writable;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record Property(@NotNull String name, @NotNull String value, @Nullable String signature) implements Writable {
    public boolean isSigned() {
        return signature != null;
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeString(this.name);
        writer.writeString(this.value);
        writer.writeBoolean(this.isSigned());
        if (this.isSigned()) {
            writer.writeString(this.signature);
        }
    }
}