package me.outspending.registry.banner;

import me.outspending.NamespacedID;

import java.util.Arrays;

public enum BannerPatterns {
    BASE(new BannerPattern(
            NamespacedID.of("base"),
            "block.minecraft.banner.base"
    )),
    BORDER(new BannerPattern(
            NamespacedID.of("border"),
            "block.minecraft.banner.border"
    )),
    BRICKS(new BannerPattern(
            NamespacedID.of("bricks"),
            "block.minecraft.banner.bricks"
    )),
    CIRCLE(new BannerPattern(
            NamespacedID.of("circle"),
            "block.minecraft.banner.circle"
    )),
    CREEPER(new BannerPattern(
            NamespacedID.of("creeper"),
            "block.minecraft.banner.creeper"
    )),
    CROSS(new BannerPattern(
            NamespacedID.of("cross"),
            "block.minecraft.banner.cross"
    )),
    CURLY_BORDER(new BannerPattern(
            NamespacedID.of("curly_border"),
            "block.minecraft.banner.curly_border"
    )),
    DIAGONAL_LEFT(new BannerPattern(
            NamespacedID.of("diagonal_left"),
            "block.minecraft.banner.diagonal_left"
    )),
    DIAGONAL_RIGHT(new BannerPattern(
            NamespacedID.of("diagonal_right"),
            "block.minecraft.banner.diagonal_right"
    )),
    DIAGONAL_UP_LEFT(new BannerPattern(
            NamespacedID.of("diagonal_up_left"),
            "block.minecraft.banner.diagonal_up_left"
    )),
    DIAGONAL_UP_RIGHT(new BannerPattern(
            NamespacedID.of("diagonal_up_right"),
            "block.minecraft.banner.diagonal_up_right"
    )),
    FLOWER(new BannerPattern(
            NamespacedID.of("flower"),
            "block.minecraft.banner.flower"
    )),
    GLOBE(new BannerPattern(
            NamespacedID.of("globe"),
            "block.minecraft.banner.globe"
    )),
    GRADIENT(new BannerPattern(
            NamespacedID.of("gradient"),
            "block.minecraft.banner.gradient"
    )),
    GRADIENT_UP(new BannerPattern(
            NamespacedID.of("gradient_up"),
            "block.minecraft.banner.gradient_up"
    )),
    HALF_HORIZONTAL(new BannerPattern(
            NamespacedID.of("half_horizontal"),
            "block.minecraft.banner.half_horizontal"
    )),
    HALF_HORIZONTAL_BOTTOM(new BannerPattern(
            NamespacedID.of("half_horizontal_bottom"),
            "block.minecraft.banner.half_horizontal_bottom"
    )),
    HALF_VERTICAL(new BannerPattern(
            NamespacedID.of("half_vertical"),
            "block.minecraft.banner.half_vertical"
    )),
    HALF_VERTICAL_RIGHT(new BannerPattern(
            NamespacedID.of("half_vertical_right"),
            "block.minecraft.banner.half_vertical_right"
    )),
    MOJANG(new BannerPattern(
            NamespacedID.of("mojang"),
            "block.minecraft.banner.mojang"
    )),
    PIGLIN(new BannerPattern(
            NamespacedID.of("piglin"),
            "block.minecraft.banner.piglin"
    )),
    RHOMBUS(new BannerPattern(
            NamespacedID.of("rhombus"),
            "block.minecraft.banner.rhombus"
    )),
    SKULL(new BannerPattern(
            NamespacedID.of("skull"),
            "block.minecraft.banner.skull"
    )),
    SMALL_STRIPES(new BannerPattern(
            NamespacedID.of("small_stripes"),
            "block.minecraft.banner.small_stripes"
    )),
    SQUARE_BOTTOM_LEFT(new BannerPattern(
            NamespacedID.of("square_bottom_left"),
            "block.minecraft.banner.square_bottom_left"
    )),
    SQUARE_BOTTOM_RIGHT(new BannerPattern(
            NamespacedID.of("square_bottom_right"),
            "block.minecraft.banner.square_bottom_right"
    )),
    SQUARE_TOP_LEFT(new BannerPattern(
            NamespacedID.of("square_top_left"),
            "block.minecraft.banner.square_top_left"
    )),
    SQUARE_TOP_RIGHT(new BannerPattern(
            NamespacedID.of("square_top_right"),
            "block.minecraft.banner.square_top_right"
    )),
    STRAIGHT_CROSS(new BannerPattern(
            NamespacedID.of("straight_cross"),
            "block.minecraft.banner.straight_cross"
    )),
    STRIPE_BOTTOM(new BannerPattern(
            NamespacedID.of("stripe_bottom"),
            "block.minecraft.banner.stripe_bottom"
    )),
    STRIPE_CENTER(new BannerPattern(
            NamespacedID.of("stripe_center"),
            "block.minecraft.banner.stripe_center"
    )),
    STRIPE_DOWNLEFT(new BannerPattern(
            NamespacedID.of("stripe_downleft"),
            "block.minecraft.banner.stripe_downleft"
    )),
    STRIPE_DOWNRIGHT(new BannerPattern(
            NamespacedID.of("stripe_downright"),
            "block.minecraft.banner.stripe_downright"
    )),
    STRIPE_LEFT(new BannerPattern(
            NamespacedID.of("stripe_left"),
            "block.minecraft.banner.stripe_left"
    )),
    STRIPE_MIDDLE(new BannerPattern(
            NamespacedID.of("stripe_middle"),
            "block.minecraft.banner.stripe_middle"
    )),
    STRIPE_RIGHT(new BannerPattern(
            NamespacedID.of("stripe_right"),
            "block.minecraft.banner.stripe_right"
    )),
    STRIPE_TOP(new BannerPattern(
            NamespacedID.of("stripe_top"),
            "block.minecraft.banner.stripe_top"
    )),
    TRIANGLE_BOTTOM(new BannerPattern(
            NamespacedID.of("triangle_bottom"),
            "block.minecraft.banner.triangle_bottom"
    )),
    TRIANGLE_TOP(new BannerPattern(
            NamespacedID.of("triangle_top"),
            "block.minecraft.banner.triangle_top"
    ));

    private final BannerPattern pattern;

    BannerPatterns(BannerPattern pattern) {
        this.pattern = pattern;
    }

    public BannerPattern getPattern() {
        return pattern;
    }

    public static BannerPatterns[] all() {
        return values();
    }

    public static BannerPattern[] allDefault() {
        return Arrays.stream(values())
                .map(BannerPatterns::getPattern)
                .toArray(BannerPattern[]::new);
    }
}
