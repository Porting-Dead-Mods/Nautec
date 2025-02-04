package com.portingdeadmods.nautec.content.multiblocks;

import com.portingdeadmods.nautec.api.blockentities.multiblock.MultiblockEntity;
import com.portingdeadmods.nautec.api.multiblocks.Multiblock;
import com.portingdeadmods.nautec.api.multiblocks.MultiblockData;
import com.portingdeadmods.nautec.api.multiblocks.MultiblockLayer;
import com.portingdeadmods.nautec.api.utils.HorizontalDirection;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import com.portingdeadmods.nautec.registries.NTBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class BioReactorMultiblock implements Multiblock {
    public static final IntegerProperty BIO_REACTOR_PART = IntegerProperty.create("bio_reactor_part", 0, 8);
    public static final BooleanProperty TOP = BooleanProperty.create("top");
    public static final BooleanProperty HATCH = BooleanProperty.create("hatch");

    @Override
    public Block getUnformedController() {
        return NTBlocks.BIO_REACTOR.get();
    }

    @Override
    public Block getFormedController() {
        return NTBlocks.BIO_REACTOR.get();
    }

    @Override
    public MultiblockLayer[] getLayout() {
        return new MultiblockLayer[]{
                layer(
                        2, 1, 2,
                        1, 3, 1,
                        2, 1, 2
                ),
                layer(
                        2, 1, 2,
                        1, 0, 1,
                        2, 1, 2
                ),
        };
    }

    @Override
    public Map<Integer, Block> getDefinition() {
        return Map.of(
                0, getUnformedController(),
                1, NTBlocks.BACTERIAL_CONTAINMENT_SHIELD.get(),
                2, NTBlocks.DARK_PRISMARINE_PILLAR.get(),
                3, NTBlocks.POLISHED_PRISMARINE.get()
        );
    }

    @Override
    public BlockEntityType<? extends MultiblockEntity> getMultiBlockEntityType() {
        return NTBlockEntityTypes.BIO_REACTOR.get();
    }

    @Override
    public @Nullable BlockState formBlock(Level level, BlockPos blockPos, BlockPos controllerPos, int layerIndex, int layoutIndex, MultiblockData multiblockData, @Nullable Player player) {
        if (layerIndex == 4 && layoutIndex == 1) {
            return getFormedController().defaultBlockState()
                    .setValue(BIO_REACTOR_PART, layerIndex)
                    .setValue(FORMED, true);
        }
        return NTBlocks.BIO_REACTOR_PART.get().defaultBlockState()
                .setValue(BIO_REACTOR_PART, layerIndex)
                .setValue(FORMED, true)
                .setValue(TOP, layoutIndex == 1);
    }

    @Override
    public boolean isFormed(Level level, BlockPos blockPos) {
        BlockState block = level.getBlockState(blockPos);
        return block.hasProperty(FORMED) && block.getValue(FORMED);
    }

    @Override
    public @Nullable HorizontalDirection getFixedDirection() {
        return HorizontalDirection.NORTH;
    }
}
