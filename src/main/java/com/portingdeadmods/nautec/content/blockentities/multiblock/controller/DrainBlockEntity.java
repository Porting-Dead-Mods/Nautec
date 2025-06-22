package com.portingdeadmods.nautec.content.blockentities.multiblock.controller;

import com.google.common.collect.ImmutableMap;
import com.portingdeadmods.nautec.NTConfig;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.api.blockentities.multiblock.MultiblockEntity;
import com.portingdeadmods.nautec.api.multiblocks.MultiblockData;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.content.blockentities.multiblock.part.DrainPartBlockEntity;
import com.portingdeadmods.nautec.content.blocks.multiblock.part.DrainPartBlock;
import com.portingdeadmods.nautec.content.multiblocks.DrainMultiblock;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import com.portingdeadmods.nautec.registries.NTFluids;
import com.portingdeadmods.nautec.utils.BlockUtils;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

// TODO: Require power to work
public class DrainBlockEntity extends LaserBlockEntity implements MultiblockEntity {
    private MultiblockData multiblockData;

    // TODO: Merge these variables
    // Client side animation stuff
    private float valveIndependentAngle;
    private float valveChasingVelocity;
    private int valveInUse;
    private int valveSpeed;

    private float lidIndependentAngle;
    private float lidChasingVelocity;
    private int lidInUse;
    private int lidSpeed;

    private boolean closing;
    private int valveLidInterval;

    public DrainBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.DRAIN.get(), blockPos, blockState);
        addFluidTank(NTConfig.drainCapacity);
        this.multiblockData = MultiblockData.EMPTY;
    }

    public void open() {
        this.valveInUse = 12;
        this.valveSpeed = 30;
        level.playSound(null, worldPosition, SoundEvents.IRON_DOOR_CLOSE, SoundSource.BLOCKS, 1, 1f);

        // Set top blocks to open
        setOpen(true);
    }

    @Override
    public void onPowerChanged() {
        super.onPowerChanged();

        updatePowerAndBubbles();

    }

    public void close() {
        this.lidInUse = 36;
        this.lidSpeed = -6;
        this.closing = true;
    }

    public boolean isMoving() {
        return lidInUse > 0 || valveInUse > 0;
    }

    public boolean isClosing() {
        return closing;
    }

    private void setOpen(boolean value) {
        BlockPos selfPos = worldPosition;
        BlockPos[] aroundSelf = BlockUtils.getBlocksAroundSelf3x3(selfPos);
        for (BlockPos blockPos : aroundSelf) {
            BlockState state = level.getBlockState(blockPos);
            // Only set OPEN property on blocks that have it
            if (state.hasProperty(DrainPartBlock.OPEN)) {
                level.setBlockAndUpdate(blockPos, state.setValue(DrainPartBlock.OPEN, value));
            }
        }
        BlockState selfState = level.getBlockState(selfPos);
        if (selfState.hasProperty(DrainPartBlock.OPEN)) {
            level.setBlockAndUpdate(selfPos, selfState.setValue(DrainPartBlock.OPEN, value));
        }
    }

    private boolean hasWater() {
        BlockPos selfPos = worldPosition.above();
        BlockPos[] aroundSelf = BlockUtils.getBlocksAroundSelf3x3(selfPos);
        for (BlockPos blockPos : aroundSelf) {
            if (!level.getBlockState(blockPos).getFluidState().is(FluidTags.WATER))
                return false;
        }
        return level.getBlockState(selfPos).getFluidState().is(FluidTags.WATER);
    }

    @Override
    public Set<Direction> getLaserInputs() {
        if (getBlockState().getValue(DrainMultiblock.FORMED)) {
            return ObjectSet.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
        }
        return ObjectSet.of();
    }

    @Override
    public Set<Direction> getLaserOutputs() {
        return ObjectSet.of();
    }

    @Override
    public void commonTick() {
        super.commonTick();

        if (getPower() == 0 && getBlockState().getValue(DrainPartBlock.HAS_POWER)) {
            updatePowerAndBubbles();
        }

        performRotation();

        performDraining();

    }

    private void updatePowerAndBubbles() {
        BlockPos[] aroundSelf = BlockUtils.getBlocksAroundSelfHorizontal(worldPosition);
        boolean hasPower = getPower() > 15;
        for (BlockPos pos : aroundSelf) {
            BlockState state = level.getBlockState(pos);
            // Only set HAS_POWER property on blocks that have it
            if (state.hasProperty(DrainPartBlock.HAS_POWER)) {
                level.setBlockAndUpdate(pos, state.setValue(DrainPartBlock.HAS_POWER, hasPower));
            }
        }
        BlockState selfState = getBlockState();
        if (selfState.hasProperty(DrainPartBlock.HAS_POWER)) {
            level.setBlockAndUpdate(worldPosition, selfState.setValue(DrainPartBlock.HAS_POWER, hasPower));
        }

        updateBubbleColumns();
    }

    private boolean openAndFormed() {
        BlockState blockState = getBlockState();
        return blockState.hasProperty(DrainPartBlock.OPEN) && blockState.getValue(DrainPartBlock.OPEN) && blockState.getValue(DrainMultiblock.FORMED);
    }

    private void performDraining() {
        // Every second
        if (level.getGameTime() % 20 == 0 && lidInUse == 0 && getPower() > NTConfig.drainPower) {
            if (hasWater()) {
                if (openAndFormed()) {
                    if (level.getBiome(worldPosition).is(BiomeTags.IS_OCEAN)) {
                        getFluidHandler().fill(new FluidStack(NTFluids.SALT_WATER.getStillFluid(), NTConfig.drainSaltWaterAmount), IFluidHandler.FluidAction.EXECUTE);
                    }
                }
            }
        }
    }

    private void performRotation() {
        // Valve opening/closing
        float actualValveSpeed = getValveSpeed();
        valveChasingVelocity += ((actualValveSpeed * 10 / 3f) - valveChasingVelocity) * .25f;
        valveIndependentAngle += valveChasingVelocity;

        if (valveInUse > 0) {
            valveInUse--;

            if (valveInUse == 0) {
                this.valveSpeed = 0;

                if (!closing) {
                    this.valveLidInterval = 60;
                } else {
                    this.closing = false;

                    // Set top blocks to close
                    setOpen(false);
                }
            }
        }

        // Interval between both actions
        if (valveLidInterval > 0) {
            valveLidInterval--;

            if (valveLidInterval == 0) {
                if (!closing) {
                    // Start opening lid
                    this.lidInUse = 72;
                    this.lidSpeed = 3;
                } else {
                    this.valveInUse = 12;
                    this.valveSpeed = -30;
                }
            }
        }

        // Lid opening/closing
        float actualLidSpeed = getLidSpeed();
        lidChasingVelocity += ((actualLidSpeed * 10 / 3f) - lidChasingVelocity) * .25f;
        lidIndependentAngle += lidChasingVelocity;

        if (lidInUse > 0) {
            lidInUse--;

            if (lidInUse == 0) {
                this.lidSpeed = 0;

                if (closing) {
                    this.valveLidInterval = 30;
                } else {
                    updateBubbleColumns();
                }
            }
        }
    }

    private void updateBubbleColumns() {
        if (getPower() > 15) {
            BlockPos selfPos = worldPosition;
            BlockPos[] aroundSelf = BlockUtils.getBlocksAroundSelfHorizontal(selfPos);
            for (BlockPos blockPos : aroundSelf) {
                BlockState blockState = level.getBlockState(blockPos);
                BubbleColumnBlock.updateColumn(level, blockPos.above(), blockState);
            }
            BubbleColumnBlock.updateColumn(level, selfPos.above(), level.getBlockState(selfPos));
        }
    }

    private float getValveSpeed() {
        return valveSpeed;
    }

    public float getValveIndependentAngle(float partialTicks) {
        return (valveIndependentAngle + partialTicks * valveChasingVelocity) / 360;
    }

    private float getLidSpeed() {
        return lidSpeed;
    }

    public float getLidIndependentAngle(float partialTicks) {
        return (lidIndependentAngle + partialTicks * lidChasingVelocity) / 360;
    }

    @Override
    public <T> ImmutableMap<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        if (capability == Capabilities.FluidHandler.BLOCK) {
            return ImmutableMap.of(
                    Direction.DOWN, Pair.of(IOActions.EXTRACT, new int[]{0})
            );
        }
        return ImmutableMap.of();
    }

    @Override
    public MultiblockData getMultiblockData() {
        return multiblockData;
    }

    @Override
    public void setMultiblockData(MultiblockData data) {
        this.multiblockData = data;
    }

    @Override
    protected void saveData(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveData(tag, provider);
        tag.put("multiblockData", saveMBData());
        tag.putFloat("angle", this.lidIndependentAngle);
    }

    @Override
    protected void loadData(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadData(tag, provider);
        this.multiblockData = loadMBData(tag.getCompound("multiblockData"));
        this.lidIndependentAngle = tag.getFloat("angle");
    }
}
