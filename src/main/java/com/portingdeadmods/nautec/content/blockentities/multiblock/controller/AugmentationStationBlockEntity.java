package com.portingdeadmods.nautec.content.blockentities.multiblock.controller;

import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.api.blockentities.multiblock.MultiblockEntity;
import com.portingdeadmods.nautec.api.multiblocks.MultiblockData;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.client.screen.AugmentationStationScreen;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import com.portingdeadmods.nautec.utils.PlayerUtils;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AugmentationStationBlockEntity extends ContainerBlockEntity implements MultiblockEntity {
    private MultiblockData multiblockData;
    private UUID playerUUID;
    private int playerOpenMenuInterval;

    public AugmentationStationBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.AUGMENTATION_STATION.get(), blockPos, blockState);
        this.multiblockData = MultiblockData.EMPTY;
    }

    @Override
    public void commonTick() {
        super.commonTick();
        List<Player> players = level.getEntitiesOfClass(Player.class, new AABB(worldPosition.above()));
        if (players.isEmpty()) {
            this.playerUUID = null;
            return;
        }

        Player player = players.getFirst();
        UUID uuid = player.getUUID();

        if (playerUUID == null){
            this.playerUUID = uuid;
            this.playerOpenMenuInterval = 10;
        }

        if (playerUUID.equals(uuid)) {
            if (playerOpenMenuInterval > 0) {
                playerOpenMenuInterval--;
                if (playerOpenMenuInterval == 0) {
                    PlayerUtils.openScreen(player, new AugmentationStationScreen(this, player, Component.literal("Augmentation Station")));
                }
            }
        } else {
            this.playerUUID = null;
        }
    }

    @Override
    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return Map.of();
    }

    @Override
    public MultiblockData getMultiblockData() {
        return multiblockData;
    }

    @Override
    public void setMultiblockData(MultiblockData data) {
        this.multiblockData = data;
    }

    @Override
    protected void loadData(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadData(tag, provider);
        this.multiblockData = loadMBData(tag.getCompound("multiblockData"));
    }

    @Override
    protected void saveData(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveData(tag, provider);
        tag.put("multiblockData", saveMBData());
    }
}
