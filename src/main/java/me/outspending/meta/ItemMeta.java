package me.outspending.meta;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public interface ItemMeta extends Cloneable {

    boolean hasDisplayName();

    @NotNull String getDisplayName();

    void setDisplayName(@NotNull String name);

    void setDisplayName(@NotNull Component name);

    String getItemName();

    boolean hasLore();

    @NotNull List<String> getLore();

    void setLore(@UnknownNullability List<String> lore);

}
