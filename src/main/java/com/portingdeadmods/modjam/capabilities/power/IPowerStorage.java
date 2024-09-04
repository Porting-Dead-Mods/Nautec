package com.portingdeadmods.modjam.capabilities.power;

import org.jetbrains.annotations.Range;

public interface IPowerStorage {
    int getPowerStored();

    int getPowerCapacity();

    @Range(from = 0, to = 1)
    float getPurity();

    void setPowerStored(int powerStored);

    void setPowerCapacity(int powerCapacity);

    void setPurity(float purity);
}
