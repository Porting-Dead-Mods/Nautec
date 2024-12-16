package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.BacteriaSerializer;
import com.portingdeadmods.nautec.content.bacteria.SimpleBacteria;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NTBacteriaSerializers {
    public static final DeferredRegister<BacteriaSerializer<?>> SERIALIZERS = DeferredRegister.create(NTRegistries.BACTERIA_SERIALIZER, Nautec.MODID);

    static {
        SERIALIZERS.register("simple", () -> SimpleBacteria.Serializer.INSTANCE);
    }
}
