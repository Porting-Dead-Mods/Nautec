package com.portingdeadmods.nautec.content.blockentities;

import com.portingdeadmods.nautec.NTConfig;
import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.content.recipes.MixingRecipe;
import com.portingdeadmods.nautec.content.recipes.inputs.MixingRecipeInput;
import com.portingdeadmods.nautec.content.recipes.utils.IngredientWithCount;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
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
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
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
    private boolean running;

    private float independentAngle;
    private float chasingVelocity;
    private int speed;

    private int duration;

    private MixingRecipe recipe;

    public MixerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.MIXER.get(), blockPos, blockState);
        addItemHandler(5, (slot, stack) -> slot != 4);
        // in
        addFluidTank(NTConfig.mixerInputCapacity);
        // out
        addSecondaryFluidTank(NTConfig.mixerOutputCapacity, fluidStack -> false);
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

        if (running) {
            this.speed = 20;
        } else {
            this.speed = 0;
        }
    }

    private void performRecipe() {
        if (recipe != null && getPower() > NTConfig.mixerPower) {
            this.running = true;
            if (duration >= recipe.duration()) {
                duration = 0;
                this.running = false;
                setOutputs(recipe);
                removeInputs(recipe);
                this.recipe = getRecipe().orElse(null);
            } else {
                duration++;
            }
        } else {
            this.running = false;
            duration = 0;
            this.recipe = null;
        }
    }

    private void removeInputs(MixingRecipe mixingRecipe) {
        IFluidHandler fluidHandler = getFluidHandler();
        IItemHandler itemHandler = getItemHandler();
        List<IngredientWithCount> ingredients = new ArrayList<>(mixingRecipe.ingredients());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack item = itemHandler.getStackInSlot(i);
            for (IngredientWithCount ingredient : ingredients) {
                if (ingredient.test(item)) {
                    itemHandler.extractItem(i, ingredient.count(), false);
                    ingredients.remove(ingredient);
                    break;
                }
            }
        }
        fluidHandler.drain(mixingRecipe.fluidIngredient().getAmount(), IFluidHandler.FluidAction.EXECUTE);
    }

    private void setOutputs(MixingRecipe mixingRecipe) {
        ItemStackHandler handler = getItemStackHandler();
        int prevCount = handler.getStackInSlot(OUTPUT_SLOT).getCount();
        int newCount = mixingRecipe.result().getCount() + prevCount;
        handler.setStackInSlot(OUTPUT_SLOT, mixingRecipe.result().copyWithCount(newCount));
        FluidTank tank = getSecondaryFluidTank();
        int prevAmount = tank.getFluidAmount();
        int newAmount = mixingRecipe.fluidResult().getAmount() + prevAmount;
        tank.setFluid(mixingRecipe.fluidResult().copyWithAmount(newAmount));
    }

    private Optional<MixingRecipe> getRecipe() {
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
        Optional<MixingRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(MixingRecipe.Type.INSTANCE, input, level).map(RecipeHolder::value);
        if (recipe.isPresent() && canInsertItem(recipe.get().result()) && canInsertFluid(recipe.get().fluidResult())) {
            return recipe;
        }
        return Optional.empty();
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

    @Override
    public void onLoad() {
        super.onLoad();
        this.recipe = getRecipe().orElse(null);
    }

    @Override
    protected void onItemsChanged(int slot) {
        super.onItemsChanged(slot);
        this.recipe = getRecipe().orElse(null);
    }

    @Override
    protected void onFluidChanged() {
        super.onFluidChanged();
        this.recipe = getRecipe().orElse(null);
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
        this.independentAngle = tag.getFloat("independentAngle");
    }

    @Override
    protected void saveData(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveData(tag, provider);
        tag.putInt("duration", this.duration);
        tag.putFloat("independentAngle", this.independentAngle);
    }

    public FluidStack getInputFluid() {
        return getFluidTank().getFluid();
    }

    public int getInputFluidAmount() {
        return getFluidTank().getFluidAmount();
    }

    public FluidStack getOutputFluid() {
        return getSecondaryFluidTank().getFluid();
    }

    public int getOutputFluidAmount() {
        return getSecondaryFluidTank().getFluidAmount();
    }

    public int getDuration() {
        return this.duration;
    }

    public boolean isActive() {
        return this.running;
    }

    public int getMaxDuration() {
        return getRecipe().map(MixingRecipe::duration).orElse(0);
    }


}
