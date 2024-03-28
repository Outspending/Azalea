package com.github.steveice10.opennbt;

import com.github.steveice10.opennbt.tag.builtin.*;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class NBTInputStream extends DataInputStream {
    public NBTInputStream(@NotNull InputStream in) {
        super(in);
    }

    public Tag readTag() throws IOException {
        int type = readByte() & 0xFF;

        String name;
        if (type != 0) {
            int nameLength = readShort() & 0xFFFF;
            byte[] nameBytes = new byte[nameLength];
            readFully(nameBytes);
            name = new String(nameBytes, "UTF-8");
        } else {
            name = "";
        }

        return readTagPayload(type, name);
    }

    public Tag readTagPayload(int type, String name) throws IOException {
        switch (type) {
            case 0:
                return null;
            case 1:
                return new ByteTag(name, readByte());
            case 2:
                return new ShortTag(name, readShort());
            case 3:
                return new IntTag(name, readInt());
            case 4:
                return new LongTag(name, readLong());
            case 5:
                return new FloatTag(name, readFloat());
            case 6:
                return new DoubleTag(name, readDouble());
            case 7:
                int length = readInt();
                byte[] bytes = new byte[length];
                readFully(bytes);
                return new ByteArrayTag(name, bytes);
            case 8:
                length = readShort();
                bytes = new byte[length];
                readFully(bytes);
                return new StringTag(name, new String(bytes, "UTF-8"));
            case 9:
                int listType = readByte();
                length = readInt();
                ListTag listTag = new ListTag(name);
                for (int i = 0; i < length; i++) {
                    listTag.add(readTagPayload(listType, ""));
                }
                return listTag;
            case 10:
                CompoundTag compoundTag = new CompoundTag(name);
                while (true) {
                    Tag tag = readTag();
                    if (tag == null) {
                        break;
                    } else {
                        compoundTag.put(tag);
                    }
                }
                return compoundTag;
            case 11:
                length = readInt();
                int[] ints = new int[length];
                for (int i = 0; i < length; i++) {
                    ints[i] = readInt();
                }
                return new IntArrayTag(name, ints);
            case 12:
                length = readInt();
                long[] longs = new long[length];
                for (int i = 0; i < length; i++) {
                    longs[i] = readLong();
                }
                return new LongArrayTag(name, longs);
            default:
                throw new IOException("Invalid tag type: " + type);
        }
    }
}
