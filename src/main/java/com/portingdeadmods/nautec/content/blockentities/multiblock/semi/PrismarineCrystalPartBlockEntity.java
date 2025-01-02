package com.portingdeadmods.nautec.content.blockentities.multiblock.semi;

import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.content.blocks.multiblock.semi.PrismarineCrystalPartBlock;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

public class PrismarineCrystalPartBlockEntity extends LaserBlockEntity {
    public PrismarineCrystalPartBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.PRISMARINE_CRYSTAL_PART.get(), blockPos, blockState);
    }

    @Override
    public Set<Direction> getLaserInputs() {
        return ObjectSet.of();
    }

    @Override
    public Set<Direction> getLaserOutputs() {
        if (getBlockState().getValue(PrismarineCrystalPartBlock.INDEX) == 0) {
            return ObjectSet.of(
                    Direction.UP
            );
        } else if (getBlockState().getValue(PrismarineCrystalPartBlock.INDEX) == 5) {
            return ObjectSet.of(
                    Direction.DOWN
            );
        }
        return ObjectSet.of();
    }

    @Override
    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return Map.of();
    }

    @Override
    public void commonTick() {
        super.commonTick();
        PrismarineCrystalBlockEntity crystalBE = getCrystalBE();

        if (crystalBE != null) {
            if (canTransmitPower()) {
                transmitPower(crystalBE.getPower());
            }
            setPurity(3);
        }
    }

    @Override
    protected void checkConnections() {
        for (Direction direction : getLaserOutputs()) {
            int maxLaserDistance = getMaxLaserDistance();
            for (int i = 1; i < maxLaserDistance; i++) {
                BlockPos pos = worldPosition.relative(direction, i);
                BlockState state = level.getBlockState(pos);

                if (level.getBlockEntity(pos) instanceof LaserBlockEntity laserBlockEntity) {
                    if (laserBlockEntity.getLaserInputs().contains(direction.getOpposite())) {
                        laserDistances.put(direction, i);
                        break;
                    }
                }

                if (!state.canBeReplaced() || i == maxLaserDistance -1) {
                    laserDistances.put(direction, i);
                    break;
                }
            }
        }

    }

    public PrismarineCrystalBlockEntity getCrystalBE() {
        if (level.getBlockEntity(getCrystalPos()) instanceof PrismarineCrystalBlockEntity be)
            return be;
        return null;
    }

    public BlockPos getCrystalPos() {
        int i = getBlockState().getValue(PrismarineCrystalPartBlock.INDEX);
        return worldPosition.above(i - 2);
    }

    private boolean canTransmitPower(){
        int i = getBlockState().getValue(PrismarineCrystalPartBlock.INDEX);
        return i == 0 || i == 5;
    }
}
