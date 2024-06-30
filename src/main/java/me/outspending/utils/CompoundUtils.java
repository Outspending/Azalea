package me.outspending.utils;

import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.api.BinaryTagHolder;

public class CompoundUtils {
    public static CompoundBinaryTag fromString(BinaryTagHolder holder) {
        return CompoundBinaryTag.builder()
                .putString("string", holder.string())
                .build();
    }
}
