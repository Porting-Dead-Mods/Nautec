package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.blockentities.*;
import com.portingdeadmods.modjam.content.blockentities.multiblock.controller.AugmentationStationBlockEntity;
import com.portingdeadmods.modjam.content.blockentities.multiblock.controller.DrainBlockEntity;
import com.portingdeadmods.modjam.content.blockentities.multiblock.part.AugmentationStationExtensionBlockEntity;
import com.portingdeadmods.modjam.content.blockentities.multiblock.part.AugmentationStationPartBlockEntity;
import com.portingdeadmods.modjam.content.blockentities.multiblock.part.DrainPartBlockEntity;
import com.portingdeadmods.modjam.content.blocks.LongDistanceLaserBlock;
import com.portingdeadmods.modjam.content.blocks.multiblock.controller.AugmentationStationBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class MJBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ModJam.MODID);

    public static final Supplier<BlockEntityType<AquaticCatalystBlockEntity>> AQUATIC_CATALYST = BLOCK_ENTITIES.register("aquatic_catalyst",
            () -> BlockEntityType.Builder.of(AquaticCatalystBlockEntity::new,
                    MJBlocks.AQUATIC_CATALYST.get()).build(null));
    public static final Supplier<BlockEntityType<PrismarineLaserRelayBlockEntity>> PRISMARINE_LASER_RELAY = BLOCK_ENTITIES.register("prismarine_laser_relay",
            () -> BlockEntityType.Builder.of(PrismarineLaserRelayBlockEntity::new,
                    MJBlocks.PRISMARINE_RELAY.get()).build(null));
    public static final Supplier<BlockEntityType<LongDistanceLaserBlockEntity>> LONG_DISTANCE_LASER = BLOCK_ENTITIES.register("long_distance_laser",
            () -> BlockEntityType.Builder.of(LongDistanceLaserBlockEntity::new,
                    MJBlocks.LONG_DISTANCE_LASER.get()).build(null));
    public static final Supplier<BlockEntityType<LaserJunctionBlockEntity>> LASER_JUNCTION = BLOCK_ENTITIES.register("laser_junction",
            () -> BlockEntityType.Builder.of(LaserJunctionBlockEntity::new,
                    MJBlocks.LASER_JUNCTION.get()).build(null));
    public static final Supplier<BlockEntityType<MixerBlockEntity>> MIXER = BLOCK_ENTITIES.register("mixer",
            () -> BlockEntityType.Builder.of(MixerBlockEntity::new,
                    MJBlocks.MIXER.get()).build(null));
    public static final Supplier<BlockEntityType<CrateBlockEntity>> CRATE = BLOCK_ENTITIES.register("crate",
            () -> BlockEntityType.Builder.of(CrateBlockEntity::new,
                    MJBlocks.CRATE.get(), MJBlocks.RUSTY_CRATE.get()).build(null));

    public static final Supplier<BlockEntityType<PrismarineCrystalBlockEntity>> PRISMARINE_CRYSTAL = BLOCK_ENTITIES.register("prismarine_crystal",
            () -> BlockEntityType.Builder.of(PrismarineCrystalBlockEntity::new,
                    MJBlocks.PRISMARINE_CRYSTAL.get()).build(null));

    public static final Supplier<BlockEntityType<CreativePowerSourceBlockEntity>> CREATIVE_POWER_SOURCE = BLOCK_ENTITIES.register("creative_power_source",
            () -> BlockEntityType.Builder.of(CreativePowerSourceBlockEntity::new,
                    MJBlocks.CREATIVE_POWER_SOURCE.get()).build(null));

    // MULTIBLOCKS
    public static final Supplier<BlockEntityType<DrainBlockEntity>> DRAIN = BLOCK_ENTITIES.register("drain",
            () -> BlockEntityType.Builder.of(DrainBlockEntity::new,
                    MJBlocks.DRAIN.get()).build(null));
    public static final Supplier<BlockEntityType<DrainPartBlockEntity>> DRAIN_PART = BLOCK_ENTITIES.register("drain_part",
            () -> BlockEntityType.Builder.of(DrainPartBlockEntity::new,
                    MJBlocks.DRAIN_PART.get()).build(null));

    public static final Supplier<BlockEntityType<AugmentationStationBlockEntity>> AUGMENTATION_STATION = BLOCK_ENTITIES.register("augmentation_station",
            () -> BlockEntityType.Builder.of(AugmentationStationBlockEntity::new,
                    MJBlocks.AUGMENTATION_STATION.get()).build(null));
    public static final Supplier<BlockEntityType<AugmentationStationPartBlockEntity>> AUGMENTATION_STATION_PART = BLOCK_ENTITIES.register("augmentation_station_part",
            () -> BlockEntityType.Builder.of(AugmentationStationPartBlockEntity::new,
                    MJBlocks.AUGMENTATION_STATION_PART.get()).build(null));
    public static final Supplier<BlockEntityType<AugmentationStationExtensionBlockEntity>> AUGMENTATION_STATION_EXTENSION = BLOCK_ENTITIES.register("augmentation_station_extension",
            () -> BlockEntityType.Builder.of(AugmentationStationExtensionBlockEntity::new,
                    MJBlocks.AUGMENTATION_STATION_EXTENSION.get()).build(null));
}
