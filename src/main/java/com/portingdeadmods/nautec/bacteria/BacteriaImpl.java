package com.portingdeadmods.nautec.bacteria;

import net.minecraft.world.item.Item;

public record BacteriaImpl(
        Item type,
        float growthRate,
        float mutationResistance,
        float productionRate,
        int lifespan) implements Bacteria {

}
