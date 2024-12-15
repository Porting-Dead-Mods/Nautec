package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.fluids.EASFluid;
import com.portingdeadmods.nautec.content.fluids.EtchingAcidFluid;
import com.portingdeadmods.nautec.content.fluids.OilFluid;
import com.portingdeadmods.nautec.content.fluids.SaltWaterFluid;
import com.portingdeadmods.nautec.utils.FluidRegistrationHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class NTFluids {
    public static final FluidRegistrationHelper HELPER = new FluidRegistrationHelper(NTBlocks.BLOCKS, NTItems.ITEMS, Nautec.MODID);

    public static final OilFluid OIL = HELPER.registerFluid(new OilFluid("oil"));
    public static final SaltWaterFluid SALT_WATER = HELPER.registerFluid(new SaltWaterFluid("saltwater"));
    public static final EASFluid EAS = HELPER.registerFluid(new EASFluid("eas"));
    public static final EtchingAcidFluid ETCHING_ACID = HELPER.registerFluid(new EtchingAcidFluid("etching_acid"));
}
