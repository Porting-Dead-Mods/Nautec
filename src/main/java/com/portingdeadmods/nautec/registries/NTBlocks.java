package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.blocks.*;
import com.portingdeadmods.nautec.content.blocks.multiblock.controller.AugmentationStationBlock;
import com.portingdeadmods.nautec.content.blocks.multiblock.controller.BioReactorBlock;
import com.portingdeadmods.nautec.content.blocks.multiblock.controller.DrainBlock;
import com.portingdeadmods.nautec.content.blocks.multiblock.part.AugmentationStationExtensionBlock;
import com.portingdeadmods.nautec.content.blocks.multiblock.part.AugmentationStationPartBlock;
import com.portingdeadmods.nautec.content.blocks.multiblock.part.BioReactorPartBlock;
import com.portingdeadmods.nautec.content.blocks.multiblock.part.DrainPartBlock;
import com.portingdeadmods.nautec.content.blocks.multiblock.semi.PrismarineCrystalBlock;
import com.portingdeadmods.nautec.content.blocks.multiblock.semi.PrismarineCrystalPartBlock;
import com.portingdeadmods.nautec.content.items.blocks.PrismarineCrystalItem;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class NTBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Nautec.MODID);

    public static final DeferredBlock<PrismarineSandBlock> PRISMARINE_SAND = registerBlockAndItem("prismarine_sand", props -> new PrismarineSandBlock(UniformInt.of(4, 6), props),
            BlockBehaviour.Properties.ofFullCopy(Blocks.SAND));

    public static final DeferredBlock<CrateBlock> CRATE = registerBlockAndItem("crate", CrateBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.BARREL));
    public static final DeferredBlock<CrateBlock> RUSTY_CRATE = registerBlockAndItem("rusty_crate", CrateBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.BARREL));
    public static final DeferredBlock<OilBarrelBlock> OIL_BARREL = registerBlockAndItem("oil_barrel", OilBarrelBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<Block> BROWN_POLYMER_BLOCK = registerBlockAndItem("brown_polymer_block", Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_WOOL));
    public static final DeferredBlock<RotatedPillarBlock> DARK_PRISMARINE_PILLAR = registerBlockAndItem("dark_prismarine_pillar", RotatedPillarBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE));
    public static final DeferredBlock<Block> CHISELED_DARK_PRISMARINE = registerBlockAndItem("chiseled_dark_prismarine", Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE));
    public static final DeferredBlock<Block> POLISHED_PRISMARINE = registerBlockAndItem("polished_prismarine", Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE));
    public static final DeferredBlock<Block> AQUARINE_STEEL_BLOCK = registerBlockAndItem("aquarine_steel_block", Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<Block> CAST_IRON_BLOCK = registerBlockAndItem("cast_iron_block", Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<AquaticCatalystBlock> AQUATIC_CATALYST = registerBlockAndItem("aquatic_catalyst", AquaticCatalystBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE));
    public static final DeferredBlock<PrismarineLaserRelayBlock> PRISMARINE_RELAY = registerBlockAndItem("prismarine_laser_relay", PrismarineLaserRelayBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.PRISMARINE_BRICKS));
    public static final DeferredBlock<MixerBlock> MIXER = registerBlockAndItem("mixer", MixerBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.PRISMARINE_BRICKS));
    public static final DeferredBlock<LongDistanceLaserBlock> LONG_DISTANCE_LASER = registerBlockAndItem("long_distance_laser", LongDistanceLaserBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE));
    public static final DeferredBlock<LaserJunctionBlock> LASER_JUNCTION = registerBlockAndItem("laser_junction", props -> new LaserJunctionBlock(props, 8),
            BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE), true, false);

    public static final DeferredBlock<PrismarineCrystalBlock> PRISMARINE_CRYSTAL = registerBlockAndItem("prismarine_crystal", PrismarineCrystalBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SEA_LANTERN).strength(50, 1200), PrismarineCrystalItem::new);
    public static final DeferredBlock<PrismarineCrystalPartBlock> PRISMARINE_CRYSTAL_PART = BLOCKS.registerBlock("prismarine_crystal_part", PrismarineCrystalPartBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SEA_LANTERN));
    public static final DeferredBlock<AnchorBlock> ANCHOR = registerBlockAndItem("anchor", AnchorBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.ANVIL).noOcclusion());
    public static final DeferredBlock<ChargerBlock> CHARGER = registerBlockAndItem("charger", ChargerBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<FishingStationBlock> FISHING_STATION = registerBlockAndItem("fishing_station", FishingStationBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<BreakerBlock> BREAKER_BLOCK = registerBlockAndItem("breaker", BreakerBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));

    // Biology
    public static final DeferredBlock<MutatorBlock> MUTATOR = registerBlockAndItem("mutator", MutatorBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<IncubatorBlock> INCUBATOR = registerBlockAndItem("incubator", IncubatorBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<BioReactorBlock> BIO_REACTOR = registerBlockAndItem("bio_reactor", BioReactorBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<BioReactorPartBlock> BIO_REACTOR_PART = registerBlockAndItem("bio_reactor_part", BioReactorPartBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<BacterialAnalyzerBlock> BACTERIAL_ANALYZER = registerBlockAndItem("bacterial_analyzer", BacterialAnalyzerBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), true, false);
    public static final DeferredBlock<BacterialAnalyzerTopBlock> BACTERIAL_ANALYZER_TOP = BLOCKS.registerBlock("bacterial_analyzer_top", BacterialAnalyzerTopBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    //public static final DeferredBlock<BacteriaPipeBlock> BACTERIA_PIPE = registerBlockAndItem("bacteria_pipe", BacteriaPipeBlock::new,
    //        BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS), true, false);

    // MULTIBLOCKS
    public static final DeferredBlock<DrainBlock> DRAIN = registerBlockAndItem("deep_sea_drain", DrainBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<Block> DRAIN_WALL = registerBlockAndItem("deep_sea_drain_wall", Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<DrainPartBlock> DRAIN_PART = BLOCKS.registerBlock("deep_sea_drain_part", DrainPartBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));

    public static final DeferredBlock<AugmentationStationBlock> AUGMENTATION_STATION = registerBlockAndItem("augmentation_station",
            AugmentationStationBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE));
    public static final DeferredBlock<AugmentationStationPartBlock> AUGMENTATION_STATION_PART = BLOCKS.registerBlock("augmentation_station_part",
            AugmentationStationPartBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE));
    public static final DeferredBlock<AugmentationStationExtensionBlock> AUGMENTATION_STATION_EXTENSION = registerBlockAndItem("augmentation_station_extension",
            AugmentationStationExtensionBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE));

    public static final DeferredBlock<Block> BACTERIAL_CONTAINMENT_SHIELD = registerBlockAndItem("bacterial_containment_shield", Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.PRISMARINE));

    public static final DeferredBlock<CreativePowerSourceBlock> CREATIVE_POWER_SOURCE = registerBlockAndItem("creative_power_source", CreativePowerSourceBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), false, true);
    public static final DeferredBlock<CreativeEnergySourceBlock> CREATIVE_ENERGY_SOURCE = registerBlockAndItem("creative_energy_source", CreativeEnergySourceBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), false, true);
    public static final DeferredBlock<EnergyConverterBlock> ENERGY_CONVERTER = registerBlockAndItem("energy_converter", EnergyConverterBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), false, true);

    private static <T extends Block> DeferredBlock<T> registerBlockAndItem(String name, Function<BlockBehaviour.Properties, T> blockConstructor, BlockBehaviour.Properties properties) {
        return registerBlockAndItem(name, blockConstructor, properties, true, true);
    }

    // NOTE: This also attempts to generate the item model for the block, when running datagen
    private static <T extends Block> DeferredBlock<T> registerBlockAndItem(String name, Function<BlockBehaviour.Properties, T> blockConstructor, BlockBehaviour.Properties properties, boolean addToTab, boolean genItemModel) {
        DeferredBlock<T> block = BLOCKS.registerBlock(name, blockConstructor, properties);
        DeferredItem<BlockItem> blockItem = NTItems.registerItem(name, props -> new BlockItem(block.get(), props), new Item.Properties(), addToTab);
        if (genItemModel) {
            NTItems.BLOCK_ITEMS.add(blockItem);
        }
        return block;
    }

    private static <T extends Block> DeferredBlock<T> registerBlockAndItem(String name, Function<BlockBehaviour.Properties, T> blockConstructor, BlockBehaviour.Properties properties, BiFunction<T, Item.Properties, BlockItem> blockItemConstructor) {
        DeferredBlock<T> block = BLOCKS.registerBlock(name, blockConstructor, properties);
        DeferredItem<BlockItem> blockItem = NTItems.registerItem(name, props -> blockItemConstructor.apply(block.get(), props), new Item.Properties());
        NTItems.BLOCK_ITEMS.add(blockItem);
        return block;
    }
}
