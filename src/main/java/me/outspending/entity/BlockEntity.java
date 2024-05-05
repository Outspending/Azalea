package me.outspending.entity;

import net.kyori.adventure.nbt.CompoundBinaryTag;

public record BlockEntity(int packedXZ, short y, int type, CompoundBinaryTag data) {}
