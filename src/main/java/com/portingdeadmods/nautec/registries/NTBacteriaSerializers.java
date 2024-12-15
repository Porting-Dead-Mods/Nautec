package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.BacteriaSerializer;
import com.portingdeadmods.nautec.api.bacteria.SimpleBacteria;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class NTBacteriaSerializers {
    public static final DeferredRegister<BacteriaSerializer<?>> SERIALIZERS = DeferredRegister.create(NTRegistries.BACTERIA_SERIALIZER, Nautec.MODID);

    public static final Supplier<SimpleBacteria.Serializer> SIMPLE = SERIALIZERS.register("simple", () -> SimpleBacteria.Serializer.INSTANCE);
}
