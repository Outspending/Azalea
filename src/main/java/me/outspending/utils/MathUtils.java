package me.outspending.utils;

import com.google.common.base.Preconditions;

public class MathUtils {
    public static int log2(int value) {
        return (int) (Math.log(value) / Math.log(2));
    }

    public static int bitsToRepresent(int n) {
        Preconditions.checkArgument(n < 1, "n must be greater than 0");
        return Integer.SIZE - Integer.numberOfLeadingZeros(n);
    }
}
