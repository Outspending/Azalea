package me.outspending.nbt;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import me.nullicorn.nedit.type.NBTCompound;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class NBTJson {
    public static @NotNull NBTCompound parse(@NotNull JsonObject object) {
        final NBTCompound compound = new NBTCompound();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            handleElement(compound, entry.getKey(), entry.getValue());
        }

        System.out.println(compound);
        return compound;
    }

    private static void handleElement(@NotNull NBTCompound compound, @NotNull String key, @NotNull JsonElement element) {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                compound.put(key, primitive.getAsString());
            } else if (primitive.isBoolean()) {
                compound.put(key, primitive.getAsBoolean());
            } else if (primitive.isNumber()) {
                Number number = primitive.getAsNumber();
                switch (number) {
                    case Byte b -> compound.put(key, b);
                    case Short s -> compound.put(key, s);
                    case Integer i -> compound.put(key, i);
                    case Long l -> compound.put(key, l);
                    case Float f -> compound.put(key, f);
                    case Double d -> compound.put(key, d);
                    default -> throw new IllegalStateException("Unexpected value: " + number);
                }
            }
        } else if (element.isJsonObject()) {
            try {
                StringWriter stringWriter = new StringWriter();
                JsonWriter writer = new JsonWriter(stringWriter);
                writer.setLenient(true);

                JsonObject jsonObject = element.getAsJsonObject();
                Streams.write(jsonObject, writer);

                NBTCompound compound1 = parse(jsonObject);
                compound.put(key, compound1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (element.isJsonArray()) {
            handleArray(compound, key, element.getAsJsonArray());
        } else {
            throw new IllegalStateException("Unexpected value: " + element);
        }
    }

    public static void handleArray(@NotNull NBTCompound compound, @NotNull String key, @NotNull JsonArray array) {
        // The only valid array types are byte, int, long, and list
        for (JsonElement element : array) {
            if (element.isJsonPrimitive()) {
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                if (primitive.isNumber()) {
                    Number number = primitive.getAsNumber();
                    switch (number) {
                        case Byte b -> compound.put(key, b.byteValue());
                        case Integer i -> compound.put(key, i.intValue());
                        case Long l -> compound.put(key, l.longValue());
                        default -> throw new IllegalStateException("Unexpected value: " + number);
                    }
                }
            } else {
                throw new IllegalStateException("Unexpected value: " + element);
            }
        }
    }
}
