package com.portingdeadmods.nautec.datagen;

import com.portingdeadmods.nautec.content.recipes.utils.IngredientWithCount;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.datagen.recipeBuilder.*;
import com.portingdeadmods.nautec.registries.NTAugments;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTFluids;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class RecipesProvider extends RecipeProvider {
    public RecipesProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput pRecipeOutput) {
        AquaticCatalystChannelingRecipeBuilder.newRecipe(Ingredient.of(Items.KELP))
                .powerAmount(500)
                .purity(0)
                .duration(100)
                .save(pRecipeOutput);

        ItemTransformationRecipeBuilder.newRecipe(new ItemStack(NTItems.AQUARINE_STEEL_INGOT.get(), 1))
                .ingredient(new ItemStack(NTItems.AQUARINE_STEEL_COMPOUND.get()))
                .purity(3)
                .duration(100)
                .save(pRecipeOutput);

        ItemEtchingRecipeBuilder.newRecipe(NTItems.VALVE.toStack())
                .ingredient(NTItems.ANCIENT_VALVE.toStack())
                .duration(200)
                .save(pRecipeOutput);

        ItemEtchingRecipeBuilder.newRecipe(NTItems.GEAR.toStack())
                .ingredient(NTItems.RUSTY_GEAR.toStack())
                .duration(160)
                .save(pRecipeOutput);

        MixingRecipeBuilder.newRecipe(new ItemStack(NTItems.RUSTY_GEAR.get()))
                .ingredients(IngredientWithCount.fromItemTag(ItemTags.ANVIL), iwcFromItemLike(NTItems.ATLANTIC_GOLD_NUGGET.get(), 3))
                .duration(60)
                .save(pRecipeOutput);

        MixingRecipeBuilder.newRecipe(new ItemStack(NTItems.ATLANTIC_GOLD_NUGGET.get(), 3))
                .ingredients(iwcFromItemLike(NTItems.ATLANTIC_GOLD_INGOT.get(), 1))
                .duration(60)
                .save(pRecipeOutput);

        MixingRecipeBuilder.newRecipe(ItemStack.EMPTY)
                .ingredients(iwcFromItemLike(Items.DRIED_KELP, 4),
                        iwcFromItemLike(Items.SLIME_BALL, 2),
                        iwcFromItemLike(Items.PRISMARINE_CRYSTALS, 1),
                        iwcFromItemLike(Items.SEAGRASS, 5))
                .duration(200)
                .fluidIngredient(new FluidStack(NTFluids.SALT_WATER_SOURCE.get(), 500))
                .fluidResult(new FluidStack(NTFluids.EAS_SOURCE.get(), 500))
                .save(pRecipeOutput);

        AugmentationRecipeBuilder.newRecipe(NTAugments.DOLPHIN_FIN.get())
                .augmentItem(NTItems.DOLPHIN_FIN.get(), "Greatly improved swimming speed")
                .ingredients(IngredientWithCount.fromItemLike(NTItems.DOLPHIN_FIN.get()))
                .ingredients(IngredientWithCount.fromItemLike(Items.SEAGRASS, 2))
                .ingredients(IngredientWithCount.fromItemLike(Items.PRISMARINE_CRYSTALS, 1))
                .ingredients(IngredientWithCount.fromItemLike(Items.PRISMARINE_SHARD, 1))
                .save(pRecipeOutput, "dolphin_fin");

        AugmentationRecipeBuilder.newRecipe(NTAugments.DROWNED_LUNG.get())
                .augmentItem(NTItems.DROWNED_LUNGS.get(), "Unlimited underwater breathing")
                .ingredients(IngredientWithCount.fromItemLike(NTItems.DROWNED_LUNGS.get()))
                .ingredients(IngredientWithCount.fromItemLike(Items.PRISMARINE_SHARD, 2))
                .ingredients(IngredientWithCount.fromItemLike(Items.PRISMARINE_CRYSTALS, 1))
                .ingredients(IngredientWithCount.fromItemLike(Items.SEAGRASS, 2))
                .save(pRecipeOutput, "drowned_lung");

        AugmentationRecipeBuilder.newRecipe(NTAugments.GUARDIAN_EYE_AUGMENT.get())
                .augmentItem(NTItems.GUARDIAN_EYE.get(), "Shoots lasers at enemies you are looking at")
                .ingredients(IngredientWithCount.fromItemLike(NTItems.GUARDIAN_EYE.get()))
                .ingredients(IngredientWithCount.fromItemLike(Items.PRISMARINE_SHARD, 2))
                .ingredients(IngredientWithCount.fromItemLike(Items.PRISMARINE_CRYSTALS, 1))
                .ingredients(IngredientWithCount.fromItemLike(Items.SEAGRASS, 2))
                .save(pRecipeOutput, "guardian_eye_augment");

        ItemStack divingChestplate = NTItems.DIVING_CHESTPLATE.get().getDefaultInstance();
        divingChestplate.set(NTDataComponents.OXYGEN, 600);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, divingChestplate)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .unlockedBy("has_item", has(NTItems.DIVING_CHESTPLATE.get()))
                .define('G', NTItems.AIR_BOTTLE.get())
                .define('D', NTItems.DIVING_CHESTPLATE.get())
                .save(pRecipeOutput, "diving_chestplate_oxygen");

        ItemStack aquarine_pickaxe = NTItems.AQUARINE_PICKAXE.get().getDefaultInstance();
        ItemStack aquarine_shovel = NTItems.AQUARINE_SHOVEL.get().getDefaultInstance();
        ItemStack aquarine_axe = NTItems.AQUARINE_AXE.get().getDefaultInstance();
        ItemStack aquarine_hoe = NTItems.AQUARINE_HOE.get().getDefaultInstance();
        ItemStack aquarine_sword = NTItems.AQUARINE_SWORD.get().getDefaultInstance();
        ItemStack aquarine_wrench = NTItems.AQUARINE_WRENCH.get().getDefaultInstance();

        ItemLike brown_polymer = NTItems.BROWN_POLYMER.get();
        ItemStack diving_helmet = NTItems.DIVING_HELMET.get().getDefaultInstance();
        ItemStack diving_chestplate = NTItems.DIVING_CHESTPLATE.get().getDefaultInstance();
        ItemStack diving_leggings = NTItems.DIVING_LEGGINGS.get().getDefaultInstance();
        ItemStack diving_boots = NTItems.DIVING_BOOTS.get().getDefaultInstance();

        ItemStack aquarine_helmet = NTItems.AQUARINE_HELMET.get().getDefaultInstance();
        ItemStack aquarine_chestplate = NTItems.AQUARINE_CHESTPLATE.get().getDefaultInstance();
        ItemStack aquarine_leggings = NTItems.AQUARINE_LEGGINGS.get().getDefaultInstance();
        ItemStack aquarine_boots = NTItems.AQUARINE_BOOTS.get().getDefaultInstance();

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, aquarine_pickaxe)
                .pattern("AAA")
                .pattern(" R ")
                .pattern(" R ")
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.get()))
                .define('A', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('R', NTItems.CAST_IRON_ROD.get())
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, aquarine_shovel)
                .pattern(" A ")
                .pattern(" R ")
                .pattern(" R ")
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.get()))
                .define('A', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('R', NTItems.CAST_IRON_ROD.get())
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, aquarine_axe)
                .pattern("AA ")
                .pattern("AR ")
                .pattern(" R ")
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.get()))
                .define('A', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('R', NTItems.CAST_IRON_ROD.get())
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, aquarine_hoe)
                .pattern("AA ")
                .pattern(" R ")
                .pattern(" R ")
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.get()))
                .define('A', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('R', NTItems.CAST_IRON_ROD.get())
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, aquarine_sword)
                .pattern(" A ")
                .pattern(" A ")
                .pattern(" R ")
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.get()))
                .define('A', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('R', NTItems.CAST_IRON_ROD.get())
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, aquarine_wrench)
                .pattern("A A")
                .pattern(" A ")
                .pattern(" A ")
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.get()))
                .define('A', NTItems.AQUARINE_STEEL_INGOT.get())
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.CAST_IRON_ROD.get(), 2)
                .pattern("C")
                .pattern("C")
                .define('C', NTItems.CAST_IRON_ROD.asItem())
                .unlockedBy("has_item", has(Items.DEEPSLATE))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, diving_helmet)
                .pattern("CCC")
                .pattern("CGC")
                .define('C', Items.COPPER_INGOT.asItem())
                .define('G', Items.GLASS_PANE.asItem())
                .unlockedBy("has_item", has(Items.COPPER_INGOT))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, diving_chestplate)
                .pattern("B B")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', brown_polymer)
                .unlockedBy("has_item", has(NTItems.BROWN_POLYMER))
                .save(pRecipeOutput, "diving_chestplate");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, diving_leggings)
                .pattern("BBB")
                .pattern("B B")
                .pattern("B B")
                .define('B', brown_polymer)
                .unlockedBy("has_item", has(NTItems.BROWN_POLYMER))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, diving_boots)
                .pattern("B B")
                .pattern("B B")
                .define('B', brown_polymer)
                .unlockedBy("has_item", has(NTItems.BROWN_POLYMER))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, aquarine_helmet)
                .pattern("CCC")
                .pattern("C C")
                .define('C', NTItems.AQUARINE_STEEL_INGOT.get())
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.get()))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, aquarine_chestplate)
                .pattern("B B")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', NTItems.AQUARINE_STEEL_INGOT.get())
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.get()))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, aquarine_leggings)
                .pattern("BBB")
                .pattern("B B")
                .pattern("B B")
                .define('B', NTItems.AQUARINE_STEEL_INGOT.get())
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.get()))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, aquarine_boots)
                .pattern("B B")
                .pattern("B B")
                .define('B', NTItems.AQUARINE_STEEL_INGOT.get())
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.get()))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.CHISELED_DARK_PRISMARINE.asItem(), 4)
                .pattern("DD")
                .pattern("DD")
                .define('D', Blocks.DARK_PRISMARINE.asItem())
                .unlockedBy("has_item", has(Blocks.DARK_PRISMARINE))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.POLISHED_PRISMARINE.asItem(), 4)
                .pattern("DD")
                .pattern("DD")
                .define('D', Blocks.PRISMARINE.asItem())
                .unlockedBy("has_item", has(Blocks.DARK_PRISMARINE))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.PRISMATIC_BATTERY.get(), 1)
                .pattern("CRC")
                .pattern("AAA")
                .define('A', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('R', Items.REDSTONE)
                .define('C', NTItems.PRISMARINE_CRYSTAL_SHARD.get())
                .unlockedBy("has_item", has(NTItems.PRISMARINE_CRYSTAL_SHARD))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.CROWBAR.get(), 1)
                .pattern(" LR")
                .pattern("LRL")
                .pattern("RL ")
                .define('R', NTItems.CAST_IRON_ROD.get())
                .define('L', Items.LAPIS_LAZULI)
                .unlockedBy("has_item", has(NTItems.CAST_IRON_ROD))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.DARK_PRISMARINE_PILLAR.asItem(), 2)
                .pattern("D")
                .pattern("D")
                .define('D', Blocks.DARK_PRISMARINE.asItem())
                .unlockedBy("has_item", has(Blocks.DARK_PRISMARINE))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.CAST_IRON_INGOT, 1)
                .pattern("NNN")
                .pattern("NNN")
                .pattern("NNN")
                .define('N', NTItems.CAST_IRON_NUGGET.asItem())
                .unlockedBy("has_item", has(NTItems.CAST_IRON_INGOT.get()))
                .save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NTItems.AQUARINE_STEEL_COMPOUND.get(), 2)
                .requires(Items.RAW_IRON)
                .requires(Items.PRISMARINE_CRYSTALS)
                .unlockedBy("has_item", has(Items.PRISMARINE_CRYSTALS))
                .save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NTItems.WHISK.get(), 1)
                .requires(NTItems.BROKEN_WHISK.get())
                .requires(NTItems.CAST_IRON_NUGGET.get())
                .unlockedBy("has_item", has(NTItems.BROKEN_WHISK.get()))
                .save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NTItems.CAST_IRON_NUGGET.get(), 9)
                .requires(NTItems.CAST_IRON_INGOT.get())
                .unlockedBy("has_item", has(NTItems.CAST_IRON_INGOT.get()))
                .save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, brown_polymer, 1)
                .requires(Items.DRIED_KELP)
                .requires(Items.BROWN_DYE)
                .unlockedBy("has_item", has(Items.DRIED_KELP))
                .save(pRecipeOutput);


    }

    private static @NotNull IngredientWithCount iwcFromItemLike(Item item, int count) {
        return IngredientWithCount.fromItemLike(item, count);
    }
}
