package com.portingdeadmods.nautec.utils;

import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.blockentities.multiblock.FakeBlockEntity;
import com.portingdeadmods.nautec.api.blockentities.multiblock.MultiblockEntity;
import com.portingdeadmods.nautec.api.blockentities.multiblock.SavesControllerPosBlockEntity;
import com.portingdeadmods.nautec.api.multiblocks.Multiblock;
import com.portingdeadmods.nautec.api.multiblocks.MultiblockData;
import com.portingdeadmods.nautec.api.multiblocks.MultiblockLayer;
import com.portingdeadmods.nautec.api.utils.HorizontalDirection;
import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.booleans.BooleanLists;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.IntegerRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class MultiblockHelper {
    private MultiblockHelper() {
    }

    /**
     * Check if all multiblock parts are placed correctly
     *
     * @param multiblock    multiblocks controller (multiblock to check)
     * @param controllerPos blockPos of the multiblock controller
     * @param level         level of the controller block
     * @param player        player that is trying to form the multi
     * @return first: isValid? second: Direction that multi is valid
     */
    public static MultiblockData getUnformedMultiblock(Multiblock multiblock, BlockPos controllerPos, Level level, @Nullable Player player, boolean sendErrorMsg) {
        MultiblockLayer[] layout = multiblock.getLayout();
        MultiblockLayer[] actualLayout = new MultiblockLayer[multiblock.getMaxSize()];
        Map<Integer, Block> def = multiblock.getDefinition();
        Vec3i relativeControllerPos = getRelativeControllerPos(multiblock);
        Set<HorizontalDirection> directions = getHorizontalDirections(multiblock, player);

        // Indexing (Positions)
        int y = 0;

        // Player feedback
        BooleanList multiblockIndexList = new BooleanArrayList();

        Pair<BooleanList, HorizontalDirection> prioritizedDirectionLayout = Pair.of(BooleanLists.emptyList(), HorizontalDirection.NORTH);
        // Direction, blockPos, blockDefinitionIndex
        Map<HorizontalDirection, Pair<BlockPos, Integer>> firstMissingBlockPoses = new HashMap<>();

        // Check if multi is valid
        // iterate through all possible directions
        for (HorizontalDirection mDirection : directions) {
            // Calculate block pos of the first block in the multi (multiblock.getLayout().get(0))
            BlockPos firstBlockPos = getFirstBlockPos(mDirection, controllerPos, relativeControllerPos);
            int actualLayoutSize = 0;

            // Iterate over layers (Y)
            for (MultiblockLayer layer : layout) {
                if (!layer.dynamic()) {
                    // initialize/reset x and z coords for indexing
                    int x = 0;
                    int z = 0;

                    int width = multiblock.getWidths().get(y).leftInt();

                    // Iterate over blocks in a layer (X, Z)
                    for (int blockIndex : layer.layer()) {
                        // Define position-related variables
                        BlockPos curBlockPos = getCurPos(firstBlockPos, new Vec3i(x, y, z), mDirection);

                        // Check if block is correct
                        if (def.get(blockIndex) != null && level.getBlockState(curBlockPos).is(def.get(blockIndex)) && !multiblock.isFormed(level, curBlockPos) || def.get(blockIndex) == null) {
                            multiblockIndexList.add(true);
                        } else {
                            firstMissingBlockPoses.putIfAbsent(mDirection, Pair.of(curBlockPos, blockIndex));
                            multiblockIndexList.add(false);
                        }
                        // Increase x and z coordinates
                        // start new x if we are done with row and increase z as another row is done
                        if (x + 1 < width) {
                            x++;
                        } else {
                            x = 0;
                            z++;
                        }
                    }
                    actualLayout[y] = layer;
                    actualLayoutSize++;
                } else {
                    int minSize = layer.range().getMinimum();
                    int maxSize = layer.range().getMaximum();

                    outer:
                    for (int i = 0; i < maxSize; i++) {
                        int x = 0;
                        int z = 0;

                        int width = multiblock.getWidths().get(y).leftInt();

                        // Iterate over blocks in a layer (X, Z)
                        for (int blockIndex : layer.layer()) {
                            // Define position-related variables
                            BlockPos curBlockPos = getCurPos(firstBlockPos, new Vec3i(x, y + i, z), mDirection);

                            // Check if block is correct
                            if ((def.get(blockIndex) != null && level.getBlockState(curBlockPos).is(def.get(blockIndex)) && !multiblock.isFormed(level, curBlockPos)) || def.get(blockIndex) == null) {
                                multiblockIndexList.add(true);
                            } else {
                                if (i >= minSize) {
                                    break outer;
                                } else {
                                    firstMissingBlockPoses.putIfAbsent(mDirection, Pair.of(curBlockPos, blockIndex));
                                    multiblockIndexList.add(false);
                                }
                            }
                            // Increase x and z coordinates
                            // start new x if we are done with row and increase z as another row is done
                            if (x + 1 < width) {
                                x++;
                            } else {
                                x = 0;
                                z++;
                            }
                        }
                        actualLayout[y + i] = new MultiblockLayer(true, IntegerRange.of(1, 1), layer.layer());
                        actualLayoutSize++;
                    }
                }
                y++;
            }

            if (!multiblockIndexList.contains(false)) {
                return new MultiblockData(true, mDirection, Arrays.copyOf(actualLayout, actualLayoutSize));
            }
            for (int i = multiblockIndexList.size() - 1; i >= 0; i--) {
                if (!multiblockIndexList.getBoolean(i)) {
                    multiblockIndexList.removeBoolean(i);
                }
            }
            if (multiblockIndexList.size() > prioritizedDirectionLayout.getFirst().size()) {
                prioritizedDirectionLayout = Pair.of(multiblockIndexList, mDirection);
            }
            multiblockIndexList = new BooleanArrayList();
            y = 0;
        }

        HorizontalDirection prioritizedDirection = prioritizedDirectionLayout.getSecond();

        if (sendErrorMsg && player != null && !level.isClientSide()) {
            sendFailureMsg(player, level, firstMissingBlockPoses.get(prioritizedDirection).getFirst(), def, firstMissingBlockPoses.get(prioritizedDirection).getSecond());
        }

        return new MultiblockData(false, null, null);
    }

    private static @NotNull Set<HorizontalDirection> getHorizontalDirections(Multiblock multiblock, @Nullable Player player) {
        HorizontalDirection direction;
        if (multiblock.getFixedDirection() != null) {
            direction = multiblock.getFixedDirection();
        } else {
            direction = player != null
                    ? HorizontalDirection.fromRegularDirection(player.getDirection())
                    : HorizontalDirection.NORTH;
        }

        // Make player direction first entry of Set to prioritize
        Set<HorizontalDirection> directions = new HashSet<>();
        directions.add(direction);

        if (multiblock.getFixedDirection() == null) {
            directions.addAll(List.of(HorizontalDirection.values()));
        }

        return directions;
    }

    public static BlockPos getCurPos(Vec3i firstPos, Vec3i relativePos, HorizontalDirection direction) {
        int firstBlockPosX = firstPos.getX();
        int firstBlockPosY = firstPos.getY();
        int firstBlockPosZ = firstPos.getZ();
        int modZ = relativePos.getZ();
        int y = relativePos.getY();
        int modX = relativePos.getX();
        return switch (direction) {
            case NORTH -> new BlockPos(firstBlockPosX + modX, firstBlockPosY + y, firstBlockPosZ + modZ);
            case EAST -> new BlockPos(firstBlockPosX - modZ, firstBlockPosY + y, firstBlockPosZ + modX);
            case SOUTH -> new BlockPos(firstBlockPosX - modX, firstBlockPosY + y, firstBlockPosZ - modZ);
            case WEST -> new BlockPos(firstBlockPosX + modZ, firstBlockPosY + y, firstBlockPosZ - modX);
        };
    }

    public static BlockPos getFirstBlockPos(HorizontalDirection direction, BlockPos controllerPos, Vec3i relativeControllerPos) {
        int firstBlockPosX = switch (direction) {
            case NORTH -> controllerPos.getX() - relativeControllerPos.getX();
            case EAST -> controllerPos.getX() + relativeControllerPos.getZ();
            case SOUTH -> controllerPos.getX() + relativeControllerPos.getX();
            case WEST -> controllerPos.getX() - relativeControllerPos.getZ();
        };
        int firstBlockPosY = controllerPos.getY() - relativeControllerPos.getY();
        int firstBlockPosZ = switch (direction) {
            case NORTH -> controllerPos.getZ() - relativeControllerPos.getZ();
            case EAST -> controllerPos.getZ() - relativeControllerPos.getX();
            case SOUTH -> controllerPos.getZ() + relativeControllerPos.getZ();
            case WEST -> controllerPos.getZ() + relativeControllerPos.getX();
        };
        return new BlockPos(firstBlockPosX, firstBlockPosY, firstBlockPosZ);
    }

    /**
     * @return x, y, z
     */
    public static Vec3i getRelativeControllerPos(Multiblock multiblock) {
        Object2IntMap<Block> revDef = new Object2IntOpenHashMap<>();
        for (var entry : multiblock.getDefinition().int2ObjectEntrySet()) {
            revDef.put(entry.getValue(), entry.getIntKey());
        }

        MultiblockLayer[] layout = multiblock.getLayout();
        int y = 0;
        for (MultiblockLayer layer : layout) {
            int x = 0;
            int z = 0;
            int width = multiblock.getWidths().get(y).leftInt();
            for (int blockIndex : layer.layer()) {
                if (blockIndex == revDef.getInt(multiblock.getUnformedController())) {
                    return new Vec3i(x, y, z);
                }
                if (x + 1 < width) {
                    x++;
                } else {
                    x = 0;
                    z++;
                }
            }
            y++;
        }
        throw new IllegalStateException("Multiblock pre checks failed, controller not found");
    }

    private static void sendFailureMsg(Player player, Level level, BlockPos curBlockPos, Map<Integer, Block> def, int blockIndex) {
        player.sendSystemMessage(Component.translatable("multiblock.info.failed_to_construct").withStyle(ChatFormatting.RED).append(":"));
        player.sendSystemMessage(Component.literal("| ")
                .withStyle(ChatFormatting.DARK_GRAY)
                .append(Component.translatable("multiblock.info.actual_block", level.getBlockState(curBlockPos).getBlock().getName().getString())
                        .withStyle(ChatFormatting.DARK_GRAY))
        );
        player.sendSystemMessage((Component.literal("| ")
                .withStyle(ChatFormatting.DARK_GRAY))
                .append(Component.translatable("multiblock.info.expected_block", def.get(blockIndex).getName().getString())
                        .withStyle(ChatFormatting.DARK_GRAY))
        );
        player.sendSystemMessage(
                Component.literal("| ")
                        .withStyle(ChatFormatting.DARK_GRAY)
                        .append(Component.translatable("multiblock.info.block_pos", curBlockPos.getX(), curBlockPos.getY(), curBlockPos.getZ())
                                .withStyle(ChatFormatting.DARK_GRAY))
        );
    }

    /**
     * @return Whether the forming was successful
     */
    public static boolean form(Multiblock multiblock, BlockPos controllerPos, Level level, @Nullable Player player) {
        MultiblockData multiblockData = getUnformedMultiblock(multiblock, controllerPos, level, player, true);
        HorizontalDirection direction = multiblockData.direction();
        if (multiblock.getFixedDirection() != null) {
            direction = multiblock.getFixedDirection();
        }
        if (multiblockData.valid()) {
            if (direction != null) {
                formBlocks(multiblock, multiblockData, controllerPos, level, player);
                return true;
            }
        }
        return false;
    }

    public static boolean form(Multiblock multiblock, BlockPos controllerPos, Level level) {
        return form(multiblock, controllerPos, level, null);
    }

    private static void formBlocks(Multiblock multiblock, MultiblockData multiblockData, BlockPos controllerPos, Level level, @Nullable Player player) {
        HorizontalDirection direction = multiblockData.direction();
        MultiblockLayer[] layout = multiblockData.layers();

        Vec3i relativeControllerPos = getRelativeControllerPos(multiblock);
        // Calculate block pos of the first block in the multi (multiblock.getLayout().get(0))
        BlockPos firstBlockPos = getFirstBlockPos(direction, controllerPos, relativeControllerPos);
        Int2ObjectMap<Block> def = multiblock.getDefinition();

        multiblock.onStartForming(level, firstBlockPos, controllerPos);

        int index = 0;
        int yIndex = 0;
        for (MultiblockLayer layer : layout) {
            int x = 0;
            int width = multiblock.getWidths().get(yIndex).leftInt();
            int z = 0;

            for (int blockIndex : layer.layer()) {
                BlockPos curBlockPos = getCurPos(firstBlockPos, new Vec3i(x, yIndex, z), direction);

                if (def.get(blockIndex) != null) {
                    BlockState newState = multiblock.formBlock(level, curBlockPos, controllerPos, index, yIndex, multiblockData, player);
                    if (newState != null) {
                        level.setBlockAndUpdate(curBlockPos, newState);
                    }

                    multiblock.afterFormBlock(level, curBlockPos, controllerPos, index, yIndex, multiblockData, player);

                    BlockEntity blockEntity = level.getBlockEntity(curBlockPos);
                    if (blockEntity instanceof SavesControllerPosBlockEntity savesControllerPosBE) {
                        savesControllerPosBE.setControllerPos(controllerPos);
                    }

                    if (blockEntity instanceof FakeBlockEntity fakeBE) {
                        blockEntity = level.getBlockEntity(fakeBE.getActualBlockEntityPos());
                    }

                    if (blockEntity instanceof MultiblockEntity entity) {
                        entity.setMultiblockData(multiblockData);
                    }
                }

                if (x + 1 < width) {
                    x++;
                } else {
                    x = 0;
                    z++;
                }
                index++;
            }
            index = 0;
            yIndex++;

        }
    }

    /**
     * @return Whether the unforming was successful
     */
    public static boolean unform(Multiblock multiblock, BlockPos controllerPos, Level level, @Nullable Player player) {
        unformBlocks(multiblock, controllerPos, level, player);
        return true;
    }

    /**
     * @return Whether the unforming was successful
     */
    public static boolean unform(Multiblock multiblock, BlockPos controllerPos, Level level) {
        return unform(multiblock, controllerPos, level, null);
    }

    private static void unformBlocks(Multiblock multiblock, BlockPos controllerPos, Level level, @Nullable Player player) {
        MultiblockData data;

        BlockPos controllerPos1 = controllerPos;

        if (level.getBlockEntity(controllerPos1) instanceof FakeBlockEntity fakeBE) {
            controllerPos1 = fakeBE.getActualBlockEntityPos();
        }

        if (level.getBlockEntity(controllerPos1) instanceof MultiblockEntity multiblockEntity) {
            data = multiblockEntity.getMultiblockData();
            Nautec.LOGGER.debug("Data: {}", data);
        } else {
            throw new IllegalStateException(multiblock + " multiblock controller does not have a blockentity");
        }

        Vec3i relativeControllerPos = getRelativeControllerPos(multiblock);
        HorizontalDirection direction = data.direction();
        // Calculate block pos of the first block in the multi (multiblock.getLayout().get(0))
        BlockPos firstBlockPos = getFirstBlockPos(direction, controllerPos, relativeControllerPos);
        Nautec.LOGGER.debug("first: {}", firstBlockPos);
        MultiblockLayer[] layout = data.layers();
        Map<Integer, Block> def = multiblock.getDefinition();

        int yIndex = 0;
        int xIndex = 0;
        for (MultiblockLayer layer : layout) {
            // relative position
            int x = 0;
            // multiblock index
            int width = multiblock.getWidths().get(yIndex).leftInt();
            int z = 0;
            for (int blockIndex : layer.layer()) {
                Block definedBlock = def.get(blockIndex);
                BlockPos curBlockPos = getCurPos(firstBlockPos, new Vec3i(x, yIndex, z), direction);

                BlockState blockState = level.getBlockState(curBlockPos);
                if (!level.getBlockState(curBlockPos).isEmpty()) {
                    BlockState expectedState = multiblock.formBlock(level, curBlockPos, controllerPos, xIndex, yIndex, data, player);
                    if (expectedState != null) {
                        if (blockState.is(expectedState.getBlock())) {
                            if (multiblock.isFormed(level, curBlockPos)) {
                                level.setBlockAndUpdate(curBlockPos, definedBlock.defaultBlockState());
                                multiblock.afterUnformBlock(level, curBlockPos, controllerPos, xIndex, yIndex, direction, player);
                            }
                        }
                    }
                }

                if (x + 1 < width) {
                    x++;
                } else {
                    x = 0;
                    z++;
                }
                xIndex++;
            }
            xIndex = 0;
            yIndex++;
        }
    }
}