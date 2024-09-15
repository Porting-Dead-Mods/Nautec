package com.portingdeadmods.modjam.content.blockentities;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.modjam.capabilities.IOActions;
import com.portingdeadmods.modjam.content.recipes.MixingRecipe;
import com.portingdeadmods.modjam.content.recipes.utils.MixingRecipeInput;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MixerBlockEntity extends LaserBlockEntity {
    public static final int OUTPUT_SLOT = 4;
    private boolean active;

    private float independentAngle;
    private float chasingVelocity;
    private int speed;

    private int duration;

    public MixerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MJBlockEntityTypes.MIXER.get(), blockPos, blockState);
        addItemHandler(5, (slot, stack) -> slot != 4);
        // in
        addFluidTank(1000);
        // out
        addSecondaryFluidTank(1000, fluidStack -> false);
    }

    @Override
    public ObjectSet<Direction> getLaserInputs() {
        return ObjectSet.of(
                Direction.UP,
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
        IItemHandler itemHandler = getItemHandler();
        int slots = itemHandler.getSlots();
        List<ItemStack> itemHandlerStacksList = new ArrayList<>(slots);
        for (int i = 0; i < slots - 1; i++) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                itemHandlerStacksList.add(stack);
            }
        }
        MixingRecipeInput input = new MixingRecipeInput(itemHandlerStacksList, getFluidTank().getFluid());
        if (level.getGameTime() % 200 == 0) {
            ModJam.LOGGER.debug("List: {}", itemHandlerStacksList);
            ModJam.LOGGER.debug("input: {}", input);
        }
        Optional<MixingRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(MixingRecipe.Type.INSTANCE, input, level).map(RecipeHolder::value);
        if (recipe.isPresent()) {
            ModJam.LOGGER.debug("has recipe");
            if (canInsertFluid(recipe.get().fluidResult())
                    && canInsertItem(recipe.get().result())) {
                ModJam.LOGGER.debug("AAAAA");
                MixingRecipe mixingRecipe = recipe.get();
                if (duration >= mixingRecipe.duration()) {
                    ItemStackHandler handler = getItemStackHandler();
                    int prevCount = handler.getStackInSlot(OUTPUT_SLOT).getCount();
                    int newCount = recipe.get().result().getCount() + prevCount;
                    handler.setStackInSlot(OUTPUT_SLOT, mixingRecipe.result().copyWithCount(newCount));
                    FluidTank tank = getFluidTank();
                    int prevAmount = tank.getFluidAmount();
                    int newAmount = recipe.get().fluidResult().getAmount() + prevAmount;
                    tank.setFluid(mixingRecipe.fluidResult().copyWithAmount(newAmount));
                    duration = 0;
                } else {
                    duration++;
                }
            }
        }
    }

    private boolean canInsertItem(ItemStack result) {
        ItemStack stack = getItemHandler().getStackInSlot(OUTPUT_SLOT);
        boolean itemMatches = result.isEmpty() || stack.isEmpty() || result.is(stack.getItem());
        int stackLimit = stack.isEmpty() ? result.getMaxStackSize() : stack.getMaxStackSize();
        boolean amountMatches = result.getCount() + stack.getCount() <= Math.min(stackLimit, getItemHandler().getSlotLimit(OUTPUT_SLOT));
        return itemMatches && amountMatches;
    }

    private boolean canInsertFluid(FluidStack fluidStack) {
        boolean fluidMatches = fluidStack.isEmpty() || getSecondaryFluidTank().isEmpty() || fluidStack.is(getSecondaryFluidTank().getFluid().getFluid());
        int fluidAmount = getSecondaryFluidTank().getFluidAmount();
        boolean amountMatches = fluidAmount + fluidStack.getAmount() <= getSecondaryFluidTank().getCapacity();
        return fluidMatches && amountMatches;
    }

    public int getSpeed() {
        return speed;
    }

    public float getIndependentAngle(float partialTicks) {
        return (independentAngle + partialTicks * chasingVelocity) / 360;
    }

    @Override
    protected void loadData(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadData(tag, provider);
        this.duration = tag.getInt("duration");
    }

    @Override
    protected void saveData(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveData(tag, provider);
        tag.putInt("duration", this.duration);
    }

}
