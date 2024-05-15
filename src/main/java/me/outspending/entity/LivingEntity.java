package me.outspending.entity;

import lombok.Getter;
import lombok.Setter;
import me.outspending.entity.metadata.LivingEntityMeta;
import me.outspending.position.Pos;
import me.outspending.world.World;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public abstract class LivingEntity implements Entity {
    private static final Logger logger = LoggerFactory.getLogger(LivingEntity.class);

    protected int viewableDistance = 50;

    protected final int entityID;
    protected final List<Player> viewers = new ArrayList<>();

    protected LivingEntityMeta metaData;

    protected volatile World world;
    protected volatile Pos position;

    public LivingEntity() {
        this.entityID = EntityCounter.getNextEntityID();
    }

    public void setRotation(float yaw, float pitch) {
        setPosition(new Pos(position.x(), position.y(), position.z(), yaw, pitch));
    }

    @Override
    public void addViewer(@NotNull Player player) {
        if (!viewers.contains(player))
            viewers.add(player);
    }

    @Override
    public void removeViewer(@NotNull Player player) {
        viewers.remove(player);
    }

    @Override
    public int compareTo(@NotNull Entity o) {
        if (o instanceof LivingEntity && o.getEntityID() == this.getEntityID())
            return 0;
        return -1;
    }
}
