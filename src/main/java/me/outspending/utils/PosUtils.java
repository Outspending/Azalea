package me.outspending.utils;

import me.outspending.position.Pos;
import org.jetbrains.annotations.NotNull;

public class PosUtils {
    public static double distanceToChunks(@NotNull Pos from, @NotNull Pos to, int chunks) {
        return from.distance(to) / 16;
    }
}
