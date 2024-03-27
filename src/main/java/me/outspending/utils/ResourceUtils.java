package me.outspending.utils;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.nullicorn.nedit.NBTReader;
import me.nullicorn.nedit.type.NBTCompound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ResourceUtils {
    public static @Nullable InputStream getResource(@NotNull String resource) {
        return ResourceUtils.class.getResourceAsStream(resource);
    }

    public static @NotNull List<String> readResourceLines(@NotNull String resource) {
        List<String> lines = new ArrayList<>();
        InputStream stream = getResource(resource);
        Preconditions.checkNotNull(stream, "Resource not found: " + resource);

        try (InputStreamReader streamReader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            BufferedReader reader = new BufferedReader(streamReader);

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public static @Nullable JsonObject getResourceJson(@NotNull String resource) {
        InputStream stream = getResource(resource);
        Preconditions.checkNotNull(stream, "Resource not found: " + resource);

        return new Gson().fromJson(new InputStreamReader(stream), JsonObject.class);
    }

    public static @NotNull NBTCompound getResourceCompound(@NotNull String resource) throws IOException {
        InputStream stream = getResource(resource);
        System.out.println(stream);
        return NBTReader.read(stream);
    }
}
