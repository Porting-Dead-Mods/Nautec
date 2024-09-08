package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.ModJam;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class MJFluids {

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, ModJam.MODID);

    public static final Supplier<FlowingFluid> SALT_WATER_SOURCE = FLUIDS.register("salt_water",
            () -> new BaseFlowingFluid.Source(MJFluids.SALT_WATER_PROPERTIES));
    public static final Supplier<FlowingFluid> SALT_WATER_FLOWING = FLUIDS.register("salt_water_flowing",
            () -> new BaseFlowingFluid.Flowing(MJFluids.SALT_WATER_PROPERTIES));

    public static final Supplier<FlowingFluid> ELECTROLYTE_ALGAE_SERUM_SOURCE = FLUIDS.register("electrolyte_algae_serum",
            () -> new BaseFlowingFluid.Source(MJFluids.ELECTROLYTE_SERUM_ALGAE_PROPERTIES));
    public static final Supplier<FlowingFluid> ELECTROLYTE_ALGAE_SERUM_FLOWING = FLUIDS.register("electrolyte_algae_serum_flowing",
            () -> new BaseFlowingFluid.Flowing(MJFluids.ELECTROLYTE_SERUM_ALGAE_PROPERTIES));


    public static final BaseFlowingFluid.Properties SALT_WATER_PROPERTIES = new BaseFlowingFluid.Properties(
            MJFluidTypes.SALT_WATER_FLUID_TYPE, SALT_WATER_SOURCE, SALT_WATER_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(2).block(MJBlocks.SALT_WATER_FLUID_BLOCK)
            .bucket(MJItems.SALT_WATER_BUCKET);

    public static final BaseFlowingFluid.Properties ELECTROLYTE_SERUM_ALGAE_PROPERTIES = new BaseFlowingFluid.Properties(
            MJFluidTypes.ELECTROLYTE_ALGAE_SERUM_FLUID_TYPE, ELECTROLYTE_ALGAE_SERUM_SOURCE, ELECTROLYTE_ALGAE_SERUM_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(2).block(MJBlocks.ELECTROLYTE_ALGAE_SERUM_FLUID_BLOCK)
            .bucket(MJItems.ELECTROLYTE_ALGAE_SERUM_BUCKET);
}
