package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.blockentities.*;
import com.portingdeadmods.nautec.content.blockentities.multiblock.controller.AugmentationStationBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.multiblock.controller.DrainBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.multiblock.part.AugmentationStationExtensionBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.multiblock.part.AugmentationStationPartBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.multiblock.part.DrainPartBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.multiblock.semi.PrismarineCrystalBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.multiblock.semi.PrismarineCrystalPartBlockEntity;
import com.portingdeadmods.nautec.content.blocks.multiblock.semi.PrismarineCrystalPartBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class NTBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Nautec.MODID);

    public static final Supplier<BlockEntityType<AquaticCatalystBlockEntity>> AQUATIC_CATALYST = BLOCK_ENTITIES.register("aquatic_catalyst",
            () -> BlockEntityType.Builder.of(AquaticCatalystBlockEntity::new,
                    NTBlocks.AQUATIC_CATALYST.get()).build(null));
    public static final Supplier<BlockEntityType<PrismarineLaserRelayBlockEntity>> PRISMARINE_LASER_RELAY = BLOCK_ENTITIES.register("prismarine_laser_relay",
            () -> BlockEntityType.Builder.of(PrismarineLaserRelayBlockEntity::new,
                    NTBlocks.PRISMARINE_RELAY.get()).build(null));
    public static final Supplier<BlockEntityType<LongDistanceLaserBlockEntity>> LONG_DISTANCE_LASER = BLOCK_ENTITIES.register("long_distance_laser",
            () -> BlockEntityType.Builder.of(LongDistanceLaserBlockEntity::new,
                    NTBlocks.LONG_DISTANCE_LASER.get()).build(null));
    public static final Supplier<BlockEntityType<LaserJunctionBlockEntity>> LASER_JUNCTION = BLOCK_ENTITIES.register("laser_junction",
            () -> BlockEntityType.Builder.of(LaserJunctionBlockEntity::new,
                    NTBlocks.LASER_JUNCTION.get()).build(null));
    public static final Supplier<BlockEntityType<MixerBlockEntity>> MIXER = BLOCK_ENTITIES.register("mixer",
            () -> BlockEntityType.Builder.of(MixerBlockEntity::new,
                    NTBlocks.MIXER.get()).build(null));
    public static final Supplier<BlockEntityType<CrateBlockEntity>> CRATE = BLOCK_ENTITIES.register("crate",
            () -> BlockEntityType.Builder.of(CrateBlockEntity::new,
                    NTBlocks.CRATE.get(), NTBlocks.RUSTY_CRATE.get()).build(null));

    public static final Supplier<BlockEntityType<CreativePowerSourceBlockEntity>> CREATIVE_POWER_SOURCE = BLOCK_ENTITIES.register("creative_power_source",
            () -> BlockEntityType.Builder.of(CreativePowerSourceBlockEntity::new,
                    NTBlocks.CREATIVE_POWER_SOURCE.get()).build(null));
    public static final Supplier<BlockEntityType<ChargerBlockEntity>> CHARGER = BLOCK_ENTITIES.register("charger",
            () -> BlockEntityType.Builder.of(ChargerBlockEntity::new,
                    NTBlocks.CHARGER.get()).build(null));

    // MULTIBLOCKS
    public static final Supplier<BlockEntityType<DrainBlockEntity>> DRAIN = BLOCK_ENTITIES.register("drain",
            () -> BlockEntityType.Builder.of(DrainBlockEntity::new,
                    NTBlocks.DRAIN.get()).build(null));
    public static final Supplier<BlockEntityType<DrainPartBlockEntity>> DRAIN_PART = BLOCK_ENTITIES.register("drain_part",
            () -> BlockEntityType.Builder.of(DrainPartBlockEntity::new,
                    NTBlocks.DRAIN_PART.get()).build(null));

    public static final Supplier<BlockEntityType<PrismarineCrystalBlockEntity>> PRISMARINE_CRYSTAL = BLOCK_ENTITIES.register("prismarine_crystal",
            () -> BlockEntityType.Builder.of(PrismarineCrystalBlockEntity::new,
                    NTBlocks.PRISMARINE_CRYSTAL.get()).build(null));
    public static final Supplier<BlockEntityType<PrismarineCrystalPartBlockEntity>> PRISMARINE_CRYSTAL_PART = BLOCK_ENTITIES.register("prismarine_crystal_part",
            () -> BlockEntityType.Builder.of(PrismarineCrystalPartBlockEntity::new,
                    NTBlocks.PRISMARINE_CRYSTAL_PART.get()).build(null));

    public static final Supplier<BlockEntityType<AugmentationStationBlockEntity>> AUGMENTATION_STATION = BLOCK_ENTITIES.register("augmentation_station",
            () -> BlockEntityType.Builder.of(AugmentationStationBlockEntity::new,
                    NTBlocks.AUGMENTATION_STATION.get()).build(null));
    public static final Supplier<BlockEntityType<AugmentationStationPartBlockEntity>> AUGMENTATION_STATION_PART = BLOCK_ENTITIES.register("augmentation_station_part",
            () -> BlockEntityType.Builder.of(AugmentationStationPartBlockEntity::new,
                    NTBlocks.AUGMENTATION_STATION_PART.get()).build(null));
    public static final Supplier<BlockEntityType<AugmentationStationExtensionBlockEntity>> AUGMENTATION_STATION_EXTENSION = BLOCK_ENTITIES.register("augmentation_station_extension",
            () -> BlockEntityType.Builder.of(AugmentationStationExtensionBlockEntity::new,
                    NTBlocks.AUGMENTATION_STATION_EXTENSION.get()).build(null));
}
