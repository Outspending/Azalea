package me.outspending.entity.metadata;

import lombok.Getter;
import lombok.Setter;
import me.outspending.entity.EntityPose;
import me.outspending.protocol.Writable;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

@Getter
public class EntityMeta extends Metadata {
    protected byte isOnFire = 0;
    protected byte isCrouching = 0;
    protected byte isSprinting = 0;
    protected byte isSwimming = 0;
    protected byte isInvisible = 0;
    protected byte hasGlowEffect = 0;
    protected byte isFlyingWithElytra = 0;

    protected int airTicks = 300;
    protected Component customName = null;
    protected boolean isCustomNameVisible = false;
    protected boolean isSilent = false;
    protected boolean hasNoGravity = false;
    protected EntityPose pose = EntityPose.STANDING;
    protected int ticksFrozen = 0;
}
