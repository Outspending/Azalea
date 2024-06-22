package me.outspending.registry;

import me.outspending.NamespacedID;
import me.outspending.connection.ClientConnection;
import me.outspending.protocol.packets.client.configuration.ClientRegistryDataPacket;
import me.outspending.registry.banner.BannerPattern;
import me.outspending.registry.banner.BannerPatterns;
import me.outspending.registry.biome.Biome;
import me.outspending.registry.biome.Biomes;
import me.outspending.registry.chat.ChatType;
import me.outspending.registry.chat.ChatTypes;
import me.outspending.registry.damage.DamageType;
import me.outspending.registry.damage.DamageTypes;
import me.outspending.registry.dimension.Dimension;
import me.outspending.registry.dimension.DimensionType;
import me.outspending.registry.trim.ArmorTrimMaterial;
import me.outspending.registry.trim.ArmorTrimMaterials;
import me.outspending.registry.trim.ArmorTrimPattern;
import me.outspending.registry.trim.ArmorTrimPatterns;
import me.outspending.registry.wolf.WolfVariant;
import me.outspending.registry.wolf.WolfVariants;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DefaultRegistries {
    public static final Registry<BannerPattern> BANNER_PATTERN;
    public static final Registry<Biome> BIOME;
    public static final Registry<ChatType> CHAT_TYPES;
    public static final Registry<DamageType> DAMAGE;
    public static final Registry<Dimension> DIMENSION;
    public static final Registry<ArmorTrimMaterial> TRIM_MATERIAL;
    public static final Registry<ArmorTrimPattern> TRIM_PATTERN;
    public static final Registry<WolfVariant> WOLF_VARIANT;

    public static final List<Registry<? extends RegistryType>> ALL_REGISTRIES;

    private static <T extends RegistryType> Registry<T> register(Registry<T> registry, T... values) {
        registry.addAll(values);
        return registry;
    }

    private static <T extends RegistryType> Registry<T> registerNew(@NotNull NamespacedID registryID, @NotNull T... values) {
        DefaultedRegistry<T> registry = new DefaultedRegistry<>(registryID);
        return register(registry, values);
    }

    private static <T extends RegistryType> Registry<T> registerNew(@NotNull String registryID, @NotNull T... values) {
        return registerNew(NamespacedID.of(registryID), values);
    }

    @ApiStatus.Internal
    public static void sendRegistry(@NotNull ClientConnection connection, @NotNull Registry<? extends RegistryType> registry) {
        connection.sendPacket(new ClientRegistryDataPacket(registry.registryID(), registry.all()));
    }

    @ApiStatus.Internal
    public static void sendRegistries(@NotNull ClientConnection connection) {
        for (Registry<? extends RegistryType> registry : ALL_REGISTRIES) {
            sendRegistry(connection, registry);
        }
    }

    static {
        BANNER_PATTERN = registerNew("banner_pattern", BannerPatterns.allDefault());
        BIOME = registerNew("worldgen/biome", Biomes.allDefault());
        CHAT_TYPES = registerNew("chat_type", ChatTypes.allDefault());
        DAMAGE = registerNew("damage_type", DamageTypes.allDefault());
        DIMENSION = registerNew("dimension_type", DimensionType.values());
        TRIM_MATERIAL = registerNew("trim_material", ArmorTrimMaterials.allDefault());
        TRIM_PATTERN = registerNew("trim_pattern", ArmorTrimPatterns.allDefault());
        WOLF_VARIANT = registerNew("wolf_variant", WolfVariants.allDefault());

        ALL_REGISTRIES = List.of(
                BANNER_PATTERN,
                BIOME,
                CHAT_TYPES,
                DAMAGE,
                DIMENSION,
                TRIM_MATERIAL,
                TRIM_PATTERN,
                WOLF_VARIANT
        );
    }

}
