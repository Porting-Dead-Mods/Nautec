package com.portingdeadmods.nautec.content.blockentities.multiblock.controller;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.api.augments.AugmentType;
import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.api.blockentities.multiblock.MultiblockEntity;
import com.portingdeadmods.nautec.api.multiblocks.Multiblock;
import com.portingdeadmods.nautec.api.multiblocks.MultiblockData;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.client.screen.AugmentationStationScreen;
import com.portingdeadmods.nautec.content.blockentities.multiblock.part.AugmentationStationExtensionBlockEntity;
import com.portingdeadmods.nautec.content.recipes.AugmentationRecipe;
import com.portingdeadmods.nautec.content.recipes.inputs.AugmentationRecipeInput;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import com.portingdeadmods.nautec.utils.AugmentHelper;
import com.portingdeadmods.nautec.utils.PlayerUtils;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class AugmentationStationBlockEntity extends ContainerBlockEntity implements MultiblockEntity {
    private MultiblockData multiblockData;
    private UUID playerUUID;
    private int playerOpenMenuInterval;

    private final Map<BlockPos, ItemStack> augmentItems;

    // Recipe dependent
    private boolean isRunning;
    private int duration;
    private Player player;
    private AugmentationRecipe recipe;
    private AugmentSlot slot;

    private double prevSpeed;
    private double prevJumpStrength;

    public AugmentationStationBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.AUGMENTATION_STATION.get(), blockPos, blockState);
        this.multiblockData = MultiblockData.EMPTY;
        this.augmentItems = new HashMap<>();
        this.player = null;
    }

    public Map<BlockPos, ItemStack> getAugmentItems() {
        return augmentItems;
    }

    public void startAugmentation(Player player, AugmentSlot augmentSlot) {
        if (!isRunning) {
            for (BlockPos augmentationExtensionPos : this.augmentItems.keySet()) {
                ItemStack augmentationItem = this.augmentItems.get(augmentationExtensionPos);
                Optional<AugmentationRecipe> recipe = level.getRecipeManager()
                        .getRecipeFor(AugmentationRecipe.Type.INSTANCE, new AugmentationRecipeInput(augmentationItem, 100), level)
                        .map(RecipeHolder::value);

                if (recipe.isPresent()) {
                    AugmentationStationExtensionBlockEntity be = (AugmentationStationExtensionBlockEntity) level.getBlockEntity(augmentationExtensionPos);

                    if (level.isClientSide()) {
                        be.equipAugment();
                    }
                    AttributeMap attributes = player.getAttributes();
                    this.prevSpeed = attributes.getBaseValue(Attributes.MOVEMENT_SPEED);
                    this.prevJumpStrength = attributes.getBaseValue(Attributes.JUMP_STRENGTH);
                    attributes.getInstance(Attributes.MOVEMENT_SPEED).setBaseValue(0);
                    attributes.getInstance(Attributes.JUMP_STRENGTH).setBaseValue(0);

                    this.isRunning = true;
                    this.duration = 80;
                    this.player = player;
                    this.recipe = recipe.get();
                    this.slot = augmentSlot;

                    return;
                }
            }
        }
    }

    public void restorePlayerAttributes() {
        AttributeMap attributes = player.getAttributes();
        attributes.getInstance(Attributes.MOVEMENT_SPEED).setBaseValue(prevSpeed);
        attributes.getInstance(Attributes.JUMP_STRENGTH).setBaseValue(prevJumpStrength);
    }

    @Override
    public void commonTick() {
        super.commonTick();
        if (isFormed()) {
            if (!isRunning) {
                List<Player> players = level.getEntitiesOfClass(Player.class, new AABB(worldPosition.above()));
                if (players.isEmpty()) {
                    this.playerUUID = null;
                    return;
                }

                Player player = players.getFirst();
                UUID uuid = player.getUUID();

                if (playerUUID == null) {
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
            } else {
                if (duration > 0) {
                    duration--;

                    if (duration == 0) {
                        this.isRunning = false;

                        AugmentType<?> type = recipe.resultAugment();
                        Augment augment = type.create(slot);
                        augment.setPlayer(player);
                        AugmentHelper.setAugment(player, slot, augment);

                        restorePlayerAttributes();
                    }
                }
            }
        }
    }

    private @NotNull Boolean isFormed() {
        return getBlockState().getValue(Multiblock.FORMED);
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
