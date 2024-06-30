package me.outspending.entity;

import net.kyori.adventure.nbt.CompoundBinaryTag;

public record BlockEntity(byte packedXZ, short y, int type, CompoundBinaryTag data) {}
