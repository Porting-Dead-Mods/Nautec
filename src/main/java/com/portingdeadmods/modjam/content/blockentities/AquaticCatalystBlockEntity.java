package com.portingdeadmods.modjam.content.blockentities;

import com.portingdeadmods.modjam.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.modjam.capabilities.IOActions;
import com.portingdeadmods.modjam.content.blocks.AquaticCatalystBlock;
import com.portingdeadmods.modjam.content.recipes.AquaticCatalystChannelingRecipe;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import com.portingdeadmods.modjam.utils.SidedCapUtils;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class AquaticCatalystBlockEntity extends LaserBlockEntity {
    private AquaticCatalystChannelingRecipe recipe;
    private int duration;

    public AquaticCatalystBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MJBlockEntityTypes.AQUATIC_CATALYST.get(), blockPos, blockState);
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
                Optional<AquaticCatalystChannelingRecipe> recipe1 = level.getRecipeManager().getRecipeFor(AquaticCatalystChannelingRecipe.Type.INSTANCE, new SingleRecipeInput(stack), level).map(RecipeHolder::value);
                if (recipe1.isPresent()) {
                    this.recipe = recipe1.get();
                } else {
                    return;
                }
            }

            if (duration >= recipe.duration()) {
                getItemStackHandler().setStackInSlot(0, stack.copyWithCount(stack.getCount() - 1));
                duration = 0;
            } else {
                transmitPower(recipe.powerAmount() / recipe.duration());
                duration++;
            }
        } else {
            this.recipe = null;
        }
    }

    @Override
    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        if (capability == Capabilities.ItemHandler.BLOCK) {
            boolean coreActive = isActive();
            if (coreActive) {
                Direction activeDir = getBlockState().getValue(BlockStateProperties.FACING);
                Map<Direction, Pair<IOActions, int[]>> map = new Object2ObjectArrayMap<>();
                for (Direction direction : Direction.values()) {
                    map.put(direction, Pair.of(IOActions.INSERT, new int[]{0}));
                }
                map.remove(activeDir);
                return map;
            }
            return SidedCapUtils.allInsert(0);
        }
        return Map.of();
    }

    private @NotNull Boolean isActive() {
        return getBlockState().getValue(AquaticCatalystBlock.CORE_ACTIVE);
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
