package com.portingdeadmods.nautec.compat.duradisplay;

import com.portingdeadmods.duradisplay.DuraDisplay;
import com.portingdeadmods.duradisplay.KeyBinds;
import com.portingdeadmods.duradisplay.compat.BuiltinCompat;
import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.capabilities.power.IPowerStorage;
import net.minecraft.SharedConstants;

import java.util.Collections;

public class DuraDisplayCompat {
    public static void register() {
        if (SharedConstants.IS_RUNNING_IN_IDE) {
            KeyBinds.ForgeClient.modEnabled = false;
        }

        registerCompat(itemStack -> {
            IPowerStorage powerStorage = itemStack.getCapability(NTCapabilities.PowerStorage.ITEM);
            return powerStorage != null
                    ? Collections.singletonList(new BuiltinCompat((double)powerStorage.getPowerStored() / (double)powerStorage.getPowerCapacity() * 100.0, itemStack.getItem().getBarColor(itemStack), itemStack.isBarVisible()))
                    : null;
        });
    }

    private static void registerCompat(BuiltinCompat.CompatSupplier supplier) {
        DuraDisplay.BUILTIN_COMPATS.add(supplier);
    }
}
