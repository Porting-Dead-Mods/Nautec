package com.portingdeadmods.nautec.datagen;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.multiblocks.Multiblock;
import com.portingdeadmods.nautec.content.blocks.AquaticCatalystBlock;
import com.portingdeadmods.nautec.content.blocks.CrateBlock;
import com.portingdeadmods.nautec.content.blocks.LaserJunctionBlock;
import com.portingdeadmods.nautec.content.blocks.multiblock.controller.AugmentationStationBlock;
import com.portingdeadmods.nautec.content.blocks.multiblock.part.AugmentationStationPartBlock;
import com.portingdeadmods.nautec.content.blocks.multiblock.part.DrainPartBlock;
import com.portingdeadmods.nautec.content.multiblocks.AugmentationStationMultiblock;
import com.portingdeadmods.nautec.content.multiblocks.DrainMultiblock;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTMultiblocks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.apache.commons.lang3.IntegerRange;
import org.jetbrains.annotations.NotNull;

public class BlockModelProvider extends BlockStateProvider {
    public BlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Nautec.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        axisBlock(NTBlocks.DARK_PRISMARINE_PILLAR.get());
        simpleBlock(NTBlocks.CHISELED_DARK_PRISMARINE.get());
        simpleBlock(NTBlocks.POLISHED_PRISMARINE.get());
        simpleBlock(NTBlocks.AQUARINE_STEEL_BLOCK.get());

        // Laser Stuffs
        simpleBlock(NTBlocks.CREATIVE_POWER_SOURCE.get());
        aquaticCatalyst(NTBlocks.AQUATIC_CATALYST.get());
        existingFacingBlock(NTBlocks.PRISMARINE_RELAY.get());
        longDistanceLaser(NTBlocks.LONG_DISTANCE_LASER.get());
        laserJunction(NTBlocks.LASER_JUNCTION.get());

        // Stuff
        simpleBlock(NTBlocks.PRISMARINE_SAND.get());
        simpleBlock(NTBlocks.MIXER.get(), models().getExistingFile(existingModelFile(NTBlocks.MIXER.get())));
        simpleBlock(NTBlocks.CHARGER.get(), models().getExistingFile(existingModelFile(NTBlocks.CHARGER.get())));
        crateBlock(NTBlocks.CRATE.get());
        rustyCrateBlock(NTBlocks.RUSTY_CRATE.get());

        // Multiblock
        drainController(NTBlocks.DRAIN.get());
        drainPart(NTBlocks.DRAIN_PART.get(), IntegerRange.of(0, 8));

        augmentationStationController(NTBlocks.AUGMENTATION_STATION.get());
        augmentationStationPart(NTBlocks.AUGMENTATION_STATION_PART.get(), IntegerRange.of(0, 8));
        augmentationStationExtension(NTBlocks.AUGMENTATION_STATION_EXTENSION.get());

        simpleBlock(NTBlocks.DRAIN_WALL.get());
    }

    private void augmentationStationController(AugmentationStationBlock augmentationStationBlock) {
        ModelFile formedModel = models().getExistingFile(existingModelFile("multiblock/augmentation_station_4"));
        getVariantBuilder(augmentationStationBlock).partialState().with(Multiblock.FORMED, true)
                .modelForState().modelFile(formedModel).addModel()
                .partialState().with(Multiblock.FORMED, false)
                .modelForState().modelFile(unformedAugmentationStationPart(augmentationStationBlock, "controller")).addModel();
    }

    private void augmentationStationExtension(Block augmentationStationExtensionBlock) {
        VariantBlockStateBuilder builder = getVariantBuilder(augmentationStationExtensionBlock);
        builder
                .partialState().with(Multiblock.FORMED, false)
                .modelForState().modelFile(unformedAugmentationStationPart(augmentationStationExtensionBlock, "extension")).addModel();
        for (Direction dir : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            builder.partialState().with(Multiblock.FORMED, true).with(BlockStateProperties.HORIZONTAL_FACING, dir)
                    .modelForState().modelFile(models().getExistingFile(existingModelFile("multiblock/augmentation_station_extension")))
                    .rotationY(((int) dir.toYRot() + 180) % 360).addModel();
        }
    }

    private void augmentationStationPart(Block augmentationStationPartBlock, IntegerRange range) {
        VariantBlockStateBuilder builder = getVariantBuilder(augmentationStationPartBlock);
        builder.partialState().with(Multiblock.FORMED, false)
                .modelForState().modelFile(drainPartModel(augmentationStationPartBlock, 0, false)).addModel();
        for (int i = range.getMinimum(); i <= range.getMaximum(); i++) {
            ModelFile formedModel = models().getExistingFile(existingModelFile("multiblock/augmentation_station_" + (8 - i)));
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

    private @NotNull BlockModelBuilder unformedAugmentationStationPart(Block augmentationStationController, String part) {
        Multiblock multiblock = NTMultiblocks.AUGMENTATION_STATION.get();
        BlockModelBuilder builder = models().withExistingParent(name(augmentationStationController), "cube");
        builder.texture("up", multiblockTexture(multiblock, "unformed/" + part + "_top"))
                .texture("down", multiblockTexture(multiblock, "unformed/" + part + "_bottom"))
                .texture("north", multiblockTexture(multiblock, "unformed/" + part + "_side"))
                .texture("east", multiblockTexture(multiblock, "unformed/" + part + "_side"))
                .texture("south", multiblockTexture(multiblock, "unformed/" + part + "_side"))
                .texture("west", multiblockTexture(multiblock, "unformed/" + part + "_side"));
        return builder;
    }

    private void drainController(Block drainController) {
        Multiblock multiblock = NTMultiblocks.DRAIN.get();
        ModelFile unformedModel = drainControllerModel(drainController, multiblock, false);
        getVariantBuilder(drainController).partialState().with(DrainMultiblock.FORMED, false)
                .modelForState().modelFile(unformedModel).addModel();
        ModelFile formedModel = drainControllerModel(drainController, multiblock, true);
        getVariantBuilder(drainController).partialState().with(DrainMultiblock.FORMED, true)
                .modelForState().modelFile(formedModel).addModel();
    }

    private @NotNull BlockModelBuilder drainControllerModel(Block drainController, Multiblock multiblock, boolean formed) {
        BlockModelBuilder builder = models().withExistingParent(name(drainController) + (formed ? "_formed" : ""), "cube");
        builder.texture("up", multiblockTexture(multiblock, formed ? "top_4" : "drain_top_unformed"))
                .texture("down", multiblockTexture(multiblock, formed ? "bottom_4" : "drain_bottom_unformed"))
                .texture("north", multiblockTexture(multiblock, "drain_side_unformed"))
                .texture("east", multiblockTexture(multiblock, "drain_side_unformed"))
                .texture("south", multiblockTexture(multiblock, "drain_side_unformed"))
                .texture("west", multiblockTexture(multiblock, "drain_side_unformed"));
        return builder;
    }

    private void drainPart(Block drainPartBlock, IntegerRange range) {
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

    private ModelFile drainPartModel(Block drainPartBlock, int index, boolean laserPort) {
        String postfix = laserPort ? "_open" : "";
        BlockModelBuilder builder = models().withExistingParent(name(drainPartBlock) + "_" + index + postfix, "cube");
        Multiblock multiblock = NTMultiblocks.DRAIN.get();
        // TODO: Clean up
        if (index % 2 != 0) {
            builder.texture("up", multiblockTexture(multiblock, "top_" + index))
                    .texture("down", multiblockTexture(multiblock, "bottom_" + index))
                    .texture("north", multiblockTexture(multiblock, "side_1" + postfix))
                    .texture("east", multiblockTexture(multiblock, "side_1" + postfix))
                    .texture("south", multiblockTexture(multiblock, "side_1" + postfix))
                    .texture("west", multiblockTexture(multiblock, "side_1" + postfix));
        } else if (index == 0 || index == 2) {
            builder.texture("up", multiblockTexture(multiblock, "top_" + index))
                    .texture("down", multiblockTexture(multiblock, "bottom_" + index))
                    .texture("north", multiblockTexture(multiblock, "side_" + (2 - index % 3)))
                    .texture("east", multiblockTexture(multiblock, "side_" + index % 3))
                    .texture("south", multiblockTexture(multiblock, "side_" + (2 - index % 3)))
                    .texture("west", multiblockTexture(multiblock, "side_" + index % 3));
        } else {
            builder.texture("up", multiblockTexture(multiblock, "top_" + index))
                    .texture("down", multiblockTexture(multiblock, "bottom_" + index))
                    .texture("north", multiblockTexture(multiblock, "side_" + index % 3))
                    .texture("east", multiblockTexture(multiblock, "side_" + (2 - index % 3)))
                    .texture("south", multiblockTexture(multiblock, "side_" + index % 3))
                    .texture("west", multiblockTexture(multiblock, "side_" + (2 - index % 3)));
        }
        return builder;
    }

    private void laserJunction(Block block) {
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block);
        laserJunctionConnection(builder, block, Direction.DOWN, 0, 0);
        laserJunctionConnection(builder, block, Direction.UP, 180, 0);
        laserJunctionConnection(builder, block, Direction.NORTH, 90, 180);
        laserJunctionConnection(builder, block, Direction.EAST, 90, 270);
        laserJunctionConnection(builder, block, Direction.SOUTH, 90, 0);
        laserJunctionConnection(builder, block, Direction.WEST, 90, 90);
        builder.part().modelFile(models().getExistingFile(extend(existingModelFile(block), "_base"))).addModel().end();
    }

    private void laserJunctionConnection(MultiPartBlockStateBuilder builder, Block block, Direction direction, int x, int y) {
        builder.part().modelFile(models().getExistingFile(extend(existingModelFile(block), "_connection_in"))).rotationX(x).rotationY(y).addModel()
                .condition(LaserJunctionBlock.CONNECTION[direction.ordinal()], LaserJunctionBlock.ConnectionType.INPUT).end()
                .part().modelFile(models().getExistingFile(extend(existingModelFile(block), "_connection_out"))).rotationX(x).rotationY(y).addModel()
                .condition(LaserJunctionBlock.CONNECTION[direction.ordinal()], LaserJunctionBlock.ConnectionType.OUTPUT).end();
    }

    public void longDistanceLaser(Block block) {
        BlockModelBuilder modelBuilder = models().withExistingParent(name(block), "cube")
                .texture("up", extend(blockTexture(block), "_top"))
                .texture("down", extend(blockTexture(block), "_bottom"))
                .texture("north", extend(blockTexture(block), "_side"))
                .texture("east", extend(blockTexture(block), "_side"))
                .texture("south", extend(blockTexture(block), "_side"))
                .texture("west", extend(blockTexture(block), "_side"));
        facingBlock(block, modelBuilder);
    }

    public void existingFacingBlock(Block block) {
        facingBlock(block, models().getExistingFile(existingModelFile(NTBlocks.PRISMARINE_RELAY.get())));
    }

    public void facingBlock(Block block, ModelFile model) {
        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.FACING, Direction.UP)
                .modelForState().modelFile(model).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.DOWN)
                .modelForState().modelFile(model).rotationX(180).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.NORTH)
                .modelForState().modelFile(model).rotationX(90).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.SOUTH)
                .modelForState().modelFile(model).rotationX(90).rotationY(180).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.EAST)
                .modelForState().modelFile(model).rotationX(90).rotationY(90).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.WEST)
                .modelForState().modelFile(model).rotationX(90).rotationY(270).addModel();
    }

    private void crateBlock(CrateBlock crateBlock) {
        VariantBlockStateBuilder builder = getVariantBuilder(crateBlock);
        builder.partialState().with(BlockStateProperties.OPEN, false)
                .modelForState().modelFile(models().getExistingFile(existingModelFile(crateBlock))).addModel();
        builder.partialState().with(BlockStateProperties.OPEN, true)
                .modelForState().modelFile(models().getExistingFile(extend(existingModelFile(crateBlock), "_open"))).addModel();
    }

    private void rustyCrateBlock(CrateBlock crateBlock) {
        VariantBlockStateBuilder builder = getVariantBuilder(crateBlock);
        builder.partialState().with(BlockStateProperties.OPEN, true)
                .modelForState().modelFile(rustedCrateModel(crateBlock, true)).addModel();
        builder.partialState().with(BlockStateProperties.OPEN, false)
                .modelForState().modelFile(rustedCrateModel(crateBlock, false)).addModel();
    }

    private ModelFile rustedCrateModel(CrateBlock block, boolean open) {
        return models().withExistingParent(name(block) + (open ? "_open" : ""), extend(existingModelFile(NTBlocks.CRATE.get()), open ? "_open" : ""))
                .texture("2", Nautec.MODID + ":block/crate/rusty_top_inner")
                .texture("4", Nautec.MODID + ":block/crate/rusty")
                .texture("5", Nautec.MODID + ":block/crate/rusty_top")
                .texture("particle", Nautec.MODID + ":block/crate/rusty");
    }

    public ResourceLocation multiblockTexture(Multiblock multiblock, String name) {
        return modLoc(ModelProvider.BLOCK_FOLDER + "/multiblock/" + NTRegistries.MULTIBLOCK.getKey(multiblock).getPath() + "/" + name);
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

    private ResourceLocation existingModelFile(String name) {
        return modLoc(ModelProvider.BLOCK_FOLDER + "/" + name);
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