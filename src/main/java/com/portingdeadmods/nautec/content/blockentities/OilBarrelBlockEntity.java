package com.portingdeadmods.nautec.content.blockentities;

import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.content.blocks.OilBarrelBlock;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import com.portingdeadmods.nautec.registries.NTFluids;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class OilBarrelBlockEntity extends ContainerBlockEntity {
    public OilBarrelBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.OIL_BARREL.get(), blockPos, blockState);
        addFluidTank(8000, fluid -> fluid.is(NTFluids.OIL.getStillFluid()));
    }

    @Override
    public IFluidHandler getFluidHandler() {
        return getBlockState().getValue(OilBarrelBlock.OPEN) ? super.getFluidHandler() : null;
    }

    @Override
    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return Map.of();
    }
}
