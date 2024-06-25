package me.outspending.events.event;

import me.outspending.entity.Entity;
import me.outspending.events.types.EntityEvent;

public class EntitySpawnEvent extends EntityEvent {
    public EntitySpawnEvent(Entity entity) {
        super(entity);
    }
}
