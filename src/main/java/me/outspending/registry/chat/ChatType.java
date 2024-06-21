package me.outspending.registry.chat;

import me.outspending.NamespacedID;
import me.outspending.protocol.Writable;
import me.outspending.protocol.writer.PacketWriter;
import me.outspending.registry.RegistryType;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;

public record ChatType(
        @NotNull NamespacedID registryID,
        @NotNull ChatTypeDecoration chat,
        @NotNull ChatTypeDecoration narration
) implements RegistryType {

    public static @NotNull Builder builder(@NotNull String namespace) {
        return new Builder(NamespacedID.of(namespace));
    }

    public static @NotNull Builder builder(@NotNull NamespacedID namespace) {
        return new Builder(namespace);
    }

    @Override
    public @NotNull NamespacedID getRegistryID() {
        return this.registryID;
    }

    @Override
    public @NotNull CompoundBinaryTag toNBT() {
        return CompoundBinaryTag.builder()
                .put("chat", this.chat.toNBT())
                .put("narration", this.narration.toNBT())
                .build();
    }

    public static final class Builder {
        private final NamespacedID namespacedID;
        private ChatTypeDecoration chat;
        private ChatTypeDecoration narration;

        public Builder(NamespacedID namespacedID) {
            this.namespacedID = namespacedID;
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
            return new ChatType(this.namespacedID, this.chat, this.narration);
        }
    }
}
