package me.outspending.nbt;

import me.outspending.nbt.arrays.NBTByteArray;
import me.outspending.nbt.arrays.NBTIntArray;
import me.outspending.nbt.arrays.NBTLongArray;
import me.outspending.nbt.io.MaxDepthIO;
import me.outspending.nbt.numbers.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public class NBTList<T extends NBTTag<?>> extends NBTTag<List<T>> implements Iterable<T>, Comparable<NBTList<T>>, MaxDepthIO {

    public static final byte ID = 9;

    private Class<?> typeClass = null;

    private NBTList(int initialCapacity) {
        super(createEmptyValue(initialCapacity));
    }

    @Override
    public byte getID() {
        return ID;
    }

    public static NBTList<?> createUnchecked(Class<?> typeClass) {
        return createUnchecked(typeClass, 3);
    }

    public static NBTList<?> createUnchecked(Class<?> typeClass, int initialCapacity) {
        NBTList<?> list = new NBTList<>(initialCapacity);
        list.typeClass = typeClass;
        return list;
    }

    private static <T> List<T> createEmptyValue(int initialCapacity) {
        return new ArrayList<>(initialCapacity);
    }

    public NBTList(Class<? super T> typeClass) throws IllegalArgumentException, NullPointerException {
        this(typeClass, 3);
    }

    public NBTList(Class<? super T> typeClass, int initialCapacity) throws IllegalArgumentException, NullPointerException {
        super(createEmptyValue(initialCapacity));
        if (typeClass == NBTEnd.class) {
            throw new IllegalArgumentException("cannot create ListTag with EndTag elements");
        }
        this.typeClass = Objects.requireNonNull(typeClass);
    }

    public Class<?> getTypeClass() {
        return typeClass == null ? NBTEnd.class : typeClass;
    }

    public int size() {
        return getValue().size();
    }

    public T remove(int index) {
        return getValue().remove(index);
    }

    public void clear() {
        getValue().clear();
    }

    public boolean contains(T value) {
        return getValue().contains(value);
    }

    public boolean containsAll(List<T> values) {
        return getValue().containsAll(values);
    }

    public void sort(Comparator<T> comparator) {
        getValue().sort(comparator);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return getValue().iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        getValue().forEach(action);
    }

    public T set(int index, T value) {
        return getValue().set(index, Objects.requireNonNull(value));
    }

    public void add(T value) {
        add(size(), value);
    }

    public void add(int index, T t) {
        Objects.requireNonNull(t);
        if (getTypeClass() == NBTEnd.class) {
            typeClass = t.getClass();
        } else if (typeClass != t.getClass()) {
            throw new ClassCastException(
                    String.format("cannot add %s to ListTag<%s>",
                            t.getClass().getSimpleName(),
                            typeClass.getSimpleName()));
        }
        getValue().add(index, t);
    }

    public void addAll(Collection<T> t) {
        for (T tt : t) {
            add(tt);
        }
    }

    public void addAll(int index, Collection<T> t) {
        int i = 0;
        for (T tt : t) {
            add(index + i, tt);
            i++;
        }
    }

    public void addBoolean(boolean value) {
        addUnchecked(new NBTByte(value));
    }

    public void addByte(byte value) {
        addUnchecked(new NBTByte(value));
    }

    public void addShort(short value) {
        addUnchecked(new NBTShort(value));
    }

    public void addInt(int value) {
        addUnchecked(new NBTInt(value));
    }

    public void addLong(long value) {
        addUnchecked(new NBTLong(value));
    }

    public void addFloat(float value) {
        addUnchecked(new NBTFloat(value));
    }

    public void addDouble(double value) {
        addUnchecked(new NBTDouble(value));
    }

    public void addString(String value) {
        addUnchecked(new NBTString(value));
    }

    public void addByteArray(byte[] value) {
        addUnchecked(new NBTByteArray(value));
    }

    public void addIntArray(int[] value) {
        addUnchecked(new NBTIntArray(value));
    }

    public void addLongArray(long[] value) {
        addUnchecked(new NBTLongArray(value));
    }

    public T get(int index) {
        return getValue().get(index);
    }

    public int indexOf(T value) {
        return getValue().indexOf(value);
    }

    @SuppressWarnings("unchecked")
    public <L extends NBTTag<?>> NBTList<L> asTypedList(Class<L> clazz) {
        checkTypeClass(clazz);
        return (NBTList<L>) this;
    }

    public NBTList<NBTByte> asByteTagList() {
        return asTypedList(NBTByte.class);
    }

    public NBTList<NBTShort> asShortTagList() {
        return asTypedList(NBTShort.class);
    }

    public NBTList<NBTInt> asIntTagList() {
        return asTypedList(NBTInt.class);
    }

    public NBTList<NBTLong> asLongTagList() {
        return asTypedList(NBTLong.class);
    }

    public NBTList<NBTFloat> asFloatTagList() {
        return asTypedList(NBTFloat.class);
    }

    public NBTList<NBTDouble> asDoubleTagList() {
        return asTypedList(NBTDouble.class);
    }

    public NBTList<NBTString> asStringTagList() {
        return asTypedList(NBTString.class);
    }

    public NBTList<NBTByteArray> asByteArrayTagList() {
        return asTypedList(NBTByteArray.class);
    }

    public NBTList<NBTIntArray> asIntArrayTagList() {
        return asTypedList(NBTIntArray.class);
    }

    public NBTList<NBTLongArray> asLongArrayTagList() {
        return asTypedList(NBTLongArray.class);
    }

    @SuppressWarnings("unchecked")
    public NBTList<NBTList<?>> asListTagList() {
        checkTypeClass(NBTList.class);
        typeClass = NBTList.class;
        return (NBTList<NBTList<?>>) this;
    }

    public NBTList<NBTCompound> asCompoundTagList() {
        return asTypedList(NBTCompound.class);
    }

    @Override
    public String valueToString(int maxDepth) {
        StringBuilder sb = new StringBuilder("{\"type\":\"").append(getTypeClass().getSimpleName()).append("\",\"list\":[");
        for (int i = 0; i < size(); i++) {
            sb.append(i > 0 ? "," : "").append(get(i).valueToString(decrementMaxDepth(maxDepth)));
        }
        sb.append("]}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!super.equals(other) || size() != ((NBTList<?>) other).size() || getTypeClass() != ((NBTList<?>) other)
                .getTypeClass()) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            if (!get(i).equals(((NBTList<?>) other).get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTypeClass().hashCode(), getValue().hashCode());
    }

    @Override
    public int compareTo(@NotNull NBTList<T> o) {
        return Integer.compare(size(), o.getValue().size());
    }

    @Override
    @SuppressWarnings("unchecked")
    public NBTTag<List<T>> clone() {
        NBTList<T> copy = new NBTList<>(this.size());
        // assure type safety for clone
        copy.typeClass = typeClass;
        for (T t : getValue()) {
            copy.add((T) t.clone());
        }
        return copy;
    }

    @SuppressWarnings("unchecked")
    public void addUnchecked(NBTTag<?> tag) {
        if (getTypeClass() != NBTEnd.class && typeClass != tag.getClass()) {
            throw new IllegalArgumentException(String.format(
                    "cannot add %s to ListTag<%s>",
                    tag.getClass().getSimpleName(), typeClass.getSimpleName()));
        }
        add(size(), (T) tag);
    }

    private void checkTypeClass(Class<?> clazz) {
        if (getTypeClass() != NBTEnd.class && typeClass != clazz) {
            throw new ClassCastException(String.format(
                    "cannot cast ListTag<%s> to ListTag<%s>",
                    typeClass.getSimpleName(), clazz.getSimpleName()));
        }
    }
}
