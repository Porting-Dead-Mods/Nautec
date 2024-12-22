package com.portingdeadmods.nautec.content.blockentities;

import com.portingdeadmods.nautec.NTConfig;
import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
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

public class FishingStationBlockEntity extends LaserBlockEntity {
    private boolean running;
    private int counter;
    public FishingStationBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.FISHING_STATION.get(), blockPos, blockState);
    }

    @Override
    public ObjectSet<Direction> getLaserInputs() {
        return ObjectSet.of(Direction.UP, Direction.DOWN);
    }

    @Override
    public ObjectSet<Direction> getLaserOutputs() {
        return ObjectSet.of();
    }

    @Override
    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return Map.of();
    }

    private boolean canRun(){
        BlockPos start = worldPosition.offset(-NTConfig.fisherRadius,-NTConfig.fisherDepth,-NTConfig.fisherRadius);
        BlockPos end = worldPosition.offset(NTConfig.fisherRadius,-1,NTConfig.fisherRadius);
        for (BlockPos pos : BlockPos.betweenClosed(start,end)) {
            if (!level.getBlockState(pos).is(Blocks.WATER)) return false;
        }
        return true;
    }

    @Override
    public void commonTick() {
        super.commonTick();
        if (level instanceof ServerLevel && getPower() >= NTConfig.fisherLaserLevel){
            if (running) {
                if (counter >= NTConfig.fisherRunDuration) {
                    counter = 0;
                    running = false;
                    spawnLoot();
                }
                counter++;
            } else {
                running = canRun();
            }
        }
    }

    private void spawnLoot() {
        LootTable lootTable = level.getServer().reloadableRegistries().getLootTable(BuiltInLootTables.FISHING);
        List<ItemStack> loot = lootTable.getRandomItems(new LootParams.Builder((ServerLevel) level).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(worldPosition)).withParameter(LootContextParams.TOOL, Items.FISHING_ROD.getDefaultInstance()).create(LootContextParamSets.FISHING));
        for (ItemStack stack : loot) {
            Vec3 pos = getItemSpawnPos();
            ItemEntity itemEntity = new ItemEntity(level, pos.x, pos.y, pos.z, stack);
            level.addFreshEntity(itemEntity);
        }
    }
    private Vec3 getItemSpawnPos(){
        return new Vec3(worldPosition.getX() + 0.5, worldPosition.getY() + 1.5, worldPosition.getZ() + 0.5);
    }}
