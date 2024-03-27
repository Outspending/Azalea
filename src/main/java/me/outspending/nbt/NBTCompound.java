package me.outspending.nbt;

import me.outspending.nbt.arrays.NBTByteArray;
import me.outspending.nbt.arrays.NBTIntArray;
import me.outspending.nbt.arrays.NBTLongArray;
import me.outspending.nbt.io.MaxDepthIO;
import me.outspending.nbt.numbers.*;

import java.util.*;
import java.util.function.BiConsumer;

public class NBTCompound extends NBTTag<Map<String, NBTTag<?>>> implements Iterable<Map.Entry<String, NBTTag<?>>>, Comparable<NBTCompound>, MaxDepthIO {

    public static final byte ID = 10;

    public NBTCompound() {
        super(createEmptyValue());
    }

    public NBTCompound(int initialCapacity) {
        super(new HashMap<>(initialCapacity));
    }

    @Override
    public byte getID() {
        return ID;
    }

    private static Map<String, NBTTag<?>> createEmptyValue() {
        return new HashMap<>(8);
    }

    public int size() {
        return getValue().size();
    }

    public NBTTag<?> remove(String key) {
        return getValue().remove(key);
    }

    public void clear() {
        getValue().clear();
    }

    public boolean containsKey(String key) {
        return getValue().containsKey(key);
    }

    public boolean containsValue(NBTTag<?> value) {
        return getValue().containsValue(value);
    }

    public Collection<NBTTag<?>> values() {
        return getValue().values();
    }

    public Set<String> keySet() {
        return getValue().keySet();
    }

    public Set<Map.Entry<String, NBTTag<?>>> entrySet() {
        return new NonNullEntrySet<>(getValue().entrySet());
    }

    @Override
    public Iterator<Map.Entry<String, NBTTag<?>>> iterator() {
        return entrySet().iterator();
    }

    public void forEach(BiConsumer<String, NBTTag<?>> action) {
        getValue().forEach(action);
    }

    public <C extends NBTTag<?>> C get(String key, Class<C> type) {
        NBTTag<?> t = getValue().get(key);
        if (t != null) {
            return type.cast(t);
        }
        return null;
    }

    public NBTTag<?> get(String key) {
        return getValue().get(key);
    }

    public NumberTag<?> getNumberTag(String key) {
        return (NumberTag<?>) getValue().get(key);
    }

    public Number getNumber(String key) {
        return getNumberTag(key).getValue();
    }

    public NBTByte getByteTag(String key) {
        return get(key, NBTByte.class);
    }

    public NBTShort getShortTag(String key) {
        return get(key, NBTShort.class);
    }

    public NBTInt getIntTag(String key) {
        return get(key, NBTInt.class);
    }

    public NBTLong getLongTag(String key) {
        return get(key, NBTLong.class);
    }

    public NBTFloat getFloatTag(String key) {
        return get(key, NBTFloat.class);
    }

    public NBTDouble getDoubleTag(String key) {
        return get(key, NBTDouble.class);
    }

    public NBTString getStringTag(String key) {
        return get(key, NBTString.class);
    }

    public NBTByteArray getByteArrayTag(String key) {
        return get(key, NBTByteArray.class);
    }

    public NBTIntArray getIntArrayTag(String key) {
        return get(key, NBTIntArray.class);
    }

    public NBTLongArray getLongArrayTag(String key) {
        return get(key, NBTLongArray.class);
    }

    public NBTList<?> getListTag(String key) {
        return get(key, NBTList.class);
    }

    public NBTCompound getCompoundTag(String key) {
        return get(key, NBTCompound.class);
    }

    public boolean getBoolean(String key) {
        NBTTag<?> t = get(key);
        return t instanceof NBTByte && ((NBTByte) t).asByte() > 0;
    }

    public byte getByte(String key) {
        NBTByte t = getByteTag(key);
        return t == null ? NBTByte.ZERO_VALUE : t.asByte();
    }

    public short getShort(String key) {
        NBTShort t = getShortTag(key);
        return t == null ? NBTShort.ZERO_VALUE : t.asShort();
    }

    public int getInt(String key) {
        NBTInt t = getIntTag(key);
        return t == null ? NBTInt.ZERO_VALUE : t.asInt();
    }

    public long getLong(String key) {
        NBTLong t = getLongTag(key);
        return t == null ? NBTLong.ZERO_VALUE : t.asLong();
    }

    public float getFloat(String key) {
        NBTFloat t = getFloatTag(key);
        return t == null ? NBTFloat.ZERO_VALUE : t.asFloat();
    }

    public double getDouble(String key) {
        NBTDouble t = getDoubleTag(key);
        return t == null ? NBTDouble.ZERO_VALUE : t.asDouble();
    }

    public String getString(String key) {
        NBTString t = getStringTag(key);
        return t == null ? NBTString.ZERO_VALUE : t.getValue();
    }

    public byte[] getByteArray(String key) {
        NBTByteArray t = getByteArrayTag(key);
        return t == null ? NBTByteArray.ZERO_VALUE : t.getValue();
    }

    public int[] getIntArray(String key) {
        NBTIntArray t = getIntArrayTag(key);
        return t == null ? NBTIntArray.ZERO_VALUE : t.getValue();
    }

    public long[] getLongArray(String key) {
        NBTLongArray t = getLongArrayTag(key);
        return t == null ? NBTLongArray.ZERO_VALUE : t.getValue();
    }

    public NBTTag<?> put(String key, NBTTag<?> tag) {
        return getValue().put(Objects.requireNonNull(key), Objects.requireNonNull(tag));
    }

    public NBTTag<?> putIfNotNull(String key, NBTTag<?> tag) {
        if (tag == null) {
            return this;
        }
        return put(key, tag);
    }

    public NBTTag<?> putBoolean(String key, boolean value) {
        return put(key, new NBTByte(value));
    }

    public NBTTag<?> putByte(String key, byte value) {
        return put(key, new NBTByte(value));
    }

    public NBTTag<?> putShort(String key, short value) {
        return put(key, new NBTShort(value));
    }

    public NBTTag<?> putInt(String key, int value) {
        return put(key, new NBTInt(value));
    }

    public NBTTag<?> putLong(String key, long value) {
        return put(key, new NBTLong(value));
    }

    public NBTTag<?> putFloat(String key, float value) {
        return put(key, new NBTFloat(value));
    }

    public NBTTag<?> putDouble(String key, double value) {
        return put(key, new NBTDouble(value));
    }

    public NBTTag<?> putString(String key, String value) {
        return put(key, new NBTString(value));
    }

    public NBTTag<?> putByteArray(String key, byte[] value) {
        return put(key, new NBTByteArray(value));
    }

    public NBTTag<?> putIntArray(String key, int[] value) {
        return put(key, new NBTIntArray(value));
    }

    public NBTTag<?> putLongArray(String key, long[] value) {
        return put(key, new NBTLongArray(value));
    }

    @Override
    public String valueToString(int maxDepth) {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, NBTTag<?>> e : getValue().entrySet()) {
            sb.append(first ? "" : ",")
                    .append(escapeString(e.getKey(), false)).append(":")
                    .append(e.getValue().toString(decrementMaxDepth(maxDepth)));
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!super.equals(other) || size() != ((NBTCompound) other).size()) {
            return false;
        }
        for (Map.Entry<String, NBTTag<?>> e : getValue().entrySet()) {
            NBTTag<?> v;
            if ((v = ((NBTCompound) other).get(e.getKey())) == null || !e.getValue().equals(v)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int compareTo(NBTCompound o) {
        return Integer.compare(size(), o.getValue().size());
    }

    @Override
    public NBTCompound clone() {
        // Choose initial capacity based on default load factor (0.75) so all entries fit in map without resizing
        NBTCompound copy = new NBTCompound((int) Math.ceil(getValue().size() / 0.75f));
        for (Map.Entry<String, NBTTag<?>> e : getValue().entrySet()) {
            copy.put(e.getKey(), e.getValue().clone());
        }
        return copy;
    }
}
