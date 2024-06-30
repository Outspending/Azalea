package me.outspending.messages.serializer;

import net.kyori.adventure.nbt.BinaryTag;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

final class NBTDataComponentValueImpl implements NBTDataComponentValue {

    private final BinaryTag binaryTag;

    NBTDataComponentValueImpl(@NotNull BinaryTag binaryTag) {
        this.binaryTag = binaryTag;
    }

    @Override
    public @NotNull BinaryTag binaryTag() {
        return this.binaryTag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NBTDataComponentValueImpl)) return false;
        NBTDataComponentValueImpl that = (NBTDataComponentValueImpl) o;
        return Objects.equals(this.binaryTag, that.binaryTag);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.binaryTag);
    }
}