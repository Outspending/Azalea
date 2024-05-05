package me.outspending.entity;

import lombok.AccessLevel;
import lombok.Getter;
<<<<<<< Updated upstream
=======
import lombok.Setter;
import me.outspending.Tickable;
import me.outspending.chunk.Chunk;
>>>>>>> Stashed changes
import me.outspending.connection.ClientConnection;
import me.outspending.position.Pos;
import me.outspending.protocol.packets.client.play.ClientUpdateEntityPositionAndRotationPacket;
import me.outspending.utils.PacketUtils;
import me.outspending.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter @Setter(AccessLevel.MODULE)
public class Player implements TickingEntity {
    private final ClientConnection connection;

    private final int entityID;
    private final String username;
    private final UUID uuid;

    private World world;
    private Pos position;

    public Player(ClientConnection connection, int entityID, String username, UUID uuid) {
        this.connection = connection;
        this.entityID = entityID;
        this.username = username;
        this.uuid = uuid;
    }
<<<<<<< Updated upstream
=======

    public void setWorld(World world) {
        this.world = world;
        world.addEntity(this);
    }

    @Override
    public List<Chunk> getChunksInDistance(int distance) {
        return List.of();
    }

    @Override
    public List<Chunk> getLoadedChunks() {
        return List.of();
    }

    @Override
    public void tick(long time) {
    }
>>>>>>> Stashed changes
}
