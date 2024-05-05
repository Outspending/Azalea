package me.outspending;

import lombok.Getter;
import me.outspending.block.ItemStack;

public record Slot(boolean present, ItemStack item) {
}
