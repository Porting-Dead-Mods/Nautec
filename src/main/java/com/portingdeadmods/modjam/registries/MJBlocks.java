package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.blocks.ExampleBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public final class MJBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ModJam.MODID);

    public static final DeferredBlock<ExampleBlock> EXAMPLE_BLOCK = registerBlockAndItem("example_block", ExampleBlock::new, BlockBehaviour.Properties.of());

    private static <T extends Block> DeferredBlock<T> registerBlockAndItem(String name, Function<BlockBehaviour.Properties, T> blockConstructor, BlockBehaviour.Properties properties) {
        DeferredBlock<T> block = BLOCKS.registerBlock(name, blockConstructor, properties);
        MJItems.ITEMS.registerItem(name, props -> new BlockItem(block.get(), props), new Item.Properties());
        return block;
    }
}
