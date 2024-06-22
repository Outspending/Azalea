package me.outspending.registry.biome;

import me.outspending.NamespacedID;

import java.util.Arrays;
import java.util.Optional;

public enum Biomes {
    BADLANDS(new Biome(
            NamespacedID.of("badlands"),
            (byte) 1,
            2.0F,
            Optional.empty(),
            0.0F,
            new BiomeEffects(
                    4159204,
                    0,
                    0,
                    0,
                    Optional.of(10387789),
                    Optional.of(9470285),
                    Optional.empty()
            )
    ));

    private final Biome biome;

    Biomes(Biome biome) {
        this.biome = biome;
    }

    public Biome getBiome() {
        return this.biome;
    }

    public static Biomes[] all() {
        return values();
    }

    public static Biome[] allDefault() {
        return Arrays.stream(values())
                .map(Biomes::getBiome)
                .toArray(Biome[]::new);
    }
}
