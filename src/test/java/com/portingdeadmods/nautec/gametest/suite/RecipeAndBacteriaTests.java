package com.portingdeadmods.nautec.gametest.suite;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.capabilities.bacteria.BacteriaStorage;
import com.portingdeadmods.nautec.content.bacteria.SimpleCollapsedStats;
import com.portingdeadmods.nautec.content.recipes.AquaticCatalystChannelingRecipe;
import com.portingdeadmods.nautec.content.recipes.AugmentationRecipe;
import com.portingdeadmods.nautec.content.recipes.BacteriaIncubationRecipe;
import com.portingdeadmods.nautec.content.recipes.BacteriaMutationRecipe;
import com.portingdeadmods.nautec.content.recipes.ItemEtchingRecipe;
import com.portingdeadmods.nautec.content.recipes.ItemTransformationRecipe;
import com.portingdeadmods.nautec.content.recipes.MixingRecipe;
import com.portingdeadmods.nautec.content.recipes.utils.IngredientWithCount;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.data.components.ComponentBacteriaStorage;
import com.portingdeadmods.nautec.registries.NTBacterias;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.Collection;

public final class RecipeAndBacteriaTests {
    private RecipeAndBacteriaTests() {
    }

    public static void register(NTTestRegistrar r) {
        r.add("recipe/item_transformation_registry_valid", 40, helper -> helper.runAfterDelay(1, () -> {
            Collection<RecipeHolder<ItemTransformationRecipe>> recipes =
                    helper.getLevel().recipeAccess().recipeMap().byType(ItemTransformationRecipe.Type.INSTANCE);
            helper.assertFalse(recipes.isEmpty(), "No item transformation recipes loaded");
            for (RecipeHolder<ItemTransformationRecipe> holder : recipes) {
                ItemTransformationRecipe recipe = holder.value();
                helper.assertFalse(recipe.result().isEmpty(), holder.id() + " has empty result");
                helper.assertTrue(recipe.ingredient().count() >= 1, holder.id() + " has ingredient count < 1");
                helper.assertTrue(recipe.duration() > 0, holder.id() + " has non-positive duration");
                helper.assertTrue(recipe.purity() >= 0.0f, holder.id() + " has negative purity");
            }
            helper.succeed();
        }));

        r.add("recipe/item_etching_registry_valid", 40, helper -> helper.runAfterDelay(1, () -> {
            Collection<RecipeHolder<ItemEtchingRecipe>> recipes =
                    helper.getLevel().recipeAccess().recipeMap().byType(ItemEtchingRecipe.Type.INSTANCE);
            helper.assertFalse(recipes.isEmpty(), "No item etching recipes loaded");
            for (RecipeHolder<ItemEtchingRecipe> holder : recipes) {
                ItemEtchingRecipe recipe = holder.value();
                helper.assertFalse(recipe.result().isEmpty(), holder.id() + " has empty result");
                helper.assertTrue(recipe.ingredient().count() >= 1, holder.id() + " has ingredient count < 1");
                helper.assertTrue(recipe.duration() > 0, holder.id() + " has non-positive duration");
            }
            helper.succeed();
        }));

        r.add("recipe/mixing_registry_valid", 40, helper -> helper.runAfterDelay(1, () -> {
            Collection<RecipeHolder<MixingRecipe>> recipes =
                    helper.getLevel().recipeAccess().recipeMap().byType(MixingRecipe.Type.INSTANCE);
            helper.assertFalse(recipes.isEmpty(), "No mixing recipes loaded");
            for (RecipeHolder<MixingRecipe> holder : recipes) {
                MixingRecipe recipe = holder.value();
                helper.assertTrue(recipe.resultTemplate().isPresent() || recipe.fluidResultTemplate().isPresent(),
                        holder.id() + " has neither item nor fluid result");
                if (recipe.resultTemplate().isPresent()) {
                    helper.assertFalse(recipe.result().isEmpty(), holder.id() + " item result materializes empty");
                }
                if (recipe.fluidResultTemplate().isPresent()) {
                    FluidStack fluidResult = recipe.fluidResult();
                    helper.assertFalse(fluidResult.isEmpty(), holder.id() + " fluid result materializes empty");
                    helper.assertTrue(fluidResult.getAmount() > 0, holder.id() + " fluid result has non-positive amount");
                }
                helper.assertFalse(recipe.ingredients().isEmpty(), holder.id() + " has no ingredients");
                for (IngredientWithCount ingredient : recipe.ingredients()) {
                    helper.assertTrue(ingredient.count() >= 1, holder.id() + " has ingredient count < 1");
                }
                if (recipe.fluidIngredientTemplate().isPresent()) {
                    helper.assertTrue(recipe.fluidIngredient().getAmount() > 0,
                            holder.id() + " fluid ingredient has non-positive amount");
                }
                helper.assertTrue(recipe.duration() > 0, holder.id() + " has non-positive duration");
            }
            helper.succeed();
        }));

        r.add("recipe/aquatic_catalyst_registry_valid", 40, helper -> helper.runAfterDelay(1, () -> {
            Collection<RecipeHolder<AquaticCatalystChannelingRecipe>> recipes =
                    helper.getLevel().recipeAccess().recipeMap().byType(AquaticCatalystChannelingRecipe.Type.INSTANCE);
            helper.assertFalse(recipes.isEmpty(), "No aquatic catalyst channeling recipes loaded");
            for (RecipeHolder<AquaticCatalystChannelingRecipe> holder : recipes) {
                AquaticCatalystChannelingRecipe recipe = holder.value();
                helper.assertTrue(recipe.powerAmount() > 0, holder.id() + " has non-positive power amount");
                helper.assertTrue(recipe.duration() > 0, holder.id() + " has non-positive duration");
                helper.assertTrue(recipe.purity() >= 0.0f,
                        holder.id() + " has negative purity");
            }
            helper.succeed();
        }));

        r.add("recipe/bacteria_recipes_registry_valid", 40, helper -> helper.runAfterDelay(1, () -> {
            Collection<RecipeHolder<BacteriaIncubationRecipe>> incubation =
                    helper.getLevel().recipeAccess().recipeMap().byType(BacteriaIncubationRecipe.TYPE);
            helper.assertFalse(incubation.isEmpty(), "No bacteria incubation recipes loaded");
            for (RecipeHolder<BacteriaIncubationRecipe> holder : incubation) {
                BacteriaIncubationRecipe recipe = holder.value();
                helper.assertTrue(recipe.bacteria() != null, holder.id() + " has null bacteria key");
                helper.assertTrue(recipe.growth().getMax() >= recipe.growth().getMin(),
                        holder.id() + " has inverted growth range");
                helper.assertTrue(recipe.consumeChance() >= 0.0f && recipe.consumeChance() <= 1.0f,
                        holder.id() + " has consume chance outside [0, 1]");
            }

            Collection<RecipeHolder<BacteriaMutationRecipe>> mutation =
                    helper.getLevel().recipeAccess().recipeMap().byType(BacteriaMutationRecipe.TYPE);
            helper.assertFalse(mutation.isEmpty(), "No bacteria mutation recipes loaded");
            for (RecipeHolder<BacteriaMutationRecipe> holder : mutation) {
                BacteriaMutationRecipe recipe = holder.value();
                helper.assertTrue(recipe.inputBacteria() != null, holder.id() + " has null input bacteria");
                helper.assertTrue(recipe.resultBacteria() != null, holder.id() + " has null result bacteria");
                helper.assertTrue(recipe.chance() > 0.0f,
                        holder.id() + " has non-positive chance");
            }
            helper.succeed();
        }));

        r.add("recipe/augmentation_registry_valid", 40, helper -> helper.runAfterDelay(1, () -> {
            Collection<RecipeHolder<AugmentationRecipe>> recipes =
                    helper.getLevel().recipeAccess().recipeMap().byType(AugmentationRecipe.Type.INSTANCE);
            helper.assertFalse(recipes.isEmpty(), "No augmentation recipes loaded");
            for (RecipeHolder<AugmentationRecipe> holder : recipes) {
                AugmentationRecipe recipe = holder.value();
                helper.assertTrue(recipe.augmentItem() != null, holder.id() + " has null augment item");
                helper.assertTrue(recipe.resultAugment() != null, holder.id() + " has null result augment");
                helper.assertFalse(recipe.ingredients().isEmpty(), holder.id() + " has no ingredients");
                for (IngredientWithCount ingredient : recipe.ingredients()) {
                    helper.assertTrue(ingredient.count() >= 1, holder.id() + " has ingredient count < 1");
                }
            }
            helper.succeed();
        }));

        r.add("recipe/aquarine_steel_transformation_lookup", 40, helper -> helper.runAfterDelay(1, () -> {
            Recipe<?> raw = lookup(helper, "aquarine_steel_ingot");
            helper.assertTrue(raw instanceof ItemTransformationRecipe, "aquarine_steel_ingot is not an item transformation recipe");
            ItemTransformationRecipe recipe = (ItemTransformationRecipe) raw;
            helper.assertValueEqual(100, recipe.duration(), "aquarine_steel_ingot duration");
            helper.assertValueEqual(0.0f, recipe.purity(), "aquarine_steel_ingot purity");
            helper.assertTrue(recipe.result().is(NTItems.AQUARINE_STEEL_INGOT.get()), "result is not aquarine steel ingot");
            helper.assertValueEqual(1, recipe.result().getCount(), "aquarine_steel_ingot result count");
            helper.assertTrue(recipe.ingredient().test(new ItemStack(NTItems.AQUARINE_STEEL_COMPOUND.get())),
                    "ingredient does not accept aquarine steel compound");
            helper.succeed();
        }));

        r.add("recipe/etching_gear_lookup", 40, helper -> helper.runAfterDelay(1, () -> {
            Recipe<?> raw = lookup(helper, "gear");
            helper.assertTrue(raw instanceof ItemEtchingRecipe, "gear is not an item etching recipe");
            ItemEtchingRecipe recipe = (ItemEtchingRecipe) raw;
            helper.assertValueEqual(160, recipe.duration(), "gear etching duration");
            helper.assertTrue(recipe.result().is(NTItems.GEAR.get()), "result is not gear");
            helper.assertTrue(recipe.ingredient().test(new ItemStack(NTItems.RUSTY_GEAR.get())),
                    "ingredient does not accept rusty gear");
            helper.succeed();
        }));

        r.add("recipe/mixing_fluid_ingredient_lookup", 40, helper -> helper.runAfterDelay(1, () -> {
            Recipe<?> raw = lookup(helper, "aquarine_steel_compound_mixing");
            helper.assertTrue(raw instanceof MixingRecipe, "aquarine_steel_compound_mixing is not a mixing recipe");
            MixingRecipe recipe = (MixingRecipe) raw;
            FluidStack fluidIngredient = recipe.fluidIngredient();
            helper.assertFalse(fluidIngredient.isEmpty(), "fluid ingredient is empty");
            helper.assertValueEqual(Nautec.rl("saltwater"), BuiltInRegistries.FLUID.getKey(fluidIngredient.getFluid()),
                    "fluid ingredient id");
            helper.assertValueEqual(1000, fluidIngredient.getAmount(), "fluid ingredient amount");
            helper.assertValueEqual(100, recipe.duration(), "mixing duration");
            helper.assertValueEqual(2, recipe.ingredients().size(), "mixing ingredient count");
            ItemStack result = recipe.result();
            helper.assertTrue(result.is(NTItems.AQUARINE_STEEL_COMPOUND.get()), "result is not aquarine steel compound");
            helper.assertValueEqual(5, result.getCount(), "mixing result count");
            helper.assertTrue(recipe.fluidResult().isEmpty(), "unexpected fluid result");
            helper.succeed();
        }));

        r.add("recipe/ingredient_with_count_codec_roundtrip", 40, helper -> helper.runAfterDelay(1, () -> {
            RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, helper.getLevel().registryAccess());
            IngredientWithCount original = new IngredientWithCount(Ingredient.of(Items.IRON_INGOT), 3);
            JsonElement json = IngredientWithCount.CODEC.encodeStart(ops, original).getOrThrow();
            IngredientWithCount decoded = IngredientWithCount.CODEC.parse(ops, json).getOrThrow();
            helper.assertValueEqual(original, decoded, "ingredient with count");
            helper.assertValueEqual(3, decoded.count(), "decoded count");
            helper.assertTrue(decoded.test(new ItemStack(Items.IRON_INGOT, 3)), "decoded ingredient rejects 3 iron ingots");
            helper.assertFalse(decoded.test(new ItemStack(Items.IRON_INGOT, 2)), "decoded ingredient accepts insufficient count");
            helper.assertFalse(decoded.test(new ItemStack(Items.GOLD_INGOT, 3)), "decoded ingredient accepts wrong item");
            helper.succeed();
        }));

        r.add("recipe/laser_item_transformation_in_world", 300, helper -> {
            BlockPos source = new BlockPos(4, 4, 4);
            BlockPos charger = new BlockPos(4, 1, 4);
            helper.setBlock(source, NTBlocks.CREATIVE_POWER_SOURCE.get());
            helper.setBlock(charger, NTBlocks.CHARGER.get());
            helper.setBlock(new BlockPos(4, 5, 4), Blocks.STONE);
            helper.setBlock(new BlockPos(3, 4, 4), Blocks.STONE);
            helper.setBlock(new BlockPos(5, 4, 4), Blocks.STONE);
            helper.setBlock(new BlockPos(4, 4, 3), Blocks.STONE);
            helper.setBlock(new BlockPos(4, 4, 5), Blocks.STONE);
            ItemEntity item = helper.spawnItem(NTItems.AQUARINE_STEEL_COMPOUND.get(), 4.5f, 2.5f, 4.5f);
            item.setNoGravity(true);
            item.setDeltaMovement(Vec3.ZERO);
            helper.succeedWhen(() ->
                    helper.assertItemEntityPresent(NTItems.AQUARINE_STEEL_INGOT.get(), new BlockPos(4, 2, 4), 3.0));
        });

        r.add("bacteria/storage_slot_access", 40, helper -> helper.runAfterDelay(1, () -> {
            BacteriaStorage storage = new BacteriaStorage(2);
            helper.assertValueEqual(2, storage.getBacteriaSlots(), "slot count");
            helper.assertTrue(storage.getBacteria(0).isEmpty(), "slot 0 not empty initially");
            helper.assertTrue(storage.getBacteria(1).isEmpty(), "slot 1 not empty initially");
            BacteriaInstance instance = BacteriaInstance.withMaxStats(NTBacterias.CYANOBACTERIA, helper.getLevel().registryAccess());
            storage.setBacteria(1, instance);
            helper.assertTrue(storage.getBacteria(0).isEmpty(), "slot 0 affected by setting slot 1");
            helper.assertValueEqual(NTBacterias.CYANOBACTERIA, storage.getBacteria(1).getBacteria(), "slot 1 bacteria key");
            helper.assertValueEqual(instance.getSize(), storage.getBacteria(1).getSize(), "slot 1 size");
            storage.setBacteria(1, BacteriaInstance.EMPTY);
            helper.assertTrue(storage.getBacteria(1).isEmpty(), "slot 1 not cleared");
            helper.succeed();
        }));

        r.add("bacteria/storage_value_io_roundtrip", 40, helper -> helper.runAfterDelay(1, () -> {
            BacteriaInstance instance = BacteriaInstance.withMaxStats(NTBacterias.HALOBACTERIA, helper.getLevel().registryAccess());
            BacteriaStorage storage = new BacteriaStorage(2);
            storage.setBacteria(0, instance);
            TagValueOutput out = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, helper.getLevel().registryAccess());
            storage.serialize(out);
            CompoundTag tag = out.buildResult();
            BacteriaStorage loaded = new BacteriaStorage(1);
            loaded.deserialize(TagValueInput.create(ProblemReporter.DISCARDING, helper.getLevel().registryAccess(), tag));
            helper.assertValueEqual(2, loaded.getBacteriaSlots(), "loaded slot count");
            BacteriaInstance loadedInstance = loaded.getBacteria(0);
            helper.assertValueEqual(NTBacterias.HALOBACTERIA, loadedInstance.getBacteria(), "loaded bacteria key");
            helper.assertValueEqual(instance.getSize(), loadedInstance.getSize(), "loaded size");
            helper.assertTrue(loadedInstance.isAnalyzed(), "analyzed flag lost");
            assertSameStats(helper, instance, loadedInstance);
            helper.assertTrue(loaded.getBacteria(1).isEmpty(), "loaded slot 1 not empty");
            helper.succeed();
        }));

        r.add("bacteria/instance_codec_roundtrip", 40, helper -> helper.runAfterDelay(1, () -> {
            RegistryOps<Tag> ops = RegistryOps.create(NbtOps.INSTANCE, helper.getLevel().registryAccess());
            BacteriaInstance original = BacteriaInstance.withMaxStats(NTBacterias.CYANOBACTERIA, helper.getLevel().registryAccess());
            Tag encoded = BacteriaInstance.CODEC.encodeStart(ops, original).getOrThrow();
            BacteriaInstance decoded = BacteriaInstance.CODEC.parse(ops, encoded).getOrThrow();
            helper.assertValueEqual(NTBacterias.CYANOBACTERIA, decoded.getBacteria(), "decoded bacteria key");
            helper.assertValueEqual(original.getSize(), decoded.getSize(), "decoded size");
            helper.assertValueEqual(original.isAnalyzed(), decoded.isAnalyzed(), "decoded analyzed flag");
            assertSameStats(helper, original, decoded);
            helper.assertTrue(BacteriaInstance.isSameBacteriaAndStats(original, decoded),
                    "separately decoded instances should compare equal via range equality");
            helper.succeed();
        }));

        r.add("bacteria/instance_empty_semantics", 40, helper -> helper.runAfterDelay(1, () -> {
            helper.assertTrue(BacteriaInstance.EMPTY.isEmpty(), "EMPTY instance is not empty");
            BacteriaInstance instance = BacteriaInstance.withMaxStats(NTBacterias.CYANOBACTERIA, helper.getLevel().registryAccess());
            helper.assertFalse(instance.isEmpty(), "max stat instance reports empty");
            helper.assertTrue(instance.copyWithSize(0).isEmpty(), "copyWithSize(0) is not empty");
            BacteriaInstance sized = instance.copyWithSize(64);
            helper.assertValueEqual(64L, sized.getSize(), "copyWithSize size");
            sized.grow(6);
            helper.assertValueEqual(70L, sized.getSize(), "size after grow");
            sized.shrink(20);
            helper.assertValueEqual(50L, sized.getSize(), "size after shrink");
            long originalSize = instance.getSize();
            BacteriaInstance copy = instance.copy();
            copy.grow(10);
            helper.assertValueEqual(originalSize, instance.getSize(), "copy shares size with original");
            helper.succeed();
        }));

        r.add("bacteria/petri_dish_component", 40, helper -> helper.runAfterDelay(1, () -> {
            ItemStack dish = new ItemStack(NTItems.PETRI_DISH.get());
            ComponentBacteriaStorage initial = dish.get(NTDataComponents.BACTERIA);
            helper.assertTrue(initial != null, "petri dish has no default bacteria component");
            helper.assertTrue(initial.bacteriaInstance().isEmpty(), "petri dish default bacteria is not empty");
            BacteriaInstance instance = BacteriaInstance.withMaxStats(NTBacterias.HALOBACTERIA, helper.getLevel().registryAccess());
            dish.set(NTDataComponents.BACTERIA, new ComponentBacteriaStorage(instance));
            ComponentBacteriaStorage read = dish.get(NTDataComponents.BACTERIA);
            helper.assertTrue(read != null, "bacteria component missing after set");
            helper.assertValueEqual(NTBacterias.HALOBACTERIA, read.bacteriaInstance().getBacteria(), "component bacteria key");
            helper.assertValueEqual(instance.getSize(), read.bacteriaInstance().getSize(), "component bacteria size");
            helper.assertTrue(read.bacteriaInstance().isAnalyzed(), "component analyzed flag lost");
            helper.succeed();
        }));
    }

    private static Recipe<?> lookup(GameTestHelper helper, String path) {
        RecipeHolder<?> holder = helper.getLevel().recipeAccess()
                .byKey(ResourceKey.create(Registries.RECIPE, Nautec.rl(path)))
                .orElse(null);
        helper.assertTrue(holder != null, "Recipe nautec:" + path + " not found");
        return holder.value();
    }

    private static void assertSameStats(GameTestHelper helper, BacteriaInstance expected, BacteriaInstance actual) {
        helper.assertTrue(expected.getStats() instanceof SimpleCollapsedStats, "expected stats are not simple stats");
        helper.assertTrue(actual.getStats() instanceof SimpleCollapsedStats, "actual stats are not simple stats");
        SimpleCollapsedStats expectedStats = (SimpleCollapsedStats) expected.getStats();
        SimpleCollapsedStats actualStats = (SimpleCollapsedStats) actual.getStats();
        helper.assertValueEqual(expectedStats.growthRate(), actualStats.growthRate(), "growth rate");
        helper.assertValueEqual(expectedStats.mutationResistance(), actualStats.mutationResistance(), "mutation resistance");
        helper.assertValueEqual(expectedStats.productionRate(), actualStats.productionRate(), "production rate");
        helper.assertValueEqual(expectedStats.lifespan(), actualStats.lifespan(), "lifespan");
        helper.assertValueEqual(expectedStats.color(), actualStats.color(), "color");
    }
}
