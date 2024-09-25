package com.portingdeadmods.nautec.content.blockentities.multiblock.semi;

import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class PrismarineCrystalPartBlockEntity extends LaserBlockEntity {
    private BlockPos crystalPos;

    public PrismarineCrystalPartBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.PRISMARINE_CRYSTAL_PART.get(), blockPos, blockState);
    }

    @Override
    public ObjectSet<Direction> getLaserInputs() {
        return ObjectSet.of();
    }

    @Override
    public ObjectSet<Direction> getLaserOutputs() {
        return ObjectSet.of(
                Direction.DOWN,
                Direction.UP
        );
    }

    @Override
    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return Map.of();
    }

    public void setCrystalPos(BlockPos crystalPos) {
        this.crystalPos = crystalPos;
    }

    public BlockPos getCrystalPos() {
        return crystalPos;
    }

    @Override
    protected void loadData(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadData(tag, provider);
        this.crystalPos = BlockPos.of(tag.getLong("crystalPos"));
    }

    @Override
    protected void saveData(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveData(tag, provider);
        tag.putLong("crystalPos", this.crystalPos.asLong());
    }
}
