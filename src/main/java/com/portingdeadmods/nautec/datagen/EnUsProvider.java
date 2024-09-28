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
    }

    private void addFluidType(Supplier<? extends FluidType> fluidType, String val) {
        add(Utils.registryTranslation(NeoForgeRegistries.FLUID_TYPES, fluidType.get()).getString(), val);
    }

    private void curiosIdent(String key, String val) {
        add("curios.identifier."+key, val);
    }
}