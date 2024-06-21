package me.outspending.registry;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class DefaultedRegistry<T> implements Registry<T> {
    private final List<T> values = Lists.newArrayList();

    @Override
    public void add(T value) {
        values.add(value);
    }

    @Override
    public void addAll(T... values) {
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
    public List<T> lookup(Predicate<T> predicate) {
        return values.stream()
                .filter(predicate)
                .toList();
    }

}
