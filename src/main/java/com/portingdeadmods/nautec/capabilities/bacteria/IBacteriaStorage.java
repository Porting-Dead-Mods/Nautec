package com.portingdeadmods.nautec.capabilities.bacteria;

import com.portingdeadmods.nautec.NTConfig;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.api.bacteria.CollapsedBacteriaStats;
import com.portingdeadmods.nautec.content.bacteria.SimpleCollapsedStats;
import net.minecraft.world.item.ItemStack;

import java.util.function.UnaryOperator;

public interface IBacteriaStorage {
    void setBacteria(int slot, BacteriaInstance bacteriaInstance);

    BacteriaInstance getBacteria(int slot);

    int getBacteriaSlots();

    default void onBacteriaChanged(int slot) {
    }

    default void modifyStats(int slot, UnaryOperator<CollapsedBacteriaStats> statsModifier) {
        BacteriaInstance bacteriaInstance = getBacteria(slot);
        CollapsedBacteriaStats newStats = statsModifier.apply(bacteriaInstance.getStats());
        bacteriaInstance.setStats(newStats);
    }

    /**
     * Instance will not be modified but copied instead
     * @return the remaining instance that was not inserted
     */
    default BacteriaInstance insertBacteria(int slot, BacteriaInstance instance, boolean simulate) {
        if (instance.isEmpty()) {
            return BacteriaInstance.EMPTY;
        }

        BacteriaInstance bacteriaInSlot = getBacteria(slot);

        if (bacteriaInSlot.isEmpty()) {
            long amount = Math.min(NTConfig.bacteriaColonySizeCap, instance.getAmount());

            if (!simulate) {
                setBacteria(slot, instance.copyWithAmount(amount));
                onBacteriaChanged(slot);
            }
            return instance.copyWithAmount(instance.getAmount() - amount);
        }

        if (BacteriaInstance.isSameBacteriaAndStats(bacteriaInSlot, instance)) {
            long rawAmount = bacteriaInSlot.getAmount() + instance.getAmount();

            long amount = Math.min(NTConfig.bacteriaColonySizeCap, rawAmount);

            if (!simulate) {
                setBacteria(slot, instance.copyWithAmount(amount));
                onBacteriaChanged(slot);
            }
            return instance.copyWithAmount(rawAmount - amount);
        }

        return instance.copy();
    }

    /**
     * Instance will not be modified but copied instead
     * @return the bacteria that was extracted
     */
    default BacteriaInstance extractBacteria(int slot, long amount, boolean simulate) {
        BacteriaInstance instance = getBacteria(slot);

        if (amount <= 0 || instance.isEmpty()) return BacteriaInstance.EMPTY;

        long toExtract = Math.min(amount, NTConfig.bacteriaColonySizeCap);

        if (instance.getAmount() <= toExtract) {
            if (!simulate) {
                setBacteria(slot, BacteriaInstance.EMPTY);
                onBacteriaChanged(slot);
                return instance;
            } else {
                return instance.copy();
            }
        } else {
            if (!simulate) {
                setBacteria(slot, instance.copyWithAmount(instance.getAmount() - toExtract));
                onBacteriaChanged(slot);
            }

            return instance.copyWithAmount(toExtract);
        }
    }

}
