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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class BreakerBlockEntity extends LaserBlockEntity {
    private boolean running;
    private int counter;
    public BreakerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.BREAKER_BLOCK.get(), blockPos, blockState);
    }

    @Override
    public ObjectSet<Direction> getLaserInputs() {
        return ObjectSet.of(Direction.UP, Direction.DOWN, Direction.EAST, Direction.NORTH,Direction.WEST, Direction.SOUTH);
    }

    @Override
    public ObjectSet<Direction> getLaserOutputs() {
        return ObjectSet.of();
    }

    @Override
    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return Map.of();
    }

    @Override
    public void commonTick() {
        super.commonTick();
        if (level instanceof ServerLevel && getPower() >= 1){
            if (running) {
                if (counter >= 20) {
                    counter = 0;
                    running = false;
                    spawnLoot();
                }
                counter++;
            } else running = true;
        }
    }

    private void spawnLoot() {
        BlockState block = level.getBlockState(worldPosition.below());
        List<ItemStack> stacks = block.getDrops(new LootParams.Builder((ServerLevel) level).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(worldPosition)).withParameter(LootContextParams.TOOL, Items.DIAMOND_PICKAXE.getDefaultInstance()));
        if (!stacks.isEmpty()) {
            level.removeBlock(worldPosition.below(), false);
            for (ItemStack stack : stacks) {
                Vec3 pos = getItemSpawnPos();
                ItemEntity itemEntity = new ItemEntity(level, pos.x, pos.y, pos.z, stack);
                level.addFreshEntity(itemEntity);
            }
        }

    }
    private Vec3 getItemSpawnPos(){
        return new Vec3(worldPosition.getX() + 0.5, worldPosition.getY() + 1.5, worldPosition.getZ() + 0.5);
    }
}
