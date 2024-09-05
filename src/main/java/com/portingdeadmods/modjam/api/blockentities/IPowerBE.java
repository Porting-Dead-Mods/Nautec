package com.portingdeadmods.modjam.api.blockentities;

import com.portingdeadmods.modjam.capabilities.power.IPowerStorage;

public interface IPowerBE {
    default ContainerBlockEntity self() {
        if (this instanceof ContainerBlockEntity containerBlockEntity) {
            return containerBlockEntity;
        }
        return null;
    }

    /**
     * Override this if your blockentity does not extend the containerblockentity class
     */
    default IPowerStorage getPowerStorage() {
        return self().getPowerStorage();
    }
}
