package com.portingdeadmods.nautec.content.blocks;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.api.blocks.blockentities.LaserBlock;
import com.portingdeadmods.nautec.registries.MJBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class LaserJunctionBlock extends LaserBlock {
    @SuppressWarnings("unchecked")
    public static final EnumProperty<ConnectionType>[] CONNECTION = new EnumProperty[6];
    public final int border;
    public final VoxelShape shapeCenter;
    public final VoxelShape shapeD;
    public final VoxelShape shapeU;
    public final VoxelShape shapeN;
    public final VoxelShape shapeS;
    public final VoxelShape shapeW;
    public final VoxelShape shapeE;
    public final VoxelShape[] shapes;

    public LaserJunctionBlock(Properties properties, int width) {
        super(properties);
        registerDefaultState(getStateDefinition().any()
                .setValue(CONNECTION[0], ConnectionType.NONE)
                .setValue(CONNECTION[1], ConnectionType.NONE)
                .setValue(CONNECTION[2], ConnectionType.NONE)
                .setValue(CONNECTION[3], ConnectionType.NONE)
                .setValue(CONNECTION[4], ConnectionType.NONE)
                .setValue(CONNECTION[5], ConnectionType.NONE)
        );
        border = (16 - width) / 2;
        int B0 = border;
        int B1 = 16 - border;
        shapeCenter = box(B0, B0, B0, B1, B1, B1);
        shapeD = box(B0, 0, B0, B1, B0, B1);
        shapeU = box(B0, B1, B0, B1, 16, B1);
        shapeN = box(B0, B0, 0, B1, B1, B0);
        shapeS = box(B0, B0, B1, B1, B1, 16);
        shapeW = box(0, B0, B0, B0, B1, B1);
        shapeE = box(B1, B0, B0, 16, B1, B1);
        shapes = new VoxelShape[64];

    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        int index = 0;

        for (Direction direction : Direction.values()) {
            if (blockState.getValue(CONNECTION[direction.ordinal()]) != ConnectionType.NONE) {
                index |= 1 << direction.ordinal();
            }
        }

        return getShape(index);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CONNECTION[0], CONNECTION[1], CONNECTION[2], CONNECTION[3], CONNECTION[4], CONNECTION[5]);
    }

    public VoxelShape getShape(int i) {
        if (shapes[i] == null) {
            shapes[i] = shapeCenter;

            if (((i >> 0) & 1) != 0) {
                shapes[i] = Shapes.or(shapes[i], shapeD);
            }

            if (((i >> 1) & 1) != 0) {
                shapes[i] = Shapes.or(shapes[i], shapeU);
            }

            if (((i >> 2) & 1) != 0) {
                shapes[i] = Shapes.or(shapes[i], shapeN);
            }

            if (((i >> 3) & 1) != 0) {
                shapes[i] = Shapes.or(shapes[i], shapeS);
            }

            if (((i >> 4) & 1) != 0) {
                shapes[i] = Shapes.or(shapes[i], shapeW);
            }

            if (((i >> 5) & 1) != 0) {
                shapes[i] = Shapes.or(shapes[i], shapeE);
            }
        }

        return shapes[i];
    }

    static {
        for (Direction dir : Direction.values()) {
            CONNECTION[dir.get3DDataValue()] = EnumProperty.create(dir.getSerializedName(), ConnectionType.class);
        }
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return MJBlockEntityTypes.LASER_JUNCTION.get();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(props -> new LaserJunctionBlock(props, 8));
    }

    public enum ConnectionType implements StringRepresentable {
        INPUT("input"),
        OUTPUT("output"),
        NONE("none");

        private final String name;

        ConnectionType(String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }
}
