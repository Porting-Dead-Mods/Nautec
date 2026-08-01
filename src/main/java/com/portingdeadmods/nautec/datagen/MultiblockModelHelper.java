package com.portingdeadmods.nautec.datagen;

import com.portingdeadmods.nautec.api.multiblocks.Multiblock;
import com.portingdeadmods.nautec.content.blocks.multiblock.controller.AugmentationStationBlock;
import com.portingdeadmods.nautec.content.blocks.multiblock.part.DrainPartBlock;
import com.portingdeadmods.nautec.content.multiblocks.AugmentationStationMultiblock;
import com.portingdeadmods.nautec.content.multiblocks.BioReactorMultiblock;
import com.portingdeadmods.nautec.content.multiblocks.DrainMultiblock;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTMultiblocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.apache.commons.lang3.IntegerRange;
import org.jetbrains.annotations.NotNull;

public class MultiblockModelHelper {
    private final BlockModelProvider bmp;

    public MultiblockModelHelper(BlockModelProvider bmp) {
        this.bmp = bmp;
    }

    public void augmentationStationController(AugmentationStationBlock augmentationStationBlock) {
        MultiVariant formedModel = BlockModelGenerators.plainVariant(bmp.existingModelFile("multiblock/augmentation_station_4"));
        MultiVariant unformedModel = BlockModelGenerators.plainVariant(unformedAugmentationStationPart(augmentationStationBlock, "controller"));
        bmp.blockModels().blockStateOutput.accept(MultiVariantGenerator.dispatch(augmentationStationBlock)
                .with(BlockModelGenerators.createBooleanModelDispatch(Multiblock.FORMED, formedModel, unformedModel)));
    }

    public void augmentationStationExtension(Block augmentationStationExtensionBlock) {
        MultiVariant unformedModel = BlockModelGenerators.plainVariant(unformedAugmentationStationPart(augmentationStationExtensionBlock, "extension"));
        MultiVariant formedModel = BlockModelGenerators.plainVariant(bmp.existingModelFile("multiblock/augmentation_station_extension"));
        PropertyDispatch.C2<MultiVariant, Boolean, Direction> dispatch = PropertyDispatch.initial(Multiblock.FORMED, BlockStateProperties.HORIZONTAL_FACING);
        for (Direction dir : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            dispatch = dispatch
                    .select(false, dir, unformedModel)
                    .select(true, dir, BlockModelProvider.rotated(formedModel, 0, ((int) dir.toYRot() + 180) % 360));
        }
        bmp.blockModels().blockStateOutput.accept(MultiVariantGenerator.dispatch(augmentationStationExtensionBlock).with(dispatch));
    }

    public void augmentationStationPart(Block augmentationStationPartBlock, IntegerRange range) {
        MultiVariant unformedModel = BlockModelGenerators.plainVariant(drainPartModel(augmentationStationPartBlock, 0, false));
        PropertyDispatch.C2<MultiVariant, Boolean, Integer> dispatch = PropertyDispatch.initial(Multiblock.FORMED, AugmentationStationMultiblock.AS_PART);
        for (int i : AugmentationStationMultiblock.AS_PART.getPossibleValues()) {
            dispatch = dispatch.select(false, i, unformedModel);
        }
        for (int i = range.getMinimum(); i <= range.getMaximum(); i++) {
            MultiVariant formedModel = BlockModelGenerators.plainVariant(bmp.existingModelFile("multiblock/augmentation_station_" + (8 - i)));
            int index = i;
            if (i == 0 || i == 3 || i == 6) {
                index += 2;
            } else if (i == 2 || i == 5 || i == 8) {
                index -= 2;
            }
            dispatch = dispatch.select(true, index, formedModel);
        }
        bmp.blockModels().blockStateOutput.accept(MultiVariantGenerator.dispatch(augmentationStationPartBlock).with(dispatch));
    }

    public @NotNull Identifier unformedAugmentationStationPart(Block augmentationStationController, String part) {
        Multiblock multiblock = NTMultiblocks.AUGMENTATION_STATION.get();
        return bmp.cube(bmp.name(augmentationStationController),
                bmp.multiblockTexture(multiblock, "unformed/" + part + "_bottom"),
                bmp.multiblockTexture(multiblock, "unformed/" + part + "_top"),
                bmp.multiblockTexture(multiblock, "unformed/" + part + "_side"),
                bmp.multiblockTexture(multiblock, "unformed/" + part + "_side"),
                bmp.multiblockTexture(multiblock, "unformed/" + part + "_side"),
                bmp.multiblockTexture(multiblock, "unformed/" + part + "_side"),
                bmp.multiblockTexture(multiblock, "unformed/" + part + "_side"));
    }

    public void drainController(Block drainController) {
        Multiblock multiblock = NTMultiblocks.DRAIN.get();
        MultiVariant unformedModel = BlockModelGenerators.plainVariant(drainControllerModel(drainController, multiblock, false));
        MultiVariant formedModel = BlockModelGenerators.plainVariant(drainControllerModel(drainController, multiblock, true));
        bmp.blockModels().blockStateOutput.accept(MultiVariantGenerator.dispatch(drainController)
                .with(BlockModelGenerators.createBooleanModelDispatch(DrainMultiblock.FORMED, formedModel, unformedModel)));
    }

    public @NotNull Identifier drainControllerModel(Block drainController, Multiblock multiblock, boolean formed) {
        return bmp.cube(bmp.name(drainController) + (formed ? "_formed" : ""),
                bmp.multiblockTexture(multiblock, formed ? "bottom_4" : "drain_bottom_unformed"),
                bmp.multiblockTexture(multiblock, formed ? "top_4" : "drain_top_unformed"),
                bmp.multiblockTexture(multiblock, "drain_side_unformed"),
                bmp.multiblockTexture(multiblock, "drain_side_unformed"),
                bmp.multiblockTexture(multiblock, "drain_side_unformed"),
                bmp.multiblockTexture(multiblock, "drain_side_unformed"),
                bmp.multiblockTexture(multiblock, "drain_side_unformed"));
    }

    public void drainPart(Block drainPartBlock, IntegerRange range) {
        MultiVariant unformedModel = BlockModelGenerators.plainVariant(drainPartModel(drainPartBlock, 0, false));
        PropertyDispatch.C3<MultiVariant, Boolean, Integer, Boolean> dispatch = PropertyDispatch.initial(DrainMultiblock.FORMED, DrainMultiblock.DRAIN_PART, DrainPartBlock.LASER_PORT);
        for (int i : DrainMultiblock.DRAIN_PART.getPossibleValues()) {
            dispatch = dispatch
                    .select(false, i, false, unformedModel)
                    .select(false, i, true, unformedModel);
        }
        for (int i = range.getMinimum(); i <= range.getMaximum(); i++) {
            dispatch = dispatch
                    .select(true, i, false, BlockModelGenerators.plainVariant(drainPartModel(drainPartBlock, i, false)))
                    .select(true, i, true, BlockModelGenerators.plainVariant(drainPartModel(drainPartBlock, i, true)));
        }
        bmp.blockModels().blockStateOutput.accept(MultiVariantGenerator.dispatch(drainPartBlock).with(dispatch));
    }

    public Identifier drainPartModel(Block drainPartBlock, int index, boolean laserPort) {
        String postfix = laserPort ? "_open" : "";
        String name = bmp.name(drainPartBlock) + "_" + index + postfix;
        Multiblock multiblock = NTMultiblocks.DRAIN.get();
        // TODO: Clean up
        Material up = bmp.multiblockTexture(multiblock, "top_" + index);
        Material down = bmp.multiblockTexture(multiblock, "bottom_" + index);
        Material north;
        Material east;
        Material south;
        Material west;
        if (index % 2 != 0) {
            north = bmp.multiblockTexture(multiblock, "side_1" + postfix);
            east = north;
            south = north;
            west = north;
        } else if (index == 0 || index == 2) {
            north = bmp.multiblockTexture(multiblock, "side_" + (2 - index % 3));
            east = bmp.multiblockTexture(multiblock, "side_" + index % 3);
            south = north;
            west = east;
        } else {
            north = bmp.multiblockTexture(multiblock, "side_" + index % 3);
            east = bmp.multiblockTexture(multiblock, "side_" + (2 - index % 3));
            south = north;
            west = east;
        }
        return bmp.cube(name, down, up, north, south, east, west, north);
    }

    public void bioReactorPart(Block block) {
        PropertyDispatch.C4<MultiVariant, Integer, Boolean, Boolean, Boolean> dispatch = PropertyDispatch.initial(
                BioReactorMultiblock.BIO_REACTOR_PART, BioReactorMultiblock.FORMED, BioReactorMultiblock.TOP, BioReactorMultiblock.HATCH);
        for (int i : BioReactorMultiblock.BIO_REACTOR_PART.getPossibleValues()) {
            for (boolean formed : new boolean[]{false, true}) {
                for (boolean top : new boolean[]{false, true}) {
                    for (boolean hatch : new boolean[]{false, true}) {
                        dispatch = dispatch.select(i, formed, top, hatch,
                                BlockModelGenerators.plainVariant(bioReactorPartModel(block, i, top, hatch)));
                    }
                }
            }
        }
        bmp.blockModels().blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(dispatch));
    }

    private Identifier bioReactorPartModel(Block block, int index, boolean top, boolean hatch) {
        Multiblock multiblock = NTMultiblocks.BIO_REACTOR.get();
        String middleFix = top ? "top" : "bottom";
        String name = bmp.name(block) + "_" + index + "_" + middleFix + (hatch ? "_hatch" : "");
        // TODO: Clean up
        Material up = hatch && index % 2 != 0
                ? bmp.multiblockTexture(multiblock, "top_" + index + "_hatch")
                : bmp.multiblockTexture(multiblock, "top_" + index);
        Material down = bmp.blockTexture(NTBlocks.POLISHED_PRISMARINE.get());
        Material north;
        Material east;
        Material south;
        Material west;
        if (index % 2 != 0) {
            north = bmp.multiblockTexture(multiblock, "side_" + middleFix + "_1");
            east = north;
            south = north;
            west = north;
        } else if (index == 0 || index == 2) {
            north = bmp.multiblockTexture(multiblock, "side_" + middleFix + "_" + (2 - index % 3));
            east = bmp.multiblockTexture(multiblock, "side_" + middleFix + "_" + index % 3);
            south = north;
            west = east;
        } else {
            north = bmp.multiblockTexture(multiblock, "side_" + middleFix + "_" + index % 3);
            east = bmp.multiblockTexture(multiblock, "side_" + middleFix + "_" + (2 - index % 3));
            south = north;
            west = east;
        }
        return bmp.cube(name, down, up, north, south, east, west, north);
    }
}
