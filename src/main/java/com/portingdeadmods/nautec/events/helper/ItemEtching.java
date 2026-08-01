package com.portingdeadmods.nautec.events.helper;

import com.portingdeadmods.nautec.content.recipes.ItemEtchingRecipe;
import com.portingdeadmods.nautec.registries.NTBlocks;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.SeededContainerLoot;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.Optional;

public class ItemEtching {
    private static final Reference2IntMap<ItemEntity> activeEtching = new Reference2IntOpenHashMap<>();

    public static void onEntityLeave(ItemEntity itemEntity) {
        activeEtching.removeInt(itemEntity);
    }

    public static void processItemEtching(ItemEntity itemEntity, Level level) {
        ItemStack stack = itemEntity.getItem();

        if (!activeEtching.containsKey(itemEntity)) {
            Optional<ItemEtchingRecipe> optionalRecipe = getEtchingRecipe(stack, level);
            if (optionalRecipe.isPresent()) {
                activeEtching.put(itemEntity, 0);
            }
        } else {
            int etchingTime = activeEtching.getInt(itemEntity);

            if (etchingTime >= 100) {
                Optional<ItemEtchingRecipe> optionalRecipe = getEtchingRecipe(stack, level);
                if (optionalRecipe.isPresent()) {
                    transformItem(itemEntity, optionalRecipe.get(), level);
                }
                activeEtching.removeInt(itemEntity);
            } else {
                activeEtching.put(itemEntity, etchingTime + 1);
                if (level instanceof ServerLevel serverLevel && etchingTime % 5 == 0) {
                    serverLevel.sendParticles(ParticleTypes.FLAME, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), 20, 0.5, 0.5, 0.5, 0);
                }
            }
        }
    }

    private static Optional<ItemEtchingRecipe> getEtchingRecipe(ItemStack stack, Level level) {
        if (level instanceof ServerLevel serverLevel) {
            return serverLevel.recipeAccess()
                    .getRecipeFor(ItemEtchingRecipe.Type.INSTANCE, new SingleRecipeInput(stack), level)
                    .map(RecipeHolder::value);
        }
        return Optional.empty();
    }

    private static void transformItem(ItemEntity itemEntity, ItemEtchingRecipe recipe, Level level) {
        BlockPos position = itemEntity.getOnPos();
        ItemStack inputStack = itemEntity.getItem();
        ItemStack resultStack = recipe.getResultItem(level.registryAccess()).copy();
        resultStack.setCount(itemEntity.getItem().getCount());

        if (inputStack.is(NTBlocks.RUSTY_CRATE.asItem()) && resultStack.is(NTBlocks.CRATE.asItem())) {
            ItemContainerContents value = itemEntity.getItem().copy().get(DataComponents.CONTAINER);
            resultStack.set(DataComponents.CONTAINER, value);
            SeededContainerLoot value1 = itemEntity.getItem().copy().get(DataComponents.CONTAINER_LOOT);
            resultStack.set(DataComponents.CONTAINER_LOOT, value1);
        }

        itemEntity.discard();

        int rand = level.getRandom().nextInt(0, 3);
        if (rand == 2) {
            level.setBlock(itemEntity.getOnPos(), Blocks.AIR.defaultBlockState(), 11);
        }

        ItemEntity newItemEntity = new ItemEntity(level, position.getX(), position.getY(), position.getZ(), resultStack);
        level.addFreshEntity(newItemEntity);
    }

}
