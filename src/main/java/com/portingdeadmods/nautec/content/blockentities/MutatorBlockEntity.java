package com.portingdeadmods.nautec.content.blockentities;

import com.portingdeadmods.nautec.NTConfig;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.content.items.PetriDishItem;
import com.portingdeadmods.nautec.content.menus.MutatorMenu;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.data.components.ComponentBacteriaStorage;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import com.portingdeadmods.nautec.utils.BacteriaHelper;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

public class MutatorBlockEntity extends LaserBlockEntity implements MenuProvider {
    public static final int MAX_PROGRESS = NTConfig.mutatorCraftingSpeed;
    public static final int POWER_USAGE = NTConfig.mutatorPowerUsage;

    private boolean hasRecipe;
    private int progress;

    public MutatorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.MUTATOR.get(), blockPos, blockState);
        addItemHandler(3, (slot) -> {
            if (slot == 0 || slot == 1) {
                return 1;
            } else {
                return 64;
            }
        }, (slot, stack) -> (slot == 0 && stack.getItem() instanceof PetriDishItem) || slot == 1 || slot == 2);
    }

    @Override
    protected void onItemsChanged(int slot) {
        super.onItemsChanged(slot);

        ItemStack stack = getItemHandler().getStackInSlot(0);
        ItemStack resultStack = getItemHandler().getStackInSlot(1);
        this.hasRecipe = stack.getCapability(NTCapabilities.BacteriaStorage.ITEM) != null
                && resultStack.isEmpty();
    }

    @Override
    public void commonTick() {
        super.commonTick();

        if (hasRecipe) {
            if (getPower() >= POWER_USAGE) {
                if (progress >= MAX_PROGRESS) {
                    ItemStack extracted = getItemHandler().extractItem(0, 1, false);

                    ItemStack result = extracted.copy();

                    BacteriaInstance instance = result.get(NTDataComponents.BACTERIA).bacteriaInstance();
                    BacteriaHelper.rollBacteriaStats(instance);

                    result.set(NTDataComponents.BACTERIA, new ComponentBacteriaStorage(instance));

                    forceInsertItem(1, result, false);

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
        return Component.literal("Mutator");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new MutatorMenu(containerId, playerInventory, this);
    }
}
