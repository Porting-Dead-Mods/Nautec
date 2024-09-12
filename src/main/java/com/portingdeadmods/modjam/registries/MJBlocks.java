package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.blocks.AquaticCatalystBlock;
import com.portingdeadmods.modjam.content.blocks.CrateBlock;
import com.portingdeadmods.modjam.content.blocks.PrismarineCrystalBlock;
import com.portingdeadmods.modjam.content.blocks.PrismarineLaserRelayBlock;
import com.portingdeadmods.modjam.content.blocks.multiblock.controller.DrainBlock;
import com.portingdeadmods.modjam.content.blocks.multiblock.part.DrainPartBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public final class MJBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ModJam.MODID);

    public static final DeferredBlock<CrateBlock> CRATE = registerBlockAndItem("crate", CrateBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.BARREL));
    public static final DeferredBlock<RotatedPillarBlock> DARK_PRISMARINE_PILLAR = registerBlockAndItem("dark_prismarine_pillar", RotatedPillarBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE));
    public static final DeferredBlock<Block> CHISELED_DARK_PRISMARINE = registerBlockAndItem("chiseled_dark_prismarine", Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE));
    public static final DeferredBlock<Block> AQUARINE_STEEL_BLOCK = registerBlockAndItem("aquarine_steel_block", Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<AquaticCatalystBlock> AQUATIC_CATALYST = registerBlockAndItem("aquatic_catalyst", AquaticCatalystBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE).lightLevel(state -> state.getValue(AquaticCatalystBlock.CORE_ACTIVE) ? 12 : 0));
    public static final DeferredBlock<PrismarineLaserRelayBlock> PRISMARINE_RELAY = registerBlockAndItem("prismarine_laser_relay", PrismarineLaserRelayBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.PRISMARINE_BRICKS));

    public static final DeferredBlock<PrismarineCrystalBlock> PRISMARINE_CRYSTAL = registerBlockAndItem("prismarine_crystal", PrismarineCrystalBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));

    // MULTIBLOCKS
    public static final DeferredBlock<DrainBlock> DRAIN = registerBlockAndItem("deep_sea_drain", DrainBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<DrainPartBlock> DRAIN_PART = BLOCKS.registerBlock("deep_sea_drain_part", DrainPartBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));

    // FLUIDS
    public static final DeferredBlock<LiquidBlock> SALT_WATER_FLUID_BLOCK = BLOCKS.register("salt_water_block",
            () -> new LiquidBlock(MJFluids.SALT_WATER_SOURCE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));

    public static final DeferredBlock<LiquidBlock> EAS_FLUID_BLOCK = BLOCKS.register("electrolyte_algae_serum_block",
            () -> new LiquidBlock(MJFluids.EAS_SOURCE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));

    public static final DeferredBlock<LiquidBlock> ETCHING_ACID_FLUID_BLOCK = BLOCKS.register("etching_acid_block",
            () -> new LiquidBlock(MJFluids.ETCHING_ACID_SOURCE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));

    private static <T extends Block> DeferredBlock<T> registerBlockAndItem(String name, Function<BlockBehaviour.Properties, T> blockConstructor, BlockBehaviour.Properties properties) {
        return registerBlockAndItem(name, blockConstructor, properties, true);
    }

    // NOTE: This also attempts to generate the item model for the block, when running datagen
    private static <T extends Block> DeferredBlock<T> registerBlockAndItem(String name, Function<BlockBehaviour.Properties, T> blockConstructor, BlockBehaviour.Properties properties, boolean addToTab) {
        DeferredBlock<T> block = BLOCKS.registerBlock(name, blockConstructor, properties);
        DeferredItem<BlockItem> blockItem = MJItems.registerItem(name, props -> new BlockItem(block.get(), props), new Item.Properties(), addToTab);
        MJItems.BLOCK_ITEMS.add(blockItem);
        return block;
    }
}
