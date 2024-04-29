package me.outspending;

import lombok.Getter;
import me.outspending.block.ItemStack;

@Getter
public class Slot {
    private final boolean present;
    private final ItemStack item;

    public Slot(boolean present, ItemStack item) {
        this.present = present;
        this.item = item;
    }
}
