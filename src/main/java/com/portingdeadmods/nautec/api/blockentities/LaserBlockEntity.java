package com.portingdeadmods.nautec.api.blockentities;

import com.portingdeadmods.nautec.NTConfig;
import com.portingdeadmods.nautec.content.recipes.ItemTransformationRecipe;
import com.portingdeadmods.nautec.content.recipes.inputs.ItemTransformationRecipeInput;
import com.portingdeadmods.nautec.utils.ParticleUtils;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class LaserBlockEntity extends ContainerBlockEntity {
    protected final Object2IntMap<Direction> laserDistances;
    private final Object2ObjectMap<Direction, Object2IntMap<ItemEntity>> activeTransformations;

    private int powerToTransfer;
    protected int power;

    private final Object2IntMap<Direction> powerPerSide;
    private final Object2FloatMap<Direction> purityPerSide;

    private float newPurity;
    protected float purity;

    private float clientLaserTime;

    public LaserBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        this.laserDistances = new Object2IntOpenHashMap<>();
        this.activeTransformations = new Object2ObjectArrayMap<>();
        this.powerPerSide = new Object2IntArrayMap<>();
        this.purityPerSide = new Object2FloatArrayMap<>();
        this.purity = 0;
    }

    public abstract Set<Direction> getLaserInputs();

    public abstract Set<Direction> getLaserOutputs();

    public boolean shouldRender(Direction direction) {
        BlockPos pos = worldPosition.relative(direction, this.laserDistances.getInt(direction));
        boolean b = getLaserOutputs().contains(direction)
                && !pos.equals(worldPosition)
                && level.getBlockEntity(pos) instanceof LaserBlockEntity be
                && be.getLaserInputs().contains(direction.getOpposite())
                && (power > 0 || powerToTransfer > 0);
        return b;
    }

    public Object2IntMap<Direction> getLaserDistances() {
        return laserDistances;
    }

    public int getMaxLaserDistance() {
        return NTConfig.laserDistance;
    }

    protected int checkConnectionsInterval() {
        return 10;
    }

    // POWER
    public void setPowerPerSide(Direction direction, int power) {
        this.powerPerSide.put(direction, power);
    }

    public int getPower() {
        return power;
    }

    public void transmitPower(int amount) {
        this.powerToTransfer = amount;
    }

    public void receivePower(int amount, Direction direction, BlockPos originPos) {
        int prevAmount = this.powerPerSide.getInt(direction);
        setPowerPerSide(direction, amount);
        if (prevAmount != amount) {
            onPowerChanged();
        }
    }

    // PURITY
    public void setPurityPerSide(Direction direction, float purity) {
        this.purityPerSide.put(direction, purity);
    }

    public float getPurity() {
        return purity;
    }

    public void setPurity(float amount) {
        this.newPurity = amount;
    }

    public void receiveNewPurity(float amount, Direction direction, BlockPos originPos) {
        setPurityPerSide(direction, amount);
    }

    // LOGIC
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

                processItemCrafting(box, direction);

                BlockPos targetPos = worldPosition.relative(direction, distance);
                if (level.getBlockEntity(targetPos) instanceof LaserBlockEntity laserBE) {
                    laserBE.receivePower(powerToTransfer, direction, worldPosition);
                    laserBE.receiveNewPurity(purity, direction, worldPosition);
                }
            }
        }

        int power = 0;
        for (int pps : this.powerPerSide.values()) {
            power += pps;
        }
        this.power = power;

        float purity = 0;
        for (float pps : this.purityPerSide.values()) {
            purity += pps;
        }
        int size = this.purityPerSide.size();
        this.purity = newPurity + purity / (size > 0 ? size : 1);

        this.powerPerSide.clear();
    }

    private void damageLivingEntities(AABB box) {
        List<LivingEntity> livingEntities = level.getEntitiesOfClass(LivingEntity.class, box);
        for (LivingEntity livingEntity : livingEntities) {
            livingEntity.hurt(level.damageSources().inFire(), 3);
        }
    }

    private Optional<ItemTransformationRecipe> getCurrentRecipe(ItemStack itemStack) {
        ItemTransformationRecipeInput recipeInput = new ItemTransformationRecipeInput(itemStack, getPurity());
        return this.level.getRecipeManager().getRecipeFor(ItemTransformationRecipe.Type.INSTANCE, recipeInput, level).map(RecipeHolder::value);
    }

    private void processItemCrafting(AABB box, Direction direction) {
        // Get all item entities within the box
        List<ItemEntity> itemEntities = level.getEntitiesOfClass(ItemEntity.class, box);

        for (ItemEntity itemEntity : itemEntities) {
            if (!activeTransformations.containsKey(direction) || !activeTransformations.get(direction).containsKey(itemEntity)) {
                Optional<ItemTransformationRecipe> optionalRecipe = getCurrentRecipe(itemEntity.getItem());
                if (optionalRecipe.isPresent()) {
                    if (!activeTransformations.containsKey(direction)) {
                        activeTransformations.put(direction, new Object2IntArrayMap<>());
                    }
                    activeTransformations.get(direction).put(itemEntity, 0);
                }
            }
        }

        if (activeTransformations.containsKey(direction) && !activeTransformations.get(direction).isEmpty()) {
            Object2IntMap<ItemEntity> activeTransformation = activeTransformations.get(direction);
            ObjectIterator<Object2IntMap.Entry<ItemEntity>> iterator = activeTransformation.object2IntEntrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<ItemEntity, Integer> entry = iterator.next();
                ItemEntity cookingItem = entry.getKey();
                int cookTime = entry.getValue();

                if (!cookingItem.isAlive() || !box.contains(cookingItem.position())) {
                    iterator.remove();
                    continue;
                }

                Optional<ItemTransformationRecipe> optionalRecipe = getCurrentRecipe(cookingItem.getItem());
                if (optionalRecipe.isPresent()) {
                    if (cookTime >= optionalRecipe.get().duration()) {
                        ItemStack resultStack = optionalRecipe.get().getResultItem(null).copy();
                        resultStack.setCount(cookingItem.getItem().getCount());

                        ItemEntity resultEntity = new ItemEntity(level, cookingItem.getX(), cookingItem.getY(), cookingItem.getZ(), resultStack);
                        level.addFreshEntity(resultEntity);

                        cookingItem.discard();
                        iterator.remove();
                    } else {
                        activeTransformation.put(cookingItem, cookTime + 1);
                        ParticleUtils.spawnParticlesAroundItem(cookingItem, level, ParticleTypes.END_ROD);
                    }
                }
            }
        }
    }

    private @NotNull AABB createLaserBeamAABB(Direction direction, int distance) {
        BlockPos pos = worldPosition.relative(direction, distance);

        Vec3 start = worldPosition.relative(direction).getCenter();
        double v = 0.3;
        if (direction == Direction.UP || direction == Direction.DOWN) {
            start = start.subtract(v, 0, v);
        } else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            start = start.subtract(v, v, 0);
        } else if (direction == Direction.EAST || direction == Direction.WEST) {
            start = start.subtract(0, v, v);
        }

        Vec3 end = pos.getCenter().add(0.1, 0, 0.1);
        if (direction == Direction.UP || direction == Direction.DOWN) {
            Vec3 endPos = pos.below().getCenter();
            end = endPos.add(v, 0, v);
        } else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            end = end.add(v, v, 0);
        } else if (direction == Direction.EAST || direction == Direction.WEST) {
            end = end.add(0, v, v);
        }

        return new AABB(start, end);
    }

    protected void checkConnections() {
        for (Direction direction : getLaserOutputs()) {
            int maxLaserDistance = getMaxLaserDistance();
            Vec3 from = worldPosition.relative(direction).getCenter();
            Vec3 to = worldPosition.relative(direction, maxLaserDistance).getCenter();
            BlockHitResult blockHitResult = level.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty()));
            BlockPos resultPos = blockHitResult.getBlockPos();
            if (level.getBlockEntity(resultPos) instanceof LaserBlockEntity laserBlockEntity) {
                if (laserBlockEntity.getLaserInputs().contains(direction.getOpposite())) {
                    Vec3i diffVec3 = worldPosition.subtract(resultPos);
                    int diff = diffVec3.getX() + diffVec3.getY() + diffVec3.getZ();
                    int prevDistance = this.laserDistances.getInt(direction);
                    int newDistance = Math.abs(diff);
                    if (prevDistance != newDistance) {
                        this.laserDistances.put(direction, newDistance);
                        onLaserDistancesChanged(direction, prevDistance);
                    }
                } else {
                    int prevDistance = this.laserDistances.getInt(direction);
                    if (prevDistance != 0) {
                        this.laserDistances.put(direction, 0);
                        onLaserDistancesChanged(direction, prevDistance);
                    }
                }
            } else {
                int prevDistance = this.laserDistances.getInt(direction);
                int newDistance = 0;
                if (prevDistance != newDistance) {
                    this.laserDistances.put(direction, 0);
                    onLaserDistancesChanged(direction, prevDistance);
                }
            }
        }
    }

    protected void onLaserDistancesChanged(Direction direction, int prevDistance) {

    }

    // CLIENT
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
