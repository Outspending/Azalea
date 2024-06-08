package me.outspending.registry.dimension;

import lombok.Getter;
import me.outspending.NamespacedID;

@Getter
public enum InfiniburnType {
    INFINIBURN_OVERWORLD(NamespacedID.of("infiniburn_overworld")),
    INFINIBURN_END(NamespacedID.of("infiniburn_end")),
    INFINIBURN_NETHER(NamespacedID.of("infiniburn_nether"));

    private final NamespacedID name;

    InfiniburnType(NamespacedID name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "#" + this.name.toString();
    }

}
