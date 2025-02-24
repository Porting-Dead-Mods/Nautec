package com.portingdeadmods.nautec.datagen;

import com.klikli_dev.modonomicon.api.datagen.AbstractModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTFluids;
import com.portingdeadmods.nautec.utils.Utils;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Arrays;
import java.util.function.Supplier;

import static com.portingdeadmods.nautec.registries.NTBacterias.*;
import static com.portingdeadmods.nautec.registries.NTItems.*;

public class EnUsProvider extends AbstractModonomiconLanguageProvider {
    public EnUsProvider(PackOutput output, ModonomiconLanguageProvider cacheProvider) {
        super(output, Nautec.MODID, "en_us", cacheProvider);
    }

    @Override
    protected void addTranslations() {
        curiosIdent("prism_monocle", "Monocle");
        curiosIdent("battery", "Battery");

        add("nautec.creative_tab.main", "NauTec");

        addFluidType(NTFluids.SALT_WATER.getFluidType(), "Salt Water");
        addFluidType(NTFluids.EAS.getFluidType(), "Electrolyte Algae Serum");
        addFluidType(NTFluids.ETCHING_ACID.getFluidType(), "Etching Acid");

        addItem(PRISM_MONOCLE, "Prism Monocle");
        addItem(AQUARINE_STEEL_INGOT, "Aquarine Steel Ingot");
        addItem(ATLANTIC_GOLD_INGOT, "Atlantic Gold Ingot");
        addItem(ATLANTIC_GOLD_NUGGET, "Atlantic Gold Nugget");
        addItem(NTFluids.SALT_WATER.getDeferredBucket(), "Salt Water Bucket");
        addItem(NTFluids.EAS.getDeferredBucket(), "Electrolyte Algae Serum (EAS) Bucket");
        addItem(NTFluids.OIL.getDeferredBucket(), "Oil Bucket");
        addItem(GLASS_VIAL, "Glass Vial");
        addItem(ELECTROLYTE_ALGAE_SERUM_VIAL, "Electrolyte Algae Serum (EAS) Vial");
        addItem(CROWBAR, "Crowbar");
        addItem(RUSTY_GEAR, "Rusty Gear");
        addItem(GEAR, "Gear");
        addItem(ANCIENT_VALVE, "Ancient Valve");
        addItem(PETRI_DISH, "Petri Dish");

        // Adding missing items
        addItem("drowned_lungs", "Drowned Lungs");
        addItem("diving_helmet", "Diving Helmet");
        addItem("diving_chestplate", "Diving Chestplate");
        addItem("diving_leggings", "Diving Leggings");
        addItem("diving_boots", "Diving Boots");
        addItem("aquarine_steel_wrench", "Aquarine Steel Wrench");
        addItem("etching_acid_bucket", "Etching Acid Bucket");
        addItem("aquarine_steel_sword", "Aquarine Steel Sword");
        addItem("aquarine_steel_pickaxe", "Aquarine Steel Pickaxe");
        addItem("aquarine_steel_axe", "Aquarine Steel Axe");
        addItem("aquarine_steel_shovel", "Aquarine Steel Shovel");
        addItem("aquarine_steel_hoe", "Aquarine Steel Hoe");
        addItem("neptunes_trident", "Neptune's Trident");
        addItem("aquarine_steel_helmet", "Aquarine Steel Helmet");
        addItem("aquarine_steel_chestplate", "Aquarine Steel Chestplate");
        addItem("aquarine_steel_leggings", "Aquarine Steel Leggings");
        addItem("aquarine_steel_boots", "Aquarine Steel Boots");
        addItem("dolphin_fin", "Dolphin Fin");
        addItem("broken_whisk", "Broken Whisk");
        addItem("whisk", "Whisk");
        addItem("prismatic_battery", "Prismatic Battery");
        addItem("air_bottle", "Pressurized Air Bottle");
        addItem("aquarine_steel_compound", "Aquarine Steel Compound");
        addItem("prismarine_crystal_shard", "Prismarine Crystal Shard");
        addItem("claw_robot_arm", "Claw Robot Arm");
        addItem("syringe_robot_arm", "Syringe Robot Arm");
        addItem("deepslate_rod", "Deepslate Rod");
        addItem("brown_polymer", "Brown Polymer");
        addItem("cast_iron_ingot", "Cast Iron Ingot");
        addItem("cast_iron_rod", "Cast Iron Rod");
        addItem("cast_iron_nugget", "Cast Iron Nugget");
        addItem(LASER_CHANNELING_COIL, "Laser Channeling Coil");
        addItem(BURNT_COIL, "Burnt Coil");
        addItem(ELDRITCH_HEART, "Eldritch Heart");
        addItem(GUARDIAN_EYE, "Guardian Eye");
        addItem(VALVE, "Valve");
        addItem(CAST_IRON_COMPOUND, "Cast Iron Compound");
        addItem(GRAFTING_TOOL, "Grafting Tool");
        addItem(PRISMARINE_LENS, "Prismarine Lens");
        addItem(AQUATIC_CHIP, "Aquatic Chip");
        
        add("nautec_guide.desc.0","Nautec's Guide");

        // Adding missing block translations
        addBlock("rusty_crate", "Rusty Crate");
        addBlock("polished_prismarine", "Polished Prismarine");
        addBlock("mixer", "Mixer");
        addBlock("long_distance_laser", "Long Distance Laser");
        addBlock("laser_junction", "Laser Junction");
        addBlock("prismarine_crystal", "Prismarine Crystal");
        addBlock("deep_sea_drain_wall", "Deep Sea Drain Wall");
        addBlock("augmentation_station", "Augmentation Station");
        addBlock("aquarine_steel_block", "Aquarine Steel Block");
        addBlock("aquatic_catalyst", "Aquatic Catalyst");
        addBlock("dark_prismarine_pillar", "Dark Prismarine Pillar");
        addBlock("chiseled_dark_prismarine", "Chiseled Dark Prismarine");
        addBlock("crate", "Crate");
        addBlock("prismarine_laser_relay", "Prismarine Laser Relay");
        addBlock("deep_sea_drain", "Deep Sea Drain");
        addBlock("augmentation_station_part", "Augmentation Station");
        addBlock("prismarine_crystal_part", "Prismarine Crystal");
        addBlock(NTBlocks.AUGMENTATION_STATION_EXTENSION, "Augmentation Station Extension");
        addBlock(NTBlocks.CHARGER, "Charger");
        addBlock(NTBlocks.PRISMARINE_SAND, "Prismarine Sand");
        addBlock(NTBlocks.CREATIVE_POWER_SOURCE,"Creative Power Source");
        addBlock(NTBlocks.MUTATOR, "Mutator");
        addBlock(NTBlocks.INCUBATOR, "Incubator");
        addBlock(NTBlocks.BIO_REACTOR, "Bio Reactor");
        addBlock(NTBlocks.BACTERIAL_ANALYZER, "Bacterial Analyzer");
        addBlock(NTBlocks.FISHING_STATION, "Fishing Station");
        addBlock(NTBlocks.BACTERIAL_CONTAINMENT_SHIELD, "Bacteria Containment Shield");
        addBlock(NTBlocks.CAST_IRON_BLOCK, "Cast Iron Block");

        // Multiblock information
        add("multiblock.info.failed_to_construct", "Missing or invalid block");
        add("multiblock.info.actual_block", "Block: %s");
        add("multiblock.info.expected_block", "Expected: %s");
        add("multiblock.info.block_pos", "Coordinates: %d, %d, %d");

        // Augmentation slots
        add("augment_slot.nautec.head", "Head");
        add("augment_slot.nautec.eyes", "Eyes");
        add("augment_slot.nautec.body", "Body");
        add("augment_slot.nautec.lung", "Lungs");
        add("augment_slot.nautec.left_leg", "Left Leg");
        add("augment_slot.nautec.right_leg", "Right Leg");
        add("augment_slot.nautec.left_arm", "Left Arm");
        add("augment_slot.nautec.right_arm", "Right Arm");
        add("augment_slot.nautec.heart", "Heart");

        add("augment_type.nautec.drowned_lung", "Drowned Lung");
        add("augment_type.nautec.guardian_eye", "Guardian Eye");
        add("augment_type.nautec.dolphin_fin", "Dolphin Fin");

        add("nautec.air_bottle.fill","Right click a glass bottle on a bubble column to fill with pressurized air");
        add("nautec.air_bottle.craft_msg","Either Craft with Chestplate or drink while wearing chestplate to increase oxygen level");
        add("nautec.edible","Edible");
        add("nautec.armor.ability.desc", "Ability: Increases protection when powered");
        add("nautec.armor.power", "Power: ");
        add("nautec.armor.status", "Status: ");
        add("nautec.armor.enabled", "Enabled");
        add("nautec.armor.disabled", "Shift + Right Click to Enable");
        add("nautec.helm.desc", "Allows you to see better underwater.");
        add("nautec.tool.axe.ability", "Ability: Chop Down Entire Trees");
        add("nautec.tool.hoe.ability", "Ability: Till 3x3 Farmland");
        add("nautec.tool.pickaxe.ability", "Ability: Mine in a 3x3 Area");
        add("nautec.tool.shovel.ability", "Ability: Mine in a 3x3 Area");
        add("nautec.tool.sword.ability", "Ability: Deal 70% more damage and spawn lightnings at targets");
        add("nautec.tool.infuse-me", "Infuse in Algae Serum to unlock Abilities");
        add("nautec.tool.status", "Status: ");
        add("nautec.tool.power", "Power: ");
        add("nautec.tool.enabled", "Enabled");
        add("nautec.tool.disabled", "Shift + Right Click to Enable");

        // Bacteria
        for (ResourceKey<?> key : BACTERIAS) {
            addDirectBacteria(key);
        }
    }

    private void addFluidType(Supplier<? extends FluidType> fluidType, String val) {
        add(Utils.registryTranslation(NeoForgeRegistries.FLUID_TYPES, fluidType.get()).getString(), val);
    }

    private void curiosIdent(String key, String val) {
        add("curios.identifier." + key, val);
    }

    private void addItem(String key, String val) {
        add("item.nautec." + key, val);
    }

    private void addBacteria(ResourceKey<?> key, String val) {
        add(key.registry().getPath() + "." + key.location().getNamespace() + "." + key.location().getPath(), val);
    }

    private void addDirectBacteria(ResourceKey<?> key) {
        String[] name = key.location().getPath().split("_");
        String val = Arrays.stream(name).map(s -> s.substring(0, 1).toUpperCase() + s.substring(1)).reduce((s1, s2) -> s1 + " " + s2).orElse("");

        add(key.registry().getPath() + "." + key.location().getNamespace() + "." + key.location().getPath(), val);
    }

    private void addBlock(String key, String val) {
        add("block.nautec." + key, val);
    }
}
