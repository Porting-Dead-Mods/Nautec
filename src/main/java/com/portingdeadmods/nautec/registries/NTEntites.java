package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.entites.ThrownBouncingTrident;
import com.portingdeadmods.nautec.content.entites.ThrownSpreadingTrident;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NTEntites {
    public static DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, Nautec.MODID);

    public static final Supplier<EntityType<ThrownBouncingTrident>> THROWN_BOUNCING_TRIDENT = ENTITIES.register("bouncing_trident",
            ()->EntityType.Builder.<ThrownBouncingTrident>of(ThrownBouncingTrident::new, MobCategory.MISC)
                    .sized(0.5f,0.5f).build(Nautec.MODID+"bouncing_trident"));
    public static final Supplier<EntityType<ThrownSpreadingTrident>> THROWN_SPREADING_TRIDENT = ENTITIES.register("spreading_trident",
            () -> EntityType.Builder.<ThrownSpreadingTrident>of(ThrownSpreadingTrident::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build(Nautec.MODID + "spreading_trident"));
}
