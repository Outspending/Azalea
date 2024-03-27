package me.outspending;

import me.outspending.nbt.NBTCompound;
import me.outspending.nbt.NBTString;
import me.outspending.nbt.io.snbt.SNBTUtil;
import me.outspending.nbt.numbers.NBTFloat;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Just for testing
        NBTCompound compound = new NBTCompound();
        compound.put("name", new NBTString("Notch"));
        compound.put("value.testing", new NBTFloat(124.2f));
        System.out.println(SNBTUtil.toSNBT(compound));

        MinecraftServer server = MinecraftServer.init("127.0.0.1", 25565);
        if (server != null) {
            server.start();
        }
    }
}
