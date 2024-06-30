package me.outspending;

import lombok.Getter;
import me.outspending.protocol.Writable;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@Getter
public class NamespacedID implements Writable {
    public static final NamespacedID EMPTY = new NamespacedID("minecraft", "empty");

    private static final String VALID_NAMESPACE = "[a-z0-9.-_]*";
    private static final String VALID_PATH = "[a-z0-9.-_/]*";

    private final String namespace;
    private final String path;

    public static NamespacedID of(String string) {
        if (string.contains(":")) {
            String[] split = string.split(":");
            return new NamespacedID(split[0], split[1]);
        }

        return new NamespacedID(string);
    }

    public NamespacedID(String namespace, String path) {
        if (!namespace.matches(VALID_NAMESPACE) || !path.matches(VALID_PATH)) {
            throw new IllegalArgumentException("Invalid namespace or path");
        }

        this.namespace = namespace;
        this.path = path;
    }

    public NamespacedID(String path) {
        if (!path.matches(VALID_PATH)) {
            throw new IllegalArgumentException("Invalid path");
        }

        this.namespace = "minecraft";
        this.path = path;
    }

    @Override
    public String toString() {
        return this.namespace + ":" + this.path;
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeString(this.toString());
    }

}
