package com.portingdeadmods.nautec.capabilities.bacteria;

import com.portingdeadmods.nautec.api.bacteria.Bacteria;

public interface IBacteriaStorage {
    void setBacteria(Bacteria bacteria);

    Bacteria getBacteria();

    void setBacteriaAmount(long bacteriaAmount);

    long getBacteriaAmount();
}
