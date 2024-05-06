package me.outspending.protocol.packets.client.play;

import me.outspending.entity.Property;
import me.outspending.protocol.Writable;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.types.ClientPacket;
import me.outspending.protocol.writer.PacketWriter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record ClientPlayerInfoUpdatePacket(byte actions, Players... players) implements ClientPacket {
    public static ClientPlayerInfoUpdatePacket of(@NotNull PacketReader reader) {
        return new ClientPlayerInfoUpdatePacket(
                reader.readByte(),
                reader.readArray(playersReader -> new Players(
                        playersReader.readUUID(),
                        playersReader.readArray(actionReader -> {
                            byte action = actionReader.readByte();
                            return switch (action) {
                                case 0 -> new Action.AddPlayer(
                                        actionReader.readString(),
                                        actionReader.readVarInt(),
                                        actionReader.readArray(propertyReader -> new Property(
                                                propertyReader.readString(),
                                                propertyReader.readString(),
                                                propertyReader.readBoolean() ? propertyReader.readString() : null
                                        ), Property[]::new)
                                );
                                case 1 -> new Action.InitializeChat(
                                        actionReader.readBoolean(),
                                        actionReader.readUUID(),
                                        actionReader.readLong(),
                                        actionReader.readVarInt(),
                                        actionReader.readByteArray(),
                                        actionReader.readVarInt(),
                                        actionReader.readByteArray()
                                );
                                case 2 -> new Action.UpdateGameMode(actionReader.readVarInt());
                                case 3 -> new Action.UpdateListed(actionReader.readBoolean());
                                case 4 -> new Action.UpdateLatency(actionReader.readVarInt());
                                case 5 -> new Action.UpdateDisplayName(
                                        actionReader.readBoolean(),
                                        actionReader.readTextComponent()
                                );
                                default -> throw new IllegalArgumentException("Unknown action: " + action);
                            };
                        }, Action[]::new)
                ), Players[]::new)
        );
    }

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
        return 0x3C;
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
