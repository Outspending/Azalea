package me.outspending.registry;

import me.outspending.NamespacedID;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class Registry {
    @ApiStatus.Internal
    public static BlockEntry block(String namespace, @NotNull Properties main) {
        return new BlockEntry(namespace, main, null);
    }

    public static final class BlockEntry implements Entry {
        private final Properties custom;

        private final NamespacedID namespace;
        private final int id;
        private final int stateId;
        private final String translationKey;
        private final double hardness;
        private final double explosionResistance;
        private final double friction;
        private final double speedFactor;
        private final double jumpFactor;
        private final boolean air;
        private final boolean solid;
        private final boolean liquid;
        private final boolean occludes;
        private final int lightEmission;
        private final boolean replaceable;
        private final String blockEntity;
        private final int blockEntityId;
        private final boolean redstoneConductor;
        private final boolean signalSource;

        private BlockEntry(String namespace, Properties main, Properties custom) {
            this.custom = custom;

            this.namespace = NamespacedID.of(namespace);
            this.id = main.getInt("id");
            this.stateId = main.getInt("stateId");
            this.translationKey = main.getString("translationKey");
            this.hardness = main.getDouble("hardness");
            this.explosionResistance = main.getDouble("explosionResistance");
            this.friction = main.getDouble("friction");
            this.speedFactor = main.getDouble("speedFactor", 1);
            this.jumpFactor = main.getDouble("jumpFactor", 1);
            this.air = main.getBoolean("air", false);
            this.solid = main.getBoolean("solid");
            this.liquid = main.getBoolean("liquid", false);
            this.occludes = main.getBoolean("occludes", true);
            this.lightEmission = main.getInt("lightEmission", 0);
            this.replaceable = main.getBoolean("replaceable", false);

            Properties blockEntity = main.section("blockEntity");
            if (blockEntity != null) {
                this.blockEntity = blockEntity.getString("namespace");
                this.blockEntityId = blockEntity.getInt("id");
            } else {
                this.blockEntity = null;
                this.blockEntityId = 0;
            }

            this.redstoneConductor = main.getBoolean("redstoneConductor");
            this.signalSource = main.getBoolean("signalSource", false);
        }

        public Properties getCustom() {
            return custom;
        }

        public NamespacedID getNamespace() {
            return namespace;
        }

        public int getId() {
            return id;
        }

        public int getStateId() {
            return stateId;
        }

        public String getTranslationKey() {
            return translationKey;
        }

        public double getHardness() {
            return hardness;
        }

        public double getExplosionResistance() {
            return explosionResistance;
        }

        public double getFriction() {
            return friction;
        }

        public double getSpeedFactor() {
            return speedFactor;
        }

        public double getJumpFactor() {
            return jumpFactor;
        }

        public boolean isAir() {
            return air;
        }

        public boolean isSolid() {
            return solid;
        }

        public boolean isLiquid() {
            return liquid;
        }

        public boolean isOccludes() {
            return occludes;
        }

        public int getLightEmission() {
            return lightEmission;
        }

        public boolean isReplaceable() {
            return replaceable;
        }

        public String getBlockEntity() {
            return blockEntity;
        }

        public int getBlockEntityId() {
            return blockEntityId;
        }

        public boolean isRedstoneConductor() {
            return redstoneConductor;
        }

        public boolean isSignalSource() {
            return signalSource;
        }

        @Override
        public Properties custom() {
            return custom;
        }
    }

    private static interface Entry {
        Properties custom();
    }

    record PropertiesMap(Map<String, Object> map) implements Properties {

        @Override
        public String getString(String name, String defaultValue) {
            Object element = element(name);
            return element != null ? (String) element : defaultValue;
        }

        @Override
        public String getString(String name) {
            return element(name);
        }

        @Override
        public double getDouble(String name, double defaultValue) {
            Object element = element(name);
            return element != null ? ((Number) element).doubleValue() : defaultValue;
        }

        @Override
        public double getDouble(String name) {
            return ((Number) element(name)).doubleValue();
        }

        @Override
        public int getInt(String name, int defaultValue) {
            Object element = element(name);
            return element != null ? ((Number) element).intValue() : defaultValue;
        }

        @Override
        public int getInt(String name) {
            return ((Number) element(name)).intValue();
        }

        @Override
        public boolean getBoolean(String name, boolean defaultValue) {
            Object element = element(name);
            return element != null ? (boolean) element : defaultValue;
        }

        @Override
        public boolean getBoolean(String name) {
            return element(name);
        }

        @Override
        public Properties section(String name) {
            Map<String, Object> map = element(name);
            if (map == null) return null;
            return new PropertiesMap(map);
        }

        @Override
        public boolean containsKey(String name) {
            return map.containsKey(name);
        }

        @Override
        public Map<String, Object> asMap() {
            return map;
        }

        @SuppressWarnings("unchecked")
        private <T> T element(String name) {
            return (T) map.get(name);
        }

    }

    public interface Properties extends Iterable<Map.Entry<String, Object>> {
        static Properties fromMap(Map<String, Object> map) {
            return new PropertiesMap(map);
        }

        String getString(String name, String defaultValue);

        String getString(String name);

        double getDouble(String name, double defaultValue);

        double getDouble(String name);

        int getInt(String name, int defaultValue);

        int getInt(String name);

        boolean getBoolean(String name, boolean defaultValue);

        boolean getBoolean(String name);

        Properties section(String name);

        boolean containsKey(String name);

        Map<String, Object> asMap();

        @Override
        default @NotNull Iterator<Map.Entry<String, Object>> iterator() {
            return asMap().entrySet().iterator();
        }

        default int size() {
            return asMap().size();
        }
    }
}
