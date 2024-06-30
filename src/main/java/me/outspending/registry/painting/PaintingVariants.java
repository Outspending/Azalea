package me.outspending.registry.painting;

import me.outspending.NamespacedID;

import java.util.Arrays;

public enum PaintingVariants {
    WANDERER(new PaintingVariant(
            NamespacedID.of("wanderer"),
            "testing",
            (byte) 16,
            (byte) 16
    ));

    private final PaintingVariant variant;

    PaintingVariants(PaintingVariant variant) {
        this.variant = variant;
    }

    public PaintingVariant getVariant() {
        return variant;
    }

    public static PaintingVariants[] all() {
        return values();
    }

    public static PaintingVariant[] allDefault() {
        return Arrays.stream(values())
                .map(PaintingVariants::getVariant)
                .toArray(PaintingVariant[]::new);
    }
}
