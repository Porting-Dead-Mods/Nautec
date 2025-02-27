package com.portingdeadmods.nautec.content.blockentities;

import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.api.items.IPowerItem;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.capabilities.power.IPowerStorage;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import com.portingdeadmods.nautec.utils.ParticleUtils;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

public class ChargerBlockEntity extends LaserBlockEntity {
    public ChargerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.CHARGER.get(), blockPos, blockState);
        addItemHandler(1);
    }


    @Override
    public Set<Direction> getLaserInputs() {
        return ObjectSet.of(Direction.values());
    }

    @Override
    public Set<Direction> getLaserOutputs() {
        return ObjectSet.of();
    }


    @Override
    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return Map.of();
    }

    @Override
    public void commonTick() {
        super.commonTick();
        if (getPower() > 0) {
            IItemHandler itemHandler = getItemHandler();
            if (itemHandler.getStackInSlot(0).getItem() instanceof IPowerItem powerItem) {
                IPowerStorage powerStorage = itemHandler.getStackInSlot(0).getCapability(NTCapabilities.PowerStorage.ITEM);
                if(powerStorage.getPowerStored() < powerStorage.getPowerCapacity()) {
                    powerStorage.tryFillPower(4, false);
                    if (level.isClientSide) {
                        ParticleUtils.spawnParticlesAroundBlock(getBlockPos(), getLevel(), ParticleTypes.ELECTRIC_SPARK);
                    }
                }
            }
        }
    }
}
