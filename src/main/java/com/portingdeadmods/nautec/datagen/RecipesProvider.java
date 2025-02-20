package com.portingdeadmods.nautec.datagen;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.recipes.utils.IngredientWithCount;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.datagen.recipeBuilder.*;
import com.portingdeadmods.nautec.registries.*;
import com.portingdeadmods.nautec.utils.ranges.IntRange;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
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

        buildingBlockRecipes(pRecipeOutput);

        utilityRecipes(pRecipeOutput);

        castIronRecipes(pRecipeOutput);

        miscItemsRecipes(pRecipeOutput);

        machineRecipes(pRecipeOutput);

        laserDeviceRecipes(pRecipeOutput);

        drainRecipes(pRecipeOutput);

        augmentationStationRecipes(pRecipeOutput);

        mutationRecipes(pRecipeOutput);

        incubationRecipes(pRecipeOutput);

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

        brownPolymerRecipes(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NTItems.NAUTEC_GUIDE.get(), 1)
                .requires(Items.BOOK)
                .requires(NTItems.CAST_IRON_NUGGET.get(), 1)
                .unlockedBy("has_item", has(NTItems.CAST_IRON_NUGGET.get()))
                .save(pRecipeOutput, Nautec.rl("nautec_guide"));


        SimpleCookingRecipeBuilder.blasting(Ingredient.of(NTBlocks.ANCHOR), RecipeCategory.MISC, NTItems.CAST_IRON_INGOT.toStack(11), 0.2f, 400)
                .unlockedBy("has_item", has(Items.IRON_INGOT))
                .save(pRecipeOutput, Nautec.rl("cast_iron_ingot_from_anchor_blasting"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(NTBlocks.OIL_BARREL), RecipeCategory.MISC, NTItems.CAST_IRON_INGOT.toStack(5), 0.2f, 400)
                .unlockedBy("has_item", has(Items.IRON_INGOT))
                .save(pRecipeOutput, Nautec.rl("cast_iron_ingot_from_oil_barrel_blasting"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(NTItems.CAST_IRON_COMPOUND), RecipeCategory.MISC, NTItems.CAST_IRON_INGOT.get(), 0.2f, 100)
                .unlockedBy("has_item", has(Items.IRON_INGOT))
                .save(pRecipeOutput, Nautec.rl("cast_iron_ingot_blasting"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(NTItems.CAST_IRON_COMPOUND), RecipeCategory.MISC, NTItems.CAST_IRON_INGOT.get(), 0.2f, 200)
                .unlockedBy("has_item", has(Items.IRON_INGOT))
                .save(pRecipeOutput, Nautec.rl("cast_iron_ingot_smelting"));
    }

    private static void aquaticCatalystRecipes(@NotNull RecipeOutput pRecipeOutput) {
        AquaticCatalystChannelingRecipeBuilder.newRecipe(Ingredient.of(Items.PRISMARINE_CRYSTALS))
                .powerAmount(1000)
                .purity(0.8f)
                .duration(160)
                .save(pRecipeOutput, Nautec.rl("prismarine_crystals_to_ap"));

        AquaticCatalystChannelingRecipeBuilder.newRecipe(Ingredient.of(Items.PRISMARINE_SHARD))
                .powerAmount(2000)
                .purity(0.4f)
                .duration(160)
                .save(pRecipeOutput, Nautec.rl("prismarine_shards_to_ap"));

        AquaticCatalystChannelingRecipeBuilder.newRecipe(Ingredient.of(NTItems.PRISMARINE_CRYSTAL_SHARD))
                .powerAmount(2400)
                .purity(1.2f)
                .duration(200)
                .save(pRecipeOutput, Nautec.rl("prismarine_crystal_shards_to_ap"));
    }

    private static void aquarineSteelRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ItemTransformationRecipeBuilder.newRecipe(new ItemStack(NTItems.AQUARINE_STEEL_INGOT.get(), 1))
                .ingredient(new ItemStack(NTItems.AQUARINE_STEEL_COMPOUND.get()))
                .purity(0)
                .duration(100)
                .save(pRecipeOutput, Nautec.rl("aquarine_steel_ingot"));

        nineBlockStorageRecipes(pRecipeOutput, RecipeCategory.MISC, NTItems.AQUARINE_STEEL_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, NTBlocks.AQUARINE_STEEL_BLOCK.get());
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
                .pattern("PCP")
                .pattern("P P")
                .pattern("PCP")
                .define('C', Items.PRISMARINE_CRYSTALS)
                .define('P', NTBlocks.POLISHED_PRISMARINE)
                .unlockedBy("has_item", has(NTBlocks.POLISHED_PRISMARINE))
                .save(pRecipeOutput, Nautec.rl("aquatic_catalyst"));
    }

    private static void machineRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, NTBlocks.MIXER.asItem())
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

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, NTBlocks.FISHING_STATION.asItem())
                .pattern("DAD")
                .pattern("RGR")
                .pattern("DAD")
                .define('D', NTBlocks.DARK_PRISMARINE_PILLAR)
                .define('R', NTItems.CAST_IRON_ROD)
                .define('G', NTItems.GEAR)
                .define('A', NTItems.AQUARINE_STEEL_INGOT)
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT))
                .save(pRecipeOutput, Nautec.rl("fishing_station"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, NTBlocks.CHARGER.asItem())
                .pattern("PAP")
                .pattern("DCD")
                .define('P', NTBlocks.POLISHED_PRISMARINE)
                .define('A', NTItems.AQUARINE_STEEL_INGOT)
                .define('D', Blocks.DARK_PRISMARINE)
                .define('C', NTItems.LASER_CHANNELING_COIL)
                .unlockedBy("has_item", has(NTItems.PRISMARINE_CRYSTAL_SHARD))
                .save(pRecipeOutput, Nautec.rl("charger"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, NTBlocks.INCUBATOR.asItem())
                .pattern("PGP")
                .pattern("CAC")
                .pattern("PLP")
                .define('P', NTBlocks.POLISHED_PRISMARINE)
                .define('G', Tags.Items.GLASS_BLOCKS)
                .define('C', Items.PRISMARINE_CRYSTALS)
                .define('A', NTItems.AQUATIC_CHIP)
                .define('L', NTItems.LASER_CHANNELING_COIL)
                .unlockedBy("has_item", has(NTItems.LASER_CHANNELING_COIL))
                .save(pRecipeOutput, Nautec.rl("incubator"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, NTBlocks.MUTATOR.asItem())
                .pattern("DCD")
                .pattern("PBP")
                .pattern("DCD")
                .define('P', NTItems.PETRI_DISH)
                .define('B', NTFluids.EAS.getBucket())
                .define('C', NTBlocks.BACTERIAL_CONTAINMENT_SHIELD)
                .define('D', NTBlocks.DARK_PRISMARINE_PILLAR)
                .unlockedBy("has_item", has(NTBlocks.BACTERIAL_CONTAINMENT_SHIELD))
                .save(pRecipeOutput, Nautec.rl("mutator"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, NTBlocks.BACTERIAL_ANALYZER.asItem())
                .pattern("PLP")
                .pattern("A A")
                .pattern("A A")
                .define('P', NTBlocks.POLISHED_PRISMARINE)
                .define('A', NTItems.AQUARINE_STEEL_INGOT)
                .define('L', NTItems.PRISMARINE_LENS)
                .unlockedBy("has_item", has(NTItems.PRISMARINE_LENS))
                .save(pRecipeOutput, Nautec.rl("bacterial_analyzer"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, NTBlocks.BIO_REACTOR.asItem())
                .pattern("CCC")
                .pattern("PAP")
                .pattern("PLP")
                .define('P', NTBlocks.POLISHED_PRISMARINE)
                .define('A', NTItems.AQUATIC_CHIP)
                .define('C', Items.PRISMARINE_CRYSTALS)
                .define('L', NTItems.LASER_CHANNELING_COIL)
                .unlockedBy("has_item", has(NTItems.AQUATIC_CHIP))
                .save(pRecipeOutput, Nautec.rl("bio_reactor"));
    }

    private static void ancientItemsRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ItemEtchingRecipeBuilder.newRecipe(NTItems.VALVE.toStack())
                .ingredient(NTItems.ANCIENT_VALVE.toStack())
                .duration(200)
                .save(pRecipeOutput, Nautec.rl("valve"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NTItems.AQUATIC_CHIP.toStack())
                .requires(NTItems.DAMAGED_AQUATIC_CHIP)
                .requires(Items.PRISMARINE_SHARD, 3)
                .unlockedBy("has_item", has(NTItems.DAMAGED_AQUATIC_CHIP.get()))
                .save(pRecipeOutput, Nautec.rl("aquatic_chip"));

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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.GRAFTING_TOOL.get(), 1)
                .pattern(" R")
                .pattern("I ")
                .define('R', NTItems.CAST_IRON_ROD.get())
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_item", has(NTItems.CAST_IRON_ROD))
                .save(pRecipeOutput, Nautec.rl("grafting_tool"));
    }

    private static void miscItemsRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NTItems.BROWN_POLYMER.get(), 2)
                .requires(Items.DRIED_KELP)
                .requires(Items.BROWN_DYE)
                .unlockedBy("has_item", has(Items.DRIED_KELP))
                .save(pRecipeOutput, Nautec.rl("brown_polymer"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NTItems.AQUARINE_STEEL_COMPOUND.get(), 2)
                .requires(Items.RAW_IRON)
                .requires(Items.PRISMARINE_CRYSTALS)
                .unlockedBy("has_item", has(Items.PRISMARINE_CRYSTALS))
                .save(pRecipeOutput, Nautec.rl("aquarine_steel_compound"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NTItems.CAST_IRON_COMPOUND.get(), 2)
                .requires(Items.RAW_IRON)
                .requires(ItemTags.COALS)
                .requires(ItemTags.COALS)
                .unlockedBy("has_raw_rion", has(Items.RAW_IRON))
                .save(pRecipeOutput, Nautec.rl("cast_iron_compound"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.PRISMARINE_LENS.get())
                .pattern(" A ")
                .pattern("AGA")
                .pattern(" A ")
                .define('A', NTItems.AQUARINE_STEEL_INGOT)
                .define('G', Tags.Items.GLASS_PANES_COLORLESS)
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT))
                .save(pRecipeOutput, Nautec.rl("prismarine_lens"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.PETRI_DISH.get())
                .pattern("G G")
                .pattern("GGG")
                .define('G', Tags.Items.GLASS_PANES_COLORLESS)
                .unlockedBy("has_item", has(Tags.Items.GLASS_PANES_COLORLESS))
                .save(pRecipeOutput, Nautec.rl("petri_dish"));
    }

    private static void castIronRecipes(@NotNull RecipeOutput pRecipeOutput) {

        nineBlockStorageRecipes(pRecipeOutput, RecipeCategory.MISC, NTItems.CAST_IRON_NUGGET, RecipeCategory.MISC, NTItems.CAST_IRON_INGOT,
                Nautec.MODID + ":cast_iron_ingot_from_nuggets", null, Nautec.MODID + ":nuggets_from_cast_iron_ingot", null);
        nineBlockStorageRecipes(pRecipeOutput, RecipeCategory.MISC, NTItems.CAST_IRON_INGOT, RecipeCategory.BUILDING_BLOCKS, NTBlocks.CAST_IRON_BLOCK,
                Nautec.MODID + ":cast_iron_block_from_ingots", null, Nautec.MODID + ":ingots_from_cast_iron_block", null);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.CAST_IRON_ROD.get(), 4)
                .pattern("C")
                .pattern("C")
                .define('C', NTItems.CAST_IRON_INGOT.asItem())
                .unlockedBy("has_item", has(Items.DEEPSLATE))
                .save(pRecipeOutput, Nautec.rl("cast_iron_rod"));
    }

    private static void buildingBlockRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NTBlocks.BACTERIAL_CONTAINMENT_SHIELD.asItem(), 2)
                .pattern("APA")
                .pattern("PCP")
                .pattern("APA")
                .define('P', NTBlocks.POLISHED_PRISMARINE.asItem())
                .define('A', NTItems.AQUARINE_STEEL_INGOT.asItem())
                .define('C', Items.PRISMARINE_CRYSTALS.asItem())
                .unlockedBy("has_item", has(NTItems.AQUARINE_STEEL_INGOT.asItem()))
                .save(pRecipeOutput, Nautec.rl("bacteria_containment_shield_from_prismarine_crystals"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NTBlocks.BACTERIAL_CONTAINMENT_SHIELD.asItem(), 4)
                .pattern("APA")
                .pattern("PCP")
                .pattern("APA")
                .define('P', NTBlocks.POLISHED_PRISMARINE.asItem())
                .define('A', NTItems.AQUARINE_STEEL_INGOT.asItem())
                .define('C', NTItems.PRISMARINE_CRYSTAL_SHARD.asItem())
                .unlockedBy("has_item", has(NTItems.PRISMARINE_CRYSTAL_SHARD.asItem()))
                .save(pRecipeOutput, Nautec.rl("bacteria_containment_shield_from_prismarine_crystal_shard"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NTBlocks.CHISELED_DARK_PRISMARINE.asItem(), 4)
                .pattern("DD")
                .pattern("DD")
                .define('D', Blocks.DARK_PRISMARINE.asItem())
                .unlockedBy("has_item", has(Blocks.DARK_PRISMARINE))
                .save(pRecipeOutput, Nautec.rl("chiseled_dark_prismarine"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NTBlocks.POLISHED_PRISMARINE.asItem(), 4)
                .pattern("DD")
                .pattern("DD")
                .define('D', Blocks.PRISMARINE.asItem())
                .unlockedBy("has_item", has(Blocks.DARK_PRISMARINE))
                .save(pRecipeOutput, Nautec.rl("polished_prismarine"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NTBlocks.DARK_PRISMARINE_PILLAR.asItem(), 2)
                .pattern("D")
                .pattern("D")
                .define('D', Blocks.DARK_PRISMARINE.asItem())
                .unlockedBy("has_item", has(Blocks.DARK_PRISMARINE))
                .save(pRecipeOutput, Nautec.rl("dark_prismarine_pillar"));
    }

    private static void brownPolymerRecipes(RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, NTBlocks.BROWN_POLYMER_BLOCK)
                .pattern("BB")
                .pattern("BB")
                .define('B', NTItems.BROWN_POLYMER.asItem())
                .unlockedBy("has_item", has(NTItems.BROWN_POLYMER))
                .save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, NTItems.BROWN_POLYMER, 4)
                .requires(NTBlocks.BROWN_POLYMER_BLOCK)
                .unlockedBy("has_item", has(NTBlocks.BROWN_POLYMER_BLOCK))
                .save(pRecipeOutput, Nautec.rl("brown_polymer_from_block"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BOOK, 2)
                .requires(NTItems.BROWN_POLYMER)
                .requires(Items.PAPER, 3)
                .unlockedBy("has_item", has(NTItems.BROWN_POLYMER))
                .save(pRecipeOutput, Nautec.rl("book_from_brown_polymer"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.BROWN_BANNER, 2)
                .pattern("BBB")
                .pattern("BBB")
                .pattern(" S ")
                .define('B', NTItems.BROWN_POLYMER.get())
                .define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_item", has(NTItems.BROWN_POLYMER.get()))
                .save(pRecipeOutput, Nautec.rl("banner_from_brown_polymer"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.ITEM_FRAME, 2)
                .pattern("SSS")
                .pattern("SBS")
                .pattern("SSS")
                .define('B', NTItems.BROWN_POLYMER.get())
                .define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_item", has(NTItems.BROWN_POLYMER.get()))
                .save(pRecipeOutput, Nautec.rl("item_frame_from_brown_polymer"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.BROWN_BED)
                .pattern("BBB")
                .pattern("PPP")
                .define('B', NTItems.BROWN_POLYMER.get())
                .define('P', ItemTags.PLANKS)
                .unlockedBy("has_item", has(NTItems.BROWN_POLYMER.get()))
                .save(pRecipeOutput, Nautec.rl("bed_from_brown_polymer"));
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

    private static void mutationRecipes(RecipeOutput output) {
        // Ores and Minerals
        new MutationRecipeBuilder(NTBacterias.THERMOPHILES, NTBacterias.LITHOPHILES, Ingredient.of(Items.STONE), 5f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.LITHOPHILES, NTBacterias.CARBOPHAGES, Ingredient.of(Items.COAL), 5f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.LITHOPHILES, NTBacterias.SILICOPHILES, Ingredient.of(Items.SAND), 5f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.LITHOPHILES, NTBacterias.CALCIOPHILES, Ingredient.of(Items.BONE_MEAL), 5f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.CARBOPHAGES, NTBacterias.METALLOPHILES, Ingredient.of(Items.COPPER_INGOT), 5f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.METALLOPHILES, NTBacterias.ACIDOPHILES, Ingredient.of(Items.REDSTONE), 5f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.ACIDOPHILES, NTBacterias.SULFUROPHILES, Ingredient.of(Items.GUNPOWDER), 5f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.METALLOPHILES, NTBacterias.AZURITOPHILES, Ingredient.of(Items.LAPIS_LAZULI), 5f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.METALLOPHILES, NTBacterias.FERROPHILES, Ingredient.of(Items.IRON_INGOT), 5f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.FERROPHILES, NTBacterias.AURROPHILES, Ingredient.of(Items.GOLD_INGOT), 5f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.AURROPHILES, NTBacterias.ADAMANTOPHILES, Ingredient.of(Items.DIAMOND), 5f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.ADAMANTOPHILES, NTBacterias.SMARAGDOPHILES, Ingredient.of(Items.EMERALD), 5f)
                .save(output);

        // Mushrooms and Wood
        new MutationRecipeBuilder(NTBacterias.METHANOGENS, NTBacterias.CARNIVOROUS_BACTERIA, Ingredient.of(Items.ROTTEN_FLESH), 5f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.METHANOGENS, NTBacterias.RED_MYCOTROPHIC_BACTERIA, Ingredient.of(Items.RED_MUSHROOM_BLOCK), 20f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.METHANOGENS, NTBacterias.BROWN_MYCOTROPHIC_BACTERIA, Ingredient.of(Items.BROWN_MUSHROOM_BLOCK), 20f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.BROWN_MYCOTROPHIC_BACTERIA, NTBacterias.WARPED_MICROBES, Ingredient.of(Items.WARPED_FUNGUS), 3f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.RED_MYCOTROPHIC_BACTERIA, NTBacterias.WARPED_MICROBES, Ingredient.of(Items.WARPED_FUNGUS), 3f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.BROWN_MYCOTROPHIC_BACTERIA, NTBacterias.CRIMSON_MICROBES, Ingredient.of(Items.CRIMSON_FUNGUS), 3f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.RED_MYCOTROPHIC_BACTERIA, NTBacterias.CRIMSON_MICROBES, Ingredient.of(Items.CRIMSON_FUNGUS), 3f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.LIGNOCYTES, NTBacterias.CRIMSON_LIGNOCYTES, Ingredient.of(Items.CRIMSON_STEM), 3f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.LIGNOCYTES, NTBacterias.WARPED_LIGNOCYTES, Ingredient.of(Items.WARPED_STEM), 3f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.METHANOGENS, NTBacterias.LIGNOCYTES, Ingredient.of(Items.OAK_LOG), 5f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.LIGNOCYTES, NTBacterias.DARK_LIGNOCYTES, Ingredient.of(Items.DARK_OAK_LOG), 5f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.LIGNOCYTES, NTBacterias.ACACIOPHYLES, Ingredient.of(Items.ACACIA_LOG), 5f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.LIGNOCYTES, NTBacterias.JUNGLOPHILES, Ingredient.of(Items.JUNGLE_LOG), 5f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.LIGNOCYTES, NTBacterias.BOREOPHILES, Ingredient.of(Items.SPRUCE_LOG), 5f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.LIGNOCYTES, NTBacterias.BETULOPHILES, Ingredient.of(Items.BIRCH_LOG), 5f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.LIGNOCYTES, NTBacterias.RHIZOPHORA_LIGNOCYTES, Ingredient.of(Items.MANGROVE_LOG), 5f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.LIGNOCYTES, NTBacterias.PRUNUS_LIGNOCYTES, Ingredient.of(Items.CHERRY_LOG), 5f)
                .save(output);

        // Plants
        new MutationRecipeBuilder(NTBacterias.CYANOBACTERIA, NTBacterias.PHOTOTROPHS, Ingredient.of(Items.SUGAR_CANE), 10f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.PHOTOTROPHS, NTBacterias.CACTOPHYLES, Ingredient.of(Items.CACTUS), 10f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.HALOBACTERIA, NTBacterias.HALOTROPHS, Ingredient.of(Items.KELP), 25f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.HALOBACTERIA, NTBacterias.ALGAEFORMERS, Ingredient.of(Items.SEAGRASS), 25f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.ALGAEFORMERS, NTBacterias.CRYOBIONTS, Ingredient.of(Items.BLUE_ICE), 5f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.HALOTROPHS, NTBacterias.PHOTOTROPHS, Ingredient.of(Items.SUGAR_CANE), 5f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.PHOTOTROPHS, NTBacterias.RHIZOBACTERIA, Ingredient.of(Items.WHEAT), 5f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.RHIZOBACTERIA, NTBacterias.BETA_PHYLOBACTERIA, Ingredient.of(Items.BEETROOT), 10f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.RHIZOBACTERIA, NTBacterias.CAROTOPHYLES, Ingredient.of(Items.CARROT), 10f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.RHIZOBACTERIA, NTBacterias.SOLANOPHILES, Ingredient.of(Items.POTATO), 10f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.RHIZOBACTERIA, NTBacterias.CUCURBITOPHILES, Ingredient.of(Items.PUMPKIN), 10f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.CUCURBITOPHILES, NTBacterias.MELOPHAGES, Ingredient.of(Items.MELON_SLICE), 10f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.RHIZOBACTERIA, NTBacterias.BAMBOOPHAGES, Ingredient.of(Items.BAMBOO), 10f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.JUNGLOPHILES, NTBacterias.COCOAPHILES, Ingredient.of(Items.COCOA_BEANS), 10f)
                .save(output);
        new MutationRecipeBuilder(NTBacterias.RHIZOBACTERIA, NTBacterias.BRYOPHYTOPHILES, Ingredient.of(Items.MOSS_BLOCK), 5f)
                .save(output);
    }

    private static void incubationRecipes(RecipeOutput output) {
        // Wood Bacteria
        new IncubationRecipeBuilder(NTBacterias.LIGNOCYTES, Ingredient.of(Items.OAK_LOG), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.DARK_LIGNOCYTES, Ingredient.of(Items.DARK_OAK_LOG), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.ACACIOPHYLES, Ingredient.of(Items.ACACIA_LOG), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.JUNGLOPHILES, Ingredient.of(Items.JUNGLE_LOG), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.BOREOPHILES, Ingredient.of(Items.SPRUCE_LOG), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.BETULOPHILES, Ingredient.of(Items.BIRCH_LOG), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.CRIMSON_LIGNOCYTES, Ingredient.of(Items.CRIMSON_STEM), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.WARPED_LIGNOCYTES, Ingredient.of(Items.WARPED_STEM), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.RHIZOPHORA_LIGNOCYTES, Ingredient.of(Items.MANGROVE_LOG), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.PRUNUS_LIGNOCYTES, Ingredient.of(Items.CHERRY_LOG), IntRange.of(10, 30), 0.07f)
                .save(output);

        // Mineral Bacteria
        new IncubationRecipeBuilder(NTBacterias.SILICOPHILES, Ingredient.of(Items.SAND), IntRange.of(8, 25), 0.05f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.LITHOPHILES, Ingredient.of(Items.STONE), IntRange.of(8, 25), 0.05f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.METALLOPHILES, Ingredient.of(Tags.Items.ORES_COPPER), IntRange.of(8, 25), 0.1f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.FERROPHILES, Ingredient.of(Tags.Items.ORES_IRON), IntRange.of(8, 25), 0.1f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.AURROPHILES, Ingredient.of(Tags.Items.ORES_GOLD), IntRange.of(8, 25), 0.1f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.ACIDOPHILES, Ingredient.of(Tags.Items.ORES_REDSTONE), IntRange.of(8, 25), 0.1f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.ADAMANTOPHILES, Ingredient.of(Tags.Items.ORES_DIAMOND), IntRange.of(8, 25), 0.1f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.SMARAGDOPHILES, Ingredient.of(Tags.Items.ORES_EMERALD), IntRange.of(8, 25), 0.1f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.AZURITOPHILES, Ingredient.of(Tags.Items.ORES_LAPIS), IntRange.of(8, 25), 0.1f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.CARBOPHAGES, Ingredient.of(Tags.Items.ORES_COAL), IntRange.of(8, 25), 0.1f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.CALCIOPHILES, Ingredient.of(Items.BONE_BLOCK), IntRange.of(8, 25), 0.1f)
                .save(output);

        // Plants
        new IncubationRecipeBuilder(NTBacterias.PHOTOTROPHS, Ingredient.of(Items.SUGAR_CANE), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.RED_MYCOTROPHIC_BACTERIA, Ingredient.of(Items.RED_MUSHROOM_BLOCK), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.BROWN_MYCOTROPHIC_BACTERIA, Ingredient.of(Items.BROWN_MUSHROOM_BLOCK), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.HALOTROPHS, Ingredient.of(Items.SAND), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.BRYOPHYTOPHILES, Ingredient.of(Items.MOSS_BLOCK), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.ALGAEFORMERS, Ingredient.of(Items.KELP), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.RHIZOBACTERIA, Ingredient.of(Items.WHEAT), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.SOLANOPHILES, Ingredient.of(Items.POTATO), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.BAMBOOPHAGES, Ingredient.of(Items.BAMBOO), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.CACTOPHYLES, Ingredient.of(Items.CACTUS), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.CAROTOPHYLES, Ingredient.of(Items.CARROT), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.CUCURBITOPHILES, Ingredient.of(Items.PUMPKIN), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.BETA_PHYLOBACTERIA, Ingredient.of(Items.BEETROOT), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.MELOPHAGES, Ingredient.of(Items.MELON_SLICE), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.CRIMSON_MICROBES, Ingredient.of(Items.CRIMSON_NYLIUM), IntRange.of(10, 30), 0.07f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.WARPED_MICROBES, Ingredient.of(Items.WARPED_NYLIUM), IntRange.of(10, 30), 0.07f)
                .save(output);

        // Misc
        new IncubationRecipeBuilder(NTBacterias.SULFUROPHILES, Ingredient.of(Items.GUNPOWDER), IntRange.of(5, 15), 0.1f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.CRYOBIONTS, Ingredient.of(Items.PACKED_ICE), IntRange.of(5, 15), 0.1f)
                .save(output);
        new IncubationRecipeBuilder(NTBacterias.CARNIVOROUS_BACTERIA, Ingredient.of(Items.ROTTEN_FLESH), IntRange.of(5, 15), 0.1f)
                .save(output);
    }

    private static @NotNull IngredientWithCount iwcFromItemLike(Item item, int count) {
        return IngredientWithCount.fromItemLike(item, count);
    }
}
