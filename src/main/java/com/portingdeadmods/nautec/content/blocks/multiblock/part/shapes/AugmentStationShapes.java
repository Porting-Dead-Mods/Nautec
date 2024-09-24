package com.portingdeadmods.nautec.content.blocks.multiblock.part.shapes;

import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Function;

public enum AugmentStationShapes {

    SHAPE_0(shape->{
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.5, 0.5, 0.625, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.625, 0.6875, 0.3125, 0.8125, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0.8125, 0.1875, 0.8125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0.625, 0.375, 1, 1), BooleanOp.OR);
        return shape;
    }),
    SHAPE_1(shape->{
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.625, 0.4375, 0.3125, 0.8125, 0.5625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0, 0.1875, 0.8125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0, 0.375, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5, 0, 0, 1, 0.625, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.5, 0.625, 1), BooleanOp.OR);
        return shape;
    }),
    SHAPE_2(shape->{
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.5, 0.625, 0.5), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.625, 0.1875, 0.3125, 0.8125, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0, 0.1875, 0.8125, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0, 0.375, 1, 0.375), BooleanOp.OR);
        return shape;
    }),
    SHAPE_3(shape->{
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.625, 0.6875, 0.5625, 0.8125, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0.8125, 1, 0.8125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0.625, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.625, 0.5), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.5, 1, 0.625, 1), BooleanOp.OR);
        return shape;
    }),
    SHAPE_5(shape->{
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.625, 0.1875, 0.5625, 0.8125, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0, 1, 0.8125, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.8125, 0, 1, 1, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.5, 1, 0.625, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.625, 0.5), BooleanOp.OR);
        return shape;
    }),
    SHAPE_6(shape->{
        shape = Shapes.join(shape, Shapes.box(0.5, 0, 0.5, 1, 0.625, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, 0.625, 0.6875, 0.8125, 0.8125, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.625, 0.8125, 1, 0.8125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0.8125, 0.625, 1, 1, 1), BooleanOp.OR);
        return shape;
    }),
    SHAPE_7(shape->{
        shape = Shapes.join(shape, Shapes.box(0.6875, 0.625, 0.4375, 0.8125, 0.8125, 0.5625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.625, 0, 1, 0.8125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0.8125, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.5, 0.625, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5, 0, 0, 1, 0.625, 1), BooleanOp.OR);
        return shape;
    }),
    SHAPE_8(shape->{
        shape = Shapes.join(shape, Shapes.box(0.5, 0, 0, 1, 0.625, 0.5), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, 0.625, 0.1875, 0.8125, 0.8125, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.625, 0, 1, 0.8125, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0.8125, 0, 1, 1, 0.375), BooleanOp.OR);
        return shape;
    }),
    ;


    private Function<VoxelShape, VoxelShape> shapeModifier;

    AugmentStationShapes(Function<VoxelShape, VoxelShape> shapeModifier) {
        this.shapeModifier = shapeModifier;
    }

    public Function<VoxelShape, VoxelShape> getShapeModifier() {
        return shapeModifier;
    }
    public static AugmentStationShapes getShape(int part){
        return valueOf("SHAPE_"+part);
    }
}
