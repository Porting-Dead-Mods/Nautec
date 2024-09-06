package com.portingdeadmods.modjam.datagen;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.utils.OptionalDirection;
import com.portingdeadmods.modjam.content.blocks.AquaticCatalystBlock;
import com.portingdeadmods.modjam.registries.MJBlocks;
import com.portingdeadmods.modjam.utils.MJBlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
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
        for (OptionalDirection direction : OptionalDirection.values()) {
            builder.partialState().with(MJBlockStateProperties.HOS_ACTIVE, direction).modelForState().modelFile(createACModel(block, direction)).addModel();
        }
    }

    private ModelFile createACModel(AquaticCatalystBlock block, OptionalDirection activeSide) {
        if (activeSide != OptionalDirection.NONE) {
            BlockModelBuilder builder = models().withExistingParent(name(block) + "_" + activeSide.getSerializedName(), "cube");
            for (Direction dir : Direction.values()) {
                if (dir == activeSide.getMcDirection()) {
                    builder.texture(dir.getName(), extend(blockTexture(block), "_active"));
                } else {
                    builder.texture(dir.getName(), blockTexture(block));
                }
            }
            return builder;
        } else {
            return models().cubeAll(name(block), blockTexture(block));
        }
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