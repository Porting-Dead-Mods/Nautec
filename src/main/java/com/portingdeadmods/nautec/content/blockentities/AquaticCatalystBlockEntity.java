package com.portingdeadmods.nautec.content.blockentities;

import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.content.blocks.AquaticCatalystBlock;
import com.portingdeadmods.nautec.content.recipes.AquaticCatalystChannelingRecipe;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import com.portingdeadmods.nautec.utils.SidedCapUtils;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class AquaticCatalystBlockEntity extends LaserBlockEntity {
    private RecipeHolder<AquaticCatalystChannelingRecipe> currentRecipe;
    private RecipeHolder<AquaticCatalystChannelingRecipe> nextRecipe;
    private int duration;
    private ResourceLocation currentRecipeId;
    private ResourceLocation nextRecipeId;

    public AquaticCatalystBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.AQUATIC_CATALYST.get(), blockPos, blockState);
        addItemHandler(1, (slot, stack) -> getRecipeForCache(stack) != null);
    }

    @Override
    public void commonTick() {
        super.commonTick();

        if (isActive()) {
            if (duration >= currentRecipe.value().duration()) {
                duration = 0;
                currentRecipe = null;
            } else {
                int amount = currentRecipe.value().powerAmount() / currentRecipe.value().duration();
                transmitPower(amount);
                duration++;
            }
        } else {
            currentRecipe = nextRecipe;
            getItemHandler().extractItem(0, 1, false);
        }
    }

    public boolean isActive() {
        return currentRecipe != null;
    }

    public RecipeHolder<AquaticCatalystChannelingRecipe> getCurrentRecipe() {
        return currentRecipe;
    }

    public int getRemainingDuration() {
        return currentRecipe != null ? currentRecipe.value().duration() - duration : 0;
    }

    public ItemStack getProcessingItem() {
        return getItemHandler().getStackInSlot(0);
    }

    public int getDuration() {
        return duration;
    }

    @Override
    protected void onItemsChanged(int slot) {
        super.onItemsChanged(slot);

        this.nextRecipe = getRecipeForCache(getItemHandler().getStackInSlot(0));
        setStage();
    }

    @Override
    protected void onLaserDistancesChanged(Direction direction, int prevDistance) {
        super.onLaserDistancesChanged(direction, prevDistance);

        this.nextRecipe = getRecipeForCache(getItemHandler().getStackInSlot(0));
    }

    public void setStage() {
        float i = (float) getItemHandler().getStackInSlot(0).getCount() / getItemHandler().getSlotLimit(0);
        int stage = (int) (i * 8);
        level.setBlockAndUpdate(worldPosition, getBlockState()
                .setValue(AquaticCatalystBlock.STAGE, stage));
    }

    public RecipeHolder<AquaticCatalystChannelingRecipe> getRecipeForCache(ItemStack stack) {
        return level.getRecipeManager()
                .getRecipeFor(AquaticCatalystChannelingRecipe.Type.INSTANCE, new SingleRecipeInput(stack), level)
                .orElse(null);
    }

    @Override
    public Set<Direction> getLaserInputs() {
        return Set.of();
    }

    @Override
    public Set<Direction> getLaserOutputs() {
        if (isActive()) {
            return Set.of(getBlockState().getValue(BlockStateProperties.FACING).getOpposite());
        }
        return Collections.emptySet();
    }

    @Override
    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return SidedCapUtils.allInsert(0);
    }

    @Override
    protected void loadData(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadData(tag, provider);

        this.duration = tag.getInt("duration");
        this.currentRecipeId = ResourceLocation.parse(tag.getString("current_recipe"));
        this.nextRecipeId = ResourceLocation.parse(tag.getString("next_recipe"));
    }

    @Override
    public void onLoad() {
        super.onLoad();

        this.currentRecipe = loadRecipe(currentRecipeId);
        this.nextRecipe = loadRecipe(nextRecipeId);
    }

    private RecipeHolder<AquaticCatalystChannelingRecipe> loadRecipe(ResourceLocation location) {
        return (RecipeHolder<AquaticCatalystChannelingRecipe>) level.getRecipeManager().byKey(location).orElse(null);
    }

    @Override
    protected void saveData(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveData(tag, provider);

        tag.putInt("duration", duration);
        if (currentRecipe != null) tag.putString("current_recipe", currentRecipe.toString());
        if (nextRecipe != null) tag.putString("next_recipe", nextRecipe.toString());
    }
}
