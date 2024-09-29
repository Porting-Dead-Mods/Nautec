package com.portingdeadmods.nautec.datagen;

import com.klikli_dev.modonomicon.api.datagen.AbstractModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.compat.modonomicon.ModonomiconCompat;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTFluidTypes;
import com.portingdeadmods.nautec.registries.NTItems;
import com.portingdeadmods.nautec.utils.Utils;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

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

        addFluidType(NTFluidTypes.SALT_WATER_FLUID_TYPE, "Salt Water");
        addFluidType(NTFluidTypes.EAS_FLUID_TYPE, "Electrolyte Algae Serum");
        addFluidType(NTFluidTypes.ETCHING_ACID_FLUID_TYPE, "Etching Acid");

        addItem(PRISM_MONOCLE, "Prism Monocle");
        addItem(AQUARINE_STEEL_INGOT, "Aquarine Steel Ingot");
        addItem(ATLANTIC_GOLD_INGOT, "Atlantic Gold Ingot");
        addItem(ATLANTIC_GOLD_NUGGET, "Atlantic Gold Nugget");
        addItem(SALT_WATER_BUCKET, "Salt Water Bucket");
        addItem(EAS_BUCKET, "Electrolyte Algae Serum (EAS) Bucket");
        addItem(GLASS_VIAL, "Glass Vial");
        addItem(ELECTROLYTE_ALGAE_SERUM_VIAL, "Electrolyte Algae Serum (EAS) Vial");
        addItem(CROWBAR, "Crowbar");
        addItem(RUSTY_GEAR, "Rusty Gear");
        addItem(GEAR, "Gear");
        addItem(ANCIENT_VALVE, "Ancient Valve");

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
        addItem(ELDRITCH_ARTIFACT, "Eldritch Artifact");
        addItem(GUARDIAN_EYE, "Guardian Eye");
        addItem(VALVE, "Valve²");
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
        addBlock("augmentation_station_extentsion", "Augmentation Station Extension");
        addBlock(NTBlocks.CHARGER, "Charger");
        addBlock(NTBlocks.PRISMARINE_SAND, "Prismarine Sand");

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

    private void addBlock(String key, String val) {
        add("block.nautec." + key, val);
    }
}
