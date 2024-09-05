package com.portingdeadmods.modjam.utils;

import com.portingdeadmods.modjam.api.utils.OptionalDirection;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public final class MJBlockStateProperties {
    public static final EnumProperty<OptionalDirection> HOS_ACTIVE = EnumProperty.create("hos_active", OptionalDirection.class);
}
