package com.portingdeadmods.nautec.datagen;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.recipes.utils.IngredientWithCount;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.datagen.recipeBuilder.*;
import com.portingdeadmods.nautec.registries.NTAugments;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTFluids;
import com.portingdeadmods.nautec.registries.NTItems;
import com.portingdeadmods.nautec.tags.NTTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
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
        aquaticCatalystRecipes(pRecipeOutput);

        aquarineSteelRecipes(pRecipeOutput);

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

        machineRecipes(pRecipeOutput);

        laserDeviceRecipes(pRecipeOutput);

        drainRecipes(pRecipeOutput);

        augmentationStationRecipes(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.GLASS_VIAL.asItem(), 3)
                .pattern("G G")
                .pattern("G G")
                .pattern(" G ")
                .define('G', Items.GLASS)
                .unlockedBy("has_item", has(Items.GLASS))
                .save(pRecipeOutput, Nautec.rl("glass_vial"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.CLAW_ROBOT_ARM.asItem(), 1)
                .pattern("AB ")
                .pattern(" AB")
                .pattern("  A")
                .define('A', NTItems.AQUARINE_STEEL_INGOT)
                .define('B', NTItems.CAST_IRON_ROD)
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT))
                .save(pRecipeOutput, Nautec.rl("claw_robot_arm"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.PRISM_MONOCLE.asItem(), 1)
                .pattern("AAA")
                .pattern("AP ")
                .define('A', NTItems.AQUARINE_STEEL_INGOT)
                .define('P', Items.PRISMARINE_CRYSTALS)
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT))
                .save(pRecipeOutput, Nautec.rl("prism_monocle"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NTItems.NAUTEC_GUIDE.get(), 1)
                .requires(Items.BOOK)
                .requires(NTItems.CAST_IRON_NUGGET.get(), 1)
                .unlockedBy("has_item", has(NTItems.CAST_IRON_NUGGET.get()))
                .save(pRecipeOutput, Nautec.rl("nautec_guide"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(Tags.Items.INGOTS_IRON), RecipeCategory.MISC, NTItems.CAST_IRON_INGOT.get(), 0.2f, 100)
                .unlockedBy("has_item", has(Items.IRON_INGOT))
                .save(pRecipeOutput, Nautec.rl("cast_iron_ingot_blasting"));
    }

    private static void aquaticCatalystRecipes(@NotNull RecipeOutput pRecipeOutput) {
        AquaticCatalystChannelingRecipeBuilder.newRecipe(Ingredient.of(Items.KELP))
                .powerAmount(700)
                .purity(0)
                .duration(100)
                .save(pRecipeOutput, Nautec.rl("kelp"));

        AquaticCatalystChannelingRecipeBuilder.newRecipe(Ingredient.of(NTTags.Items.CORALS))
                .powerAmount(2000)
                .purity(0.4f)
                .duration(200)
                .save(pRecipeOutput, Nautec.rl("corals"));
    }

    private static void aquarineSteelRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ItemTransformationRecipeBuilder.newRecipe(new ItemStack(NTItems.AQUARINE_STEEL_INGOT.get(), 1))
                .ingredient(new ItemStack(NTItems.AQUARINE_STEEL_COMPOUND.get()))
                .purity(3)
                .duration(100)
                .save(pRecipeOutput, Nautec.rl("aquarine_steel_ingot"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NTBlocks.AQUARINE_STEEL_BLOCK.asItem(), 1)
                .requires(NTItems.AQUARINE_STEEL_INGOT, 9)
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT))
                .save(pRecipeOutput, Nautec.rl( "cast_iron_ingot_from_blasting"));
    }

    private static void augmentationStationRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.AUGMENTATION_STATION.asItem(), 1)
                .pattern("ACA")
                .pattern("PEP")
                .pattern("AAA")
                .define('A', NTItems.AQUARINE_STEEL_INGOT)
                .define('P', NTBlocks.POLISHED_PRISMARINE)
                .define('C', NTItems.PRISMARINE_CRYSTAL_SHARD)
                .define('E', NTItems.ELDRITCH_HEART)
                .unlockedBy("has_item", has(NTItems.PRISMARINE_CRYSTAL_SHARD))
                .save(pRecipeOutput, Nautec.rl("augmentation_station"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.AUGMENTATION_STATION_EXTENSION.asItem(), 2)
                .pattern("ASA")
                .pattern("APA")
                .pattern("ACA")
                .define('A', NTItems.AQUARINE_STEEL_INGOT)
                .define('P', NTBlocks.POLISHED_PRISMARINE)
                .define('C', NTItems.LASER_CHANNELING_COIL)
                .define('S', NTItems.PRISMARINE_CRYSTAL_SHARD)
                .unlockedBy("has_item", has(NTItems.PRISMARINE_CRYSTAL_SHARD))
                .save(pRecipeOutput, Nautec.rl("augmentation_station_extension"));
    }

    private static void drainRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.DRAIN.asItem(), 1)
                .pattern("CVC")
                .pattern("AGA")
                .pattern("CCC")
                .define('C', NTItems.CAST_IRON_INGOT)
                .define('V', NTItems.VALVE)
                .define('G', NTItems.GEAR)
                .define('A', NTItems.AQUARINE_STEEL_INGOT)
                .unlockedBy("has_item", has(NTItems.VALVE))
                .save(pRecipeOutput, Nautec.rl("drain"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.DRAIN_WALL.asItem(), 2)
                .pattern("CCC")
                .pattern("R R")
                .pattern("CCC")
                .define('C', NTItems.CAST_IRON_INGOT)
                .define('R', NTItems.CAST_IRON_ROD)
                .unlockedBy("has_item", has(NTItems.CAST_IRON_INGOT))
                .save(pRecipeOutput, Nautec.rl("drain_wall"));
    }

    private static void laserDeviceRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.PRISMARINE_RELAY.asItem(), 4)
                .pattern("AAA")
                .pattern("   ")
                .pattern("AAA")
                .define('A', NTBlocks.POLISHED_PRISMARINE)
                .unlockedBy("has_item", has(NTBlocks.POLISHED_PRISMARINE))
                .save(pRecipeOutput, Nautec.rl("prismarine_relay"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.LASER_JUNCTION.asItem(), 2)
                .pattern("ARA")
                .pattern("RHR")
                .pattern("ARA")
                .define('A', NTItems.AQUARINE_STEEL_INGOT)
                .define('R', NTBlocks.PRISMARINE_RELAY)
                .define('H', Items.HEART_OF_THE_SEA)
                .unlockedBy("has_item", has(NTBlocks.PRISMARINE_RELAY))
                .save(pRecipeOutput, Nautec.rl("laser_junction"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.LONG_DISTANCE_LASER.asItem(), 1)
                .pattern("DRD")
                .pattern("PSP")
                .pattern("PRP")
                .define('D', Blocks.DARK_PRISMARINE)
                .define('R', NTBlocks.PRISMARINE_RELAY)
                .define('P', NTBlocks.POLISHED_PRISMARINE)
                .define('S', NTItems.PRISMARINE_CRYSTAL_SHARD)
                .unlockedBy("has_item", has(NTBlocks.PRISMARINE_RELAY))
                .save(pRecipeOutput, Nautec.rl("long_distance_laser"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.AQUATIC_CATALYST.asItem(), 1)
                .pattern("PDP")
                .pattern("D D")
                .pattern("PDP")
                .define('D', NTBlocks.DARK_PRISMARINE_PILLAR)
                .define('P', NTBlocks.POLISHED_PRISMARINE)
                .unlockedBy("has_item", has(NTBlocks.POLISHED_PRISMARINE))
                .save(pRecipeOutput, Nautec.rl("aquatic_catalyst"));
    }

    private static void machineRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.MIXER.asItem())
                .pattern("DGD")
                .pattern("PWP")
                .pattern("PAP")
                .define('G', NTItems.GEAR)
                .define('D', NTBlocks.POLISHED_PRISMARINE)
                .define('P', NTBlocks.POLISHED_PRISMARINE)
                .define('W', NTItems.WHISK)
                .define('A', NTItems.AQUARINE_STEEL_INGOT)
                .unlockedBy("has_item", has(NTItems.PRISMARINE_CRYSTAL_SHARD))
                .save(pRecipeOutput, Nautec.rl("mixer"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.CHARGER.asItem())
                .pattern("PAP")
                .pattern("DCD")
                .define('P', NTBlocks.POLISHED_PRISMARINE)
                .define('A', NTItems.AQUARINE_STEEL_INGOT)
                .define('D', Blocks.DARK_PRISMARINE)
                .define('C', NTItems.LASER_CHANNELING_COIL)
                .unlockedBy("has_item", has(NTItems.PRISMARINE_CRYSTAL_SHARD))
                .save(pRecipeOutput, Nautec.rl("charger"));
    }

    private static void ancientItemsRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ItemEtchingRecipeBuilder.newRecipe(NTItems.VALVE.toStack())
                .ingredient(NTItems.ANCIENT_VALVE.toStack())
                .duration(200)
                .save(pRecipeOutput, Nautec.rl("valve"));

        ItemEtchingRecipeBuilder.newRecipe(NTItems.GEAR.toStack())
                .ingredient(NTItems.RUSTY_GEAR.toStack())
                .duration(160)
                .save(pRecipeOutput, Nautec.rl("gear"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NTItems.WHISK.get(), 1)
                .requires(NTItems.BROKEN_WHISK.get())
                .requires(NTItems.CAST_IRON_NUGGET.get(), 4)
                .unlockedBy("has_item", has(NTItems.BROKEN_WHISK.get()))
                .save(pRecipeOutput, Nautec.rl("whisk"));

        ItemTransformationRecipeBuilder.newRecipe(NTItems.LASER_CHANNELING_COIL.toStack())
                .ingredient(NTItems.BURNT_COIL.get())
                .purity(1.5f)
                .duration(200)
                .save(pRecipeOutput, Nautec.rl("laser_channeling_coil"));
    }

    private static void aquarineSteelToolsRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.AQUARINE_PICKAXE.get().getDefaultInstance())
                .pattern("AGA")
                .pattern(" C ")
                .pattern(" R ")
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.get()))
                .define('A', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('R', NTItems.CAST_IRON_ROD.get())
                .define('G', NTItems.GEAR.get())
                .define('C', NTItems.LASER_CHANNELING_COIL.get())
                .save(pRecipeOutput, Nautec.rl("aquarine_pickaxe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.AQUARINE_SHOVEL.get().getDefaultInstance())
                .pattern(" A ")
                .pattern(" G ")
                .pattern(" R ")
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.get()))
                .define('A', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('G', NTItems.GEAR.get())
                .define('R', NTItems.CAST_IRON_ROD.get())
                .save(pRecipeOutput, Nautec.rl("aquarine_shovel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.AQUARINE_AXE.get().getDefaultInstance())
                .pattern("AG ")
                .pattern("AR ")
                .pattern(" R ")
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.get()))
                .define('A', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('G', NTItems.GEAR.get())
                .define('R', NTItems.CAST_IRON_ROD.get())
                .save(pRecipeOutput, Nautec.rl("aquarine_axe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.AQUARINE_HOE.get().getDefaultInstance())
                .pattern("AA ")
                .pattern(" C ")
                .pattern(" R ")
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.get()))
                .define('A', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('R', NTItems.CAST_IRON_ROD.get())
                .define('C', NTItems.LASER_CHANNELING_COIL.get())
                .save(pRecipeOutput, Nautec.rl("aquarine_hoe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.AQUARINE_SWORD.get().getDefaultInstance())
                .pattern(" A ")
                .pattern(" A ")
                .pattern(" C ")
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.get()))
                .define('A', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('C', NTItems.LASER_CHANNELING_COIL.get())
                .save(pRecipeOutput, Nautec.rl("aquarine_sword"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.AQUARINE_WRENCH.get().getDefaultInstance())
                .pattern("A A")
                .pattern(" A ")
                .pattern(" A ")
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.get()))
                .define('A', NTItems.AQUARINE_STEEL_INGOT.get())
                .save(pRecipeOutput, Nautec.rl("aquarine_wrench"));
    }

    private static void utilityRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.PRISMATIC_BATTERY.get(), 1)
                .pattern("SRS")
                .pattern("ACA")
                .define('A', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('R', Items.REDSTONE)
                .define('S', NTItems.PRISMARINE_CRYSTAL_SHARD.get())
                .define('C', NTItems.LASER_CHANNELING_COIL.get())
                .unlockedBy("has_item", has(NTItems.PRISMARINE_CRYSTAL_SHARD))
                .save(pRecipeOutput, Nautec.rl("prismatic_battery"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.CROWBAR.get(), 1)
                .pattern(" LR")
                .pattern("LRL")
                .pattern("RL ")
                .define('R', NTItems.CAST_IRON_ROD.get())
                .define('L', Tags.Items.DYES_BLUE)
                .unlockedBy("has_item", has(NTItems.CAST_IRON_ROD))
                .save(pRecipeOutput, Nautec.rl("crowbar"));
    }

    private static void miscItemsRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NTItems.BROWN_POLYMER.get(), 1)
                .requires(Items.DRIED_KELP)
                .requires(Items.BROWN_DYE)
                .unlockedBy("has_item", has(Items.DRIED_KELP))
                .save(pRecipeOutput, Nautec.rl("brown_polymer"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NTItems.AQUARINE_STEEL_COMPOUND.get(), 2)
                .requires(Items.RAW_IRON)
                .requires(Items.PRISMARINE_CRYSTALS)
                .unlockedBy("has_item", has(Items.PRISMARINE_CRYSTALS))
                .save(pRecipeOutput, Nautec.rl("aquarine_steel_compound"));
    }

    private static void castIronRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.CAST_IRON_INGOT, 1)
                .pattern("NNN")
                .pattern("NNN")
                .pattern("NNN")
                .define('N', NTItems.CAST_IRON_NUGGET.asItem())
                .unlockedBy("has_item", has(NTItems.CAST_IRON_INGOT.get()))
                .save(pRecipeOutput, Nautec.rl("cast_iron_ingot"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NTItems.CAST_IRON_NUGGET.get(), 9)
                .requires(NTItems.CAST_IRON_INGOT.get())
                .unlockedBy("has_item", has(NTItems.CAST_IRON_INGOT.get()))
                .save(pRecipeOutput, Nautec.rl("cast_iron_nugget"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.CAST_IRON_ROD.get(), 2)
                .pattern("C")
                .pattern("C")
                .define('C', NTItems.CAST_IRON_INGOT.asItem())
                .unlockedBy("has_item", has(Items.DEEPSLATE))
                .save(pRecipeOutput, Nautec.rl("cast_iron_rod"));
    }

    private static void decoBlockRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.CHISELED_DARK_PRISMARINE.asItem(), 4)
                .pattern("DD")
                .pattern("DD")
                .define('D', Blocks.DARK_PRISMARINE.asItem())
                .unlockedBy("has_item", has(Blocks.DARK_PRISMARINE))
                .save(pRecipeOutput, Nautec.rl("chiseled_dark_prismarine"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.POLISHED_PRISMARINE.asItem(), 4)
                .pattern("DD")
                .pattern("DD")
                .define('D', Blocks.PRISMARINE.asItem())
                .unlockedBy("has_item", has(Blocks.DARK_PRISMARINE))
                .save(pRecipeOutput, Nautec.rl("polished_prismarine"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTBlocks.DARK_PRISMARINE_PILLAR.asItem(), 2)
                .pattern("D")
                .pattern("D")
                .define('D', Blocks.DARK_PRISMARINE.asItem())
                .unlockedBy("has_item", has(Blocks.DARK_PRISMARINE))
                .save(pRecipeOutput, Nautec.rl("dark_prismarine_pillar"));
    }

    private static void aquarineSteelArmorRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.AQUARINE_HELMET.get().getDefaultInstance())
                .pattern("ICI")
                .pattern("I I")
                .define('I', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('C', NTItems.PRISMARINE_CRYSTAL_SHARD.get())
                .unlockedBy("has_item", has(NTItems.PRISMARINE_CRYSTAL_SHARD.get()))
                .save(pRecipeOutput, Nautec.rl("aquarine_helmet"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.AQUARINE_CHESTPLATE.get().getDefaultInstance())
                .pattern("I I")
                .pattern("ICI")
                .pattern("IVI")
                .define('I', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('C', NTItems.PRISMARINE_CRYSTAL_SHARD.get())
                .define('V', NTItems.VALVE.get())
                .unlockedBy("has_item", has(NTItems.PRISMARINE_CRYSTAL_SHARD.get()))
                .save(pRecipeOutput, Nautec.rl("aquarine_chestplate"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.AQUARINE_LEGGINGS.get().getDefaultInstance())
                .pattern("IVI")
                .pattern("C C")
                .pattern("I I")
                .define('I', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('C', NTItems.PRISMARINE_CRYSTAL_SHARD.get())
                .define('V', NTItems.VALVE.get())
                .unlockedBy("has_item", has(NTItems.PRISMARINE_CRYSTAL_SHARD.get()))
                .save(pRecipeOutput, Nautec.rl("aquarine_leggings"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.AQUARINE_BOOTS.get().getDefaultInstance())
                .pattern("C C")
                .pattern("I I")
                .define('I', NTItems.AQUARINE_STEEL_INGOT.get())
                .define('C', NTItems.PRISMARINE_CRYSTAL_SHARD.get())
                .unlockedBy("has_item", has(NTItems.PRISMARINE_CRYSTAL_SHARD.get()))
                .save(pRecipeOutput, Nautec.rl("aquarine_boots"));
    }

    private static void divingArmorRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.DIVING_HELMET.get().getDefaultInstance())
                .pattern("CCC")
                .pattern("CGC")
                .define('C', Items.COPPER_INGOT.asItem())
                .define('G', Items.GLASS_PANE.asItem())
                .unlockedBy("has_item", has(Items.COPPER_INGOT))
                .save(pRecipeOutput, Nautec.rl("diving_helmet"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.DIVING_CHESTPLATE.get().getDefaultInstance())
                .pattern("C C")
                .pattern("BCB")
                .pattern("BBB")
                .define('B', NTItems.BROWN_POLYMER.get())
                .define('C', Tags.Items.INGOTS_COPPER)
                .unlockedBy("has_item", has(NTItems.BROWN_POLYMER))
                .save(pRecipeOutput, Nautec.rl("diving_chestplate"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.DIVING_LEGGINGS.get().getDefaultInstance())
                .pattern("BBB")
                .pattern("B B")
                .pattern("B B")
                .define('B', NTItems.BROWN_POLYMER.get())
                .unlockedBy("has_item", has(NTItems.BROWN_POLYMER))
                .save(pRecipeOutput, Nautec.rl("diving_leggings"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.DIVING_BOOTS.get().getDefaultInstance())
                .pattern("B B")
                .pattern("B B")
                .define('B', NTItems.BROWN_POLYMER.get())
                .unlockedBy("has_item", has(NTItems.BROWN_POLYMER))
                .save(pRecipeOutput, Nautec.rl("diving_boots"));

        ItemStack divingChestplate = NTItems.DIVING_CHESTPLATE.get().getDefaultInstance();
        divingChestplate.set(NTDataComponents.OXYGEN, 600);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, divingChestplate)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .unlockedBy("has_item", has(NTItems.DIVING_CHESTPLATE.get()))
                .define('G', NTItems.AIR_BOTTLE.get())
                .define('D', NTItems.DIVING_CHESTPLATE.get())
                .save(pRecipeOutput, Nautec.rl("diving_chestplate_oxygen"));

        ItemStack inputCrate = NTBlocks.RUSTY_CRATE.toStack();
        ItemStack outputCrate = NTBlocks.CRATE.toStack();

        ItemEtchingRecipeBuilder.newRecipe(outputCrate)
                .ingredient(inputCrate)
                .duration(200)
                .save(pRecipeOutput, Nautec.rl("crate"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NTFluids.ETCHING_ACID.getBucket())
                .requires(Items.POISONOUS_POTATO)
                .requires(Items.GUNPOWDER)
                .requires(Items.BONE_MEAL)
                .requires(Items.WATER_BUCKET)
                .requires(Items.PUFFERFISH)
                .unlockedBy("has_item", has(Items.POISONOUS_POTATO))
                .save(pRecipeOutput, Nautec.rl("etching_acid_crafting"));
    }

    private static void chemistryRecipes(@NotNull RecipeOutput pRecipeOutput) {
        MixingRecipeBuilder.newRecipe(ItemStack.EMPTY)
                .ingredients(iwcFromItemLike(Items.DRIED_KELP, 4),
                        iwcFromItemLike(Items.SLIME_BALL, 2),
                        iwcFromItemLike(Items.PRISMARINE_CRYSTALS, 1),
                        iwcFromItemLike(Items.SEAGRASS, 5))
                .duration(200)
                .fluidIngredient(new FluidStack(NTFluids.SALT_WATER.getStillFluid(), 1000))
                .fluidResult(new FluidStack(NTFluids.EAS.getStillFluid(), 1000))
                .save(pRecipeOutput, Nautec.rl("eas"));

        MixingRecipeBuilder.newRecipe(ItemStack.EMPTY)
                .ingredients(iwcFromItemLike(Items.PUFFERFISH, 1),
                        iwcFromItemLike(Items.GUNPOWDER, 1),
                        iwcFromItemLike(Items.BONE_MEAL, 1))
                .duration(150)
                .fluidIngredient(new FluidStack(NTFluids.SALT_WATER.getStillFluid(), 1000))
                .fluidResult(new FluidStack(NTFluids.ETCHING_ACID.getStillFluid(), 1000))
                .save(pRecipeOutput, Nautec.rl("etching_acid_mixing"));

        MixingRecipeBuilder.newRecipe(NTItems.AQUARINE_STEEL_COMPOUND.toStack(5))
                .ingredients(iwcFromItemLike(Items.RAW_IRON, 2),
                        iwcFromItemLike(Items.PRISMARINE_CRYSTALS, 1))
                .duration(100)
                .fluidIngredient(new FluidStack(NTFluids.SALT_WATER.getStillFluid(), 1000))
                .fluidResult(FluidStack.EMPTY)
                .save(pRecipeOutput, Nautec.rl("aquarine_steel_compound_mixing"));
    }

    private static void augmentationRecipes(@NotNull RecipeOutput pRecipeOutput) {
        AugmentationRecipeBuilder.newRecipe(NTAugments.DOLPHIN_FIN.get())
                .augmentItem(NTItems.DOLPHIN_FIN.get(), "Greatly improved swimming speed")
                .ingredients(IngredientWithCount.fromItemLike(NTItems.DOLPHIN_FIN.get()))
                .save(pRecipeOutput, Nautec.rl("dolphin_fin"));

        AugmentationRecipeBuilder.newRecipe(NTAugments.DROWNED_LUNG.get())
                .augmentItem(NTItems.DROWNED_LUNGS.get(), "Unlimited underwater breathing")
                .ingredients(IngredientWithCount.fromItemLike(NTItems.DROWNED_LUNGS.get()))
                .save(pRecipeOutput, Nautec.rl("drowned_lung"));

        AugmentationRecipeBuilder.newRecipe(NTAugments.GUARDIAN_EYE.get())
                .augmentItem(NTItems.GUARDIAN_EYE.get(), "Shoots lasers at enemies you are looking at")
                .ingredients(IngredientWithCount.fromItemLike(NTItems.GUARDIAN_EYE.get()))
                .save(pRecipeOutput, Nautec.rl("guardian_eye"));

        AugmentationRecipeBuilder.newRecipe(NTAugments.ELDRITCH_HEART.get())
                .augmentItem(NTItems.ELDRITCH_HEART.get(), "Increased health regeneration when underwater")
                .ingredients(IngredientWithCount.fromItemLike(NTItems.ELDRITCH_HEART.get()))
                .save(pRecipeOutput, Nautec.rl("eldritch_heart"));
    }

    private static @NotNull IngredientWithCount iwcFromItemLike(Item item, int count) {
        return IngredientWithCount.fromItemLike(item, count);
    }
}
