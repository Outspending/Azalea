package me.outspending.protocol.types;

import me.outspending.protocol.Writable;
import me.outspending.protocol.writer.PacketWriter;
import org.jetbrains.annotations.Contract;

import java.io.IOException;

public interface ClientPacket extends Packet, Writable {
}
