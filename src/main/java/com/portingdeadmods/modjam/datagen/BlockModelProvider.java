package com.portingdeadmods.modjam.datagen;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.blocks.AquaticCatalystBlock;
import com.portingdeadmods.modjam.registries.MJBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BlockModelProvider extends BlockStateProvider {
    public BlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ModJam.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        axisBlock(MJBlocks.DARK_PRISMARINE_PILLAR.get());
        simpleBlock(MJBlocks.CHISELED_DARK_PRISMARINE.get());
        simpleBlock(MJBlocks.AQUARINE_STEEL_BLOCK.get());
        aquaticCatalyst(MJBlocks.AQUATIC_CATALYST.get());
    }

    private void aquaticCatalyst(AquaticCatalystBlock block) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        builder.partialState().with(AquaticCatalystBlock.CORE_ACTIVE, false)
                .modelForState().modelFile(models().cubeAll(name(block), blockTexture(block))).addModel();
        for (Direction direction : Direction.values()) {
            builder.partialState().with(BlockStateProperties.FACING, direction).with(AquaticCatalystBlock.CORE_ACTIVE, true)
                    .modelForState().modelFile(createActiveACModel(block, direction)).addModel();
        }
    }

    private ModelFile createActiveACModel(AquaticCatalystBlock block, Direction activeSide) {
        BlockModelBuilder builder = models().withExistingParent(name(block) + "_" + activeSide.getSerializedName(), "cube");
        for (Direction dir : Direction.values()) {
            if (dir == activeSide) {
                builder.texture(dir.getName(), extend(blockTexture(block), "_active"));
            } else {
                builder.texture(dir.getName(), blockTexture(block));
            }
        }
        return builder;
    }

    private ResourceLocation existingModelFile(Block block) {
        ResourceLocation name = key(block);
        return ResourceLocation.fromNamespaceAndPath(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath());
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        return ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), rl.getPath() + suffix);
    }
}