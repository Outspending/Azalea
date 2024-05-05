package me.outspending.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
public class GameProfile {
    public static final GameProfile EMPTY = new GameProfile("empty", UUID.randomUUID(), new Property[0]);

    private String username;
    private UUID uuid;
    private Property[] properties;
}