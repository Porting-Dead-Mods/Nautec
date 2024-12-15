package com.portingdeadmods.nautec.capabilities.bacteria;

import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import net.minecraft.resources.ResourceKey;

public interface IBacteriaStorage {
    void setBacteria(ResourceKey<Bacteria> bacteria);

    ResourceKey<Bacteria> getBacteria();

    void setBacteriaAmount(long bacteriaAmount);

    long getBacteriaAmount();
}
