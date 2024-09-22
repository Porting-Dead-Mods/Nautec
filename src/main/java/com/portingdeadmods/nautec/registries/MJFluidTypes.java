package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.fluids.BaseFluidType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.common.SoundAction;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector4i;

import java.util.function.Supplier;

public final class MJFluidTypes {

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, Nautec.MODID);

    public static final Supplier<FluidType> SALT_WATER_FLUID_TYPE = register("soap_water",
            FluidType.Properties.create().lightLevel(2).density(15).viscosity(5).sound(SoundAction.get("drink"),
                    SoundEvents.HONEY_DRINK), new Vector4i(174, 227, 227, 176), FluidTemplate.WATER);

    public static final Supplier<FluidType> EAS_FLUID_TYPE = register("electrolyte_algae_serum",
            FluidType.Properties.create().lightLevel(2).density(30).viscosity(5).sound(SoundAction.get("drink"),
                    SoundEvents.HONEY_DRINK), new Vector4i(255, 255, 255, 255), FluidTemplate.EAS);

    public static final Supplier<FluidType> ETCHING_ACID_FLUID_TYPE = register("etching_acid",
            FluidType.Properties.create().lightLevel(2).density(5).viscosity(1).sound(SoundAction.get("drink"),
                    SoundEvents.HONEY_DRINK), new Vector4i(255, 255, 255, 255), FluidTemplate.ETCHING_ACID);

    private static Supplier<FluidType> register(String name, FluidType.Properties properties, Vector4i color, FluidTemplate template) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(template.still, template.flowing, template.overlay, color, properties));
    }

    public enum FluidTemplate {
        WATER(ResourceLocation.withDefaultNamespace("block/water_still"),
                ResourceLocation.withDefaultNamespace("block/water_flow"),
                ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "misc/in_water")),
        EAS(modFluidTexture("eas_fluid"), modFluidTexture("eas_fluid"), ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "misc/in_water")),

        ETCHING_ACID(modFluidTexture("etching_acid"), modFluidTexture("etching_acid"), ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "misc/in_water"));

        private final ResourceLocation still;
        private final ResourceLocation flowing;
        private final ResourceLocation overlay;

        FluidTemplate(ResourceLocation still, ResourceLocation flowing, ResourceLocation overlay) {
            this.still = still;
            this.flowing = flowing;
            this.overlay = overlay;
        }

        private static ResourceLocation modFluidTexture(String name) {
            return ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "fluid/"+name);
        }
    }

}
