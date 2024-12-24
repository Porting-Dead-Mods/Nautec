package com.portingdeadmods.nautec.capabilities.bacteria;

import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.api.bacteria.BacteriaStats;
import com.portingdeadmods.nautec.content.bacteria.CollapsedStats;

import java.util.function.UnaryOperator;

public interface IBacteriaStorage {
    void setBacteria(int slot, BacteriaInstance bacteriaInstance);

    BacteriaInstance getBacteria(int slot);

    int getBacteriaSlots();

    default void modifyStats(int slot, UnaryOperator<CollapsedStats> statsModifier) {
        BacteriaInstance bacteriaInstance = getBacteria(slot);
        CollapsedStats newStats = statsModifier.apply(bacteriaInstance.getStats());
    }
}
