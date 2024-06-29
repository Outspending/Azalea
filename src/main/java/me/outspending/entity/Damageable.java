package me.outspending.entity;

public interface Damageable {

    void damage(double amount);

    default void damageHearts(double amount) {
        this.damage(amount * 2.0);
    }

    void setHealth(float health);

    float getHealth();

    float getMaxHealth();

    default void heal() {
        this.setHealth(this.getMaxHealth());
    }

}
