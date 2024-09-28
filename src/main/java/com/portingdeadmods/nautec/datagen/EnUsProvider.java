package com.portingdeadmods.nautec.datagen;

import com.klikli_dev.modonomicon.api.datagen.AbstractModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.registries.NTFluidTypes;
import com.portingdeadmods.nautec.registries.NTItems;
import com.portingdeadmods.nautec.utils.Utils;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
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

        // Adding missing language keys
        addItem("nautec.drowned_lungs", "Drowned Lungs");
        addItem("nautec.diving_helmet", "Diving Helmet");
        addItem("nautec.diving_chestplate", "Diving Chestplate");
        addItem("nautec.diving_leggings", "Diving Leggings");
        addItem("nautec.diving_boots", "Diving Boots");
        addItem("nautec.aquarine_steel_wrench", "Aquarine Steel Wrench");
        addItem("nautec.etching_acid_bucket", "Etching Acid Bucket");
        addItem("nautec.aquarine_steel_sword", "Aquarine Steel Sword");
        addItem("nautec.aquarine_steel_pickaxe", "Aquarine Steel Pickaxe");
        addItem("nautec.aquarine_steel_axe", "Aquarine Steel Axe");
        addItem("nautec.aquarine_steel_shovel", "Aquarine Steel Shovel");
        addItem("nautec.aquarine_steel_hoe", "Aquarine Steel Hoe");
        addItem("nautec.neptunes_trident", "Neptune's Trident");
        addItem("nautec.aquarine_steel_helmet", "Aquarine Steel Helmet");
        addItem("nautec.aquarine_steel_chestplate", "Aquarine Steel Chestplate");
        addItem("nautec.aquarine_steel_leggings", "Aquarine Steel Leggings");
        addItem("nautec.aquarine_steel_boots", "Aquarine Steel Boots");
        addItem("nautec.dolphin_fin", "Dolphin Fin");
        addItem("nautec.broken_whisk", "Broken Whisk");
        addItem("nautec.whisk", "Whisk");
        addItem("nautec.prismatic_battery", "Prismatic Battery");
        addItem("nautec.air_bottle", "Pressurized Air Bottle");
        addItem("nautec.aquarine_steel_compound", "Aquarine Steel Compound");
        addItem("nautec.prismarine_crystal_shard", "Prismarine Crystal Shard");
        addItem("nautec.claw_robot_arm", "Claw Robot Arm");
        addItem("nautec.syringe_robot_arm", "Syringe Robot Arm");
        addItem("nautec.deepslate_rod", "Deepslate Rod");
        addItem("nautec.brown_polymer", "Brown Polymer");
        addItem("nautec.cast_iron_ingot", "Cast Iron Ingot");
        addItem("nautec.cast_iron_rod", "Cast Iron Rod");
        addItem("nautec.cast_iron_nugget", "Cast Iron Nugget");

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
}
