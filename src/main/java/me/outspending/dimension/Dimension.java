package me.outspending.dimension;

import lombok.Getter;
import me.outspending.NamespacedID;
import me.outspending.protocol.Writable;
import me.outspending.protocol.writer.PacketWriter;
import me.outspending.registry.RegistryType;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Dimension implements Writable, RegistryType {
    private static final NamespacedID registerID = NamespacedID.of("dimension_type");
    private static final AtomicInteger idCounter = new AtomicInteger(0);

    private final NamespacedID biomeKey;
    private final int id = idCounter.incrementAndGet();

    private final boolean piglinSafe;
    private final boolean natural;
    private final float ambientLight;
    private final int monsterSpawnBlockLightLimit;
    private final InfiniburnType infiniburn;
    private final boolean respawnAnchorWorks;
    private final boolean hasSkyLight;
    private final boolean bedWorks;
    private final NamespacedID effects;
    private final long fixedTime;
    private final boolean hasRaids;
    private final int logicalHeight;
    private final double coordinateScale;
    private final MonsterSpawnRules monsterSpawnLightLevel;
    private final int minY;
    private final boolean ultrawarm;
    private final boolean hasCeiling;
    private final int height;

    public Dimension(NamespacedID biomeKey, boolean piglinSafe, boolean natural, float ambientLight, int monsterSpawnBlockLightLimit, InfiniburnType infiniburn, boolean respawnAnchorWorks, boolean hasSkyLight, boolean bedWorks, NamespacedID effects, long fixedTime, boolean hasRaids, int logicalHeight, double coordinateScale, MonsterSpawnRules monsterSpawnLightLevel, int minY, boolean ultrawarm, boolean hasCeiling, int height) {
        this.biomeKey = biomeKey;
        this.piglinSafe = piglinSafe;
        this.natural = natural;
        this.ambientLight = ambientLight;
        this.monsterSpawnBlockLightLimit = monsterSpawnBlockLightLimit;
        this.infiniburn = infiniburn;
        this.respawnAnchorWorks = respawnAnchorWorks;
        this.hasSkyLight = hasSkyLight;
        this.bedWorks = bedWorks;
        this.effects = effects;
        this.fixedTime = fixedTime;
        this.hasRaids = hasRaids;
        this.logicalHeight = logicalHeight;
        this.coordinateScale = coordinateScale;
        this.monsterSpawnLightLevel = monsterSpawnLightLevel;
        this.minY = minY;
        this.ultrawarm = ultrawarm;
        this.hasCeiling = hasCeiling;
        this.height = height;
    }

    @ApiStatus.Internal
    public final CompoundBinaryTag getNBT() {
        return CompoundBinaryTag.builder()
                .putBoolean("piglin_safe", this.piglinSafe)
                .putBoolean("natural", this.natural)
                .putFloat("ambient_light", this.ambientLight)
                .putInt("monster_spawn_block_light_limit", this.monsterSpawnBlockLightLimit)
                .putString("infiniburn", this.infiniburn.toString())
                .putBoolean("respawn_anchor_works", this.respawnAnchorWorks)
                .putBoolean("has_skylight", this.hasSkyLight)
                .putBoolean("bed_works", this.bedWorks)
                .putString("effects", this.effects.toString())
                .putLong("fixed_time", this.fixedTime)
                .putBoolean("has_raids", this.hasRaids)
                .putInt("logical_height", this.logicalHeight)
                .putDouble("coordinate_scale", this.coordinateScale)
                .put(
                        CompoundBinaryTag.builder()
                                .putString("type", this.monsterSpawnLightLevel.type.toString())
                                .putInt("min_inclusive", this.monsterSpawnLightLevel.minInclusive)
                                .putInt("max_inclusive", this.monsterSpawnLightLevel.maxInclusive)
                                .build()
                )
                .putInt("min_y", this.minY)
                .putBoolean("ultrawarm", this.ultrawarm)
                .putBoolean("has_ceiling", this.hasCeiling)
                .putInt("height", this.height)
                .build();
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        this.biomeKey.write(writer);
        writer.writeBoolean(true);
        writer.writeNBTCompound(this.getNBT());
    }

    @Contract("_ -> new")
    public static @NotNull Builder builder(@NotNull NamespacedID key) {
        return new Dimension.Builder(key);
    }

    @Override
    public @NotNull NamespacedID getRegistryID() {
        return registerID;
    }

    public record MonsterSpawnRules(NamespacedID type, int minInclusive, int maxInclusive) {}

    public static class Builder {
        private final NamespacedID biomeKey;

        private boolean piglinSafe = false;
        private boolean natural = true;
        private float ambientLight = 0.0F;
        private int monsterSpawnBlockLightLimit = 0;
        private InfiniburnType infiniburn = InfiniburnType.INFINIBURN_OVERWORLD;
        private boolean respawnAnchorWorks = false;
        private boolean hasSkyLight = true;
        private boolean bedWorks = true;
        private NamespacedID effects = NamespacedID.of("overworld");
        private long fixedTime = 0L;
        private boolean hasRaids = true;
        private int logicalHeight = 384;
        private double coordinateScale = 1.0;
        private MonsterSpawnRules monsterSpawnLightLevel = new MonsterSpawnRules(
                NamespacedID.of("uniform"),
                0, 7
        );
        private int minY = -64;
        private boolean ultrawarm = false;
        private boolean hasCeiling = false;
        private int height = 384;

        public Builder(@NotNull NamespacedID biomeKey) {
            this.biomeKey = biomeKey;
        }

        @Contract("_ -> this")
        public Builder isPiglinSafe(boolean piglinSafe) {
            this.piglinSafe = piglinSafe;
            return this;
        }

        @Contract("_ -> this")
        public Builder isNatural(boolean natural) {
            this.natural = natural;
            return this;
        }

        @Contract("_ -> this")
        public Builder ambientLight(float ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        @Contract("_ -> this")
        public Builder monsterSpawnBlockLightLimit(int monsterSpawnBlockLightLimit) {
            this.monsterSpawnBlockLightLimit = monsterSpawnBlockLightLimit;
            return this;
        }

        @Contract("_ -> this")
        public Builder infiniburn(@NotNull InfiniburnType infiniburn) {
            this.infiniburn = infiniburn;
            return this;
        }

        @Contract("_ -> this")
        public Builder respawnAnchorWorks(boolean respawnAnchorWorks) {
            this.respawnAnchorWorks = respawnAnchorWorks;
            return this;
        }

        @Contract("_ -> this")
        public Builder hasSkyLight(boolean hasSkyLight) {
            this.hasSkyLight = hasSkyLight;
            return this;
        }

        @Contract("_ -> this")
        public Builder bedWorks(boolean bedWorks) {
            this.bedWorks = bedWorks;
            return this;
        }

        @Contract("_ -> this")
        public Builder effects(@NotNull NamespacedID effects) {
            this.effects = effects;
            return this;
        }

        @Contract("_ -> this")
        public Builder fixedTime(long fixedTime) {
            this.fixedTime = fixedTime;
            return this;
        }

        @Contract("_ -> this")
        public Builder hasRaids(boolean hasRaids) {
            this.hasRaids = hasRaids;
            return this;
        }

        @Contract("_ -> this")
        public Builder logicalHeight(int logicalHeight) {
            this.logicalHeight = logicalHeight;
            return this;
        }

        @Contract("_ -> this")
        public Builder coordinateScale(double coordinateScale) {
            this.coordinateScale = coordinateScale;
            return this;
        }

        @Contract("_ -> this")
        public Builder monsterSpawnLightLevel(@NotNull MonsterSpawnRules monsterSpawnLightLevel) {
            this.monsterSpawnLightLevel = monsterSpawnLightLevel;
            return this;
        }

        @Contract("_ -> this")
        public Builder minY(int minY) {
            this.minY = minY;
            return this;
        }

        @Contract("_ -> this")
        public Builder ultrawarm(boolean ultrawarm) {
            this.ultrawarm = ultrawarm;
            return this;
        }

        @Contract("_ -> this")
        public Builder hasCeiling(boolean hasCeiling) {
            this.hasCeiling = hasCeiling;
            return this;
        }

        @Contract("_ -> this")
        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Dimension build() {
            return new Dimension(
                    this.biomeKey,

                    this.piglinSafe,
                    this.natural,
                    this.ambientLight,
                    this.monsterSpawnBlockLightLimit,
                    this.infiniburn,
                    this.respawnAnchorWorks,
                    this.hasSkyLight,
                    this.bedWorks,
                    this.effects,
                    this.fixedTime,
                    this.hasRaids,
                    this.logicalHeight,
                    this.coordinateScale,
                    this.monsterSpawnLightLevel,
                    this.minY,
                    this.ultrawarm,
                    this.hasCeiling,
                    this.height
            );
        }
    }
}
