package me.outspending.entity;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import me.outspending.Tickable;
import me.outspending.connection.ClientConnection;
import me.outspending.entity.meta.EntityMeta;
import me.outspending.player.Player;
import me.outspending.position.Pos;
import me.outspending.world.World;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class Entity implements Viewable, Tickable, Comparable<Entity> {
    private static final Logger logger = LoggerFactory.getLogger(Entity.class);

    private final List<Entity> viewers = new ArrayList<>();
    protected final EntityType type;
    protected final int entityID;
    protected final UUID entityUUID;
    // private final EntityMeta entityMeta;

    protected EntityMeta entityMeta = new EntityMeta();

    protected boolean onGround = true;
    protected Pos position = Pos.ZERO;
    protected World world;

    public Entity(@NotNull EntityType type) {
        this(type, UUID.randomUUID());
    }

    public Entity(@NotNull EntityType type, @NotNull UUID entityUUID) {
        this.type = type;
        this.entityID = EntityCounter.getNextEntityID();
        this.entityUUID = entityUUID;
    }

    public void setRotation(float yaw, float pitch) {
        setPosition(new Pos(position.x(), position.y(), position.z(), yaw, pitch));
    }

    @Contract("null -> fail")
    public double distanceFrom(@UnknownNullability Entity entity) {
        return distanceFrom(entity.getPosition());
    }

    @Contract("null -> fail")
    public double distanceFrom(@UnknownNullability Pos position) {
        Preconditions.checkNotNull(position, "Position cannot be null");

        return getPosition().distance(position);
    }

    @Override
    public int compareTo(@NotNull Entity o) {
        int idCompare = Integer.compare(this.entityID, o.entityID);
        if (idCompare != 0) {
            return idCompare;
        }

        return this.entityUUID.compareTo(o.entityUUID);
    }

    @Override
    public void addViewer(@NotNull Entity entity) {
        viewers.add(entity);
    }

    @Override
    public void removeViewer(@NotNull Entity entity) {
        viewers.remove(entity);
    }

    @Override
    public @NotNull List<Entity> getViewers() {
        return viewers;
    }

    @Override
    public void updateViewers() {
        if (world == null) return;

        world.getAllEntities().forEach(entity -> {
            if (this.equals(entity)) return;

            boolean isViewer = this.isViewer(entity);
            double distance = this.distanceFrom(entity);

            int viewableDistance = this.entityMeta.getViewableDistance();
            if (distance <= viewableDistance && !isViewer) {
                this.addViewer(entity);
            } else if (distance >= viewableDistance && isViewer) {
                this.removeViewer(entity);
            }
        });
    }

    @Override
    public void tick(long time) {
        updateViewers();
    }

    public enum Hand {
        MAIN_HAND,
        OFF_HAND;

        public static @Nullable Hand getById(@Range(from = 0, to = 1) int id) {
            return values()[id];
        }
    }

    public enum Pose {
        STANDING,
        FALL_FLYING,
        SLEEPING,
        SWIMMING,
        SPIN_ATTACK,
        SNEAKING,
        LONG_JUMPING,
        DYING,
        CROAKING,
        USING_TONGUE,
        SITTING,
        ROARING,
        SNIFFING,
        EMERGING,
        DIGGING;

        public static @Nullable Pose getById(@Range(from = 0, to = 14) int id) {
            return values()[id];
        }

    }

}
