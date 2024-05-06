package me.outspending.entity;

import me.outspending.chunk.Chunk;
import me.outspending.position.Pos;
import me.outspending.world.World;

import java.util.List;

public interface Entity extends Viewable {

    Pos getPosition();

    int getEntityID();

    void setWorld(World world);

    default double distance(Entity entity) {
        return distance(entity.getPosition());
    }

    default double distance(Pos position) {
        return getPosition().distance(position);
    }

}
