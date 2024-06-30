package me.outspending.registry.trim;

import me.outspending.NamespacedID;

import java.util.Arrays;

public enum ArmorTrimMaterials {
    AMETHYST(new ArmorTrimMaterial(
            NamespacedID.of("amethyst"),
            "amethyst",
            "minecraft:amethyst_shard",
            1.0F,
            "A material from the coast"
    )),
    COPPER(new ArmorTrimMaterial(
            NamespacedID.of("copper"),
            "copper",
            "minecraft:copper_ingot",
            0.5F,
            "A material from the mountains"
    )),
    DIAMOND(new ArmorTrimMaterial(
            NamespacedID.of("diamond"),
            "diamond",
            "minecraft:diamond",
             0.8F,
            "A material from the depths"
    )),
    EMERALD(new ArmorTrimMaterial(
            NamespacedID.of("emerald"),
            "emerald",
            "minecraft:emerald",
            0.7F,
            "A material from the jungle"
    )),
    GOLD(new ArmorTrimMaterial(
            NamespacedID.of("gold"),
            "gold",
            "minecraft:gold_ingot",
            0.6F,
            "A material from the sky"
    )),
    IRON(new ArmorTrimMaterial(
            NamespacedID.of("iron"),
            "iron",
            "minecraft:iron_ingot",
            0.5F,
            "A material from the earth"
    )),
    LAPIS_LAZULI(new ArmorTrimMaterial(
            NamespacedID.of("lapis_lazuli"),
            "lapis_lazuli",
            "minecraft:lapis_lazuli",
            0.4F,
            "A material from the depths"
    )),
    NETHERITE(new ArmorTrimMaterial(
            NamespacedID.of("netherite"),
            "netherite",
            "minecraft:netherite_ingot",
            0.3F,
            "A material from the depths"
    )),
    QUARTZ(new ArmorTrimMaterial(
            NamespacedID.of("quartz"),
            "quartz",
            "minecraft:quartz",
            0.2F,
            "A material from the mountains"
    )),
    REDSTONE(new ArmorTrimMaterial(
            NamespacedID.of("redstone"),
            "redstone",
            "minecraft:redstone",
            0.1F,
            "A material from the earth"
    ));

    private final ArmorTrimMaterial material;

    ArmorTrimMaterials(ArmorTrimMaterial material) {
        this.material = material;
    }

    public ArmorTrimMaterial getMaterial() {
        return this.material;
    }

    public static ArmorTrimMaterials[] all() {
        return values();
    }

    public static ArmorTrimMaterial[] allDefault() {
        return Arrays.stream(values())
                .map(ArmorTrimMaterials::getMaterial)
                .toArray(ArmorTrimMaterial[]::new);
    }
}
