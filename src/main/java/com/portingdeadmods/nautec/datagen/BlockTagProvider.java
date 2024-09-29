package com.portingdeadmods.nautec.datagen;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.registries.NTBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.portingdeadmods.nautec.registries.NTBlocks.*;

public class BlockTagProvider extends BlockTagsProvider {

    public BlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Nautec.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Add block tags here
        tag(BlockTags.MINEABLE_WITH_AXE,
                NTBlocks.CRATE,
                RUSTY_CRATE);
        tag(BlockTags.MINEABLE_WITH_PICKAXE,
                CRATE,
                RUSTY_CRATE,
                DARK_PRISMARINE_PILLAR,
                CHISELED_DARK_PRISMARINE,
                POLISHED_PRISMARINE,
                AQUARINE_STEEL_BLOCK,
                AQUATIC_CATALYST,
                PRISMARINE_RELAY,
                MIXER,
                CHARGER,
                LONG_DISTANCE_LASER,
                LASER_JUNCTION,
                DRAIN,
                DRAIN_WALL,
                DRAIN_PART,
                AUGMENTATION_STATION,
                AUGMENTATION_STATION_EXTENSION,
                AUGMENTATION_STATION_PART);
    }

    private void tag(TagKey<Block> blockTagKey, Block... blocks) {
        tag(blockTagKey).add(blocks);
    }

    @SafeVarargs
    private void tag(TagKey<Block> blockTagKey, DeferredBlock<? extends Block>... blocks) {
        IntrinsicTagAppender<Block> tag = tag(blockTagKey);
        for (DeferredBlock<? extends Block> block : blocks) {
            tag.add(block.get());
        }
    }
}
