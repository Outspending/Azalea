package me.outspending.registry.dimension;

import lombok.Getter;
import me.outspending.NamespacedID;

import java.util.Arrays;

@Getter
public enum DimensionType {
    OVERWORLD(Dimension.builder(NamespacedID.of("overworld")).build()),
    OVERWORLD_CAVES(Dimension.builder(NamespacedID.of("overworld_caves")).build()),
    THE_END(Dimension.builder(NamespacedID.of("the_end"))
                .isNatural(false)
                .infiniburn(InfiniburnType.INFINIBURN_END)
                .hasSkyLight(false)
                .bedWorks(false)
                .effects(NamespacedID.of("the_end"))
                .fixedTime(6000L)
                .logicalHeight(256)
                .minY(0)
                .height(256)
                .build()
    ),
    NETHER(Dimension.builder(NamespacedID.of("the_nether"))
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
                .minY(0)
                .ultrawarm(true)
                .hasCeiling(true)
                .height(256)
                .build()
    );

    private final Dimension dimension;

    DimensionType(Dimension dimension) {
        this.dimension = dimension;
    }

    public static Dimension[] getDimensions() {
        return Arrays.stream(DimensionType.values())
                .map(DimensionType::getDimension)
                .toArray(Dimension[]::new);
    }
}
