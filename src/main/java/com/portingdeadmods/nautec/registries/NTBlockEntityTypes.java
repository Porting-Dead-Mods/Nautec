package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.blockentities.*;
import com.portingdeadmods.nautec.content.blockentities.multiblock.controller.AugmentationStationBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.multiblock.controller.BioReactorBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.multiblock.controller.DrainBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.multiblock.part.AugmentationStationExtensionBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.multiblock.part.AugmentationStationPartBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.multiblock.part.BioReactorPartBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.multiblock.part.DrainPartBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.multiblock.semi.PrismarineCrystalBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.multiblock.semi.PrismarineCrystalPartBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class NTBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Nautec.MODID);

    public static final Supplier<BlockEntityType<AquaticCatalystBlockEntity>> AQUATIC_CATALYST = BLOCK_ENTITIES.register("aquatic_catalyst",
            () -> new BlockEntityType<>(AquaticCatalystBlockEntity::new,
                    NTBlocks.AQUATIC_CATALYST.get()));
    public static final Supplier<BlockEntityType<PrismarineLaserRelayBlockEntity>> PRISMARINE_LASER_RELAY = BLOCK_ENTITIES.register("prismarine_laser_relay",
            () -> new BlockEntityType<>(PrismarineLaserRelayBlockEntity::new,
                    NTBlocks.PRISMARINE_RELAY.get()));
    public static final Supplier<BlockEntityType<LongDistanceLaserBlockEntity>> LONG_DISTANCE_LASER = BLOCK_ENTITIES.register("long_distance_laser",
            () -> new BlockEntityType<>(LongDistanceLaserBlockEntity::new,
                    NTBlocks.LONG_DISTANCE_LASER.get()));
    public static final Supplier<BlockEntityType<LaserJunctionBlockEntity>> LASER_JUNCTION = BLOCK_ENTITIES.register("laser_junction",
            () -> new BlockEntityType<>(LaserJunctionBlockEntity::new,
                    NTBlocks.LASER_JUNCTION.get()));
    public static final Supplier<BlockEntityType<MixerBlockEntity>> MIXER = BLOCK_ENTITIES.register("mixer",
            () -> new BlockEntityType<>(MixerBlockEntity::new,
                    NTBlocks.MIXER.get()));
    public static final Supplier<BlockEntityType<CrateBlockEntity>> CRATE = BLOCK_ENTITIES.register("crate",
            () -> new BlockEntityType<>(CrateBlockEntity::new,
                    NTBlocks.CRATE.get(), NTBlocks.RUSTY_CRATE.get()));
    public static final Supplier<BlockEntityType<AnchorBlockEntity>> ANCHOR = BLOCK_ENTITIES.register("anchor",
            () -> new BlockEntityType<>(AnchorBlockEntity::new,
                    NTBlocks.ANCHOR.get()));
    public static final Supplier<BlockEntityType<FishingStationBlockEntity>> FISHING_STATION = BLOCK_ENTITIES.register("fishing_station",
            () -> new BlockEntityType<>(FishingStationBlockEntity::new,
                    NTBlocks.FISHING_STATION.get()));
    public static final Supplier<BlockEntityType<OilBarrelBlockEntity>> OIL_BARREL = BLOCK_ENTITIES.register("oil_barrel",
            () -> new BlockEntityType<>(OilBarrelBlockEntity::new,
                    NTBlocks.OIL_BARREL.get()));

    // Biology
    public static final Supplier<BlockEntityType<MutatorBlockEntity>> MUTATOR = BLOCK_ENTITIES.register("mutator",
            () -> new BlockEntityType<>(MutatorBlockEntity::new,
                    NTBlocks.MUTATOR.get()));
    public static final Supplier<BlockEntityType<IncubatorBlockEntity>> INCUBATOR = BLOCK_ENTITIES.register("incubator",
            () -> new BlockEntityType<>(IncubatorBlockEntity::new,
                    NTBlocks.INCUBATOR.get()));
    public static final Supplier<BlockEntityType<BioReactorBlockEntity>> BIO_REACTOR = BLOCK_ENTITIES.register("bio_reactor",
            () -> new BlockEntityType<>(BioReactorBlockEntity::new,
                    NTBlocks.BIO_REACTOR.get()));
    public static final Supplier<BlockEntityType<BioReactorPartBlockEntity>> BIO_REACTOR_PART = BLOCK_ENTITIES.register("bio_reactor_part",
            () -> new BlockEntityType<>(BioReactorPartBlockEntity::new,
                    NTBlocks.BIO_REACTOR_PART.get()));
    public static final Supplier<BlockEntityType<BacterialAnalyzerBlockEntity>> BACTERIAL_ANALYZER = BLOCK_ENTITIES.register("bacterial_analyzer",
            () -> new BlockEntityType<>(BacterialAnalyzerBlockEntity::new,
                    NTBlocks.BACTERIAL_ANALYZER.get()));

    public static final Supplier<BlockEntityType<CreativePowerSourceBlockEntity>> CREATIVE_POWER_SOURCE = BLOCK_ENTITIES.register("creative_power_source",
            () -> new BlockEntityType<>(CreativePowerSourceBlockEntity::new,
                    NTBlocks.CREATIVE_POWER_SOURCE.get()));
    public static final Supplier<BlockEntityType<CreativeEnergySourceBlockEntity>> CREATIVE_ENERGY_SOURCE = BLOCK_ENTITIES.register("creative_energy_source",
            () -> new BlockEntityType<>(CreativeEnergySourceBlockEntity::new,
                    NTBlocks.CREATIVE_ENERGY_SOURCE.get()));
    public static final Supplier<BlockEntityType<EnergyConverterBlockEntity>> ENERGY_CONVERTER = BLOCK_ENTITIES.register("energy_converter",
            () -> new BlockEntityType<>(EnergyConverterBlockEntity::new,
                    NTBlocks.ENERGY_CONVERTER.get()));
    public static final Supplier<BlockEntityType<ChargerBlockEntity>> CHARGER = BLOCK_ENTITIES.register("charger",
            () -> new BlockEntityType<>(ChargerBlockEntity::new,
                    NTBlocks.CHARGER.get()));

    // MULTIBLOCKS
    public static final Supplier<BlockEntityType<DrainBlockEntity>> DRAIN = BLOCK_ENTITIES.register("drain",
            () -> new BlockEntityType<>(DrainBlockEntity::new,
                    NTBlocks.DRAIN.get()));
    public static final Supplier<BlockEntityType<DrainPartBlockEntity>> DRAIN_PART = BLOCK_ENTITIES.register("drain_part",
            () -> new BlockEntityType<>(DrainPartBlockEntity::new,
                    NTBlocks.DRAIN_PART.get()));

    public static final Supplier<BlockEntityType<PrismarineCrystalBlockEntity>> PRISMARINE_CRYSTAL = BLOCK_ENTITIES.register("prismarine_crystal",
            () -> new BlockEntityType<>(PrismarineCrystalBlockEntity::new,
                    NTBlocks.PRISMARINE_CRYSTAL.get()));
    public static final Supplier<BlockEntityType<PrismarineCrystalPartBlockEntity>> PRISMARINE_CRYSTAL_PART = BLOCK_ENTITIES.register("prismarine_crystal_part",
            () -> new BlockEntityType<>(PrismarineCrystalPartBlockEntity::new,
                    NTBlocks.PRISMARINE_CRYSTAL_PART.get()));

    public static final Supplier<BlockEntityType<DecorativePrismarineCrystalBlockEntity>> DECORATIVE_PRISMARINE_CRYSTAL = BLOCK_ENTITIES.register("decorative_prismarine_crystal",
            () -> new BlockEntityType<>(DecorativePrismarineCrystalBlockEntity::new,
                    NTBlocks.DECORATIVE_PRISMARINE_CRYSTAL.get()));

    public static final Supplier<BlockEntityType<AugmentationStationBlockEntity>> AUGMENTATION_STATION = BLOCK_ENTITIES.register("augmentation_station",
            () -> new BlockEntityType<>(AugmentationStationBlockEntity::new,
                    NTBlocks.AUGMENTATION_STATION.get()));
    public static final Supplier<BlockEntityType<AugmentationStationPartBlockEntity>> AUGMENTATION_STATION_PART = BLOCK_ENTITIES.register("augmentation_station_part",
            () -> new BlockEntityType<>(AugmentationStationPartBlockEntity::new,
                    NTBlocks.AUGMENTATION_STATION_PART.get()));
    public static final Supplier<BlockEntityType<AugmentationStationExtensionBlockEntity>> AUGMENTATION_STATION_EXTENSION = BLOCK_ENTITIES.register("augmentation_station_extension",
            () -> new BlockEntityType<>(AugmentationStationExtensionBlockEntity::new,
                    NTBlocks.AUGMENTATION_STATION_EXTENSION.get()));
}
