package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.entities.ThrownBouncingTrident;
import com.portingdeadmods.nautec.content.entities.ThrownSpreadingTrident;
import com.portingdeadmods.nautec.content.entities.mobs.AbyssalMaw;
import com.portingdeadmods.nautec.content.entities.mobs.LanternJelly;
import com.portingdeadmods.nautec.content.entities.mobs.SiltSkipper;
import com.portingdeadmods.nautec.content.entities.mobs.VentCrawler;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public final class NTEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, Nautec.MODID);

    public static final Supplier<EntityType<ThrownBouncingTrident>> THROWN_BOUNCING_TRIDENT = ENTITIES.register("bouncing_trident",
            ()->EntityType.Builder.<ThrownBouncingTrident>of(ThrownBouncingTrident::new, MobCategory.MISC)
                    .sized(0.5f,0.5f).build(ResourceKey.create(Registries.ENTITY_TYPE, Nautec.rl("bouncing_trident"))));
    public static final Supplier<EntityType<ThrownSpreadingTrident>> THROWN_SPREADING_TRIDENT = ENTITIES.register("spreading_trident",
            () -> EntityType.Builder.<ThrownSpreadingTrident>of(ThrownSpreadingTrident::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build(ResourceKey.create(Registries.ENTITY_TYPE, Nautec.rl("spreading_trident"))));

    public static final Supplier<EntityType<SiltSkipper>> SILT_SKIPPER = mob("silt_skipper", SiltSkipper::new,
            MobCategory.WATER_AMBIENT, builder -> builder.sized(0.5f, 0.35f).eyeHeight(0.2f));
    public static final Supplier<EntityType<LanternJelly>> LANTERN_JELLY = mob("lantern_jelly", LanternJelly::new,
            MobCategory.WATER_CREATURE, builder -> builder.sized(0.7f, 0.9f).eyeHeight(0.6f));
    public static final Supplier<EntityType<VentCrawler>> VENT_CRAWLER = mob("vent_crawler", VentCrawler::new,
            MobCategory.WATER_CREATURE, builder -> builder.sized(0.9f, 0.5f).eyeHeight(0.35f));
    public static final Supplier<EntityType<AbyssalMaw>> ABYSSAL_MAW = mob("abyssal_maw", AbyssalMaw::new,
            MobCategory.MONSTER, builder -> builder.sized(1.4f, 0.9f).eyeHeight(0.65f));

    private static <T extends net.minecraft.world.entity.Mob> Supplier<EntityType<T>> mob(String name,
                                                                                         EntityType.EntityFactory<T> factory,
                                                                                         MobCategory category,
                                                                                         UnaryOperator<EntityType.Builder<T>> builder) {
        return ENTITIES.register(name, () -> builder.apply(EntityType.Builder.of(factory, category))
                .build(ResourceKey.create(Registries.ENTITY_TYPE, Nautec.rl(name))));
    }
}
