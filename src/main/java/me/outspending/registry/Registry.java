package me.outspending.registry;

import me.outspending.NamespacedID;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public interface Registry<T extends RegistryType> {

    @NotNull NamespacedID registryID();

    void add(T value);

    void addAll(T... values);

    void addAll(List<T> values);

    boolean contains(T value);

    default List<T> lookup(Predicate<T> predicate) {
        return all().stream()
                .filter(predicate)
                .toList();
    }

    List<T> all();

}
