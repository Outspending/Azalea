package me.outspending.registry.wolf;

import me.outspending.NamespacedID;
import me.outspending.registry.biome.Biomes;

import java.util.Arrays;

public enum WolfVariants {
    ASHEN(new WolfVariant(
            NamespacedID.of("minecraft:ashen_wolf"),
            "textures/entity/wolf/ashen_wolf",
            "textures/entity/wolf/ashen_wolf_tame",
            "textures/entity/wolf/ashen_wolf_angry",
            Biomes.BADLANDS.getBiome()
    ));

    private final WolfVariant variant;

    WolfVariants(WolfVariant variant) {
        this.variant = variant;
    }

    public WolfVariant getVariant() {
        return variant;
    }

    public static WolfVariants[] all() {
        return values();
    }

    public static WolfVariant[] allDefault() {
        return Arrays.stream(values())
                .map(WolfVariants::getVariant)
                .toArray(WolfVariant[]::new);
    }
}
