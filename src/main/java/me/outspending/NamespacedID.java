package me.outspending;

import lombok.Getter;

@Getter
public class NamespacedID {
    private static final String VALID_NAMESPACE = "[a-z0-9.-_]*";
    private static final String VALID_PATH = "[a-z0-9.-_/]*";

    private final String namespace;
    private final String path;

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
}
