package com.portingdeadmods.modjam.content.blocks;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.modjam.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.modjam.api.blocks.DisplayBlock;
import com.portingdeadmods.modjam.api.blocks.blockentities.LaserBlock;
import com.portingdeadmods.modjam.content.blockentities.PrismarineCrucibleBlockEntity;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.stream.Stream;

public class PrismarineCrucibleBlock extends LaserBlock implements DisplayBlock {
    private static final VoxelShape SHAPE = Stream.of(
            Block.box(0, 0, 0, 16, 3, 16),
            Block.box(0, 3, 0, 16, 16, 3),
            Block.box(0, 3, 13, 16, 16, 16),
            Block.box(0, 3, 3, 3, 16, 13),
            Block.box(13, 3, 3, 16, 16, 13)
    ).reduce(Shapes::or).get();

    public PrismarineCrucibleBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return MJBlockEntityTypes.PRISMARINE_CRUCIBLE.get();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(PrismarineCrucibleBlock::new);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public List<Component> displayText(Level level, BlockPos blockPos, Player player) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof PrismarineCrucibleBlockEntity prismarineCrucibleBlockEntity) {
            return List.of(
                    Component.literal("Powered: ?")
            );
        }
        return List.of();
    }
}
