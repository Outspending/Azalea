package me.outspending.entity.meta;

import lombok.Getter;
import lombok.Setter;
import me.outspending.position.Pos;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@Getter @Setter
public class LivingEntityMeta extends EntityMeta {
    protected byte handStates = 0;
    protected byte isHandActive = 0;
    protected byte activeHand = 0;
    protected byte isInRiptideSpin = 0;

    protected float health = 1.0f;
    protected int potionEffectColor = 0;
    protected boolean isPotionEffectAmbient = false;
    protected int numberOfArrowsInEntity = 0;
    protected int numberOfBeeStingersInEntity = 0;
    protected Pos bedLocation;

    @Override
    public void write(@NotNull PacketWriter writer) {
        super.write(writer);
    }

}
