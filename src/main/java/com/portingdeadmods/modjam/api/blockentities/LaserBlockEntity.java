package com.portingdeadmods.modjam.api.blockentities;

import com.portingdeadmods.modjam.content.recipes.ItemTransformationRecipe;
import com.portingdeadmods.modjam.utils.ParticlesUtils;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class LaserBlockEntity extends ContainerBlockEntity {
    protected final Object2IntMap<Direction> laserDistances;
    private final Object2IntMap<ItemEntity> activeTransformations;

    private int powerToTransfer;
    private int power;

    private float clientLaserTime;

    public LaserBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        this.laserDistances = new Object2IntOpenHashMap<>();
        this.activeTransformations = new Object2IntArrayMap<>();
    }

    public abstract ObjectSet<Direction> getLaserInputs();

    public abstract ObjectSet<Direction> getLaserOutputs();

    public boolean shouldRender(Direction direction) {
        return getLaserOutputs().contains(direction) && (power > 0 || powerToTransfer > 0);
    }

    public Object2IntMap<Direction> getLaserDistances() {
        return laserDistances;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public void transmitPower(int amount) {
        this.powerToTransfer = amount;
    }

    public void receivePower(int amount, Direction direction, BlockPos originPos) {
        setPower(amount);
    }

    public int getMaxLaserDistance() {
        return 16;
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

        if (level.getGameTime() % checkConnectionsInterval() == 0) {
            checkConnections();
        }

        for (Direction direction : getLaserOutputs()) {
            int distance = this.laserDistances.getInt(direction);
            if (distance > 0) {
                AABB box = createLaserBeamAABB(direction, distance);

                damageLivingEntities(box);

                processItemCrafting(box);

                BlockPos targetPos = worldPosition.relative(direction, distance);
                if (level.getBlockEntity(targetPos) instanceof LaserBlockEntity laserBE) {
                    laserBE.receivePower(powerToTransfer, direction, worldPosition);
                }
            }
        }
    }

    protected int checkConnectionsInterval() {
        return 10;
    }

    private void damageLivingEntities(AABB box) {
        List<LivingEntity> livingEntities = level.getEntitiesOfClass(LivingEntity.class, box);
        for (LivingEntity livingEntity : livingEntities) {
            livingEntity.hurt(level.damageSources().inFire(), 3);
        }
    }

    private Optional<ItemTransformationRecipe> getCurrentRecipe(ItemStack itemStack) {
        SingleRecipeInput recipeInput = new SingleRecipeInput(itemStack);
        return this.level.getRecipeManager().getRecipeFor(ItemTransformationRecipe.Type.INSTANCE, recipeInput, level).map(RecipeHolder::value);
    }

    private void processItemCrafting(AABB box) {
        // Get all item entities within the box
        List<ItemEntity> itemEntities = level.getEntitiesOfClass(ItemEntity.class, box);

        for (ItemEntity itemEntity : itemEntities) {
            if (!activeTransformations.containsKey(itemEntity)) {
                Optional<ItemTransformationRecipe> optionalRecipe = getCurrentRecipe(itemEntity.getItem());
                if (optionalRecipe.isPresent()) {
                    activeTransformations.put(itemEntity, 0);
                }
            }
        }

        ObjectIterator<Object2IntMap.Entry<ItemEntity>> iterator = activeTransformations.object2IntEntrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ItemEntity, Integer> entry = iterator.next();
            ItemEntity cookingItem = entry.getKey();
            int cookTime = entry.getValue();

            if (!cookingItem.isAlive()) {
                iterator.remove();
                continue;
            }

            if (cookTime >= 40) {
                Optional<ItemTransformationRecipe> optionalRecipe = getCurrentRecipe(cookingItem.getItem());
                if (optionalRecipe.isPresent()) {
                    ItemStack resultStack = optionalRecipe.get().getResultItem(null).copy();
                    resultStack.setCount(cookingItem.getItem().getCount());

                    ItemEntity resultEntity = new ItemEntity(level, cookingItem.getX(), cookingItem.getY(), cookingItem.getZ(), resultStack);
                    level.addFreshEntity(resultEntity);

                    cookingItem.discard();
                    iterator.remove();
                }
            } else {
                activeTransformations.put(cookingItem, cookTime + 1);
                ParticlesUtils.spawnParticles(cookingItem, level, ParticleTypes.END_ROD);
            }
        }
    }

    private @NotNull AABB createLaserBeamAABB(Direction direction, int distance) {
        BlockPos pos = worldPosition.relative(direction, distance - 1);

        Vec3 start = worldPosition.relative(direction).getCenter();
        if (direction == Direction.UP || direction == Direction.DOWN) {
            start = start.subtract(0.2, 0, 0.2);
        } else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            start = start.subtract(0.2, 0.2, 0);
        } else if (direction == Direction.EAST || direction == Direction.WEST) {
            start = start.subtract(0, 0.2, 0.2);
        }

        Vec3 end = pos.getCenter().add(0.1, 0, 0.1);
        if (direction == Direction.UP || direction == Direction.DOWN) {
            end = end.add(0.2, 0, 0.2);
        } else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            end = end.add(0.2, 0.2, 0);
        } else if (direction == Direction.EAST || direction == Direction.WEST) {
            end = end.add(0, 0.2, 0.2);
        }

        return new AABB(start, end);
    }

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

                if (!state.canBeReplaced() || i == maxLaserDistance - 1) {
                    laserDistances.put(direction, 0);
                    break;
                }
            }
        }
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
}
