package com.portingdeadmods.nautec.datagen;

import com.portingdeadmods.nautec.api.multiblocks.Multiblock;
import com.portingdeadmods.nautec.content.blocks.multiblock.controller.AugmentationStationBlock;
import com.portingdeadmods.nautec.content.blocks.multiblock.part.DrainPartBlock;
import com.portingdeadmods.nautec.content.multiblocks.AugmentationStationMultiblock;
import com.portingdeadmods.nautec.content.multiblocks.BioReactorMultiblock;
import com.portingdeadmods.nautec.content.multiblocks.DrainMultiblock;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTMultiblocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import org.apache.commons.lang3.IntegerRange;
import org.jetbrains.annotations.NotNull;

public class MultiblockModelHelper {
    private final BlockModelProvider bmp;

    public MultiblockModelHelper(BlockModelProvider bmp) {
        this.bmp = bmp;
    }

    public void augmentationStationController(AugmentationStationBlock augmentationStationBlock) {
        ModelFile formedModel = bmp.models().getExistingFile(bmp.existingModelFile("multiblock/augmentation_station_4"));
        bmp.getVariantBuilder(augmentationStationBlock).partialState().with(Multiblock.FORMED, true)
                .modelForState().modelFile(formedModel).addModel()
                .partialState().with(Multiblock.FORMED, false)
                .modelForState().modelFile(unformedAugmentationStationPart(augmentationStationBlock, "controller")).addModel();
    }

    public void augmentationStationExtension(Block augmentationStationExtensionBlock) {
        VariantBlockStateBuilder builder = bmp.getVariantBuilder(augmentationStationExtensionBlock);
        builder
                .partialState().with(Multiblock.FORMED, false)
                .modelForState().modelFile(unformedAugmentationStationPart(augmentationStationExtensionBlock, "extension")).addModel();
        for (Direction dir : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            builder.partialState().with(Multiblock.FORMED, true).with(BlockStateProperties.HORIZONTAL_FACING, dir)
                    .modelForState().modelFile(bmp.models().getExistingFile(bmp.existingModelFile("multiblock/augmentation_station_extension")))
                    .rotationY(((int) dir.toYRot() + 180) % 360).addModel();
        }
    }

    public void augmentationStationPart(Block augmentationStationPartBlock, IntegerRange range) {
        VariantBlockStateBuilder builder = bmp.getVariantBuilder(augmentationStationPartBlock);
        builder.partialState().with(Multiblock.FORMED, false)
                .modelForState().modelFile(drainPartModel(augmentationStationPartBlock, 0, false)).addModel();
        for (int i = range.getMinimum(); i <= range.getMaximum(); i++) {
            ModelFile formedModel = bmp.models().getExistingFile(bmp.existingModelFile("multiblock/augmentation_station_" + (8 - i)));
            int index = i;
            if (i == 0 || i == 3 || i == 6) {
                index += 2;
            } else if (i == 2 || i == 5 || i == 8) {
                index -= 2;
            }
            builder.partialState().with(Multiblock.FORMED, true).with(AugmentationStationMultiblock.AS_PART, index)
                    .modelForState().modelFile(formedModel).addModel();
        }
    }

    public @NotNull BlockModelBuilder unformedAugmentationStationPart(Block augmentationStationController, String part) {
        Multiblock multiblock = NTMultiblocks.AUGMENTATION_STATION.get();
        BlockModelBuilder builder = bmp.models().withExistingParent(bmp.name(augmentationStationController), "cube");
        builder.texture("up", bmp.multiblockTexture(multiblock, "unformed/" + part + "_top"))
                .texture("down", bmp.multiblockTexture(multiblock, "unformed/" + part + "_bottom"))
                .texture("north", bmp.multiblockTexture(multiblock, "unformed/" + part + "_side"))
                .texture("east", bmp.multiblockTexture(multiblock, "unformed/" + part + "_side"))
                .texture("south", bmp.multiblockTexture(multiblock, "unformed/" + part + "_side"))
                .texture("west", bmp.multiblockTexture(multiblock, "unformed/" + part + "_side"));
        return builder;
    }

    public void drainController(Block drainController) {
        Multiblock multiblock = NTMultiblocks.DRAIN.get();
        ModelFile unformedModel = drainControllerModel(drainController, multiblock, false);
        bmp.getVariantBuilder(drainController).partialState().with(DrainMultiblock.FORMED, false)
                .modelForState().modelFile(unformedModel).addModel();
        ModelFile formedModel = drainControllerModel(drainController, multiblock, true);
        bmp.getVariantBuilder(drainController).partialState().with(DrainMultiblock.FORMED, true)
                .modelForState().modelFile(formedModel).addModel();
    }

    public @NotNull BlockModelBuilder drainControllerModel(Block drainController, Multiblock multiblock, boolean formed) {
        BlockModelBuilder builder = bmp.models().withExistingParent(bmp.name(drainController) + (formed ? "_formed" : ""), "cube");
        builder.texture("up", bmp.multiblockTexture(multiblock, formed ? "top_4" : "drain_top_unformed"))
                .texture("down", bmp.multiblockTexture(multiblock, formed ? "bottom_4" : "drain_bottom_unformed"))
                .texture("north", bmp.multiblockTexture(multiblock, "drain_side_unformed"))
                .texture("east", bmp.multiblockTexture(multiblock, "drain_side_unformed"))
                .texture("south", bmp.multiblockTexture(multiblock, "drain_side_unformed"))
                .texture("west", bmp.multiblockTexture(multiblock, "drain_side_unformed"));
        return builder;
    }

    public void drainPart(Block drainPartBlock, IntegerRange range) {
        VariantBlockStateBuilder builder = bmp.getVariantBuilder(drainPartBlock);
        builder.partialState().with(DrainMultiblock.FORMED, false)
                .modelForState().modelFile(drainPartModel(drainPartBlock, 0, false)).addModel();
        for (int i = range.getMinimum(); i <= range.getMaximum(); i++) {
            builder.partialState().with(DrainMultiblock.DRAIN_PART, i).with(DrainMultiblock.FORMED, true).with(DrainPartBlock.LASER_PORT, false)
                    .modelForState().modelFile(drainPartModel(drainPartBlock, i, false)).addModel();
            builder.partialState().with(DrainMultiblock.DRAIN_PART, i).with(DrainMultiblock.FORMED, true).with(DrainPartBlock.LASER_PORT, true)
                    .modelForState().modelFile(drainPartModel(drainPartBlock, i, true)).addModel();
        }
    }

    public ModelFile drainPartModel(Block drainPartBlock, int index, boolean laserPort) {
        String postfix = laserPort ? "_open" : "";
        BlockModelBuilder builder = bmp.models().withExistingParent(bmp.name(drainPartBlock) + "_" + index + postfix, "cube");
        Multiblock multiblock = NTMultiblocks.DRAIN.get();
        // TODO: Clean up
        if (index % 2 != 0) {
            builder.texture("up", bmp.multiblockTexture(multiblock, "top_" + index))
                    .texture("down", bmp.multiblockTexture(multiblock, "bottom_" + index))
                    .texture("north", bmp.multiblockTexture(multiblock, "side_1" + postfix))
                    .texture("east", bmp.multiblockTexture(multiblock, "side_1" + postfix))
                    .texture("south", bmp.multiblockTexture(multiblock, "side_1" + postfix))
                    .texture("west", bmp.multiblockTexture(multiblock, "side_1" + postfix));
        } else if (index == 0 || index == 2) {
            builder.texture("up", bmp.multiblockTexture(multiblock, "top_" + index))
                    .texture("down", bmp.multiblockTexture(multiblock, "bottom_" + index))
                    .texture("north", bmp.multiblockTexture(multiblock, "side_" + (2 - index % 3)))
                    .texture("east", bmp.multiblockTexture(multiblock, "side_" + index % 3))
                    .texture("south", bmp.multiblockTexture(multiblock, "side_" + (2 - index % 3)))
                    .texture("west", bmp.multiblockTexture(multiblock, "side_" + index % 3));
        } else {
            builder.texture("up", bmp.multiblockTexture(multiblock, "top_" + index))
                    .texture("down", bmp.multiblockTexture(multiblock, "bottom_" + index))
                    .texture("north", bmp.multiblockTexture(multiblock, "side_" + index % 3))
                    .texture("east", bmp.multiblockTexture(multiblock, "side_" + (2 - index % 3)))
                    .texture("south", bmp.multiblockTexture(multiblock, "side_" + index % 3))
                    .texture("west", bmp.multiblockTexture(multiblock, "side_" + (2 - index % 3)));
        }
        return builder;
    }

    public void bioReactorPart(Block block) {
        VariantBlockStateBuilder builder = bmp.getVariantBuilder(block);
        for (int i : BioReactorMultiblock.BIO_REACTOR_PART.getPossibleValues()) {
            builder.partialState().with(BioReactorMultiblock.BIO_REACTOR_PART, i).with(BioReactorMultiblock.FORMED, true)
                    .with(BioReactorMultiblock.TOP, true).with(BioReactorMultiblock.HATCH, true)
                    .modelForState().modelFile(bioReactorPartModel(block, i, true, true)).addModel()
                    .partialState().with(BioReactorMultiblock.BIO_REACTOR_PART, i).with(BioReactorMultiblock.FORMED, false)
                    .with(BioReactorMultiblock.TOP, true).with(BioReactorMultiblock.HATCH, true)
                    .modelForState().modelFile(bioReactorPartModel(block, i, true, true)).addModel()
                    .partialState().with(BioReactorMultiblock.BIO_REACTOR_PART, i).with(BioReactorMultiblock.FORMED, true)
                    .with(BioReactorMultiblock.TOP, false).with(BioReactorMultiblock.HATCH, true)
                    .modelForState().modelFile(bioReactorPartModel(block, i, false, true)).addModel()
                    .partialState().with(BioReactorMultiblock.BIO_REACTOR_PART, i).with(BioReactorMultiblock.FORMED, false)
                    .with(BioReactorMultiblock.TOP, false).with(BioReactorMultiblock.HATCH, true)
                    .modelForState().modelFile(bioReactorPartModel(block, i, false, true)).addModel()
                    .partialState().with(BioReactorMultiblock.BIO_REACTOR_PART, i).with(BioReactorMultiblock.FORMED, true)
                    .with(BioReactorMultiblock.TOP, true).with(BioReactorMultiblock.HATCH, false)
                    .modelForState().modelFile(bioReactorPartModel(block, i, true, false)).addModel()
                    .partialState().with(BioReactorMultiblock.BIO_REACTOR_PART, i).with(BioReactorMultiblock.FORMED, false)
                    .with(BioReactorMultiblock.TOP, true).with(BioReactorMultiblock.HATCH, false)
                    .modelForState().modelFile(bioReactorPartModel(block, i, true, false)).addModel()
                    .partialState().with(BioReactorMultiblock.BIO_REACTOR_PART, i).with(BioReactorMultiblock.FORMED, true)
                    .with(BioReactorMultiblock.TOP, false).with(BioReactorMultiblock.HATCH, false)
                    .modelForState().modelFile(bioReactorPartModel(block, i, false, false)).addModel()
                    .partialState().with(BioReactorMultiblock.BIO_REACTOR_PART, i).with(BioReactorMultiblock.FORMED, false)
                    .with(BioReactorMultiblock.TOP, false).with(BioReactorMultiblock.HATCH, false)
                    .modelForState().modelFile(bioReactorPartModel(block, i, false, false)).addModel();
        }
    }

    private ModelFile bioReactorPartModel(Block block, int index, boolean top, boolean hatch) {
        Multiblock multiblock = NTMultiblocks.BIO_REACTOR.get();
        String middleFix = top ? "top" : "bottom";
        BlockModelBuilder builder = bmp.models().withExistingParent(bmp.name(block) + "_" + index + "_" + middleFix + (hatch ? "_hatch" : ""), "cube");
        // TODO: Clean up

        if (index % 2 != 0) {
            builder.texture("up", hatch ? bmp.multiblockTexture(multiblock, "top_" + index + "_hatch") : bmp.multiblockTexture(multiblock, "top_" + index))
                    .texture("down", bmp.blockTexture(NTBlocks.POLISHED_PRISMARINE.get()))
                    .texture("north", bmp.multiblockTexture(multiblock, "side_" + middleFix + "_1"))
                    .texture("east", bmp.multiblockTexture(multiblock, "side_" + middleFix + "_1"))
                    .texture("south", bmp.multiblockTexture(multiblock, "side_" + middleFix + "_1"))
                    .texture("west", bmp.multiblockTexture(multiblock, "side_" + middleFix + "_1"));
        } else if (index == 0 || index == 2) {
            builder.texture("up", bmp.multiblockTexture(multiblock, "top_" + index))
                    .texture("down", bmp.blockTexture(NTBlocks.POLISHED_PRISMARINE.get()))
                    .texture("north", bmp.multiblockTexture(multiblock, "side_" + middleFix + "_" + (2 - index % 3)))
                    .texture("east", bmp.multiblockTexture(multiblock, "side_" + middleFix + "_" + index % 3))
                    .texture("south", bmp.multiblockTexture(multiblock, "side_" + middleFix + "_" + (2 - index % 3)))
                    .texture("west", bmp.multiblockTexture(multiblock, "side_" + middleFix + "_" + index % 3));
        } else {
            builder.texture("up", bmp.multiblockTexture(multiblock, "top_" + index))
                    .texture("down", bmp.blockTexture(NTBlocks.POLISHED_PRISMARINE.get()))
                    .texture("north", bmp.multiblockTexture(multiblock, "side_" + middleFix + "_" + index % 3))
                    .texture("east", bmp.multiblockTexture(multiblock, "side_" + middleFix + "_" + (2 - index % 3)))
                    .texture("south", bmp.multiblockTexture(multiblock, "side_" + middleFix + "_" + index % 3))
                    .texture("west", bmp.multiblockTexture(multiblock, "side_" + middleFix + "_" + (2 - index % 3)));
        }
        return builder;
    }
}
