package com.portingdeadmods.nautec.compat.duradisplay;

import com.leclowndu93150.duradisplay.compat.BuiltinCompat;
import com.leclowndu93150.duradisplay.compat.CompatRegistry;
import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.capabilities.power.IPowerStorage;

import java.util.Collections;

public class DuraDisplayCompat {
    public static void register() {
        CompatRegistry.register(itemStack -> {
            IPowerStorage powerStorage = NTCapabilities.PowerStorage.ITEM.getCapability(itemStack, null);
            return powerStorage != null
                    ? Collections.singletonList(new BuiltinCompat((double) powerStorage.getPowerStored() / (double) powerStorage.getPowerCapacity() * 100.0, itemStack.getItem().getBarColor(itemStack), itemStack.isBarVisible()))
                    : null;
        });
    }
}
