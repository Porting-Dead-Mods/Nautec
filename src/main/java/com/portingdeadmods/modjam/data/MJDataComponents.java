package com.portingdeadmods.modjam.data;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.data.components.ComponentPowerStorage;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public final class MJDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(ModJam.MODID);

    public static final Supplier<DataComponentType<ComponentPowerStorage>> POWER = registerDataComponentType("power",
            () -> builder -> builder.persistent(ComponentPowerStorage.CODEC).networkSynchronized(ComponentPowerStorage.STREAM_CODEC));

    public static <T> Supplier<DataComponentType<T>> registerDataComponentType(
            String name, Supplier<UnaryOperator<DataComponentType.Builder<T>>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.get().apply(DataComponentType.builder()).build());
    }
}
