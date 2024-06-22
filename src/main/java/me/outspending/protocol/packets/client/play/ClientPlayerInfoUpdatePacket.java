package me.outspending.protocol.packets.client.play;

import me.outspending.player.Property;
import me.outspending.protocol.Writable;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record ClientPlayerInfoUpdatePacket(byte actions, Players... players) implements ClientPacket {

    @Override
    public void write(@NotNull PacketWriter writer) {
        writer.writeByte(actions);
        writer.writeVarInt(players.length);
        for (Players players1 : players) {
            writer.writeUUID(players1.uuid());
            for (Action action : players1.actions()) {
                action.write(writer);
            }
        }
    }

    @Override
    public int id() {
        return 0x3E;
    }

    public record Players(UUID uuid, Action... actions) {}
    public interface Action extends Writable {
        record AddPlayer(String name, int numOfProperties, Property[] properties) implements Action {
            @Override
            public void write(@NotNull PacketWriter writer) {
                writer.writeString(name);
                writer.writeVarInt(numOfProperties);
                for (Property property : properties) {
                    property.write(writer);
                }
            }
        }

        record InitializeChat(boolean hasSignatureData, UUID chatSessionID, long publicKeyExpiration, int encodedPublicKeySize, byte[] encodedPublicKey, int encodedNonceSize, byte[] encodedNonce) implements Action {
            @Override
            public void write(@NotNull PacketWriter writer) {
                writer.writeBoolean(hasSignatureData);
                writer.writeUUID(chatSessionID);
                writer.writeLong(publicKeyExpiration);
                writer.writeVarInt(encodedPublicKeySize);
                writer.writeByteArray(encodedPublicKey);
                writer.writeVarInt(encodedNonceSize);
                writer.writeByteArray(encodedNonce);
            }
        }

        record UpdateGameMode(int gameMode) implements Action {
            @Override
            public void write(@NotNull PacketWriter writer) {
                writer.writeVarInt(gameMode);
            }
        }

        record UpdateListed(boolean listed) implements Action {
            @Override
            public void write(@NotNull PacketWriter writer) {
                writer.writeBoolean(listed);
            }
        }

        record UpdateLatency(int ping) implements Action {
            @Override
            public void write(@NotNull PacketWriter writer) {
                writer.writeVarInt(ping);
            }
        }

        record UpdateDisplayName(boolean hasDisplayName, Component displayName) implements Action {
            @Override
            public void write(@NotNull PacketWriter writer) {
                writer.writeBoolean(hasDisplayName);
                writer.writeTextComponent(displayName);
            }
        }

    }

}
