package me.outspending.entity.metadata;

import lombok.Getter;
import lombok.Setter;
import me.outspending.position.Pos;

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
