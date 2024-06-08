package me.outspending.registry;

public interface Registry<T> {

    void add(T value);

    void addAll(T... values);

    boolean contains(T value);

}
