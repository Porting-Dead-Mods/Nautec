package com.portingdeadmods.modjam.api.blockentities;

import com.portingdeadmods.modjam.api.blocks.blockentities.LaserBlock;
import com.portingdeadmods.modjam.recipes.ItemTransformationRecipe;
import com.portingdeadmods.modjam.registries.MJItems;
import com.portingdeadmods.modjam.utils.ParticlesUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.*;

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



    private void damageLivingEntities(AABB box) {
        for (Direction direction : getLaserOutputs()) {
            assert level != null;
            List<LivingEntity> livingEntities = level.getEntitiesOfClass(LivingEntity.class, box);
            for (LivingEntity livingEntity : livingEntities) {
                livingEntity.hurt(level.damageSources().inFire(), 3);
            }
        }
    }

    private Optional<ItemTransformationRecipe> getCurrentRecipe(ItemStack itemStack) {
        SingleRecipeInput recipeInput = new SingleRecipeInput(itemStack);
        return this.level.getRecipeManager().getRecipeFor(ItemTransformationRecipe.Type.INSTANCE, recipeInput, level).map(RecipeHolder::value);
    }

    private ItemEntity cookingItem = null;
    private int cookTime = 0;

    private Map<ItemEntity, Integer> activeCookings = new HashMap<>();

    private void processItemCrafting(AABB box) {
        // Get all item entities within the box
        List<ItemEntity> itemEntities = level.getEntitiesOfClass(ItemEntity.class, box);

        for (ItemEntity itemEntity : itemEntities) {
            if (!activeCookings.containsKey(itemEntity)) {
                Optional<ItemTransformationRecipe> optionalRecipe = getCurrentRecipe(itemEntity.getItem());
                if (optionalRecipe.isPresent()) {
                    activeCookings.put(itemEntity, 0);
                }
            }
        }

        Iterator<Map.Entry<ItemEntity, Integer>> iterator = activeCookings.entrySet().iterator();
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
                activeCookings.put(cookingItem, cookTime + 1);
                ParticlesUtils.spawnParticles(cookingItem, level, ParticleTypes.END_ROD);
            }
        }
    }


    private void damageLiving() {
        for (Direction direction : getLaserOutputs()) {
            int distance = this.laserDistances.getInt(direction);
            BlockPos pos = worldPosition.above().relative(direction, distance - 1);
            Vec3 start = worldPosition.getCenter().subtract(0.1, 0, 0.1);
            Vec3 end = pos.getCenter().add(0.1, 0, 0.1);
            AABB box = new AABB(start, end);
            this.box = box;

            assert level != null;

            damageLivingEntities(box);

            processItemCrafting(box);
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