package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.blocks.ExampleBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public final class MJBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ModJam.MODID);

    public static final DeferredBlock<RotatedPillarBlock> DARK_PRISMARINE_PILLAR = registerBlockAndItem("chiseled_dark_prismarine", RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE));
    public static final DeferredBlock<Block> CHISELED_DARK_PRISMARINE = registerBlockAndItem("chiseled_prismarine_pillar", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE));

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
