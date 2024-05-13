package me.outspending.item;

import lombok.Getter;

@Getter
public class ItemStack implements Cloneable {
    private final Item item;
    private final int amount;

    public ItemStack(Item item) {
        this(item, 1);
    }

    public ItemStack(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }
}
