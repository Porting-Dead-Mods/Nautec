package com.portingdeadmods.modjam.datagen;

import com.portingdeadmods.modjam.MJRegistries;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.multiblocks.Multiblock;
import com.portingdeadmods.modjam.content.blocks.AquaticCatalystBlock;
import com.portingdeadmods.modjam.content.blocks.CrateBlock;
import com.portingdeadmods.modjam.content.blocks.multiblock.part.DrainPartBlock;
import com.portingdeadmods.modjam.content.multiblocks.DrainMultiblock;
import com.portingdeadmods.modjam.registries.MJBlocks;
import com.portingdeadmods.modjam.registries.MJMultiblocks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.apache.commons.lang3.IntegerRange;

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
        drainPart(MJBlocks.DRAIN_PART.get(), IntegerRange.of(0, 8));
        crateBlock(MJBlocks.CRATE.get());
    }

    private void crateBlock(CrateBlock crateBlock) {
        VariantBlockStateBuilder builder = getVariantBuilder(crateBlock);
        builder.partialState().with(CrateBlock.RUSTY,false).with(BlockStateProperties.OPEN,false)
                .modelForState().modelFile(models().getExistingFile(existingModelFile(crateBlock))).addModel();
        builder.partialState().with(CrateBlock.RUSTY,true).with(BlockStateProperties.OPEN,false)
                .modelForState().modelFile(rustedCrateModel(crateBlock,false)).addModel();
        builder.partialState().with(CrateBlock.RUSTY,false).with(BlockStateProperties.OPEN,true)
                .modelForState().modelFile(models().getExistingFile(extend(existingModelFile(crateBlock),"_open"))).addModel();
        builder.partialState().with(CrateBlock.RUSTY,true).with(BlockStateProperties.OPEN,true)
                .modelForState().modelFile(rustedCrateModel(crateBlock,true)).addModel();
    }

    private ModelFile rustedCrateModel(CrateBlock block,boolean open) {
        return models().withExistingParent("rusty_" + name(block) + (open ? "_open": ""), extend(existingModelFile(block),open ? "_open":""))
                .texture("2", "modjam:block/crate/rusty_top_inner")
                .texture("4", "modjam:block/crate/rusty")
                .texture("5", "modjam:block/crate/rusty_top")
                .texture("particle", "modjam:block/crate/rusty");
    }


    private void drainPart(DrainPartBlock drainPartBlock, IntegerRange range) {
        VariantBlockStateBuilder builder = getVariantBuilder(drainPartBlock);
        builder.partialState().with(DrainMultiblock.FORMED, false)
                .modelForState().modelFile(drainPartModel(drainPartBlock, 0, false)).addModel();
        for (int i = range.getMinimum(); i <= range.getMaximum(); i++) {
            builder.partialState().with(DrainMultiblock.DRAIN_PART, i).with(DrainMultiblock.FORMED, true).with(DrainPartBlock.LASER_PORT, false)
                    .modelForState().modelFile(drainPartModel(drainPartBlock, i, false)).addModel();
            builder.partialState().with(DrainMultiblock.DRAIN_PART, i).with(DrainMultiblock.FORMED, true).with(DrainPartBlock.LASER_PORT, true)
                    .modelForState().modelFile(drainPartModel(drainPartBlock, i, true)).addModel();
        }
    }

    private ModelFile drainPartModel(DrainPartBlock drainPartBlock, int index, boolean laserPort) {
        String postfix = laserPort ? "_open" : "";
        BlockModelBuilder builder = models().withExistingParent(name(drainPartBlock) + "_" + index + postfix, "cube");
        Multiblock multiblock = MJMultiblocks.DRAIN.get();
        // TODO: Clean up
        if (index % 2 != 0) {
            builder.texture("up", multiblockTexture(multiblock, "top_" + index))
                    .texture("north", multiblockTexture(multiblock, "side_1" + postfix))
                    .texture("east", multiblockTexture(multiblock, "side_1" + postfix))
                    .texture("south", multiblockTexture(multiblock, "side_1" + postfix))
                    .texture("west", multiblockTexture(multiblock, "side_1" + postfix));
        } else if (index == 0 || index == 2) {
            builder.texture("up", multiblockTexture(multiblock, "top_" + index))
                    .texture("north", multiblockTexture(multiblock, "side_" + (2 - index % 3)))
                    .texture("east", multiblockTexture(multiblock, "side_" + index % 3))
                    .texture("south", multiblockTexture(multiblock, "side_" + (2 - index % 3)))
                    .texture("west", multiblockTexture(multiblock, "side_" + index % 3));
        } else {
            builder.texture("up", multiblockTexture(multiblock, "top_" + index))
                    .texture("north", multiblockTexture(multiblock, "side_" + index % 3))
                    .texture("east", multiblockTexture(multiblock, "side_" + (2 - index % 3)))
                    .texture("south", multiblockTexture(multiblock, "side_" + index % 3))
                    .texture("west", multiblockTexture(multiblock, "side_" + (2 - index % 3)));
        }
        return builder;
    }

    public ResourceLocation multiblockTexture(Multiblock multiblock, String name) {
        return modLoc(ModelProvider.BLOCK_FOLDER + "/multiblock/" + MJRegistries.MULTIBLOCK.getKey(multiblock).getPath() + "/" + name);
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