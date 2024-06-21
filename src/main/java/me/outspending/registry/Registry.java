package me.outspending.registry;

import java.util.List;
import java.util.function.Predicate;

public interface Registry<T> {

    void add(T value);

    void addAll(T... values);

    void addAll(List<T> values);

    boolean contains(T value);

    List<T> lookup(Predicate<T> predicate);

}
