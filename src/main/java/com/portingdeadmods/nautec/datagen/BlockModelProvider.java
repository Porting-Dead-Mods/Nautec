package com.portingdeadmods.nautec.datagen;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.multiblocks.Multiblock;
import com.portingdeadmods.nautec.content.blocks.*;
import com.portingdeadmods.nautec.registries.NTBlocks;
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
        super(output, Nautec.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        MultiblockModelHelper helper = new MultiblockModelHelper(this);

        axisBlock(NTBlocks.DARK_PRISMARINE_PILLAR.get());
        simpleBlock(NTBlocks.CHISELED_DARK_PRISMARINE.get());
        simpleBlock(NTBlocks.PRISMARINE_SAND.get());
        simpleBlock(NTBlocks.POLISHED_PRISMARINE.get());
        simpleBlock(NTBlocks.AQUARINE_STEEL_BLOCK.get());
        simpleBlock(NTBlocks.CAST_IRON_BLOCK.get());

        // Laser Stuffs
        simpleBlock(NTBlocks.CREATIVE_POWER_SOURCE.get());
        aquaticCatalyst(NTBlocks.AQUATIC_CATALYST.get());

        existingFacingBlock(NTBlocks.PRISMARINE_RELAY.get());
        longDistanceLaser(NTBlocks.LONG_DISTANCE_LASER.get());
        laserJunction(NTBlocks.LASER_JUNCTION.get());

        // Stuff
        simpleBlock(NTBlocks.MIXER.get(), models().getExistingFile(existingModelFile(NTBlocks.MIXER.get())));
        simpleBlock(NTBlocks.CHARGER.get(), models().getExistingFile(existingModelFile(NTBlocks.CHARGER.get())));
        simpleBlock(NTBlocks.FISHING_STATION.get(), models().getExistingFile(existingModelFile(NTBlocks.FISHING_STATION.get())));
        crateBlock(NTBlocks.CRATE.get());
        rustyCrateBlock(NTBlocks.RUSTY_CRATE.get());

        // Biology - TODO: Models
//        mutator(NTBlocks.MUTATOR.get());
//        bacterialAnalyzer(NTBlocks.BACTERIAL_ANALYZER.get());
//        incubator(NTBlocks.INCUBATOR.get());
//        bioReactor(NTBlocks.BIO_REACTOR.get());

        // Multiblock
        helper.drainController(NTBlocks.DRAIN.get());
        helper.drainPart(NTBlocks.DRAIN_PART.get(), IntegerRange.of(0, 8));

        helper.augmentationStationController(NTBlocks.AUGMENTATION_STATION.get());
        helper.augmentationStationPart(NTBlocks.AUGMENTATION_STATION_PART.get(), IntegerRange.of(0, 8));
        helper.augmentationStationExtension(NTBlocks.AUGMENTATION_STATION_EXTENSION.get());

        helper.bioReactorPart(NTBlocks.BIO_REACTOR_PART.get());

        simpleBlock(NTBlocks.BACTERIAL_CONTAINMENT_SHIELD.get());
        //pipeBlock(NTBlocks.BACTERIA_PIPE.get());

        horizontalBlock(NTBlocks.BIO_REACTOR.get(), models()
                .cubeTop(name(NTBlocks.BIO_REACTOR.get()), blockTexture(NTBlocks.POLISHED_PRISMARINE.get()), blockTexture(NTBlocks.BIO_REACTOR.get(), "_top")));
        simpleBlock(NTBlocks.DRAIN_WALL.get());
        simpleBlock(NTBlocks.BROWN_POLYMER_BLOCK.get());

        oilBarrel(NTBlocks.OIL_BARREL.get(), models().cubeBottomTop(
                name(NTBlocks.OIL_BARREL.get()),
                blockTexture(NTBlocks.OIL_BARREL.get(), "_side"),
                blockTexture(NTBlocks.OIL_BARREL.get(), "_bottom"),
                blockTexture(NTBlocks.OIL_BARREL.get())
        ), models().cubeBottomTop(
                name(NTBlocks.OIL_BARREL.get()) + "_open",
                blockTexture(NTBlocks.OIL_BARREL.get(), "_side"),
                blockTexture(NTBlocks.OIL_BARREL.get(), "_bottom"),
                blockTexture(NTBlocks.OIL_BARREL.get(), "_open")
        ));

        horizontalBlock(NTBlocks.BACTERIAL_ANALYZER.get(), models().getExistingFile(existingModelFile(NTBlocks.BACTERIAL_ANALYZER.get())));
        horizontalBlock(NTBlocks.BACTERIAL_ANALYZER_TOP.get(), models().getExistingFile(existingModelFile(NTBlocks.BACTERIAL_ANALYZER_TOP.get())));
    }

    private void aquaticCatalyst(AquaticCatalystBlock block) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        builder.partialState().with(AquaticCatalystBlock.ACTIVE, false)
                .modelForState().modelFile(models().cubeAll(name(block), blockTexture(block))).addModel();
        for (Direction direction : Direction.values()) {
            for (int stage : AquaticCatalystBlock.STAGE.getPossibleValues()) {
                builder.partialState()
                        .with(BlockStateProperties.FACING, direction)
                        .with(AquaticCatalystBlock.ACTIVE, true)
                        .with(AquaticCatalystBlock.STAGE, stage)
                        .modelForState().modelFile(createActiveACModel(block, direction, stage)).addModel();
            }
        }
    }

    private void pipeBlock(Block block) {
        ResourceLocation loc = BuiltInRegistries.BLOCK.getKey(block);
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block);
        pipeConnection(builder, loc, Direction.DOWN, 0, 0);
        pipeConnection(builder, loc, Direction.UP, 180, 0);
        pipeConnection(builder, loc, Direction.NORTH, 90, 180);
        pipeConnection(builder, loc, Direction.EAST, 90, 270);
        pipeConnection(builder, loc, Direction.SOUTH, 90, 0);
        pipeConnection(builder, loc, Direction.WEST, 90, 90);
        builder.part().modelFile(pipeBaseModel(loc)).addModel().end();
    }

    private ModelFile pipeBaseModel(ResourceLocation blockLoc) {
        return models().withExistingParent(blockLoc.getPath() + "_base", modLoc("block/pipe_base"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(blockLoc.getNamespace(), "block/" + blockLoc.getPath()));
    }

    private ModelFile pipeConnectionModel(ResourceLocation blockLoc) {
        return models().withExistingParent(blockLoc.getPath() + "_connection", modLoc("block/pipe_connection"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(blockLoc.getNamespace(), "block/" + blockLoc.getPath()));
    }

    private void pipeConnection(MultiPartBlockStateBuilder builder, ResourceLocation loc, Direction direction, int x, int y) {
        builder.part().modelFile(pipeConnectionModel(loc)).rotationX(x).rotationY(y).addModel()
                .condition(BacteriaPipeBlock.CONNECTION[direction.get3DDataValue()], true).end();
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

    public void oilBarrel(Block block, ModelFile model, ModelFile openModel) {
        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.FACING, Direction.UP).with(OilBarrelBlock.OPEN, false)
                .modelForState().modelFile(model).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.DOWN).with(OilBarrelBlock.OPEN, false)
                .modelForState().modelFile(model).rotationX(180).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.NORTH).with(OilBarrelBlock.OPEN, false)
                .modelForState().modelFile(model).rotationX(90).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.SOUTH).with(OilBarrelBlock.OPEN, false)
                .modelForState().modelFile(model).rotationX(90).rotationY(180).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.EAST).with(OilBarrelBlock.OPEN, false)
                .modelForState().modelFile(model).rotationX(90).rotationY(90).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.WEST).with(OilBarrelBlock.OPEN, false)
                .modelForState().modelFile(model).rotationX(90).rotationY(270).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.UP).with(OilBarrelBlock.OPEN, true)
                .modelForState().modelFile(openModel).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.DOWN).with(OilBarrelBlock.OPEN, true)
                .modelForState().modelFile(openModel).rotationX(180).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.NORTH).with(OilBarrelBlock.OPEN, true)
                .modelForState().modelFile(openModel).rotationX(90).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.SOUTH).with(OilBarrelBlock.OPEN, true)
                .modelForState().modelFile(openModel).rotationX(90).rotationY(180).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.EAST).with(OilBarrelBlock.OPEN, true)
                .modelForState().modelFile(openModel).rotationX(90).rotationY(90).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.WEST).with(OilBarrelBlock.OPEN, true)
                .modelForState().modelFile(openModel).rotationX(90).rotationY(270).addModel();
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

    private ModelFile createActiveACModel(AquaticCatalystBlock block, Direction activeSide, int stage) {
        BlockModelBuilder builder = models().withExistingParent(name(block) + "_" + activeSide.getSerializedName() + "_" + stage, "cube");
        for (Direction dir : Direction.values()) {
            if (dir == activeSide) {
                builder.texture(dir.getName(), extend(blockTexture(block), "_active_" + stage));
            } else {
                builder.texture(dir.getName(), blockTexture(block));
            }
        }
        return builder;
    }

    public ResourceLocation blockTexture(Block block, String suffix) {
        ResourceLocation name = key(block);
        return ResourceLocation.fromNamespaceAndPath(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath() + suffix);
    }

    public ResourceLocation existingModelFile(Block block) {
        ResourceLocation name = key(block);
        return ResourceLocation.fromNamespaceAndPath(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath());
    }

    public ResourceLocation existingModelFile(String name) {
        return modLoc(ModelProvider.BLOCK_FOLDER + "/" + name);
    }

    public ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    public String name(Block block) {
        return key(block).getPath();
    }

    public ResourceLocation extend(ResourceLocation rl, String suffix) {
        return ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), rl.getPath() + suffix);
    }
}