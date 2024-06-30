package me.outspending.entity;

import lombok.Getter;
import lombok.Setter;
import me.outspending.entity.meta.LivingEntityMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Getter @Setter
public abstract class LivingEntity extends Entity implements Damageable {
    private static final Logger logger = LoggerFactory.getLogger(LivingEntity.class);

    private float maxHealth = 20.0f;
    private LivingEntityMeta entityMeta = new LivingEntityMeta();

    public LivingEntity(@NotNull EntityType type) {
        super(type);
    }

    public LivingEntity(@NotNull EntityType type, @NotNull UUID entityUUID) {
        super(type, entityUUID);
    }

    @Override
    public void setHealth(float health) {
        this.entityMeta.setHealth(health);
        // TODO: Send packet to viewers
    }

    @Override
    public void damage(double amount) {
        this.entityMeta.setHealth(this.entityMeta.getHealth() - (float) amount);
        // TODO: Send packet to viewers
    }

    @Override
    public float getHealth() {
        return this.entityMeta.getHealth();
    }

    @Override
    public float getMaxHealth() {
        return maxHealth;
    }

    public enum Hand {
        MAIN_HAND,
        OFF_HAND;

        public static @NotNull Hand getById(@Range(from = 0, to = 1) int id) {
            final Hand value = values()[id];
            return value != null ? value : MAIN_HAND;
        }
    }
}
