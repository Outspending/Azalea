package me.outspending.block;

import lombok.Getter;
import net.kyori.adventure.nbt.CompoundBinaryTag;

@Getter
public class ItemStack {
    private final int id;
    private final int count;
    private final CompoundBinaryTag itemNBT;

    public ItemStack(int id, int count, CompoundBinaryTag itemNBT) {
        this.id = id;
        this.count = count;
        this.itemNBT = itemNBT;
    }
}
