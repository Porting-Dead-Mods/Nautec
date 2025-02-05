package com.portingdeadmods.nautec.content.blockentities;

import com.portingdeadmods.nautec.NTConfig;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.content.items.PetriDishItem;
import com.portingdeadmods.nautec.content.menus.MutatorMenu;
import com.portingdeadmods.nautec.content.recipes.BacteriaMutationRecipe;
import com.portingdeadmods.nautec.content.recipes.inputs.BacteriaMutationRecipeInput;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.data.components.ComponentBacteriaStorage;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import com.portingdeadmods.nautec.utils.BacteriaHelper;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

public class MutatorBlockEntity extends LaserBlockEntity implements MenuProvider {
    public static final int MAX_PROGRESS = NTConfig.mutatorCraftingSpeed;
    public static final int POWER_USAGE = NTConfig.mutatorPowerUsage;

    private BacteriaMutationRecipe recipe;
    private int progress;

    public MutatorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.MUTATOR.get(), blockPos, blockState);
        addBacteriaStorage(2);
        addItemHandler(1);
    }

    @Override
    protected void onItemsChanged(int slot) {
        super.onItemsChanged(slot);

        checkRecipe();
    }

    private void checkRecipe() {
        ItemStack catalyst = getItemHandler().getStackInSlot(0);
        BacteriaInstance inputBacteria = getBacteriaStorage().getBacteria(0);
        BacteriaInstance resultBacteria = getBacteriaStorage().getBacteria(1);
        BacteriaMutationRecipe recipe1 = level.getRecipeManager().getRecipeFor(BacteriaMutationRecipe.TYPE, new BacteriaMutationRecipeInput(inputBacteria, catalyst), level).map(RecipeHolder::value).orElse(null);
        this.recipe = (recipe1 != null && (resultBacteria.isEmpty() || resultBacteria.is(recipe1.resultBacteria()))) ? recipe1 : null;
    }

    @Override
    public void onBacteriaChanged(int slot) {
        super.onBacteriaChanged(slot);

        checkRecipe();
    }

    @Override
    public void commonTick() {
        super.commonTick();

        if (this.recipe != null) {
            if (getPower() >= POWER_USAGE) {
                if (progress >= MAX_PROGRESS) {
                    ResourceKey<Bacteria> bacteria = recipe.resultBacteria();
                    BacteriaInstance inputBacteria = getBacteriaStorage().getBacteria(0);
                    getBacteriaStorage().extractBacteria(0, inputBacteria.getAmount(), false);

                    getBacteriaStorage().insertBacteria(1, new BacteriaInstance(bacteria, level.registryAccess(), inputBacteria.getAmount()), false);

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
