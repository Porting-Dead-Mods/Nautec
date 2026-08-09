package com.portingdeadmods.nautec.events;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.entities.mobs.AbyssalMaw;
import com.portingdeadmods.nautec.content.entities.mobs.LanternJelly;
import com.portingdeadmods.nautec.content.entities.mobs.SiltSkipper;
import com.portingdeadmods.nautec.content.entities.mobs.VentCrawler;
import com.portingdeadmods.nautec.registries.NTEntities;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = Nautec.MODID)
public final class NTEntityEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(NTEntities.SILT_SKIPPER.get(), SiltSkipper.createAttributes().build());
        event.put(NTEntities.LANTERN_JELLY.get(), LanternJelly.createAttributes().build());
        event.put(NTEntities.VENT_CRAWLER.get(), VentCrawler.createAttributes().build());
        event.put(NTEntities.ABYSSAL_MAW.get(), AbyssalMaw.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(NTEntities.SILT_SKIPPER.get(), SpawnPlacementTypes.IN_WATER,
                net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (type, level, reason, pos, random) -> level.getFluidState(pos).is(FluidTags.WATER)
                        && level.getFluidState(pos.above()).is(FluidTags.WATER),
                RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(NTEntities.LANTERN_JELLY.get(), SpawnPlacementTypes.IN_WATER,
                net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (type, level, reason, pos, random) -> level.getFluidState(pos).is(FluidTags.WATER)
                        && level.getFluidState(pos.above()).is(FluidTags.WATER),
                RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(NTEntities.VENT_CRAWLER.get(), SpawnPlacementTypes.IN_WATER,
                net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (type, level, reason, pos, random) -> level.getFluidState(pos).is(FluidTags.WATER),
                RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(NTEntities.ABYSSAL_MAW.get(), SpawnPlacementTypes.IN_WATER,
                net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (type, level, reason, pos, random) -> level.getFluidState(pos).is(FluidTags.WATER)
                        && (reason == EntitySpawnReason.SPAWN_ITEM_USE || pos.getY() < 40)
                        && level.getMaxLocalRawBrightness(pos) <= 7,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}
