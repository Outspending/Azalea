package me.outspending.utils;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ResourceUtils {
    public static URL getResource(@NotNull String resource) {
        return ResourceUtils.class.getResource(resource);
    }

    public static @Nullable InputStream getResourceAsStream(@NotNull String resource) {
        return ResourceUtils.class.getResourceAsStream(resource);
    }

    public static @NotNull String getResourceAsString(@NotNull String resource) {
        InputStream stream = getResourceAsStream(resource);
        Preconditions.checkNotNull(stream, "Resource not found: " + resource);

        try (InputStreamReader streamReader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }

            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static @NotNull List<String> readResourceLines(@NotNull String resource) {
        List<String> lines = new ArrayList<>();
        InputStream stream = getResourceAsStream(resource);
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
        InputStream stream = getResourceAsStream(resource);
        Preconditions.checkNotNull(stream, "Resource not found: " + resource);

        return new Gson().fromJson(new InputStreamReader(stream), JsonObject.class);
    }
}
