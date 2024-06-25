package me.outspending.events.event;

import lombok.Getter;
import me.outspending.entity.Entity;
import me.outspending.events.types.EntityEvent;
import me.outspending.world.World;

@Getter
public class EntitySpawnEvent extends EntityEvent {
    private final World world;

    public EntitySpawnEvent(Entity entity, World world) {
        super(entity);
        this.world = world;
    }

}
