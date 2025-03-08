package com.portingdeadmods.nautec.utils;

import com.portingdeadmods.nautec.NTConfig;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class NTProperties {
    public static IntegerProperty KELP_AGE;

    public static void initProperties() {
        KELP_AGE = IntegerProperty.create("age", 0, NTConfig.kelpHeight);
    }
}

