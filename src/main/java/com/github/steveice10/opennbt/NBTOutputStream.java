package com.github.steveice10.opennbt;

import com.github.steveice10.opennbt.tag.builtin.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class NBTOutputStream extends DataOutputStream {
    public NBTOutputStream(OutputStream out) {
        super(out);
    }

    public void writeTag(Tag tag) throws IOException {
        int type = NBTUtils.getTypeCode(tag.getClass());
        byte[] nameBytes = tag.getName().getBytes("UTF-8");

        writeByte(type);
        if (type != 0) {
            writeShort(nameBytes.length);
            write(nameBytes);
        }

        if (type != 0) {
            writeTagPayload(tag);
        }
    }

    public void writeTagPayload(Tag tag) throws IOException {
        switch (tag) {
            case ByteTag byteTag -> writeByte(byteTag.getValue());
            case ShortTag shortTag -> writeShort(shortTag.getValue());
            case IntTag intTag -> writeInt(intTag.getValue());
            case LongTag longTag -> writeLong(longTag.getValue());
            case FloatTag floatTag -> writeFloat(floatTag.getValue());
            case DoubleTag doubleTag -> writeDouble(doubleTag.getValue());
            case ByteArrayTag byteArrayTag -> {
                byte[] bytes = byteArrayTag.getValue();
                writeInt(bytes.length);
                write(bytes);
            }
            case StringTag stringTag -> {
                byte[] bytes = stringTag.getValue().getBytes("UTF-8");
                writeShort(bytes.length);
                write(bytes);
            }
            case ListTag listTag -> {
                writeByte(NBTUtils.getTypeCode(listTag.getValue().get(0).getClass()));
                writeInt(listTag.getValue().size());
                for (Tag t : listTag.getValue()) {
                    writeTagPayload(t);
                }
            }
            case CompoundTag compoundTag -> {
                for (Tag t : compoundTag.getValue().values()) {
                    writeTag(t);
                }
                writeByte(0); // end of compound tag
            }
            case IntArrayTag intArrayTag -> {
                int[] ints = intArrayTag.getValue();
                writeInt(ints.length);
                for (int i : ints) {
                    writeInt(i);
                }
            }
            case LongArrayTag longArrayTag -> {
                long[] longs = longArrayTag.getValue();
                writeInt(longs.length);
                for (long l : longs) {
                    writeLong(l);
                }
            }
            case null, default -> throw new IOException("Invalid tag type: " + tag.getClass());
        }
    }
}
