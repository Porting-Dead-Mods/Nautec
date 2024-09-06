package com.portingdeadmods.modjam.api.utils;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum OptionalDirection implements StringRepresentable {
    NORTH("north", Direction.NORTH),
    EAST("east", Direction.EAST),
    SOUTH("south", Direction.SOUTH),
    WEST("west", Direction.WEST),
    UP("up", Direction.UP),
    DOWN("down", Direction.DOWN),
    NONE("none", null);

    private final String name;
    private final @Nullable Direction mcDirection;

    OptionalDirection(String name, @Nullable Direction mcDirection) {
        this.name = name;
        this.mcDirection = mcDirection;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

    public @Nullable Direction getMcDirection() {
        return mcDirection;
    }

    public static OptionalDirection fromMcDirection(Direction direction) {
        return switch (direction) {
            case NORTH -> NORTH;
            case EAST -> EAST;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            case UP -> UP;
            case DOWN -> DOWN;
            case null -> NONE;
        };
    }

    public OptionalDirection rotateClockwise() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
            case UP -> UP;
            case DOWN -> DOWN;
            default -> NONE;
        };
    }
}
