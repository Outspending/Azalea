package me.outspending.events.event;

import lombok.Getter;
import me.outspending.entity.Entity;
import me.outspending.events.types.EntityEvent;
import me.outspending.position.Pos;
import me.outspending.world.World;

@Getter
public class EntityWorldAddEvent extends EntityEvent {
    private final World world;
    private final Pos pos;

    public EntityWorldAddEvent(Entity entity, World world, Pos pos) {
        super(entity);
        this.world = world;
        this.pos = pos;
    }

}
