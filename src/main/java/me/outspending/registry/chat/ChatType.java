package me.outspending.registry.chat;

import me.outspending.NamespacedID;
import org.jetbrains.annotations.NotNull;

public final record ChatType(
        @NotNull NamespacedID namespace,
        @NotNull ChatTypeDecoration chat,
        @NotNull ChatTypeDecoration narration
) {

    public static @NotNull Builder builder(@NotNull String namespace) {
        return builder(NamespacedID.of(namespace));
    }

    public static @NotNull Builder builder(@NotNull NamespacedID namespace) {
        return new Builder(namespace);
    }

    public static final class Builder {
        private final NamespacedID namespace;
        private ChatTypeDecoration chat;
        private ChatTypeDecoration narration;

        public Builder(@NotNull NamespacedID namespace) {
            this.namespace = namespace;
        }

        public Builder chat(@NotNull ChatTypeDecoration chat) {
            this.chat = chat;
            return this;
        }

        public Builder narration(@NotNull ChatTypeDecoration narration) {
            this.narration = narration;
            return this;
        }

        public ChatType build() {
            return new ChatType(this.namespace, this.chat, this.narration);
        }
    }
}
