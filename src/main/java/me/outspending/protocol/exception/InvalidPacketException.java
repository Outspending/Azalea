package me.outspending.protocol.exception;

public class InvalidPacketException extends RuntimeException {

    public InvalidPacketException(String message) {
        super(message);
    }

    public InvalidPacketException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPacketException(Throwable cause) {
        super(cause);
    }

    public InvalidPacketException() {
        super();
    }

}
