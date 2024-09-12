package com.portingdeadmods.modjam.content.blockentities;

import com.portingdeadmods.modjam.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.modjam.capabilities.IOActions;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class MixerBlockEntity extends LaserBlockEntity {
    private boolean active;

    private float independentAngle;
    private float chasingVelocity;
    private int speed;

    public MixerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MJBlockEntityTypes.MIXER.get(), blockPos, blockState);
        addFluidTank(1000);
        addItemHandler(5, (slot, stack) -> slot != 4);
    }

    @Override
    public ObjectSet<Direction> getLaserInputs() {
        return ObjectSet.of(
                Direction.DOWN
        );
    }

    @Override
    public ObjectSet<Direction> getLaserOutputs() {
        return ObjectSet.of();
    }

    @Override
    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        if (capability == Capabilities.ItemHandler.BLOCK) {
            return Map.of(
                    Direction.NORTH, Pair.of(IOActions.INSERT, new int[]{0, 1, 2, 3}),
                    Direction.EAST, Pair.of(IOActions.INSERT, new int[]{0, 1, 2, 3}),
                    Direction.SOUTH, Pair.of(IOActions.INSERT, new int[]{0, 1, 2, 3}),
                    Direction.WEST, Pair.of(IOActions.INSERT, new int[]{0, 1, 2, 3})
            );
        } else if (capability == Capabilities.FluidHandler.BLOCK) {
            return Map.of(
                    Direction.NORTH, Pair.of(IOActions.INSERT, new int[]{0}),
                    Direction.EAST, Pair.of(IOActions.INSERT, new int[]{0}),
                    Direction.SOUTH, Pair.of(IOActions.INSERT, new int[]{0}),
                    Direction.WEST, Pair.of(IOActions.INSERT, new int[]{0})
            );
        }
        return Map.of();
    }

    @Override
    public void commonTick() {
        super.commonTick();
        float actualSpeed = getSpeed();
        chasingVelocity += ((actualSpeed * 10 / 3f) - chasingVelocity) * .25f;
        independentAngle += chasingVelocity;

        performRecipe();
    }

    private void performRecipe() {

    }

    public int getSpeed() {
        return speed;
    }

    public float getIndependentAngle(float partialTicks) {
        return (independentAngle + partialTicks * chasingVelocity) / 360;
    }
}
