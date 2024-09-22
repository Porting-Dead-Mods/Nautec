package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.Nautec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class NTFluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, Nautec.MODID);

    public static final Supplier<FlowingFluid> SALT_WATER_SOURCE = FLUIDS.register("salt_water",
            () -> new BaseFlowingFluid.Source(NTFluids.SALT_WATER_PROPERTIES));
    public static final Supplier<FlowingFluid> SALT_WATER_FLOWING = FLUIDS.register("salt_water_flowing",
            () -> new BaseFlowingFluid.Flowing(NTFluids.SALT_WATER_PROPERTIES));

    public static final Supplier<FlowingFluid> EAS_SOURCE = FLUIDS.register("electrolyte_algae_serum",
            () -> new BaseFlowingFluid.Source(NTFluids.EAS_PROPERTIES));
    public static final Supplier<FlowingFluid> EAS_FLOWING = FLUIDS.register("electrolyte_algae_serum_flowing",
            () -> new BaseFlowingFluid.Flowing(NTFluids.EAS_PROPERTIES));

    public static final Supplier<FlowingFluid> ETCHING_ACID_SOURCE = FLUIDS.register("etching_acid",
            () -> new BaseFlowingFluid.Source(NTFluids.ETCHING_ACID_PROPERTIES));
    public static final Supplier<FlowingFluid> ETCHING_ACID_FLOWING = FLUIDS.register("etching_acid_flowing",
            () -> new BaseFlowingFluid.Flowing(NTFluids.ETCHING_ACID_PROPERTIES));

    public static final BaseFlowingFluid.Properties SALT_WATER_PROPERTIES = new BaseFlowingFluid.Properties(
            NTFluidTypes.SALT_WATER_FLUID_TYPE, SALT_WATER_SOURCE, SALT_WATER_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(2).block(NTBlocks.SALT_WATER_FLUID_BLOCK)
            .bucket(NTItems.SALT_WATER_BUCKET);

    public static final BaseFlowingFluid.Properties EAS_PROPERTIES = new BaseFlowingFluid.Properties(
            NTFluidTypes.EAS_FLUID_TYPE, EAS_SOURCE, EAS_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(2).block(NTBlocks.EAS_FLUID_BLOCK)
            .bucket(NTItems.EAS_BUCKET);

    public static final BaseFlowingFluid.Properties ETCHING_ACID_PROPERTIES = new BaseFlowingFluid.Properties(
            NTFluidTypes.ETCHING_ACID_FLUID_TYPE, ETCHING_ACID_SOURCE, ETCHING_ACID_FLOWING)
            .slopeFindDistance(4).levelDecreasePerBlock(1).block(NTBlocks.ETCHING_ACID_FLUID_BLOCK)
            .bucket(NTItems.ETCHING_ACID_BUCKET);
}
