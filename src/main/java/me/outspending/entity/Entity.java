package me.outspending.entity;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import me.outspending.Tickable;
import me.outspending.connection.ClientConnection;
import me.outspending.entity.meta.EntityMeta;
import me.outspending.player.Player;
import me.outspending.position.Pos;
import me.outspending.protocol.packets.client.play.ClientSetEntityMetaPacket;
import me.outspending.protocol.packets.client.play.ClientSpawnEntityPacket;
import me.outspending.world.World;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class Entity implements Viewable, Tickable, Comparable<Entity> {
    private static final Logger logger = LoggerFactory.getLogger(Entity.class);

    private final List<Entity> viewers = new ArrayList<>();
    protected final EntityType type;
    protected final int entityID;
    protected final UUID entityUUID;
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

    public static @NotNull Builder builder(@NotNull EntityType type) {
        return new Builder(type);
    }

    public void setRotation(float yaw, float pitch) {
        setPosition(new Pos(position.x(), position.y(), position.z(), yaw, pitch));
    }

    public void setMeta(@NotNull EntityMeta meta) {
        this.entityMeta = meta;
        sendPacketsToViewers(new ClientSetEntityMetaPacket(this));
    }

    public void spawn(@NotNull Player player) {
        Preconditions.checkNotNull(this.world, "Spawning entity without a world!");

        player.sendPacket(new ClientSpawnEntityPacket(this));
//        player.sendBundledPackets(
//                new ClientSpawnEntityPacket(this),
//                new ClientSetEntityMetaPacket(this)
//        );
    }

    public void spawn(@NotNull Player... players) {
        this.spawn(List.of(players));
    }

    public void spawn(@NotNull Collection<Player> players) {
        players.forEach(this::spawn);
    }

    public void spawnGlobal() {
        world.addEntity(this);
        this.spawn(world.getPlayers());
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

        public static @NotNull Hand getById(@Range(from = 0, to = 1) int id) {
            final Hand value = values()[id];
            return value != null ? value : MAIN_HAND;
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

        public static @NotNull Pose getById(@Range(from = 0, to = 14) int id) {
            final Pose value = values()[id];
            return value != null ? value : STANDING;
        }

    }

    public static class Builder {
        private final EntityType type;
        private UUID entityUUID = UUID.randomUUID();
        private Pos position = Pos.ZERO;
        private World world;

        public Builder(EntityType type) {
            this.type = type;
        }

        @Contract("_ -> this")
        public @NotNull Builder setEntityUUID(@NotNull UUID entityUUID) {
            this.entityUUID = entityUUID;
            return this;
        }

        @Contract("_ -> this")
        public @NotNull Builder setPosition(@NotNull Pos position) {
            this.position = position;
            return this;
        }

        @Contract("_ -> this")
        public @NotNull Builder setWorld(@NotNull World world) {
            this.world = world;
            return this;
        }

        public @NotNull Entity build() {
            Preconditions.checkNotNull(world, "World cannot be null");

            final Entity entity = new Entity(type, entityUUID);
            entity.setPosition(this.position);
            entity.setWorld(this.world);

            return entity;
        }

    }

}
