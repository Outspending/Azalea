package me.outspending.entity;

import lombok.Getter;
import lombok.Setter;
import me.outspending.position.Pos;
import me.outspending.world.World;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public abstract class LivingEntity extends Entity {
    private static final Logger logger = LoggerFactory.getLogger(LivingEntity.class);

    // LivingEntityMeta - Start
    private byte handStates = 0;
    private byte isHandActive = 0;
    private byte activeHand = 0;
    private byte isInRiptideSpin = 0;

    private float health = 1.0f;
    private int potionEffectColor = 0;
    private boolean isPotionEffectAmbient = false;
    private int numberOfArrowsInEntity = 0;
    private int numberOfBeeStingersInEntity = 0;
    private Pos bedLocation;
    // LivingEntityMeta - End

    public LivingEntity(@NotNull EntityType type) {
        super(type);
    }

    public LivingEntity(@NotNull EntityType type, @NotNull UUID entityUUID) {
        super(type, entityUUID);
    }

}
