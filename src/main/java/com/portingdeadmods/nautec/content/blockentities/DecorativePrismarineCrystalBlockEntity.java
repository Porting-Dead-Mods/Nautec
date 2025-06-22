package com.portingdeadmods.nautec.content.blockentities;

import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DecorativePrismarineCrystalBlockEntity extends BlockEntity {
    public DecorativePrismarineCrystalBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.DECORATIVE_PRISMARINE_CRYSTAL.get(), blockPos, blockState);
    }
}