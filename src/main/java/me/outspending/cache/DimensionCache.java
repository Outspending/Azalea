package me.outspending.cache;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import me.outspending.NamespacedID;
import me.outspending.registry.dimension.Dimension;
import me.outspending.protocol.Writable;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Collection;
import java.util.Map;

public final class DimensionCache implements Cache<Dimension>, Writable {
    private final Map<NamespacedID, Dimension> dimensions = Maps.newHashMap();

    @Override
    public void add(@UnknownNullability Dimension key) {
        Preconditions.checkNotNull(key, "The dimension cannot be null!");
        dimensions.put(key.getBiomeKey(), key);
    }

    @Override
    public void remove(@UnknownNullability Dimension key) {
        Preconditions.checkNotNull(key, "The dimension cannot be null!");
        dimensions.remove(key.getBiomeKey());
    }

    @Override
    public @Nullable Dimension get(@UnknownNullability String key) {
        final NamespacedID id = NamespacedID.of(key);
        return dimensions.get(id);
    }

    @Override
    public @NotNull Collection<Dimension> getAll() {
        return dimensions.values();
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeNamespacedKey(NamespacedID.of("dimension_type"));
        writer.writeVarInt(dimensions.size());
        for (Dimension dimension : dimensions.values()) {
            dimension.write(writer);
        }
    }

}
