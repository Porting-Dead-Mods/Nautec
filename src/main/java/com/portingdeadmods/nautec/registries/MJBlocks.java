package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.blocks.*;
import com.portingdeadmods.nautec.content.blocks.multiblock.controller.AugmentationStationBlock;
import com.portingdeadmods.nautec.content.blocks.multiblock.controller.DrainBlock;
import com.portingdeadmods.nautec.content.blocks.multiblock.part.AugmentationStationExtensionBlock;
import com.portingdeadmods.nautec.content.blocks.multiblock.part.AugmentationStationPartBlock;
import com.portingdeadmods.nautec.content.blocks.multiblock.part.DrainPartBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

import static net.minecraft.world.level.block.Blocks.register;

public final class MJBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Nautec.MODID);

    public static final DeferredBlock<CrateBlock> CRATE = registerBlockAndItem("crate", CrateBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.BARREL));
    public static final DeferredBlock<CrateBlock> RUSTY_CRATE = registerBlockAndItem("rusty_crate", CrateBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.BARREL));
    public static final DeferredBlock<RotatedPillarBlock> DARK_PRISMARINE_PILLAR = registerBlockAndItem("dark_prismarine_pillar", RotatedPillarBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE));
    public static final DeferredBlock<Block> CHISELED_DARK_PRISMARINE = registerBlockAndItem("chiseled_dark_prismarine", Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE));
    public static final DeferredBlock<Block> POLISHED_PRISMARINE = registerBlockAndItem("polished_prismarine", Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE));
    public static final DeferredBlock<Block> AQUARINE_STEEL_BLOCK = registerBlockAndItem("aquarine_steel_block", Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<AquaticCatalystBlock> AQUATIC_CATALYST = registerBlockAndItem("aquatic_catalyst", AquaticCatalystBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE).lightLevel(state -> state.getValue(AquaticCatalystBlock.CORE_ACTIVE) ? 12 : 0));
    public static final DeferredBlock<PrismarineLaserRelayBlock> PRISMARINE_RELAY = registerBlockAndItem("prismarine_laser_relay", PrismarineLaserRelayBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.PRISMARINE_BRICKS));
    public static final DeferredBlock<MixerBlock> MIXER = registerBlockAndItem("mixer", MixerBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.PRISMARINE_BRICKS));
    public static final DeferredBlock<LongDistanceLaserBlock> LONG_DISTANCE_LASER = registerBlockAndItem("long_distance_laser", LongDistanceLaserBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE));
    public static final DeferredBlock<LaserJunctionBlock> LASER_JUNCTION = registerBlockAndItem("laser_junction", props -> new LaserJunctionBlock(props, 8),
            BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE), true, false);

    public static final DeferredBlock<PrismarineCrystalBlock> PRISMARINE_CRYSTAL = registerBlockAndItem("prismarine_crystal", PrismarineCrystalBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));

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
    public static final DeferredBlock<AugmentationStationExtensionBlock> AUGMENTATION_STATION_EXTENSION = BLOCKS.registerBlock("augmentation_station_extension",
            AugmentationStationExtensionBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE));


    // FLUIDS
    public static final DeferredBlock<LiquidBlock> SALT_WATER_FLUID_BLOCK = BLOCKS.register("salt_water_block",
            () -> new LiquidBlock(MJFluids.SALT_WATER_SOURCE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));

    public static final DeferredBlock<LiquidBlock> EAS_FLUID_BLOCK = BLOCKS.register("electrolyte_algae_serum_block",
            () -> new LiquidBlock(MJFluids.EAS_SOURCE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));

    public static final DeferredBlock<LiquidBlock> ETCHING_ACID_FLUID_BLOCK = BLOCKS.register("etching_acid_block",
            () -> new LiquidBlock(MJFluids.ETCHING_ACID_SOURCE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));

    public static final DeferredBlock<CreativePowerSourceBlock> CREATIVE_POWER_SOURCE = registerBlockAndItem("creative_power_source", CreativePowerSourceBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), false, true);

    private static <T extends Block> DeferredBlock<T> registerBlockAndItem(String name, Function<BlockBehaviour.Properties, T> blockConstructor, BlockBehaviour.Properties properties) {
        return registerBlockAndItem(name, blockConstructor, properties, true, true);
    }

    // NOTE: This also attempts to generate the item model for the block, when running datagen
    private static <T extends Block> DeferredBlock<T> registerBlockAndItem(String name, Function<BlockBehaviour.Properties, T> blockConstructor, BlockBehaviour.Properties properties, boolean addToTab, boolean genItemModel) {
        DeferredBlock<T> block = BLOCKS.registerBlock(name, blockConstructor, properties);
        DeferredItem<BlockItem> blockItem = MJItems.registerItem(name, props -> new BlockItem(block.get(), props), new Item.Properties(), addToTab);
        if (genItemModel) {
            MJItems.BLOCK_ITEMS.add(blockItem);
        }
        return block;
    }
}
