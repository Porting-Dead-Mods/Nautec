package com.portingdeadmods.nautec.api.bacteria;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import java.util.List;

public interface BaseBacteriaStats {
    Item resource();

    float growthRate();

    float mutationResistance();

    float productionRate();

    float colonySize();

    int lifespan();

    int color();

    List<Component> statsTooltip();

    // Mutator
    BaseBacteriaStats rollGrowthRate();
    BaseBacteriaStats rollMutationResistance();
    BaseBacteriaStats rollProductionRate();
    BaseBacteriaStats rollLifespan();
    BaseBacteriaStats rollStats();

    // Incubator
    BaseBacteriaStats grow();
    BaseBacteriaStats shrink();
}
