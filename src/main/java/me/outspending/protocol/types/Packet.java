package me.outspending.protocol.types;

import lombok.Getter;

@Getter
public abstract class Packet {
    private final int id;

    public Packet(int id) {
        this.id = id;
    }
}
