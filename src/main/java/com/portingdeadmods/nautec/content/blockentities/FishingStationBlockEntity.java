package com.portingdeadmods.nautec.content.blockentities;

import com.portingdeadmods.nautec.NTConfig;
import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.content.menus.FishingStationMenu;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class FishingStationBlockEntity extends LaserBlockEntity implements MenuProvider {
    private boolean running;
    private int progress;
    private boolean itemsFull;

    private float independentAngle;
    private float chasingVelocity;
    private int speed;

    public FishingStationBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.FISHING_STATION.get(), blockPos, blockState);
        addItemHandler(3 * 5, (slot, stack) -> false);
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

    private boolean canRun() {
        BlockPos start = worldPosition.offset(-NTConfig.fisherRadius, -NTConfig.fisherDepth, -NTConfig.fisherRadius);
        BlockPos end = worldPosition.offset(NTConfig.fisherRadius, -1, NTConfig.fisherRadius);
        for (BlockPos pos : BlockPos.betweenClosed(start, end)) {
            if (!level.getBlockState(pos).is(Blocks.WATER)) {
                return false;
            }
        }
        return !itemsFull;
    }

    @Override
    public void commonTick() {
        super.commonTick();

        if (getPower() >= NTConfig.fisherLaserLevel) {
            if (progress >= NTConfig.fisherRunDuration) {
                progress = 0;
                if (!level.isClientSide()) {
                    List<ItemStack> items = spawnLoot();
                    itemsLoop:
                    for (ItemStack stack : items) {
                        for (int i = 0; i < getItemHandler().getSlots(); i++) {
                            ItemStack itemStack = forceInsertItem(i, stack.copy(), false);
                            if (itemStack.isEmpty()) {
                                itemsFull = false;
                                continue itemsLoop;
                            }
                        }
                        itemsFull = true;

                    }
                }
            }

            if (isRunning()) {
                progress++;
            }

            // Check every 5 seconds
            if (level.getGameTime() % 100 == 0) {
                running = canRun();
            }
        } else {
            running = false;
        }

        float actualSpeed = getSpeed();
        chasingVelocity += ((actualSpeed * 10 / 3f) - chasingVelocity) * .25f;
        independentAngle += chasingVelocity;

        if (running) {
            this.speed = 100;
        } else {
            this.speed = 0;
        }
    }

    public int getSpeed() {
        return speed;
    }

    public float getIndependentAngle(float partialTicks) {
        return (independentAngle + partialTicks * chasingVelocity) / 360;
    }

    private List<ItemStack> spawnLoot() {
        LootTable lootTable = level.getServer().reloadableRegistries().getLootTable(BuiltInLootTables.FISHING);
        return lootTable.getRandomItems(new LootParams.Builder((ServerLevel) level)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(worldPosition))
                .withParameter(LootContextParams.TOOL, Items.FISHING_ROD.getDefaultInstance()).create(LootContextParamSets.FISHING));
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    protected void loadData(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadData(tag, provider);
        this.independentAngle = tag.getFloat("angle");
        this.progress = tag.getInt("progress");
        this.itemsFull = tag.getBoolean("itemsFull");
    }

    @Override
    protected void saveData(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveData(tag, provider);
        tag.putFloat("angle", this.independentAngle);
        tag.putInt("progress", this.progress);
        tag.putBoolean("itemsFull", this.itemsFull);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Fishing Station");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new FishingStationMenu(containerId, playerInventory, this);
    }
}
