package me.outspending.registry;

import com.google.common.collect.Lists;
import me.outspending.NamespacedID;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class DefaultedRegistry<T extends RegistryType> implements Registry<T> {
    private final List<T> values = Lists.newArrayList();
    private final NamespacedID registryID;

    public DefaultedRegistry(NamespacedID registryID) {
        this.registryID = registryID;
    }

    @Override
    public @NotNull NamespacedID registryID() {
        return this.registryID;
    }

    @Override
    public void add(T value) {
        values.add(value);
    }

    @Override
    @SafeVarargs
    public final void addAll(T... values) {
        this.values.addAll(Lists.newArrayList(values));
    }

    @Override
    public void addAll(List<T> values) {
        this.values.addAll(values);
    }

    @Override
    public boolean contains(T value) {
        return values.contains(value);
    }

    @Override
    public List<T> all() {
        return this.values;
    }

}
