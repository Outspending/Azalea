package me.outspending.registry.trim;

import me.outspending.NamespacedID;

import java.util.Arrays;

public enum ArmorTrimPatterns {
    COAST(new ArmorTrimPattern(
            NamespacedID.of("coast"),
            "minecraft:coast",
            "minecraft:coast_armor_trim_smithing_template",
            "A pattern from the coast",
            (byte) 0
    )),
    DUNE(new ArmorTrimPattern(
            NamespacedID.of("dune"),
            "minecraft:dune",
            "minecraft:dune_armor_trim_smithing_template",
            "A pattern from the dunes",
            (byte) 0
    )),
    EYE(new ArmorTrimPattern(
            NamespacedID.of("eye"),
            "minecraft:eye",
            "minecraft:eye_armor_trim_smithing_template",
            "A pattern from the eye",
            (byte) 0
    )),
    HOST(new ArmorTrimPattern(
            NamespacedID.of("host"),
            "minecraft:host",
            "minecraft:host_armor_trim_smithing_template",
            "A pattern from the host",
            (byte) 0
    )),
    RAISER(new ArmorTrimPattern(
            NamespacedID.of("raiser"),
            "minecraft:raiser",
            "minecraft:raiser_armor_trim_smithing_template",
            "A pattern from the raiser",
            (byte) 0
    )),
    RIB(new ArmorTrimPattern(
            NamespacedID.of("rib"),
            "minecraft:rib",
            "minecraft:rib_armor_trim_smithing_template",
            "A pattern from the rib",
            (byte) 0
    )),
    SENTRY(new ArmorTrimPattern(
            NamespacedID.of("sentry"),
            "minecraft:sentry",
            "minecraft:sentry_armor_trim_smithing_template",
            "A pattern from the sentry",
            (byte) 0
    )),
    SHAPER(new ArmorTrimPattern(
            NamespacedID.of("shaper"),
            "minecraft:shaper",
            "minecraft:shaper_armor_trim_smithing_template",
            "A pattern from the shaper",
            (byte) 0
    )),
    SILENCE(new ArmorTrimPattern(
            NamespacedID.of("silence"),
            "minecraft:silence",
            "minecraft:silence_armor_trim_smithing_template",
            "A pattern from the silence",
            (byte) 0
    )),
    SNOUT(new ArmorTrimPattern(
            NamespacedID.of("snout"),
            "minecraft:snout",
            "minecraft:snout_armor_trim_smithing_template",
            "A pattern from the snout",
            (byte) 0
    )),
    SPIRE(new ArmorTrimPattern(
            NamespacedID.of("spire"),
            "minecraft:spire",
            "minecraft:spire_armor_trim_smithing_template",
            "A pattern from the spire",
            (byte) 0
    )),
    TIDE(new ArmorTrimPattern(
            NamespacedID.of("tide"),
            "minecraft:tide",
            "minecraft:tide_armor_trim_smithing_template",
            "A pattern from the tide",
            (byte) 0
    )),
    VEX(new ArmorTrimPattern(
            NamespacedID.of("vex"),
            "minecraft:vex",
            "minecraft:vex_armor_trim_smithing_template",
            "A pattern from the vex",
            (byte) 0
    )),
    WARD(new ArmorTrimPattern(
            NamespacedID.of("ward"),
            "minecraft:ward",
            "minecraft:ward_armor_trim_smithing_template",
            "A pattern from the ward",
            (byte) 0
    )),
    WAYFINDER(new ArmorTrimPattern(
            NamespacedID.of("wayfinder"),
            "minecraft:wayfinder",
            "minecraft:wayfinder_armor_trim_smithing_template",
            "A pattern from the wayfinder",
            (byte) 0
    )),
    WILD(new ArmorTrimPattern(
            NamespacedID.of("wild"),
            "minecraft:wild",
            "minecraft:wild_armor_trim_smithing_template",
            "A pattern from the wild",
            (byte) 0
    ));

    private final ArmorTrimPattern pattern;

    ArmorTrimPatterns(ArmorTrimPattern pattern) {
        this.pattern = pattern;
    }

    public ArmorTrimPattern getPattern() {
        return this.pattern;
    }

    public static ArmorTrimPatterns[] all() {
        return values();
    }

    public static ArmorTrimPattern[] allDefault() {
        return Arrays.stream(values())
                .map(ArmorTrimPatterns::getPattern)
                .toArray(ArmorTrimPattern[]::new);
    }
}
