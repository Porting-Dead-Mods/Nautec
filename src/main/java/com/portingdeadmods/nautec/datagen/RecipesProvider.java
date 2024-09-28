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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
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

        ancientItemsRecipes(pRecipeOutput);

        chemistryRecipes(pRecipeOutput);

        augmentationRecipes(pRecipeOutput);

        aquarineSteelToolsRecipes(pRecipeOutput);

        divingArmorRecipes(pRecipeOutput);

        aquarineSteelArmorRecipes(pRecipeOutput);

        decoBlockRecipes(pRecipeOutput);

        utilityRecipes(pRecipeOutput);

        castIronRecipes(pRecipeOutput);

        miscItemsRecipes(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NTBlocks.AQUARINE_STEEL_BLOCK.asItem(), 1)
                .requires(NTItems.AQUARINE_STEEL_INGOT, 9)
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.GLASS_VIAL.asItem(), 3)
                .pattern("G G").pattern("G G").pattern(" G ")
                .define('G', Items.GLASS)
                .unlockedBy("has_item", has(Items.GLASS))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.MIXER.asItem(), 1)
                .pattern("P P").pattern("PWP").pattern("PSP")
                .define('P', NTBlocks.POLISHED_PRISMARINE)
                .define('W', NTItems.WHISK)
                .define('S', NTItems.PRISMARINE_CRYSTAL_SHARD)
                .unlockedBy("has_item", has(NTItems.PRISMARINE_CRYSTAL_SHARD))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.CHARGER.asItem(), 1)
                .pattern("P P").pattern("PSP")
                .define('P', NTBlocks.POLISHED_PRISMARINE)
                .define('S', NTItems.PRISMARINE_CRYSTAL_SHARD)
                .unlockedBy("has_item", has(NTItems.PRISMARINE_CRYSTAL_SHARD))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.CLAW_ROBOT_ARM.asItem(), 1)
                .pattern("AB ").pattern(" AB").pattern("  A")
                .define('A', NTItems.AQUARINE_STEEL_INGOT)
                .define('B', NTItems.CAST_IRON_ROD)
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.PRISM_MONOCLE.asItem(), 1)
                .pattern("AAA").pattern("ACA").pattern("AAA")
                .define('A', NTItems.AQUARINE_STEEL_INGOT)
                .define('C', NTItems.PRISMARINE_CRYSTAL_SHARD)
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.PRISMARINE_RELAY.asItem(), 6)
                .pattern("AAA").pattern("   ").pattern("AAA")
                .define('A', NTBlocks.POLISHED_PRISMARINE)
                .unlockedBy("has_item", has(NTBlocks.POLISHED_PRISMARINE))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.LASER_JUNCTION.asItem(), 1)
                .pattern(" A ").pattern("ACA").pattern(" A ")
                .define('A', NTBlocks.PRISMARINE_RELAY)
                .define('C', NTItems.PRISMARINE_CRYSTAL_SHARD)
                .unlockedBy("has_item", has(NTBlocks.PRISMARINE_RELAY))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.LONG_DISTANCE_LASER.asItem(), 1)
                .pattern("DJD").pattern("DPD").pattern("PPP")
                .define('D', NTBlocks.DARK_PRISMARINE_PILLAR)
                .define('J', NTBlocks.LASER_JUNCTION)
                .define('P', NTBlocks.POLISHED_PRISMARINE)
                .unlockedBy("has_item", has(NTBlocks.LASER_JUNCTION))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.AQUATIC_CATALYST.asItem(), 1)
                .pattern("PDP").pattern("DPD").pattern("PDP")
                .define('D', NTBlocks.DARK_PRISMARINE_PILLAR)
                .define('P', NTBlocks.POLISHED_PRISMARINE)
                .unlockedBy("has_item", has(NTBlocks.POLISHED_PRISMARINE))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.DRAIN.asItem(), 1)
                .pattern("C C").pattern("C C").pattern("CCC")
                .define('C', NTItems.CAST_IRON_INGOT)
                .unlockedBy("has_item", has(NTItems.CAST_IRON_INGOT))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.DRAIN_WALL.asItem(), 1)
                .pattern("CCC").pattern("C C").pattern("CCC")
                .define('C', NTItems.CAST_IRON_INGOT)
                .unlockedBy("has_item", has(NTItems.CAST_IRON_INGOT))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.AUGMENTATION_STATION.asItem(), 1)
                .pattern("APA").pattern("PCP").pattern("PPP")
                .define('A', NTItems.AQUARINE_STEEL_INGOT)
                .define('P', NTBlocks.POLISHED_PRISMARINE)
                .define('C', NTItems.PRISMARINE_CRYSTAL_SHARD)
                .unlockedBy("has_item", has(NTItems.PRISMARINE_CRYSTAL_SHARD))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.AUGMENTATION_STATION_EXTENSION.asItem(), 2)
                .pattern("PAP").pattern("PCP").pattern("PPP")
                .define('A', NTItems.AQUARINE_STEEL_INGOT)
                .define('P', NTBlocks.POLISHED_PRISMARINE)
                .define('C', NTItems.PRISMARINE_CRYSTAL_SHARD)
                .unlockedBy("has_item", has(NTItems.PRISMARINE_CRYSTAL_SHARD))
                .save(pRecipeOutput);

    }

    private static void ancientItemsRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ItemEtchingRecipeBuilder.newRecipe(NTItems.VALVE.toStack())
                .ingredient(NTItems.ANCIENT_VALVE.toStack())
                .duration(200)
                .save(pRecipeOutput);

        ItemEtchingRecipeBuilder.newRecipe(NTItems.GEAR.toStack())
                .ingredient(NTItems.RUSTY_GEAR.toStack())
                .duration(160)
                .save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NTItems.WHISK.get(), 1)
                .requires(NTItems.BROKEN_WHISK.get())
                .requires(NTItems.CAST_IRON_NUGGET.get(), 4)
                .unlockedBy("has_item", has(NTItems.BROKEN_WHISK.get()))
                .save(pRecipeOutput);
    }

    private static void aquarineSteelToolsRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.AQUARINE_PICKAXE.get().getDefaultInstance())
                .pattern("AAA")
                .pattern(" R ")
                .pattern(" R ")
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.get()))
                .define('A', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('R', NTItems.CAST_IRON_ROD.get())
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.AQUARINE_SHOVEL.get().getDefaultInstance())
                .pattern(" A ")
                .pattern(" R ")
                .pattern(" R ")
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.get()))
                .define('A', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('R', NTItems.CAST_IRON_ROD.get())
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.AQUARINE_AXE.get().getDefaultInstance())
                .pattern("AA ")
                .pattern("AR ")
                .pattern(" R ")
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.get()))
                .define('A', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('R', NTItems.CAST_IRON_ROD.get())
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.AQUARINE_HOE.get().getDefaultInstance())
                .pattern("AA ")
                .pattern(" R ")
                .pattern(" R ")
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.get()))
                .define('A', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('R', NTItems.CAST_IRON_ROD.get())
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.AQUARINE_SWORD.get().getDefaultInstance())
                .pattern(" A ")
                .pattern(" A ")
                .pattern(" R ")
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.get()))
                .define('A', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('R', NTItems.CAST_IRON_ROD.get())
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.AQUARINE_WRENCH.get().getDefaultInstance())
                .pattern("A A")
                .pattern(" A ")
                .pattern(" A ")
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.get()))
                .define('A', NTItems.AQUARINE_STEEL_INGOT.get())
                .save(pRecipeOutput);
    }

    private static void utilityRecipes(@NotNull RecipeOutput pRecipeOutput) {
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
                .define('L', Tags.Items.DYED_BLUE)
                .unlockedBy("has_item", has(NTItems.CAST_IRON_ROD))
                .save(pRecipeOutput);
    }

    private static void miscItemsRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NTItems.BROWN_POLYMER.get(), 1)
                .requires(Items.DRIED_KELP)
                .requires(Items.BROWN_DYE)
                .unlockedBy("has_item", has(Items.DRIED_KELP))
                .save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NTItems.AQUARINE_STEEL_COMPOUND.get(), 2)
                .requires(Items.RAW_IRON)
                .requires(Items.PRISMARINE_CRYSTALS)
                .unlockedBy("has_item", has(Items.PRISMARINE_CRYSTALS))
                .save(pRecipeOutput);
    }

    private static void castIronRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.CAST_IRON_INGOT, 1)
                .pattern("NNN")
                .pattern("NNN")
                .pattern("NNN")
                .define('N', NTItems.CAST_IRON_NUGGET.asItem())
                .unlockedBy("has_item", has(NTItems.CAST_IRON_INGOT.get()))
                .save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NTItems.CAST_IRON_NUGGET.get(), 9)
                .requires(NTItems.CAST_IRON_INGOT.get())
                .unlockedBy("has_item", has(NTItems.CAST_IRON_INGOT.get()))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.CAST_IRON_ROD.get(), 2)
                .pattern("C")
                .pattern("C")
                .define('C', NTItems.CAST_IRON_INGOT.asItem())
                .unlockedBy("has_item", has(Items.DEEPSLATE))
                .save(pRecipeOutput);
    }

    private static void decoBlockRecipes(@NotNull RecipeOutput pRecipeOutput) {
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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.DARK_PRISMARINE_PILLAR.asItem(), 2)
                .pattern("D")
                .pattern("D")
                .define('D', Blocks.DARK_PRISMARINE.asItem())
                .unlockedBy("has_item", has(Blocks.DARK_PRISMARINE))
                .save(pRecipeOutput);
    }

    private static void aquarineSteelArmorRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.AQUARINE_HELMET.get().getDefaultInstance())
                .pattern("ICI")
                .pattern("I I")
                .define('I', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('C', NTItems.PRISMARINE_CRYSTAL_SHARD.get())
                .unlockedBy("has_item", has(NTItems.PRISMARINE_CRYSTAL_SHARD.get()))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.AQUARINE_CHESTPLATE.get().getDefaultInstance())
                .pattern("I I")
                .pattern("ICI")
                .pattern("IVI")
                .define('I', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('C', NTItems.PRISMARINE_CRYSTAL_SHARD.get())
                .define('V', NTItems.VALVE.get())
                .unlockedBy("has_item", has(NTItems.PRISMARINE_CRYSTAL_SHARD.get()))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.AQUARINE_LEGGINGS.get().getDefaultInstance())
                .pattern("IVI")
                .pattern("C C")
                .pattern("I I")
                .define('I', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('C', NTItems.PRISMARINE_CRYSTAL_SHARD.get())
                .define('V', NTItems.VALVE.get())
                .unlockedBy("has_item", has(NTItems.PRISMARINE_CRYSTAL_SHARD.get()))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.AQUARINE_BOOTS.get().getDefaultInstance())
                .pattern("C C")
                .pattern("I I")
                .define('I', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('C', NTItems.PRISMARINE_CRYSTAL_SHARD.get())
                .unlockedBy("has_item", has(NTItems.PRISMARINE_CRYSTAL_SHARD.get()))
                .save(pRecipeOutput);
    }

    private static void divingArmorRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.DIVING_HELMET.get().getDefaultInstance())
                .pattern("CCC")
                .pattern("CGC")
                .define('C', Items.COPPER_INGOT.asItem())
                .define('G', Items.GLASS_PANE.asItem())
                .unlockedBy("has_item", has(Items.COPPER_INGOT))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.DIVING_CHESTPLATE.get().getDefaultInstance())
                .pattern("C C")
                .pattern("BCB")
                .pattern("BBB")
                .define('B', NTItems.BROWN_POLYMER.get())
                .define('C', Tags.Items.INGOTS_COPPER)
                .unlockedBy("has_item", has(NTItems.BROWN_POLYMER))
                .save(pRecipeOutput, "diving_chestplate");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.DIVING_LEGGINGS.get().getDefaultInstance())
                .pattern("BBB")
                .pattern("B B")
                .pattern("B B")
                .define('B', NTItems.BROWN_POLYMER.get())
                .unlockedBy("has_item", has(NTItems.BROWN_POLYMER))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.DIVING_BOOTS.get().getDefaultInstance())
                .pattern("B B")
                .pattern("B B")
                .define('B', NTItems.BROWN_POLYMER.get())
                .unlockedBy("has_item", has(NTItems.BROWN_POLYMER))
                .save(pRecipeOutput);

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
    }

    private static void chemistryRecipes(@NotNull RecipeOutput pRecipeOutput) {
        MixingRecipeBuilder.newRecipe(ItemStack.EMPTY)
                .ingredients(iwcFromItemLike(Items.DRIED_KELP, 4),
                        iwcFromItemLike(Items.SLIME_BALL, 2),
                        iwcFromItemLike(Items.PRISMARINE_CRYSTALS, 1),
                        iwcFromItemLike(Items.SEAGRASS, 5))
                .duration(200)
                .fluidIngredient(new FluidStack(NTFluids.SALT_WATER_SOURCE.get(), 1000))
                .fluidResult(new FluidStack(NTFluids.EAS_SOURCE.get(), 1000))
                .save(pRecipeOutput);
    }

    private static void augmentationRecipes(@NotNull RecipeOutput pRecipeOutput) {
        AugmentationRecipeBuilder.newRecipe(NTAugments.DOLPHIN_FIN.get())
                .augmentItem(NTItems.DOLPHIN_FIN.get(), "Greatly improved swimming speed")
                .ingredients(IngredientWithCount.fromItemLike(NTItems.DOLPHIN_FIN.get()))
                .save(pRecipeOutput, "dolphin_fin");

        AugmentationRecipeBuilder.newRecipe(NTAugments.DROWNED_LUNG.get())
                .augmentItem(NTItems.DROWNED_LUNGS.get(), "Unlimited underwater breathing")
                .ingredients(IngredientWithCount.fromItemLike(NTItems.DROWNED_LUNGS.get()))
                .save(pRecipeOutput, "drowned_lung");

        AugmentationRecipeBuilder.newRecipe(NTAugments.GUARDIAN_EYE_AUGMENT.get())
                .augmentItem(NTItems.GUARDIAN_EYE.get(), "Shoots lasers at enemies you are looking at")
                .ingredients(IngredientWithCount.fromItemLike(NTItems.GUARDIAN_EYE.get()))
                .save(pRecipeOutput, "guardian_eye_augment");
    }

    private static @NotNull IngredientWithCount iwcFromItemLike(Item item, int count) {
        return IngredientWithCount.fromItemLike(item, count);
    }
}
