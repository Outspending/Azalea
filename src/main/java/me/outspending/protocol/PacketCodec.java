package me.outspending.protocol;

import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class PacketCodec<T extends Enum<T>> {
    private final int protocolVersion;
    private final String minecraftVersion;

    private final EnumMap<T, Map<Integer, Function<PacketReader, Packet>>> packets;

    public static <T extends Enum<T>> @NotNull Builder<T> builder(Class<T> type) {
        return new Builder<>(type);
    }

    public @Nullable Function<PacketReader, Packet> getPacket(T type, int id) {
        Map<Integer, Function<PacketReader, Packet>> value = packets.get(type);

        if (value == null) throw new IllegalArgumentException("Unknown packet type: " + type);
        else return value.get(id);
    }

    public @NotNull Builder<T> toBuilder() {
        return new Builder<>(protocolVersion, minecraftVersion, packets);
    }

    @AllArgsConstructor(access = AccessLevel.PUBLIC)
    @NoArgsConstructor
    public static class Builder<T extends Enum<T>> {
        private int protocolVersion = -1;
        private String minecraftVersion = null;
        private EnumMap<T, Map<Integer, Function<PacketReader, Packet>>> packets;

        public Builder(@NotNull Class<T> keyType) {
            this.packets = new EnumMap<>(keyType);
        }

        public Builder<T> protocolVersion(int protocolVersion) {
            this.protocolVersion = protocolVersion;
            return this;
        }

        @Contract("_ -> this")
        public Builder<T> minecraftVersion(@NotNull String minecraftVersion) {
            this.minecraftVersion = minecraftVersion;
            return this;
        }

        @Contract("_, _ -> this")
        public Builder<T> packetType(@NotNull T type, @NotNull Map<Integer, Function<PacketReader, Packet>> packets) {
            this.packets.put(type, packets);
            return this;
        }

        public @NotNull PacketCodec<T> build() {
            return new PacketCodec<>(protocolVersion, minecraftVersion, packets);
        }
    }
}
