package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.fluids.BaseFluidType;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.common.SoundAction;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class MJFluidTypes {

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, ModJam.MODID);

    public static final Supplier<FluidType> SALT_WATER_FLUID_TYPE = register("soap_water",
            FluidType.Properties.create().lightLevel(2).density(15).viscosity(5).sound(SoundAction.get("drink"),
                    SoundEvents.HONEY_DRINK), new Vec3i(224, 56, 208), FluidTemplate.WATER);

    public static final Supplier<FluidType> ELECTROLYTE_ALGAE_SERUM_FLUID_TYPE = register("electrolyte_algae_serum",
            FluidType.Properties.create().lightLevel(2).density(30).viscosity(10).sound(SoundAction.get("drink"),
                    SoundEvents.HONEY_DRINK), new Vec3i(224, 56, 208), FluidTemplate.WATER);


    private static Supplier<FluidType> register(String name, FluidType.Properties properties, Vec3i color, FluidTemplate template) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(template.still, template.flowing, template.overlay, color, properties));
    }


    public enum FluidTemplate {
        WATER(ResourceLocation.parse("block/salt_water_still"),
                ResourceLocation.parse("block/salt_water_flow"),
                ResourceLocation.fromNamespaceAndPath(ModJam.MODID, "misc/in_salt_water"));

        private final ResourceLocation still;
        private final ResourceLocation flowing;
        private final ResourceLocation overlay;

        FluidTemplate(ResourceLocation still, ResourceLocation flowing, ResourceLocation overlay) {
            this.still = still;
            this.flowing = flowing;
            this.overlay = overlay;
        }
    }

}
