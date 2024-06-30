package me.outspending.entity.meta;

import lombok.Getter;
import lombok.Setter;
import me.outspending.entity.Entity;
import me.outspending.protocol.Writable;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

@Setter @Getter
public class EntityMeta implements Writable {

    protected boolean isOnFire = false;
    protected boolean isCrouching = false;
    protected boolean isSprinting = false;
    protected boolean isSwimming = false;
    protected boolean isInvisible = false;
    protected boolean hasGlowingEffect = false;
    protected boolean isFlyingWithAnElytra = false;
    protected int airTicks = 0;
    protected Component customName = Component.empty();
    protected boolean isCustomNameVisible = false;
    protected boolean isSilent = false;
    protected boolean hasNoGravity = false;
    protected Entity.Pose pose = Entity.Pose.STANDING;
    protected int ticksFrozenInPowderedSnow = 0;

    // Custom Meta (not in the protocol)
    protected int viewableDistance = 100;
    protected boolean canTick = true;

    @Override
    public void write(@NotNull PacketWriter writer) {
        // TODO: Write entity metadata
    }

}
