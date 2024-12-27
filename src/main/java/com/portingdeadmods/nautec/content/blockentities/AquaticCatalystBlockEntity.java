package com.portingdeadmods.nautec.content.blockentities;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.content.blocks.AquaticCatalystBlock;
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
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

import static com.portingdeadmods.nautec.content.blocks.AquaticCatalystBlock.ACTIVE;
import static com.portingdeadmods.nautec.content.blocks.AquaticCatalystBlock.STAGE;

// TODO: actually consume item before starting to generate power
public class AquaticCatalystBlockEntity extends LaserBlockEntity {
    private RecipeHolder<AquaticCatalystChannelingRecipe> recipe;
    private int amount;
    private int duration;

    public AquaticCatalystBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.AQUATIC_CATALYST.get(), blockPos, blockState);
        addItemHandler(1, (slot, stack) -> hasRecipe(stack));
    }

    private boolean hasRecipe(ItemStack stack) {
        return level.getRecipeManager().getRecipeFor(AquaticCatalystChannelingRecipe.Type.INSTANCE, new SingleRecipeInput(stack), level).isPresent();
    }

    @Override
    public ObjectSet<Direction> getLaserInputs() {
        return ObjectSet.of();
    }

    public int getDuration() {
        return duration;
    }

    public int getRemainingDuration() {
        return recipe != null ? recipe.value().duration() - duration : 0;
    }

    public ItemStack getProcessingItem() {
        return getItemHandler().getStackInSlot(0);
    }

    public Optional<AquaticCatalystChannelingRecipe> getCurrentRecipe() {
        return Optional.ofNullable(this.recipe).map(RecipeHolder::value);
    }

    @Override
    protected void onItemsChanged(int slot) {
        super.onItemsChanged(slot);

        BlockState state = getBlockState().setValue(ACTIVE, !getItemHandler().getStackInSlot(0).isEmpty());

        float i = (float) getItemHandler().getStackInSlot(0).getCount() / getItemHandler().getSlotLimit(0);
        int stage = (int) (i * 6);
        Nautec.LOGGER.debug("ratio: {}, stage: {}", i, stage);
        level.setBlockAndUpdate(worldPosition, state.setValue(STAGE, stage));
    }

    @Override
    public ObjectSet<Direction> getLaserOutputs() {
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

        ItemStack stack = getItemHandler().getStackInSlot(0);
        if (!stack.isEmpty() && isActive()) {
            // caching
            if (recipe == null) {
                Optional<RecipeHolder<AquaticCatalystChannelingRecipe>> recipe1 = level.getRecipeManager().getRecipeFor(AquaticCatalystChannelingRecipe.Type.INSTANCE, new SingleRecipeInput(stack), level);
                if (recipe1.isPresent()) {
                    this.recipe = recipe1.get();
                    getItemStackHandler().setStackInSlot(0, stack.copyWithCount(stack.getCount() - 1));
                } else {
                    return;
                }
            }

            Direction direction = getBlockState().getValue(BlockStateProperties.FACING);
            if (getLaserDistances().getInt(direction.getOpposite()) > 0) {
                if (duration >= recipe.value().duration()) {
                    duration = 0;
                } else {
                    amount = recipe.value().powerAmount() / recipe.value().duration();
                    transmitPower(amount);
                    setPurity(recipe.value().purity());
                    duration++;
                }
            }
        } else {
            this.recipe = null;
        }
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

    public boolean isActive() {
        return getBlockState().getValue(ACTIVE);
    }

    @Override
    protected void loadData(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadData(tag, provider);
        this.duration = tag.getInt("duration");

        Optional<RecipeHolder<?>> recipe1 = level.getRecipeManager().byKey(ResourceLocation.parse(tag.getString("recipe")));
        recipe1.ifPresent(recipeHolder -> this.recipe = (RecipeHolder<AquaticCatalystChannelingRecipe>) recipeHolder);
    }

    @Override
    protected void saveData(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveData(tag, provider);
        tag.putInt("duration", this.duration);
        tag.putString("recipe", this.recipe != null ? this.recipe.toString() : "");
    }
}
