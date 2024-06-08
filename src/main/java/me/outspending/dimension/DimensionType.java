package me.outspending.dimension;

import me.outspending.NamespacedID;

public interface DimensionType {

    Dimension OVERWORLD = Dimension.builder(NamespacedID.of("overworld")).build(); // Settings are defaulted to overworld

    Dimension OVERWORLD_CAVES = Dimension.builder(NamespacedID.of("overworld_caves")).build(); // Identical to OVERWORLD

    Dimension THE_END = Dimension.builder(NamespacedID.of("the_end"))
            .isNatural(false)
            .infiniburn(InfiniburnType.INFINIBURN_END)
            .hasSkyLight(false)
            .bedWorks(false)
            .effects(NamespacedID.of("the_end"))
            .fixedTime(6000L)
            .logicalHeight(256)
            .minY(0)
            .height(256)
            .build();

    Dimension NETHER = Dimension.builder(NamespacedID.of("the_nether"))
            .isNatural(false)
            .isPiglinSafe(true)
            .ambientLight(0.1F)
            .monsterSpawnBlockLightLimit(15)
            .infiniburn(InfiniburnType.INFINIBURN_NETHER)
            .respawnAnchorWorks(true)
            .hasSkyLight(false)
            .bedWorks(false)
            .effects(NamespacedID.of("the_nether"))
            .fixedTime(18000L)
            .hasRaids(false)
            .logicalHeight(128)
            .coordinateScale(8.0)
            .monsterSpawnLightLevel(new Dimension.MonsterSpawnRules(NamespacedID.of("darkness"), 0, 7))
            .minY(0)
            .ultrawarm(true)
            .hasCeiling(true)
            .height(256)
            .build();

}
