package me.outspending.entity;

import lombok.Getter;
import lombok.Setter;
import me.outspending.meta.EntityMeta;
import me.outspending.meta.LivingEntityMeta;
import me.outspending.position.Pos;
import me.outspending.world.World;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public abstract class LivingEntity implements Entity {
    private static final Logger logger = LoggerFactory.getLogger(LivingEntity.class);

    protected final int entityID;
    protected final List<Player> viewers = new ArrayList<>();

    protected LivingEntityMeta meta;

    protected volatile World world;
    protected volatile Pos position;

    public LivingEntity() {
        this.entityID = EntityCounter.getNextEntityID();
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
    public void updateViewers() {
        world.getPlayers().forEach(player -> {
            if (this.equals(player)) return;

            double simulationDistance = player.getSimulationDistance();
            boolean isViewer = this.isViewer(player);
            double distance = this.distance(player);

            if (distance <= simulationDistance && !isViewer) {
                this.addViewer(player);
                logger.info("Adding viewer: {}", player.getName());
            } else if (distance >= simulationDistance && isViewer) {
                logger.info("Removing viewer: {}", player.getName());
                this.removeViewer(player);
            }
        });
    }

    @Override
    public int compareTo(@NotNull Entity o) {
        if (o instanceof LivingEntity && o.getEntityID() == this.getEntityID())
            return 0;
        return -1;
    }
}
