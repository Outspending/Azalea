package me.outspending.events.types;

import lombok.Getter;
import me.outspending.entity.Entity;

@Getter
public class EntityEvent implements Event {
    private final Entity entity;

    public EntityEvent(Entity entity) {
        this.entity = entity;
    }
}
