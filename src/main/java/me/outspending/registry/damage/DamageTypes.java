package me.outspending.registry.damage;

import me.outspending.NamespacedID;
import me.outspending.registry.banner.BannerPattern;
import me.outspending.registry.banner.BannerPatterns;

import java.util.Arrays;
import java.util.Optional;

public enum DamageTypes {
    ARROW(new DamageType(
            NamespacedID.of("arrow"),
            "arrow",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.empty(),
            Optional.empty()
    )),
    BAD_RESPAWN_POINT(new DamageType(
            NamespacedID.of("bad_respawn_point"),
            "badRespawnPoint",
            "always",
            0.1F,
            Optional.empty(),
            Optional.of("intentional_game_design")
    )),
    CACTUS(new DamageType(
            NamespacedID.of("cactus"),
            "cactus",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.empty(),
            Optional.empty()
    )),
    CRAMMING(new DamageType(
            NamespacedID.of("cramming"),
            "cramming",
            "when_caused_by_living_non_player",
            0.0F,
            Optional.empty(),
            Optional.empty()
    )),
    DRAGON_BREATH(new DamageType(
            NamespacedID.of("dragon_breath"),
            "dragonBreath",
            "when_caused_by_living_non_player",
            0.0F,
            Optional.empty(),
            Optional.empty()
    )),
    DROWN(new DamageType(
            NamespacedID.of("drown"),
            "drown",
            "when_caused_by_living_non_player",
            0.0F,
            Optional.of("drowning"),
            Optional.empty()
    )),
    DRY_OUT(new DamageType(
            NamespacedID.of("dry_out"),
            "dryout",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.empty(),
            Optional.empty()
    )),
    EXPLOSION(new DamageType(
            NamespacedID.of("explosion"),
            "explosion",
            "always",
            0.1F,
            Optional.empty(),
            Optional.empty()
    )),
    FALL(new DamageType(
            NamespacedID.of("fall"),
            "fall",
            "when_caused_by_living_non_player",
            0.0F,
            Optional.empty(),
            Optional.of("fall_variants")
    )),
    FALLING_ANVIL(new DamageType(
            NamespacedID.of("falling_anvil"),
            "anvil",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.empty(),
            Optional.empty()
    )),
    FALLING_BLOCK(new DamageType(
            NamespacedID.of("falling_block"),
            "fallingBlock",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.empty(),
            Optional.empty()
    )),
    FALLING_STALACTITE(new DamageType(
            NamespacedID.of("falling_stalactite"),
            "fallingStalactite",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.empty(),
            Optional.empty()
    )),
    FIREBALL(new DamageType(
            NamespacedID.of("fireball"),
            "fireball",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.empty(),
            Optional.empty()
    )),
    FIREWORKS(new DamageType(
            NamespacedID.of("fireworks"),
            "fireworks",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.empty(),
            Optional.empty()
    )),
    FLY_INTO_WALL(new DamageType(
            NamespacedID.of("fly_into_wall"),
            "flyIntoWall",
            "when_caused_by_living_non_player",
            0.0F,
            Optional.empty(),
            Optional.empty()
    )),
    FREEZE(new DamageType(
            NamespacedID.of("freeze"),
            "freeze",
            "when_caused_by_living_non_player",
            0.0F,
            Optional.of("freezing"),
            Optional.empty()
    )),
    GENERIC(new DamageType(
            NamespacedID.of("generic"),
            "generic",
            "when_caused_by_living_non_player",
            0.0F,
            Optional.empty(),
            Optional.empty()
    )),
    GENERIC_KILL(new DamageType(
            NamespacedID.of("generic_kill"),
            "genericKill",
            "when_caused_by_living_non_player",
            0.0F,
            Optional.empty(),
            Optional.empty()
    )),
    HOT_FLOOR(new DamageType(
            NamespacedID.of("hot_floor"),
            "hotFloor",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.of("burning"),
            Optional.empty()
    )),
    IN_FIRE(new DamageType(
            NamespacedID.of("in_fire"),
            "inFire",
            "when_caused_by_living_non_player",
            0.0F,
            Optional.of("burning"),
            Optional.empty()
    )),
    CAMPFIRE(new DamageType(
            NamespacedID.of("campfire"),
            "inFire",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.of("burning"),
            Optional.empty()
    )),
    IN_WALL(new DamageType(
            NamespacedID.of("in_wall"),
            "inWall",
            "when_caused_by_living_non_player",
            0.0F,
            Optional.empty(),
            Optional.empty()
    )),
    INDIRECT_MAGIC(new DamageType(
            NamespacedID.of("indirect_magic"),
            "indirectMagic",
            "when_caused_by_living_non_player",
            0.0F,
            Optional.empty(),
            Optional.empty()
    )),
    LAVA(new DamageType(
            NamespacedID.of("lava"),
            "lava",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.of("burning"),
            Optional.empty()
    )),
    LIGHTNING_BOLT(new DamageType(
            NamespacedID.of("lightning_bolt"),
            "lightningBolt",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.empty(),
            Optional.empty()
    )),
    MAGIC(new DamageType(
            NamespacedID.of("magic"),
            "magic",
            "when_caused_by_living_non_player",
            0.0F,
            Optional.empty(),
            Optional.empty()
    )),
    MOB_ATTACK(new DamageType(
            NamespacedID.of("mob_attack"),
            "mob",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.empty(),
            Optional.empty()
    )),
    MOB_ATTACK_NO_AGGRO(new DamageType(
            NamespacedID.of("mob_attack_no_aggro"),
            "mob",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.empty(),
            Optional.empty()
    )),
    MOB_PROJECTILE(new DamageType(
            NamespacedID.of("mob_projectile"),
            "mob",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.empty(),
            Optional.empty()
    )),
    ON_FIRE(new DamageType(
            NamespacedID.of("on_fire"),
            "onFire",
            "when_caused_by_living_non_player",
            0.0F,
            Optional.of("burning"),
            Optional.empty()
    )),
    OUT_OF_WORLD(new DamageType(
            NamespacedID.of("out_of_world"),
            "outOfWorld",
            "when_caused_by_living_non_player",
            0.0F,
            Optional.empty(),
            Optional.empty()
    )),
    OUTSIDE_BORDER(new DamageType(
            NamespacedID.of("outside_border"),
            "outsideBorder",
            "when_caused_by_living_non_player",
            0.0F,
            Optional.empty(),
            Optional.empty()
    )),
    PLAYER_ATTACK(new DamageType(
            NamespacedID.of("player_attack"),
            "player",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.empty(),
            Optional.empty()
    )),
    PLAYER_EXPLOSION(new DamageType(
            NamespacedID.of("player_explosion"),
            "explosion.player",
            "always",
            0.1F,
            Optional.empty(),
            Optional.empty()
    )),
    SONIC_BOOM(new DamageType(
            NamespacedID.of("sonic_boom"),
            "sonic_boom",
            "always",
            0.0F,
            Optional.empty(),
            Optional.empty()
    )),
    SPIT(new DamageType(
            NamespacedID.of("spit"),
            "mob",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.empty(),
            Optional.empty()
    )),
    STALAGMITE(new DamageType(
            NamespacedID.of("stalagmite"),
            "stalagmite",
            "when_caused_by_living_non_player",
            0.0F,
            Optional.empty(),
            Optional.empty()
    )),
    STARVE(new DamageType(
            NamespacedID.of("starve"),
            "starve",
            "when_caused_by_living_non_player",
            0.0F,
            Optional.empty(),
            Optional.empty()
    )),
    STING(new DamageType(
            NamespacedID.of("sting"),
            "sting",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.empty(),
            Optional.empty()
    )),
    SWEET_BERRY_BUSH(new DamageType(
            NamespacedID.of("sweet_berry_bush"),
            "sweetBerryBush",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.of("poking"),
            Optional.empty()
    )),
    THORNS(new DamageType(
            NamespacedID.of("thorns"),
            "thorns",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.of("thorns"),
            Optional.empty()
    )),
    THROWN(new DamageType(
            NamespacedID.of("thrown"),
            "thrown",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.empty(),
            Optional.empty()
    )),
    TRIDENT(new DamageType(
            NamespacedID.of("trident"),
            "trident",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.empty(),
            Optional.empty()
    )),
    UNATTRIBUTED_FIREBALL(new DamageType(
            NamespacedID.of("unattributed_fireball"),
            "onFire",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.of("burning"),
            Optional.empty()
    )),
    WITHER(new DamageType(
            NamespacedID.of("wither"),
            "wither",
            "when_caused_by_living_non_player",
            0.0F,
            Optional.empty(),
            Optional.empty()
    )),
    WITHER_SKULL(new DamageType(
            NamespacedID.of("wither_skull"),
            "witherSkull",
            "when_caused_by_living_non_player",
            0.1F,
            Optional.empty(),
            Optional.empty()
    ));

    private final DamageType type;

    DamageTypes(DamageType type) {
        this.type = type;
    }

    public DamageType getType() {
        return type;
    }

    public static DamageTypes[] all() {
        return values();
    }

    public static DamageType[] allDefault() {
        return Arrays.stream(values())
                .map(DamageTypes::getType)
                .toArray(DamageType[]::new);
    }
}
