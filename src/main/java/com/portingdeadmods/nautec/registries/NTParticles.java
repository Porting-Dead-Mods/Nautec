package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.Nautec;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class NTParticles {
    public static final DeferredRegister<net.minecraft.core.particles.ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(Registries.PARTICLE_TYPE, Nautec.MODID);

    public static final Supplier<SimpleParticleType> VENT_BUBBLE = PARTICLE_TYPES.register("vent_bubble",
            () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> GLOW_SPORE = PARTICLE_TYPES.register("glow_spore",
            () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> ABYSSAL_MOTE = PARTICLE_TYPES.register("abyssal_mote",
            () -> new SimpleParticleType(false));

    private NTParticles() {
    }
}
