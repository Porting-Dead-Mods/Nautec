package com.portingdeadmods.nautec.content.fluids;

import com.portingdeadmods.nautec.api.fluids.NTFluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import org.joml.Vector4i;

public class EtchingAcidFluid extends NTFluid {
    public EtchingAcidFluid(String name) {
        super(name);
        this.fluidType = registerFluidType(FluidType.Properties.create(), new Vector4i(255, 255, 255, 255), FluidTemplates.ETCHING_ACID);
    }

    @Override
    public BaseFlowingFluid.Properties fluidProperties() {
        return super.fluidProperties().block(this.block).bucket(this.deferredBucket);
    }

    @Override
    public BlockBehaviour.Properties blockProperties() {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.LAVA);
    }
}
