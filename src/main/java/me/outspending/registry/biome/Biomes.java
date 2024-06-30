package me.outspending.registry.biome;

import me.outspending.NamespacedID;

import java.util.Arrays;
import java.util.Optional;

public enum Biomes {
    BADLANDS(new Biome(
            NamespacedID.of("badlands"),
            (byte) 0,
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
    )),
    BAMBOO_JUNGLE(new Biome(
            NamespacedID.of("bamboo_jungle"),
            (byte) 1,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    5470985,
                    5470985,
                    5470985,
                    5470985,
                    Optional.of(5470985),
                    Optional.of(5470985),
                    Optional.empty()
            )
    )),
    BASALT_DELTAS(new Biome(
            NamespacedID.of("basalt_deltas"),
            (byte) 0,
            0.2F,
            Optional.empty(),
            0.2F,
            new BiomeEffects(
                    8991416,
                    8991416,
                    8991416,
                    8991416,
                    Optional.of(8991416),
                    Optional.of(8991416),
                    Optional.empty()
            )
    )),
    BEACH(new Biome(
            NamespacedID.of("beach"),
            (byte) 1,
            0.0F,
            Optional.empty(),
            0.025F,
            new BiomeEffects(
                    2607981,
                    2697509,
                    2960520,
                    2697509,
                    Optional.of(2697509),
                    Optional.of(2960520),
                    Optional.empty()
            )
    )),
    BIRCH_FOREST(new Biome(
            NamespacedID.of("birch_forest"),
            (byte) 1,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    3175492,
                    1456435,
                    3175492,
                    1456435,
                    Optional.of(3175492),
                    Optional.of(1456435),
                    Optional.empty()
            )
    )),
    CHERRY_GROVE(new Biome(
            NamespacedID.of("cherry_grove"),
            (byte) 1,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    3175492,
                    1456435,
                    3175492,
                    1456435,
                    Optional.of(3175492),
                    Optional.of(1456435),
                    Optional.empty()
            )
    )),
    COLD_OCEAN(new Biome(
            NamespacedID.of("cold_ocean"),
            (byte) 1,
            0.5F,
            Optional.empty(),
            0.05F,
            new BiomeEffects(
                    10526975,
                    10526975,
                    10526975,
                    10526975,
                    Optional.of(10526975),
                    Optional.of(10526975),
                    Optional.empty()
            )
    )),
    CRIMSON_FOREST(new Biome(
            NamespacedID.of("crimson_forest"),
            (byte) 0,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    9470285,
                    10387789,
                    9470285,
                    10387789,
                    Optional.of(9470285),
                    Optional.of(10387789),
                    Optional.empty()
            )
    )),
    DARK_FOREST(new Biome(
            NamespacedID.of("dark_forest"),
            (byte) 1,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    1456435,
                    1456435,
                    1456435,
                    1456435,
                    Optional.of(1456435),
                    Optional.of(1456435),
                    Optional.empty()
            )
    )),
    DEEP_COLD_OCEAN(new Biome(
            NamespacedID.of("deep_cold_ocean"),
            (byte) 1,
            0.5F,
            Optional.empty(),
            0.05F,
            new BiomeEffects(
                    10526975,
                    10526975,
                    10526975,
                    10526975,
                    Optional.of(10526975),
                    Optional.of(10526975),
                    Optional.empty()
            )
    )),
    DEEP_DARK(new Biome(
            NamespacedID.of("deep_dark"),
            (byte) 1,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    1456435,
                    1456435,
                    1456435,
                    1456435,
                    Optional.of(1456435),
                    Optional.of(1456435),
                    Optional.empty()
            )
    )),
    DEEP_FROZEN_OCEAN(new Biome(
            NamespacedID.of("deep_frozen_ocean"),
            (byte) 1,
            0.5F,
            Optional.empty(),
            0.05F,
            new BiomeEffects(
                    10526975,
                    10526975,
                    10526975,
                    10526975,
                    Optional.of(10526975),
                    Optional.of(10526975),
                    Optional.empty()
            )
    )),
    DEEP_LUKEWARM_OCEAN(new Biome(
            NamespacedID.of("deep_lukewarm_ocean"),
            (byte) 1,
            0.5F,
            Optional.empty(),
            0.05F,
            new BiomeEffects(
                    10526975,
                    10526975,
                    10526975,
                    10526975,
                    Optional.of(10526975),
                    Optional.of(10526975),
                    Optional.empty()
            )
    )),
    DEEP_OCEAN(new Biome(
            NamespacedID.of("deep_ocean"),
            (byte) 1,
            0.5F,
            Optional.empty(),
            0.05F,
            new BiomeEffects(
                    10526975,
                    10526975,
                    10526975,
                    10526975,
                    Optional.of(10526975),
                    Optional.of(10526975),
                    Optional.empty()
            )
    )),
    DESERT(new Biome(
            NamespacedID.of("desert"),
            (byte) 0,
            2.0F,
            Optional.empty(),
            0.05F,
            new BiomeEffects(
                    16421912,
                    13786898,
                    16421912,
                    13786898,
                    Optional.of(16421912),
                    Optional.of(13786898),
                    Optional.empty()
            )
    )),
    DRIPSTONE_CAVE(new Biome(
            NamespacedID.of("dripstone_cave"),
            (byte) 1,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    10518688,
                    10518688,
                    10518688,
                    10518688,
                    Optional.of(10518688),
                    Optional.of(10518688),
                    Optional.empty()
            )
    )),
    END_BARRENS(new Biome(
            NamespacedID.of("end_barrens"),
            (byte) 0,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    10518688,
                    10518688,
                    10518688,
                    10518688,
                    Optional.of(10518688),
                    Optional.of(10518688),
                    Optional.empty()
            )
    )),
    END_HIGHLANDS(new Biome(
            NamespacedID.of("end_highlands"),
            (byte) 0,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    10518688,
                    10518688,
                    10518688,
                    10518688,
                    Optional.of(10518688),
                    Optional.of(10518688),
                    Optional.empty()
            )
    )),
    END_MIDLANDS(new Biome(
            NamespacedID.of("end_midlands"),
            (byte) 0,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    10518688,
                    10518688,
                    10518688,
                    10518688,
                    Optional.of(10518688),
                    Optional.of(10518688),
                    Optional.empty()
            )
    )),
    ERODED_BADLANDS(new Biome(
            NamespacedID.of("eroded_badlands"),
            (byte) 0,
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
    )),
    FLOWER_FOREST(new Biome(
            NamespacedID.of("flower_forest"),
            (byte) 1,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    3175492,
                    1456435,
                    3175492,
                    1456435,
                    Optional.of(3175492),
                    Optional.of(1456435),
                    Optional.empty()
            )
    )),
    FOREST(new Biome(
            NamespacedID.of("forest"),
            (byte) 1,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    3175492,
                    1456435,
                    3175492,
                    1456435,
                    Optional.of(3175492),
                    Optional.of(1456435),
                    Optional.empty()
            )
    )),
    FROZEN_OCEAN(new Biome(
            NamespacedID.of("frozen_ocean"),
            (byte) 1,
            0.5F,
            Optional.empty(),
            0.05F,
            new BiomeEffects(
                    10526975,
                    10526975,
                    10526975,
                    10526975,
                    Optional.of(10526975),
                    Optional.of(10526975),
                    Optional.empty()
            )
    )),
    FROZEN_PEAKS(new Biome(
            NamespacedID.of("frozen_peaks"),
            (byte) 1,
            0.2F,
            Optional.empty(),
            0.05F,
            new BiomeEffects(
                    10518688,
                    10518688,
                    10518688,
                    10518688,
                    Optional.of(10518688),
                    Optional.of(10518688),
                    Optional.empty()
            )
    )),
    FROZEN_RIVER(new Biome(
            NamespacedID.of("frozen_river"),
            (byte) 1,
            0.0F,
            Optional.empty(),
            0.025F,
            new BiomeEffects(
                    10526975,
                    10526975,
                    10526975,
                    10526975,
                    Optional.of(10526975),
                    Optional.of(10526975),
                    Optional.empty()
            )
    )),
    GROVE(new Biome(
            NamespacedID.of("grove"),
            (byte) 1,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    3175492,
                    1456435,
                    3175492,
                    1456435,
                    Optional.of(3175492),
                    Optional.of(1456435),
                    Optional.empty()
            )
    )),
    ICE_SPIKES(new Biome(
            NamespacedID.of("ice_spikes"),
            (byte) 1,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    10518688,
                    10518688,
                    10518688,
                    10518688,
                    Optional.of(10518688),
                    Optional.of(10518688),
                    Optional.empty()
            )
    )),
    JAGGED_PEAKS(new Biome(
            NamespacedID.of("jagged_peaks"),
            (byte) 1,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    10518688,
                    10518688,
                    10518688,
                    10518688,
                    Optional.of(10518688),
                    Optional.of(10518688),
                    Optional.empty()
            )
    )),
    JUNGLE(new Biome(
            NamespacedID.of("jungle"),
            (byte) 1,
            0.2F,
            Optional.empty(),
            0.2F,
            new BiomeEffects(
                    5470985,
                    5470985,
                    5470985,
                    5470985,
                    Optional.of(5470985),
                    Optional.of(5470985),
                    Optional.empty()
            )
    )),
    LUKEWARM_OCEAN(new Biome(
            NamespacedID.of("lukewarm_ocean"),
            (byte) 1,
            0.5F,
            Optional.empty(),
            0.05F,
            new BiomeEffects(
                    10526975,
                    10526975,
                    10526975,
                    10526975,
                    Optional.of(10526975),
                    Optional.of(10526975),
                    Optional.empty()
            )
    )),
    LUSH_CAVES(new Biome(
            NamespacedID.of("lush_caves"),
            (byte) 1,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    3175492,
                    1456435,
                    3175492,
                    1456435,
                    Optional.of(3175492),
                    Optional.of(1456435),
                    Optional.empty()
            )
    )),
    MANGROVE_SWAMP(new Biome(
            NamespacedID.of("mangrove_swamp"),
            (byte) 1,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    3175492,
                    1456435,
                    3175492,
                    1456435,
                    Optional.of(3175492),
                    Optional.of(1456435),
                    Optional.empty()
            )
    )),
    MEADOW(new Biome(
            NamespacedID.of("meadow"),
            (byte) 1,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    3175492,
                    1456435,
                    3175492,
                    1456435,
                    Optional.of(3175492),
                    Optional.of(1456435),
                    Optional.empty()
            )
    )),
    MUSHROOM_FIELDS(new Biome(
            NamespacedID.of("mushroom_fields"),
            (byte) 1,
            0.9F,
            Optional.empty(),
            0.05F,
            new BiomeEffects(
                    16734296,
                    10486015,
                    16734296,
                    10486015,
                    Optional.of(16734296),
                    Optional.of(10486015),
                    Optional.empty()
            )
    )),
    NETHER_WASTES(new Biome(
            NamespacedID.of("nether_wastes"),
            (byte) 0,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    10387789,
                    10387789,
                    10387789,
                    10387789,
                    Optional.of(10387789),
                    Optional.of(10387789),
                    Optional.empty()
            )
    )),
    OCEAN(new Biome(
            NamespacedID.of("ocean"),
            (byte) 1,
            0.5F,
            Optional.empty(),
            0.05F,
            new BiomeEffects(
                    10526975,
                    10526975,
                    10526975,
                    10526975,
                    Optional.of(10526975),
                    Optional.of(10526975),
                    Optional.empty()
            )
    )),
    OLD_GROWTH_BIRCH_FOREST(new Biome(
            NamespacedID.of("old_growth_birch_forest"),
            (byte) 1,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    3175492,
                    1456435,
                    3175492,
                    1456435,
                    Optional.of(3175492),
                    Optional.of(1456435),
                    Optional.empty()
            )
    )),
    OLD_GROWTH_PINE_TAIGA(new Biome(
            NamespacedID.of("old_growth_pine_taiga"),
            (byte) 1,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    3175492,
                    1456435,
                    3175492,
                    1456435,
                    Optional.of(3175492),
                    Optional.of(1456435),
                    Optional.empty()
            )
    )),
    OLD_GROWTH_SPRUCE_TAIGA(new Biome(
            NamespacedID.of("old_growth_spruce_taiga"),
            (byte) 1,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    3175492,
                    1456435,
                    3175492,
                    1456435,
                    Optional.of(3175492),
                    Optional.of(1456435),
                    Optional.empty()
            )
    )),
    PLAINS(new Biome(
            NamespacedID.of("plains"),
            (byte) 1,
            0.8F,
            Optional.empty(),
            0.05F,
            new BiomeEffects(
                    9286496,
                    15198183,
                    9286496,
                    15198183,
                    Optional.of(9286496),
                    Optional.of(15198183),
                    Optional.empty()
            )
    )),
    RIVER(new Biome(
            NamespacedID.of("river"),
            (byte) 1,
            0.5F,
            Optional.empty(),
            0.05F,
            new BiomeEffects(
                    10526975,
                    10526975,
                    10526975,
                    10526975,
                    Optional.of(10526975),
                    Optional.of(10526975),
                    Optional.empty()
            )
    )),
    SAVANNA(new Biome(
            NamespacedID.of("savanna"),
            (byte) 0,
            1.2F,
            Optional.empty(),
            0.05F,
            new BiomeEffects(
                    12431967,
                    10987431,
                    12431967,
                    10987431,
                    Optional.of(12431967),
                    Optional.of(10987431),
                    Optional.empty()
            )
    )),
    SAVANNA_PLATEAU(new Biome(
            NamespacedID.of("savanna_plateau"),
            (byte) 0,
            1.0F,
            Optional.empty(),
            0.025F,
            new BiomeEffects(
                    12431967,
                    10987431,
                    12431967,
                    10987431,
                    Optional.of(12431967),
                    Optional.of(10987431),
                    Optional.empty()
            )
    )),
    SMALL_END_ISLANDS(new Biome(
            NamespacedID.of("small_end_islands"),
            (byte) 0,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    10518688,
                    10518688,
                    10518688,
                    10518688,
                    Optional.of(10518688),
                    Optional.of(10518688),
                    Optional.empty()
            )
    )),
    SNOWY_BEACH(new Biome(
            NamespacedID.of("snowy_beach"),
            (byte) 1,
            0.0F,
            Optional.empty(),
            0.025F,
            new BiomeEffects(
                    2607981,
                    2697509,
                    2960520,
                    2697509,
                    Optional.of(2697509),
                    Optional.of(2960520),
                    Optional.empty()
            )
    )),
    SNOWY_PLAINS(new Biome(
            NamespacedID.of("snowy_plains"),
            (byte) 1,
            0.8F,
            Optional.empty(),
            0.05F,
            new BiomeEffects(
                    9286496,
                    15198183,
                    9286496,
                    15198183,
                    Optional.of(9286496),
                    Optional.of(15198183),
                    Optional.empty()
            )
    )),
    SNOWY_SLOPES(new Biome(
            NamespacedID.of("snowy_slopes"),
            (byte) 1,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    10518688,
                    10518688,
                    10518688,
                    10518688,
                    Optional.of(10518688),
                    Optional.of(10518688),
                    Optional.empty()
            )
    )),
    SNOWY_TAIGA(new Biome(
            NamespacedID.of("snowy_taiga"),
            (byte) 1,
            0.2F,
            Optional.empty(),
            0.2F,
            new BiomeEffects(
                    3175492,
                    1456435,
                    3175492,
                    1456435,
                    Optional.of(3175492),
                    Optional.of(1456435),
                    Optional.empty()
            )
    )),
    SOUL_SAND_VALLEY(new Biome(
            NamespacedID.of("soul_sand_valley"),
            (byte) 0,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    9470285,
                    10387789,
                    9470285,
                    10387789,
                    Optional.of(9470285),
                    Optional.of(10387789),
                    Optional.empty()
            )
    )),
    SPARSE_JUNGLE(new Biome(
            NamespacedID.of("sparse_jungle"),
            (byte) 1,
            0.2F,
            Optional.empty(),
            0.2F,
            new BiomeEffects(
                    5470985,
                    5470985,
                    5470985,
                    5470985,
                    Optional.of(5470985),
                    Optional.of(5470985),
                    Optional.empty()
            )
    )),
    STONY_PEAKS(new Biome(
            NamespacedID.of("stony_peaks"),
            (byte) 1,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    10518688,
                    10518688,
                    10518688,
                    10518688,
                    Optional.of(10518688),
                    Optional.of(10518688),
                    Optional.empty()
            )
    )),
    STONY_SHORE(new Biome(
            NamespacedID.of("stony_shore"),
            (byte) 1,
            0.0F,
            Optional.empty(),
            0.025F,
            new BiomeEffects(
                    2607981,
                    2697509,
                    2960520,
                    2697509,
                    Optional.of(2697509),
                    Optional.of(2960520),
                    Optional.empty()
            )
    )),
    SUNFLOWER_PLAINS(new Biome(
            NamespacedID.of("sunflower_plains"),
            (byte) 1,
            0.8F,
            Optional.empty(),
            0.05F,
            new BiomeEffects(
                    9286496,
                    15198183,
                    9286496,
                    15198183,
                    Optional.of(9286496),
                    Optional.of(15198183),
                    Optional.empty()
            )
    )),
    SWAMP(new Biome(
            NamespacedID.of("swamp"),
            (byte) 1,
            0.8F,
            Optional.empty(),
            0.025F,
            new BiomeEffects(
                    5011004,
                    8970569,
                    5011004,
                    8970569,
                    Optional.of(5011004),
                    Optional.of(8970569),
                    Optional.empty()
            )
    )),
    TAIGA(new Biome(
            NamespacedID.of("taiga"),
            (byte) 1,
            0.2F,
            Optional.empty(),
            0.2F,
            new BiomeEffects(
                    3175492,
                    1456435,
                    3175492,
                    1456435,
                    Optional.of(3175492),
                    Optional.of(1456435),
                    Optional.empty()
            )
    )),
    THE_END(new Biome(
            NamespacedID.of("the_end"),
            (byte) 0,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    10518688,
                    10518688,
                    10518688,
                    10518688,
                    Optional.of(10518688),
                    Optional.of(10518688),
                    Optional.empty()
            )
    )),
    THE_VOID(new Biome(
            NamespacedID.of("the_void"),
            (byte) 0,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    10518688,
                    10518688,
                    10518688,
                    10518688,
                    Optional.of(10518688),
                    Optional.of(10518688),
                    Optional.empty()
            )
    )),
    WARM_OCEAN(new Biome(
            NamespacedID.of("warm_ocean"),
            (byte) 1,
            0.5F,
            Optional.empty(),
            0.05F,
            new BiomeEffects(
                    10526975,
                    10526975,
                    10526975,
                    10526975,
                    Optional.of(10526975),
                    Optional.of(10526975),
                    Optional.empty()
            )
    )),
    WARPED_FOREST(new Biome(
            NamespacedID.of("warped_forest"),
            (byte) 1,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    9470285,
                    10387789,
                    9470285,
                    10387789,
                    Optional.of(9470285),
                    Optional.of(10387789),
                    Optional.empty()
            )
    )),
    WINDSWEPT_FOREST(new Biome(
            NamespacedID.of("windswept_forest"),
            (byte) 0,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    3175492,
                    1456435,
                    3175492,
                    1456435,
                    Optional.of(3175492),
                    Optional.of(1456435),
                    Optional.empty()
            )
    )),
    WINDSWEPT_GRAVELLY_HILLS(new Biome(
            NamespacedID.of("windswept_gravelly_hills"),
            (byte) 1,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    3175492,
                    1456435,
                    3175492,
                    1456435,
                    Optional.of(3175492),
                    Optional.of(1456435),
                    Optional.empty()
            )
    )),
    WINDSWEPT_HILLS(new Biome(
            NamespacedID.of("windswept_hills"),
            (byte) 1,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    3175492,
                    1456435,
                    3175492,
                    1456435,
                    Optional.of(3175492),
                    Optional.of(1456435),
                    Optional.empty()
            )
    )),
    WINDSWEPT_SAVANNA(new Biome(
            NamespacedID.of("windswept_savanna"),
            (byte) 0,
            0.1F,
            Optional.empty(),
            0.1F,
            new BiomeEffects(
                    3175492,
                    1456435,
                    3175492,
                    1456435,
                    Optional.of(3175492),
                    Optional.of(1456435),
                    Optional.empty()
            )
    )),
    WOODED_BADLANDS(new Biome(
            NamespacedID.of("wooded_badlands"),
            (byte) 0,
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
