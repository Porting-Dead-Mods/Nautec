package com.portingdeadmods.nautec.content.blockentities.multiblock.controller;

import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.api.blockentities.multiblock.MultiblockEntity;
import com.portingdeadmods.nautec.api.multiblocks.MultiblockData;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.capabilities.bacteria.IBacteriaStorage;
import com.portingdeadmods.nautec.content.menus.BioReactorMenu;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import com.portingdeadmods.nautec.utils.BacteriaHelper;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

public class BioReactorBlockEntity extends LaserBlockEntity implements MenuProvider, MultiblockEntity {
    private MultiblockData multiblockData;
    private int[] progress;

    public BioReactorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.BIO_REACTOR.get(), blockPos, blockState);
        addItemHandler(3);
        addBacteriaStorage(3);
        this.multiblockData = MultiblockData.EMPTY;
        this.progress = new int[3];
    }

    // TODO: Energy usage
    @Override
    public void commonTick() {
        super.commonTick();

        if (getPower() > 50) {
            IBacteriaStorage storage = getBacteriaStorage();
            for (int i = 0; i < this.progress.length; i++) {
                BacteriaInstance bacteria = storage.getBacteria(i);
                if (!bacteria.isEmpty()) {
                    if (this.progress[i] > 100) {
                        Bacteria bacteria1 = BacteriaHelper.getBacteria(level.registryAccess(), bacteria.getBacteria());
                        Bacteria.Resource resource = bacteria1.resource();

                        if (resource instanceof Bacteria.Resource.ItemResource(Item item)) {
                            getItemHandler().insertItem(i, item.getDefaultInstance(), false);
                        }
                        this.progress[i] = 0;
                    } else {
                        this.progress[i]++;
                    }
                }
            }
        }
    }

    @Override
    public Set<Direction> getLaserInputs() {
        return ObjectSet.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
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
    public @NotNull Component getDisplayName() {
        return Component.literal("Bio Reactor");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new BioReactorMenu(containerId, playerInventory, this);
    }

    @Override
    public MultiblockData getMultiblockData() {
        return this.multiblockData;
    }

    @Override
    public void setMultiblockData(MultiblockData data) {
        this.multiblockData = data;
    }

    @Override
    protected void saveData(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveData(tag, provider);
        tag.put("multiblockData", saveMBData());
        tag.putIntArray("progress", progress);
    }

    @Override
    protected void loadData(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadData(tag, provider);
        this.multiblockData = loadMBData(tag.getCompound("multiblockData"));
        int[] progress = tag.getIntArray("progress");
        for (int i = 0; i < this.progress.length; i++) {
            if (i < progress.length) {
                this.progress[i] = progress[i];
            }
        }
    }

    public int getProgress(int i) {
        return progress[i];
    }
}
