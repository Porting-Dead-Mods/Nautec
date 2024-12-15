package com.portingdeadmods.nautec.api.bacteria;

import net.minecraft.world.item.Item;

public interface BaseBacteriaStats {
    Item resource();

    float growthRate();

    float mutationResistance();

    float productionRate();

    int lifespan();

    int color();
}
