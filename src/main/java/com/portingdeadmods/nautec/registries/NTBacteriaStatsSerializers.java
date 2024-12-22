package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.BacteriaStatsSerializer;
import com.portingdeadmods.nautec.content.bacteria.SimpleBacteriaStats;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NTBacteriaStatsSerializers {
    public static final DeferredRegister<BacteriaStatsSerializer<?>> SERIALIZERS = DeferredRegister.create(NTRegistries.BACTERIA_STATS_SERIALIZER, Nautec.MODID);

    static {
        SERIALIZERS.register("simple", () -> SimpleBacteriaStats.Serializer.INSTANCE);
    }
}
