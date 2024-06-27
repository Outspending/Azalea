package me.outspending.events.event;

import me.outspending.entity.Entity;
import me.outspending.events.types.Event;
import me.outspending.position.Pos;
import me.outspending.protocol.InteractType;

public record EntityInteractEvent(int entityID, InteractType interactType, Pos interactPos, Entity.Hand hand, boolean sneaking) implements Event {}
