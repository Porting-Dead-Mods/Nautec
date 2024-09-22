package com.portingdeadmods.nautec.api.blockentities.multiblock;


import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;

public interface FakeBlockEntity {
    boolean actualBlockEntity();

    @Nullable BlockPos getActualBlockEntityPos();
}