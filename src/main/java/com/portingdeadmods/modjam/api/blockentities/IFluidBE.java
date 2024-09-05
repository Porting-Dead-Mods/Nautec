package com.portingdeadmods.modjam.api.blockentities;

import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public interface IFluidBE {
    default ContainerBlockEntity self() {
        if (this instanceof ContainerBlockEntity containerBlockEntity) {
            return containerBlockEntity;
        }
        return null;
    }

    /**
     * Override this if your blockentity does not extend the containerblockentity class
     */
    default IFluidHandler getFluidStorage() {
        return self().getFluidHandler();
    }
}
