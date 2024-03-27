package me.outspending.nbt.io.exceptions;

public class MaxDepthReachedException extends RuntimeException {
    public MaxDepthReachedException(String message) {
        super(message);
    }
}
