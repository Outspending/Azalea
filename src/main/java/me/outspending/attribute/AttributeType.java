package me.outspending.attribute;

public enum AttributeType {
    ARMOR(0, 0.0, 0.0, 30.0),
    ARMOR_TOUGHNESS(1, 0.0, 0.0, 20.0),
    ATTACK_DAMAGE(2, 2.0, 0.0, 2048.0),
    ATTACK_KNOCKBACK(3, 0.0, 0.0, 5.0),
    ATTACK_SPEED(4, 4.0, 0.0, 1024.0),
    BLOCK_BREAK_SPEED(5, 1.0, 0.0, 1024.0),
    BLOCK_INTERACTION_RANGE(6, 4.5, 0.0, 64.0),
    ENTITY_INTERACTION_RANGE(7, 3.0, 0.0, 64.0),
    FALL_DAMAGE_MULTIPLIER(8, 1.0, 0.0, 100.0),
    FLYING_SPEED(9, 0.4, 0.0, 1024.0),
    FOLLOW_RANGE(10, 32.0, 0.0, 2048.0),
    GRAVITY(11, 0.08, -1.0, 1.0),
    JUMP_STRENGTH(12, 0.42, 0.0, 32.0),
    KNOCKBACK_RESISTANCE(13, 0.0, 0.0, 1.0),
    LUCK(14, 0.0, -1024.0, 1024.0),
    MAX_ABSORPTION(15, 0.0, 0.0, 2048.0),
    MAX_HEALTH(16, 20.0, 0.0, 1024.0),
    MOVEMENT_SPEED(17, 0.7, 0.0, 1024.0),
    SAFE_FALL_DISTANCE(18, 3.0, -1024.0, 1024.0),
    SCALE(19, 1.0, 0.0625, 16.0),
    SPAWN_REINFORCEMENTS(20, 0.0, 0.0, 1.0),
    STEP_HEIGHT(21, 0.6, 0.0, 1.0);

    private final int id;
    private final double defaultValue;
    private final double minValue;
    private final double maxValue;

    AttributeType(int id, double defaultValue, double minValue, double maxValue) {
        this.id = id;
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public boolean isInRange(double value) {
        return value >= minValue && value <= maxValue;
    }

    public int getId() {
        return id;
    }

    public double getDefaultValue() {
        return defaultValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }
}
