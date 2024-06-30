package me.outspending.messages.serializer;

import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.text.event.DataComponentValue;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * An {@link DataComponentValue} implementation that holds a {@linkplain BinaryTag binary tag}.
 *
 * <p>This holder is exposed to allow conversions to/from NBT data holders.</p>
 *
 * @since 4.18.0
 */
@ApiStatus.NonExtendable
public interface NBTDataComponentValue extends DataComponentValue {
    /**
     * The contained element, intended for read-only use.
     *
     * @return a copy of the contained element
     * @since 4.18.0
     */
    @NotNull BinaryTag binaryTag();

    /**
     * Create a box for item data that can be understood by the NBT serializer.
     *
     * @param binaryTag the item data to hold
     * @return a newly created item data holder instance
     * @since 4.18.0
     */
    static @NotNull NBTDataComponentValue nbtDataComponentValue(@NotNull BinaryTag binaryTag) {
        return new NBTDataComponentValueImpl(binaryTag);
    }
}