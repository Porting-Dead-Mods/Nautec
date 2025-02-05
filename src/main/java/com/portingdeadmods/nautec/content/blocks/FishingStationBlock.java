package com.portingdeadmods.nautec.content.blocks;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.api.blocks.blockentities.LaserBlock;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class FishingStationBlock extends LaserBlock {
    public static final VoxelShape SHAPE = Stream.of(
            Block.box(0, 10, 0, 16, 16, 16),
            Block.box(4, 6, 4, 12, 10, 12),
            Block.box(0, 0, 0, 16, 6, 16)
    ).reduce(Shapes::or).get();

    public FishingStationBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return NTBlockEntityTypes.FISHING_STATION.get();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(FishingStationBlock::new);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, BlockHitResult p_60508_) {

        return super.useWithoutItem(p_60503_, p_60504_, p_60505_, p_60506_, p_60508_);
    }
}
