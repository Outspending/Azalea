package me.outspending.chunk.palette;

import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.Getter;
import me.outspending.block.BlockState;
import me.outspending.protocol.reader.PacketReader;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.NotNull;

@Getter
public class IndirectPalette implements ChunkPalette {
    private Int2ObjectOpenHashMap<BlockState> idToState;
    private Object2IntOpenHashMap<BlockState> stateToID;
    private final byte bitsPerBlock;

    public IndirectPalette(byte bitsPerBlock) {
        this.bitsPerBlock = bitsPerBlock;
    }

    @Override
    public int getStateID(@NotNull BlockState data) {
        return stateToID.getInt(data);
    }

    @Override
    public BlockState getStateForID(int id) {
        return idToState.get(id);
    }

    @Override
    public byte getBitsPerBlock() {
        return bitsPerBlock;
    }

    @Override
    public void read(@NotNull PacketReader reader) {
        idToState = new Int2ObjectOpenHashMap<>();
        stateToID = new Object2IntOpenHashMap<>();
        int length = reader.readVarInt();

        for (int id = 0; id < length; id++) {
            int stateID = reader.readVarInt();
            // TODO: Grab blockState from generated classes
            idToState.put(stateID, null); // Temporary
            // stateToID.put(stateID, 0);
        }
    }

    @Override
    public void write(@NotNull PacketWriter writer) {
        Preconditions.checkArgument(idToState.size() == stateToID.size());
        writer.writeVarInt(idToState.size());

        for (int id = 0; id < idToState.size(); id++) {
            BlockState state = idToState.get(id);
            // int stateID = // grab stateID from generated classes
            // writer.writeVarInt(stateID);
        }
    }
}
