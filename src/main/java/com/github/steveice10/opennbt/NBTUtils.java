package com.github.steveice10.opennbt;

import com.github.steveice10.opennbt.tag.builtin.*;

import java.util.HashMap;
import java.util.Map;

public class NBTUtils {
    private static final Map<Class<? extends Tag>, Integer> TAG_IDS = new HashMap<>();

    static {
        TAG_IDS.put(null, 0);
        TAG_IDS.put(ByteTag.class, 1);
        TAG_IDS.put(ShortTag.class, 2);
        TAG_IDS.put(IntTag.class, 3);
        TAG_IDS.put(LongTag.class, 4);
        TAG_IDS.put(FloatTag.class, 5);
        TAG_IDS.put(DoubleTag.class, 6);
        TAG_IDS.put(ByteArrayTag.class, 7);
        TAG_IDS.put(StringTag.class, 8);
        TAG_IDS.put(ListTag.class, 9);
        TAG_IDS.put(CompoundTag.class, 10);
        TAG_IDS.put(IntArrayTag.class, 11);
        TAG_IDS.put(LongArrayTag.class, 12);
    }

    public static int getTypeCode(Class<? extends Tag> tagClass) {
        return TAG_IDS.getOrDefault(tagClass, -1);
    }
}
