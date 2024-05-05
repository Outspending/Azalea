package me.outspending.protocol.exception;

import me.outspending.connection.GameState;

public class UnknownPacketException extends RuntimeException {
    public UnknownPacketException(GameState state, int id) {
        super("Unknown packet with ID " + id + " in state " + state);
    }
}
