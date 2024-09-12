package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.entites.ThrownBouncingTrident;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MJEntites {
    public static DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, ModJam.MODID);

    public static final Supplier<EntityType<ThrownBouncingTrident>> THROWN_BOUNCING_TRIDENT = ENTITIES.register("bouncing_trident",
            ()->EntityType.Builder.<ThrownBouncingTrident>of(ThrownBouncingTrident::new, MobCategory.MISC).sized(0.5f,0.5f).build(ModJam.MODID+"bouncing_trident"));
}