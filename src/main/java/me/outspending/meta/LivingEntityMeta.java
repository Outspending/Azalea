package me.outspending.meta;

import lombok.Getter;
import lombok.Setter;
import me.outspending.position.Pos;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@Getter @Setter
public class LivingEntityMeta extends EntityMeta {
    protected byte handState = 0;
    protected byte isHandActive = 0;
    protected byte activeHand = 0;
    protected byte isInRiptideSpinAttack = 0;

    protected float health = 1f;
    protected float potionEffectColor = 0;
    protected boolean ambientPotionEffect = false;
    protected int arrowsInEntity = 0;
    protected int beeStingersInEntity = 0;
    protected Pos bedLocation = null;
}
