package com.portingdeadmods.modjam.api.blockentities;

import net.neoforged.neoforge.items.IItemHandler;

public interface IItemBE {
    default ContainerBlockEntity self() {
        if (this instanceof ContainerBlockEntity containerBlockEntity) {
            return containerBlockEntity;
        }
        return null;
    }

    /**
     * Override this if your blockentity does not extend the containerblockentity class
     */
    default IItemHandler getItemStorage() {
        return self().getItemHandler();
    }
}
