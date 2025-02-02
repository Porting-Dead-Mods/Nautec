package com.portingdeadmods.nautec.capabilities.bacteria;

import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.api.bacteria.CollapsedBacteriaStats;
import com.portingdeadmods.nautec.content.bacteria.SimpleCollapsedStats;

import java.util.function.UnaryOperator;

public interface IBacteriaStorage {
    void setBacteria(int slot, BacteriaInstance bacteriaInstance);

    BacteriaInstance getBacteria(int slot);

    int getBacteriaSlots();

    default void modifyStats(int slot, UnaryOperator<CollapsedBacteriaStats> statsModifier) {
        BacteriaInstance bacteriaInstance = getBacteria(slot);
        CollapsedBacteriaStats newStats = statsModifier.apply(bacteriaInstance.getStats());
        bacteriaInstance.setStats(newStats);
    }
}
