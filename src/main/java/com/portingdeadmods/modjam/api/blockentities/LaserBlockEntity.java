package com.portingdeadmods.modjam.api.blockentities;

import com.portingdeadmods.modjam.api.blocks.blockentities.LaserBlock;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Set;

public abstract class LaserBlockEntity extends ContainerBlockEntity {
    private static final int MAX_DISTANCE = 16;

    private float clientLaserTime;
    private final Object2IntMap<Direction> laserDistances;
    private int itemTransformTime;
    public AABB box;

    public LaserBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        this.laserDistances = new Object2IntOpenHashMap<>();
        this.box = new AABB(blockPos);
    }

    public abstract ObjectSet<Direction> getLaserInputs();

    public abstract ObjectSet<Direction> getLaserOutputs();

    public Object2IntMap<Direction> getLaserDistances() {
        return laserDistances;
    }

    public int getLaserAnimTimeDuration() {
        return 80;
    }

    public float getClientLaserTime() {
        return clientLaserTime;
    }

    public float getLaserScale(float partialTick) {
        return (this.clientLaserTime + partialTick) / (float) this.getLaserAnimTimeDuration();
    }

    @Override
    public void commonTick() {
        super.commonTick();
        if (level.isClientSide()) {
            if (clientLaserTime < getLaserAnimTimeDuration()) {
                this.clientLaserTime += 0.5f;
            } else {
                this.clientLaserTime = 0;
            }
        }

        if (level.getGameTime() % 10 == 0) {
            checkConnections();
        }
        damageLiving();

        transmitPower();
    }

    private void transmitPower() {

    }

    private void damageLiving() {
        for (Direction direction : getLaserOutputs()) {
            int distance = this.laserDistances.getInt(direction);
            BlockPos pos = worldPosition.above().relative(direction, distance - 1);
            Vec3 start = worldPosition.getCenter().subtract(0.1, 0, 0.1);
            Vec3 end = pos.getCenter().add(0.1, 0, 0.1);
            AABB box = new AABB(start, end);
            this.box = box;
            List<LivingEntity> livingEntities = level.getEntitiesOfClass(LivingEntity.class, box);
            for (LivingEntity livingEntity : livingEntities) {
                livingEntity.hurt(level.damageSources().inFire(), 3);
            }
        }
    }

    private void checkConnections() {
        for (Direction direction : getLaserOutputs()) {
            for (int i = 1; i < MAX_DISTANCE; i++) {
                BlockPos pos = worldPosition.relative(direction, i);
                BlockState state = level.getBlockState(pos);

                if (state.getBlock() instanceof LaserBlock) {
                    laserDistances.put(direction, i);
                    break;
                }

                if (!state.canBeReplaced() || i == MAX_DISTANCE - 1) {
                    laserDistances.put(direction, 0);
                    break;
                }
            }
        }
    }
}
