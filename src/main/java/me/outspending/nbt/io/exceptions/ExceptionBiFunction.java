package me.outspending.nbt.io.exceptions;

@FunctionalInterface
public interface ExceptionBiFunction<T, U, R, E extends Exception> {
    R accept(T t, U u) throws E;
}
