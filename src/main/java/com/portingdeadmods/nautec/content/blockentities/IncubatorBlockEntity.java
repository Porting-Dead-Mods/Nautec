package com.portingdeadmods.nautec.content.blockentities;

import com.portingdeadmods.nautec.NTConfig;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.content.menus.IncubatorMenu;
import com.portingdeadmods.nautec.content.recipes.BacteriaIncubationRecipe;
import com.portingdeadmods.nautec.content.recipes.inputs.BacteriaRecipeInput;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import com.portingdeadmods.nautec.utils.RNGUtils;
import com.portingdeadmods.nautec.utils.ranges.IntRange;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

public class IncubatorBlockEntity extends LaserBlockEntity implements MenuProvider {
    public static final int MAX_PROGRESS = 100;
    public static final int POWER_USAGE = 20;

    private BacteriaIncubationRecipe recipe;
    private int progress;

    public IncubatorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.INCUBATOR.get(), blockPos, blockState);
        addItemHandler(1, 1);
        addBacteriaStorage(1);
    }

    @Override
    protected void onItemsChanged(int slot) {
        super.onItemsChanged(slot);
        checkRecipe();
    }

    @Override
    public void onBacteriaChanged(int slot) {
        super.onBacteriaChanged(slot);
        checkRecipe();
    }

    private void checkRecipe() {
        ItemStack stack = getItemHandler().getStackInSlot(0);
        BacteriaInstance instance = getBacteriaStorage().getBacteria(0);
        this.recipe = instance.getSize() < NTConfig.bacteriaColonySizeCap
                ? level.getRecipeManager().getRecipeFor(BacteriaIncubationRecipe.TYPE, new BacteriaRecipeInput(instance, stack), level).map(RecipeHolder::value).orElse(null)
                : null;
    }

    @Override
    public void commonTick() {
        super.commonTick();

        if (this.recipe != null) {
            if (getPower() >= POWER_USAGE) {
                if (progress >= MAX_PROGRESS) {
                    IntRange growth = recipe.growth();
                    if (level.getRandom().nextFloat() < recipe.consumeChance()) {
                        getItemHandler().extractItem(0, 1, false);
                    }

                    BacteriaInstance bacteria = getBacteriaStorage().getBacteria(0);
                    bacteria.grow(RNGUtils.uniformRandInt(growth));
                    getBacteriaStorage().onBacteriaChanged(0);

                    progress = 0;
                } else {
                    progress++;
                }
            }
        } else {
            progress = 0;
        }
    }

    public int getProgress() {
        return progress;
    }

    @Override
    public Set<Direction> getLaserInputs() {
        return ObjectSet.of(Direction.UP, Direction.DOWN);
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
    public Component getDisplayName() {
        return Component.literal("Incubator");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new IncubatorMenu(containerId, playerInventory, this);
    }

    @Override
    protected void loadData(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadData(tag, provider);
        this.progress = tag.getInt("progress");
    }

    @Override
    protected void saveData(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveData(tag, provider);
        tag.putInt("progress", this.progress);
    }
}
