package me.outspending.protocol.exception;

import me.outspending.connection.ConnectionState;

public class UnknownPacketException extends RuntimeException {
    public UnknownPacketException(ConnectionState state, int id) {
        super("Unknown packet with ID " + id + " in state " + state);
    }
}
