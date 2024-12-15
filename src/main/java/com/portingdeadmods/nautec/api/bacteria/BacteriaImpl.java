package com.portingdeadmods.nautec.api.bacteria;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public record BacteriaImpl(ResourceLocation id, BacteriaStats stats) implements Bacteria {
    @Override
    public BacteriaSerializer getSerializer() {
        return null;
    }
}
