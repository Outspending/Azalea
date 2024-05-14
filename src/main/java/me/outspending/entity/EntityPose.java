package me.outspending.entity;

public enum EntityPose {
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

    public int getPose() {
        return ordinal();
    }

    public static EntityPose getById(int id) {
        return values()[id];
    }

}
