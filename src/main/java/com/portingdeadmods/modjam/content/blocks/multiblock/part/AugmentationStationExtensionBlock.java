package com.portingdeadmods.modjam.content.blocks.multiblock.part;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.modjam.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.modjam.api.blocks.blockentities.LaserBlock;
import com.portingdeadmods.modjam.api.multiblocks.Multiblock;
import com.portingdeadmods.modjam.content.blockentities.multiblock.part.AugmentationStationExtensionBlockEntity;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class AugmentationStationExtensionBlock extends LaserBlock {
    public AugmentationStationExtensionBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(Multiblock.FORMED, false));
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return MJBlockEntityTypes.AUGMENTATION_STATION_EXTENSION.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(BlockStateProperties.HORIZONTAL_FACING, Multiblock.FORMED));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(AugmentationStationExtensionBlock::new);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, BlockHitResult p_60508_) {
        AugmentationStationExtensionBlockEntity be = (AugmentationStationExtensionBlockEntity) p_60504_.getBlockEntity(p_60505_);
        be.setHasRobotArm(!be.hasRobotArm());
        return InteractionResult.SUCCESS;
    }
}
