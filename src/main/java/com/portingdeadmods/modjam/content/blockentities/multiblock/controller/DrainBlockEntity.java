package com.portingdeadmods.modjam.content.blockentities.multiblock.controller;

import com.google.common.collect.ImmutableMap;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.modjam.api.blockentities.multiblock.MultiblockEntity;
import com.portingdeadmods.modjam.api.multiblocks.MultiblockData;
import com.portingdeadmods.modjam.capabilities.IOActions;
import com.portingdeadmods.modjam.content.blocks.multiblock.part.DrainPartBlock;
import com.portingdeadmods.modjam.content.multiblocks.DrainMultiblock;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import com.portingdeadmods.modjam.registries.MJFluids;
import com.portingdeadmods.modjam.utils.BlockUtils;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

public class DrainBlockEntity extends ContainerBlockEntity implements MultiblockEntity {
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
        super(MJBlockEntityTypes.DRAIN.get(), blockPos, blockState);
        addFluidTank(128_000);
        this.multiblockData = MultiblockData.EMPTY;
    }

    public void open() {
        this.valveInUse = 12;
        this.valveSpeed = 30;
        level.playSound(null, worldPosition, SoundEvents.IRON_DOOR_CLOSE, SoundSource.BLOCKS, 1, 1f);

        // Set top blocks to open
        setTop(true);
    }

    public void close() {
        this.lidInUse = 36;
        this.lidSpeed = -6;
        this.closing = true;
    }

    private void setTop(boolean value) {
        BlockPos selfPos = worldPosition.above();
        BlockPos[] aroundSelf = BlockUtils.getBlocksAroundSelf3x3(selfPos);
        for (BlockPos blockPos : aroundSelf) {
            level.setBlockAndUpdate(blockPos, level.getBlockState(blockPos).setValue(DrainPartBlock.OPEN, value));
        }
        level.setBlockAndUpdate(selfPos, level.getBlockState(selfPos).setValue(DrainPartBlock.OPEN, value));
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
    public void commonTick() {
        super.commonTick();
        performRotation();

        performDraining();

    }

    private boolean openAndFormed() {
        BlockState blockState = level.getBlockState(worldPosition.above());
        return blockState.hasProperty(DrainPartBlock.OPEN) && blockState.getValue(DrainPartBlock.OPEN) && blockState.getValue(DrainMultiblock.FORMED);
    }

    private void performDraining() {
        // Every second
        if (level.getGameTime() % 20 == 0) {
            ModJam.LOGGER.debug("Filling 0 ");
            if (hasWater()) {
                ModJam.LOGGER.debug("Filling 1 ");
                if (openAndFormed()) {
                    ModJam.LOGGER.debug("Filling 2 ");
                    if (level.getBiome(worldPosition).is(BiomeTags.IS_OCEAN)) {
                        ModJam.LOGGER.debug("Filling");
                        getFluidHandler().fill(new FluidStack(MJFluids.SALT_WATER_SOURCE.get(), 500), IFluidHandler.FluidAction.EXECUTE);
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
                    setTop(false);
                }
            }
        }

        // Interval between both actions
        if (valveLidInterval > 0) {
            valveLidInterval--;

            if (valveLidInterval == 0) {
                if (!closing) {
                    // Start opening lid
                    this.lidInUse = 36;
                    this.lidSpeed = 6;
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
        BlockPos selfPos = worldPosition.above();
        BlockPos[] aroundSelf = BlockUtils.getBlocksAroundSelfHorizontal(selfPos);
        for (BlockPos blockPos : aroundSelf) {
            BlockState blockState = level.getBlockState(blockPos);
            BubbleColumnBlock.updateColumn(level, blockPos.above(), blockState);
        }
        BubbleColumnBlock.updateColumn(level, selfPos, level.getBlockState(selfPos));
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
    }

    @Override
    protected void loadData(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadData(tag, provider);
        this.multiblockData = loadMBData(tag.getCompound("multiblockData"));
    }
}
