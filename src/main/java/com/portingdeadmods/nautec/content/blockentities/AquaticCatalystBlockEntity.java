package com.portingdeadmods.nautec.content.blockentities;

import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.content.recipes.AquaticCatalystChannelingRecipe;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import com.portingdeadmods.nautec.utils.SidedCapUtils;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
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
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.portingdeadmods.nautec.content.blocks.AquaticCatalystBlock.ACTIVE;
import static com.portingdeadmods.nautec.content.blocks.AquaticCatalystBlock.STAGE;

// TODO: actually consume item before starting to generate power
public class AquaticCatalystBlockEntity extends LaserBlockEntity {
    // cache that uses the current item
    private RecipeHolder<AquaticCatalystChannelingRecipe> nextRecipeCache;
    // actually need to serialize this to know the amount of AP we need to produce
    private RecipeHolder<AquaticCatalystChannelingRecipe> currentRecipe;
    private int amount;
    private int duration;
    private boolean active;
    private boolean updateRecipe = true;

    public AquaticCatalystBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.AQUATIC_CATALYST.get(), blockPos, blockState);
        addItemHandler(1, (slot, stack) -> hasRecipe(stack));
    }

    private boolean hasRecipe(ItemStack stack) {
        return getRecipeForCache(stack) != null;
    }

    @Override
    public Set<Direction> getLaserInputs() {
        return ObjectSet.of();
    }

    public int getDuration() {
        return duration;
    }

    public int getRemainingDuration() {
        return nextRecipeCache != null ? nextRecipeCache.value().duration() - duration : 0;
    }

    public ItemStack getProcessingItem() {
        return getItemHandler().getStackInSlot(0);
    }

    public Optional<AquaticCatalystChannelingRecipe> getCurrentRecipe() {
        return Optional.ofNullable(this.nextRecipeCache).map(RecipeHolder::value);
    }

    @Override
    protected void onItemsChanged(int slot) {
        if (updateRecipe) {
            updateRecipe(slot);
        }
    }

    @Override
    protected void onLaserDistancesChanged(Direction direction, int prevDistance) {
        if (updateRecipe) {
            updateRecipe(0);
        }
    }

    private void updateRecipe(int slot) {
        ItemStackHandler itemStackHandler = getItemStackHandler();
        ItemStack stackInSlot1 = itemStackHandler.getStackInSlot(slot);
        RecipeHolder<AquaticCatalystChannelingRecipe> recipe1 = getRecipeForCache(stackInSlot1);
        if (recipe1 != null) {
            this.updateRecipe = false;
            itemStackHandler.extractItem(slot, 1, false);
            this.updateRecipe = true;
        }
        this.nextRecipeCache = recipe1;
        setActive(this.nextRecipeCache != null);
    }

    public void setActive(boolean active) {
        float i = (float) getItemHandler().getStackInSlot(0).getCount() / getItemHandler().getSlotLimit(0);
        int stage = (int) (i * 6);
        level.setBlockAndUpdate(worldPosition, getBlockState()
                .setValue(ACTIVE, active)
                .setValue(STAGE, active ? stage : 0));
        this.active = active;
    }

    public boolean isActive() {
        return getBlockState().getValue(ACTIVE) && this.active;
    }

    @Override
    public Set<Direction> getLaserOutputs() {
        Direction direction = getBlockState().getValue(BlockStateProperties.FACING);
        boolean coreActive = isActive();
        if (coreActive) {
            return ObjectSet.of(direction.getOpposite());
        }
        return ObjectSet.of();
    }

    @Override
    public void commonTick() {
        super.commonTick();

        if (isActive() && nextRecipeCache != null) {
            Direction direction = getBlockState().getValue(BlockStateProperties.FACING);
            if (getLaserDistances().getInt(direction.getOpposite()) > 0) {
                if (duration >= nextRecipeCache.value().duration()) {
                    duration = 0;
                    updateRecipe(0);
                    setActive(this.nextRecipeCache != null);
                } else {
                    amount = nextRecipeCache.value().powerAmount() / nextRecipeCache.value().duration();
                    transmitPower(amount);
                    setPurity(nextRecipeCache.value().purity());
                    duration++;
                }
            }
        }
    }

    private @Nullable RecipeHolder<AquaticCatalystChannelingRecipe> getRecipeForCache(ItemStack stack) {
        return level.getRecipeManager()
                .getRecipeFor(AquaticCatalystChannelingRecipe.Type.INSTANCE, new SingleRecipeInput(stack), level)
                .orElse(null);
    }

    public int getPower() {
        return amount;
    }

    @Override
    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        if (capability == Capabilities.ItemHandler.BLOCK) {
            return SidedCapUtils.allInsert(0);
        }
        return Map.of();
    }

    @Override
    protected void loadData(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadData(tag, provider);
        this.duration = tag.getInt("duration");
        this.active = tag.getBoolean("active");

        Optional<RecipeHolder<?>> recipe1 = level.getRecipeManager().byKey(ResourceLocation.parse(tag.getString("recipe")));
        recipe1.ifPresent(recipeHolder -> this.nextRecipeCache = (RecipeHolder<AquaticCatalystChannelingRecipe>) recipeHolder);
    }

    @Override
    protected void saveData(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveData(tag, provider);
        tag.putInt("duration", this.duration);
        tag.putBoolean("active", this.active);
        tag.putString("recipe", this.nextRecipeCache != null ? this.nextRecipeCache.toString() : "");
    }
}
