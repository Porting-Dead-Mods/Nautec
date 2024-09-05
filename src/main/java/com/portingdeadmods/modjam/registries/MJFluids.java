package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.ModJam;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MJFluids {

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, ModJam.MODID);

    public static final Supplier<FlowingFluid> SALT_WATER_SOURCE = FLUIDS.register("salt_water",
            () -> new BaseFlowingFluid.Source(MJFluids.SALT_WATER_PROPERTIES));
    public static final Supplier<FlowingFluid> SALT_WATER_FLOWING = FLUIDS.register("salt_water_flowing",
            () -> new BaseFlowingFluid.Flowing(MJFluids.SALT_WATER_PROPERTIES));


    public static final BaseFlowingFluid.Properties SALT_WATER_PROPERTIES = new BaseFlowingFluid.Properties(
            MJFluidTypes.SALT_WATER_FLUID_TYPE, SALT_WATER_SOURCE, SALT_WATER_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(2).block(MJBlocks.SALT_WATER_FLUID_BLOCK)
            .bucket(MJItems.SALT_WATER_BUCKET);
}
