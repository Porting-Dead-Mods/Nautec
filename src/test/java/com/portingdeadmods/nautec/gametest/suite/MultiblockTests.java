package com.portingdeadmods.nautec.gametest.suite;

import com.portingdeadmods.nautec.api.multiblocks.Multiblock;
import com.portingdeadmods.nautec.content.blockentities.multiblock.controller.AugmentationStationBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.multiblock.controller.BioReactorBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.multiblock.controller.DrainBlockEntity;
import com.portingdeadmods.nautec.content.blocks.multiblock.part.DrainPartBlock;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTFluids;
import com.portingdeadmods.nautec.registries.NTMultiblocks;
import com.portingdeadmods.nautec.utils.MultiblockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

public final class MultiblockTests {
    private MultiblockTests() {
    }

    private static final BlockPos DRAIN_C = new BlockPos(4, 1, 4);
    private static final BlockPos REACTOR_C = new BlockPos(4, 2, 4);
    private static final BlockPos AS_C = new BlockPos(4, 1, 4);
    private static final int[][] AS_EXTENSIONS = {{2, 0}, {0, 2}, {4, 2}, {2, 4}};
    private static final int[][] AS_STEEL = {{1, 1}, {3, 1}, {1, 3}, {3, 3}};
    private static final int[][] AS_PRISMARINE = {{2, 1}, {1, 2}, {3, 2}, {2, 3}};

    private static void placeDrain(GameTestHelper helper) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                Block block = (dx == 0 && dz == 0) ? NTBlocks.DRAIN.get() : NTBlocks.DRAIN_WALL.get();
                helper.setBlock(DRAIN_C.offset(dx, 0, dz), block);
            }
        }
    }

    private static void forDrainRing(Consumer<BlockPos> action) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (dx != 0 || dz != 0) {
                    action.accept(DRAIN_C.offset(dx, 0, dz));
                }
            }
        }
    }

    private static boolean formDrain(GameTestHelper helper) {
        return MultiblockHelper.form(NTMultiblocks.DRAIN.get(), helper.absolutePos(DRAIN_C), helper.getLevel());
    }

    private static void assertDrainUnformed(GameTestHelper helper, BlockPos broken) {
        forDrainRing(pos -> {
            BlockState state = helper.getBlockState(pos);
            helper.assertTrue(!state.is(NTBlocks.DRAIN_PART.get()), "No formed drain part expected at " + pos);
            if (!pos.equals(broken)) {
                helper.assertTrue(state.is(NTBlocks.DRAIN_WALL.get()), "Expected drain wall restored at " + pos);
            }
        });
        BlockState controller = helper.getBlockState(DRAIN_C);
        if (!DRAIN_C.equals(broken)) {
            helper.assertTrue(controller.is(NTBlocks.DRAIN.get()), "Expected unformed drain controller");
            helper.assertFalse(controller.getValue(Multiblock.FORMED), "Controller should not be formed");
        } else {
            helper.assertTrue(!controller.hasProperty(Multiblock.FORMED) || !controller.getValue(Multiblock.FORMED),
                    "Broken controller position should not be formed");
        }
    }

    private static void placeBioReactor(GameTestHelper helper) {
        for (int y = 0; y < 2; y++) {
            for (int z = 0; z < 3; z++) {
                for (int x = 0; x < 3; x++) {
                    Block block;
                    if (x == 1 && z == 1) {
                        block = y == 0 ? NTBlocks.POLISHED_PRISMARINE.get() : NTBlocks.BIO_REACTOR.get();
                    } else if (x != 1 && z != 1) {
                        block = NTBlocks.DARK_PRISMARINE_PILLAR.get();
                    } else {
                        block = NTBlocks.BACTERIAL_CONTAINMENT_SHIELD.get();
                    }
                    helper.setBlock(REACTOR_C.offset(x - 1, y - 1, z - 1), block);
                }
            }
        }
    }

    private static boolean formBioReactor(GameTestHelper helper) {
        return MultiblockHelper.form(NTMultiblocks.BIO_REACTOR.get(), helper.absolutePos(REACTOR_C), helper.getLevel());
    }

    private static void assertBioReactorUnformed(GameTestHelper helper, BlockPos broken) {
        checkRestored(helper, REACTOR_C.offset(0, -1, 0), NTBlocks.POLISHED_PRISMARINE.get(), broken);
        checkRestored(helper, REACTOR_C.offset(1, -1, 1), NTBlocks.DARK_PRISMARINE_PILLAR.get(), broken);
        checkRestored(helper, REACTOR_C.offset(0, 0, -1), NTBlocks.BACTERIAL_CONTAINMENT_SHIELD.get(), broken);
        checkRestored(helper, REACTOR_C.offset(-1, 0, 1), NTBlocks.DARK_PRISMARINE_PILLAR.get(), broken);
        BlockState controller = helper.getBlockState(REACTOR_C);
        helper.assertTrue(!controller.is(NTBlocks.BIO_REACTOR_PART.get()), "Controller pos should not be a reactor part");
        if (!REACTOR_C.equals(broken)) {
            helper.assertTrue(controller.is(NTBlocks.BIO_REACTOR.get()), "Expected unformed bio reactor controller");
            helper.assertFalse(controller.getValue(Multiblock.FORMED), "Controller should not be formed");
        }
    }

    private static void checkRestored(GameTestHelper helper, BlockPos pos, Block expected, BlockPos broken) {
        if (pos.equals(broken)) {
            return;
        }
        BlockState state = helper.getBlockState(pos);
        helper.assertTrue(state.is(expected), "Expected " + expected + " restored at " + pos + ", got " + state.getBlock());
    }

    private static BlockPos asPos(int lx, int lz) {
        return AS_C.offset(2 - lz, 0, lx - 2);
    }

    private static void placeAugmentationStation(GameTestHelper helper) {
        helper.setBlock(asPos(2, 2), NTBlocks.AUGMENTATION_STATION.get());
        for (int[] c : AS_EXTENSIONS) {
            helper.setBlock(asPos(c[0], c[1]), NTBlocks.AUGMENTATION_STATION_EXTENSION.get());
        }
        for (int[] c : AS_STEEL) {
            helper.setBlock(asPos(c[0], c[1]), NTBlocks.AQUARINE_STEEL_BLOCK.get());
        }
        for (int[] c : AS_PRISMARINE) {
            helper.setBlock(asPos(c[0], c[1]), NTBlocks.POLISHED_PRISMARINE.get());
        }
    }

    private static boolean formAugmentationStation(GameTestHelper helper) {
        return MultiblockHelper.form(NTMultiblocks.AUGMENTATION_STATION.get(), helper.absolutePos(asPos(2, 2)), helper.getLevel());
    }

    private static void assertAugmentationUnformed(GameTestHelper helper, BlockPos broken) {
        for (int[] c : AS_EXTENSIONS) {
            BlockPos pos = asPos(c[0], c[1]);
            if (pos.equals(broken)) {
                continue;
            }
            BlockState state = helper.getBlockState(pos);
            helper.assertTrue(state.is(NTBlocks.AUGMENTATION_STATION_EXTENSION.get()), "Expected extension restored at " + pos);
            helper.assertFalse(state.getValue(Multiblock.FORMED), "Extension should be unformed at " + pos);
        }
        for (int[] c : AS_STEEL) {
            checkRestored(helper, asPos(c[0], c[1]), NTBlocks.AQUARINE_STEEL_BLOCK.get(), broken);
        }
        for (int[] c : AS_PRISMARINE) {
            checkRestored(helper, asPos(c[0], c[1]), NTBlocks.POLISHED_PRISMARINE.get(), broken);
        }
        BlockPos controllerPos = asPos(2, 2);
        BlockState controller = helper.getBlockState(controllerPos);
        helper.assertTrue(!controller.is(NTBlocks.AUGMENTATION_STATION_PART.get()), "Controller pos should not be a station part");
        if (!controllerPos.equals(broken)) {
            helper.assertTrue(controller.is(NTBlocks.AUGMENTATION_STATION.get()), "Expected unformed augmentation station controller");
            helper.assertFalse(controller.getValue(Multiblock.FORMED), "Controller should not be formed");
        }
    }

    public static void register(NTTestRegistrar r) {
        r.add("multiblock/drain_form_valid", 60, helper -> {
            placeDrain(helper);
            helper.runAfterDelay(1, () -> {
                helper.assertTrue(formDrain(helper), "Drain multiblock should form with a valid layout");
                BlockState controller = helper.getBlockState(DRAIN_C);
                helper.assertTrue(controller.is(NTBlocks.DRAIN.get()), "Controller should stay a drain block");
                helper.assertTrue(controller.getValue(Multiblock.FORMED), "Controller should be formed");
                forDrainRing(pos -> {
                    BlockState state = helper.getBlockState(pos);
                    helper.assertTrue(state.is(NTBlocks.DRAIN_PART.get()), "Expected drain part at " + pos);
                    helper.assertTrue(state.getValue(Multiblock.FORMED), "Drain part should be formed at " + pos);
                });
                DrainBlockEntity be = helper.getBlockEntity(DRAIN_C, DrainBlockEntity.class);
                helper.assertTrue(be != null, "Formed drain should have a DrainBlockEntity");
                helper.assertTrue(be.getMultiblockData().valid(), "Controller multiblock data should be valid");
                helper.succeed();
            });
        });

        r.add("multiblock/drain_form_incomplete_fails", 60, helper -> {
            placeDrain(helper);
            helper.setBlock(DRAIN_C.offset(1, 0, 1), Blocks.AIR);
            helper.runAfterDelay(1, () -> {
                helper.assertFalse(formDrain(helper), "Drain should not form with a missing wall");
                helper.assertFalse(helper.getBlockState(DRAIN_C).getValue(Multiblock.FORMED), "Controller should stay unformed");
                forDrainRing(pos -> helper.assertTrue(!helper.getBlockState(pos).is(NTBlocks.DRAIN_PART.get()),
                        "No drain part expected at " + pos));
                helper.succeed();
            });
        });

        r.add("multiblock/drain_unform_restores", 60, helper -> {
            placeDrain(helper);
            helper.runAfterDelay(1, () -> {
                helper.assertTrue(formDrain(helper), "Drain should form");
                helper.assertTrue(MultiblockHelper.unform(NTMultiblocks.DRAIN.get(), helper.absolutePos(DRAIN_C), helper.getLevel()),
                        "Drain should unform");
                assertDrainUnformed(helper, null);
                helper.succeed();
            });
        });

        r.add("multiblock/drain_break_part_unforms", 80, helper -> {
            placeDrain(helper);
            helper.runAfterDelay(1, () -> {
                helper.assertTrue(formDrain(helper), "Drain should form");
                helper.destroyBlock(DRAIN_C.offset(1, 0, 0));
            });
            helper.runAfterDelay(10, () -> {
                assertDrainUnformed(helper, DRAIN_C.offset(1, 0, 0));
                helper.succeed();
            });
        });

        r.add("multiblock/drain_break_controller_unforms", 80, helper -> {
            placeDrain(helper);
            helper.runAfterDelay(1, () -> {
                helper.assertTrue(formDrain(helper), "Drain should form");
                helper.destroyBlock(DRAIN_C);
            });
            helper.runAfterDelay(10, () -> {
                assertDrainUnformed(helper, DRAIN_C);
                helper.succeed();
            });
        });

        r.add("multiblock/drain_produces_salt_water", 300, helper -> {
            helper.setBiome(Biomes.OCEAN);
            placeDrain(helper);
            helper.runAfterDelay(1, () -> {
                helper.assertTrue(formDrain(helper), "Drain should form");
                BlockPos abs = helper.absolutePos(DRAIN_C);
                BlockState state = helper.getLevel().getBlockState(abs);
                helper.getLevel().setBlockAndUpdate(abs, state.setValue(DrainPartBlock.OPEN, true));
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        helper.setBlock(DRAIN_C.offset(dx, 1, dz), Blocks.WATER);
                    }
                }
            });
            helper.onEachTick(() -> {
                DrainBlockEntity be = helper.getBlockEntity(DRAIN_C, DrainBlockEntity.class);
                if (be != null) {
                    be.receivePower(100, Direction.NORTH, helper.absolutePos(DRAIN_C));
                }
            });
            helper.runAfterDelay(220, () -> {
                DrainBlockEntity be = helper.getBlockEntity(DRAIN_C, DrainBlockEntity.class);
                helper.assertTrue(be != null, "Drain controller BE missing");
                helper.assertTrue(be.getFluidTank().getFluidAmount() > 0, "Powered open drain in ocean water should produce fluid");
                helper.assertTrue(be.getFluidTank().getFluid().is(NTFluids.SALT_WATER.getStillFluid()),
                        "Drain should produce salt water, got " + be.getFluidTank().getFluid().getFluid());
                helper.succeed();
            });
        });

        r.add("multiblock/bio_reactor_form_valid", 60, helper -> {
            placeBioReactor(helper);
            helper.runAfterDelay(1, () -> {
                helper.assertTrue(formBioReactor(helper), "Bio reactor should form with a valid layout");
                BlockState controller = helper.getBlockState(REACTOR_C);
                helper.assertTrue(controller.is(NTBlocks.BIO_REACTOR.get()), "Controller should stay a bio reactor block");
                helper.assertTrue(controller.getValue(Multiblock.FORMED), "Controller should be formed");
                BlockState basePart = helper.getBlockState(REACTOR_C.offset(0, -1, 0));
                helper.assertTrue(basePart.is(NTBlocks.BIO_REACTOR_PART.get()), "Base center should become a reactor part");
                helper.assertTrue(basePart.getValue(Multiblock.FORMED), "Base part should be formed");
                BlockState cornerPart = helper.getBlockState(REACTOR_C.offset(-1, 0, -1));
                helper.assertTrue(cornerPart.is(NTBlocks.BIO_REACTOR_PART.get()), "Top corner should become a reactor part");
                BioReactorBlockEntity be = helper.getBlockEntity(REACTOR_C, BioReactorBlockEntity.class);
                helper.assertTrue(be != null, "Formed bio reactor should have a BioReactorBlockEntity");
                helper.assertTrue(be.getMultiblockData().valid(), "Controller multiblock data should be valid");
                helper.succeed();
            });
        });

        r.add("multiblock/bio_reactor_form_wrong_block_fails", 60, helper -> {
            placeBioReactor(helper);
            helper.setBlock(REACTOR_C.offset(0, -1, -1), Blocks.STONE);
            helper.runAfterDelay(1, () -> {
                helper.assertFalse(formBioReactor(helper), "Bio reactor should not form with a wrong block");
                helper.assertFalse(helper.getBlockState(REACTOR_C).getValue(Multiblock.FORMED), "Controller should stay unformed");
                helper.assertTrue(!helper.getBlockState(REACTOR_C.offset(0, -1, 0)).is(NTBlocks.BIO_REACTOR_PART.get()),
                        "No reactor parts should exist");
                helper.succeed();
            });
        });

        r.add("multiblock/bio_reactor_break_part_unforms", 80, helper -> {
            placeBioReactor(helper);
            helper.runAfterDelay(1, () -> {
                helper.assertTrue(formBioReactor(helper), "Bio reactor should form");
                helper.destroyBlock(REACTOR_C.offset(-1, -1, -1));
            });
            helper.runAfterDelay(10, () -> {
                assertBioReactorUnformed(helper, REACTOR_C.offset(-1, -1, -1));
                helper.succeed();
            });
        });

        r.add("multiblock/bio_reactor_break_controller_unforms", 80, helper -> {
            placeBioReactor(helper);
            helper.runAfterDelay(1, () -> {
                helper.assertTrue(formBioReactor(helper), "Bio reactor should form");
                helper.destroyBlock(REACTOR_C);
            });
            helper.runAfterDelay(10, () -> {
                assertBioReactorUnformed(helper, REACTOR_C);
                helper.succeed();
            });
        });

        r.add("multiblock/augmentation_form_valid", 60, helper -> {
            placeAugmentationStation(helper);
            helper.runAfterDelay(1, () -> {
                helper.assertTrue(formAugmentationStation(helper), "Augmentation station should form with a valid layout");
                BlockState controller = helper.getBlockState(asPos(2, 2));
                helper.assertTrue(controller.is(NTBlocks.AUGMENTATION_STATION.get()), "Controller should stay an augmentation station");
                helper.assertTrue(controller.getValue(Multiblock.FORMED), "Controller should be formed");
                for (int[] c : AS_EXTENSIONS) {
                    BlockState ext = helper.getBlockState(asPos(c[0], c[1]));
                    helper.assertTrue(ext.is(NTBlocks.AUGMENTATION_STATION_EXTENSION.get()), "Expected extension at " + asPos(c[0], c[1]));
                    helper.assertTrue(ext.getValue(Multiblock.FORMED), "Extension should be formed at " + asPos(c[0], c[1]));
                }
                for (int[] c : AS_PRISMARINE) {
                    helper.assertTrue(helper.getBlockState(asPos(c[0], c[1])).is(NTBlocks.AUGMENTATION_STATION_PART.get()),
                            "Expected station part at " + asPos(c[0], c[1]));
                }
                AugmentationStationBlockEntity be = helper.getBlockEntity(asPos(2, 2), AugmentationStationBlockEntity.class);
                helper.assertTrue(be != null, "Formed station should have an AugmentationStationBlockEntity");
                helper.assertTrue(be.getMultiblockData().valid(), "Controller multiblock data should be valid");
                helper.succeed();
            });
        });

        r.add("multiblock/augmentation_form_incomplete_fails", 60, helper -> {
            placeAugmentationStation(helper);
            helper.setBlock(asPos(1, 1), Blocks.AIR);
            helper.runAfterDelay(1, () -> {
                helper.assertFalse(formAugmentationStation(helper), "Augmentation station should not form with a missing block");
                helper.assertFalse(helper.getBlockState(asPos(2, 2)).getValue(Multiblock.FORMED), "Controller should stay unformed");
                helper.assertFalse(helper.getBlockState(asPos(2, 0)).getValue(Multiblock.FORMED), "Extensions should stay unformed");
                helper.succeed();
            });
        });

        r.add("multiblock/augmentation_break_part_unforms", 80, helper -> {
            placeAugmentationStation(helper);
            helper.runAfterDelay(1, () -> {
                helper.assertTrue(formAugmentationStation(helper), "Augmentation station should form");
                helper.destroyBlock(asPos(2, 1));
            });
            helper.runAfterDelay(10, () -> {
                assertAugmentationUnformed(helper, asPos(2, 1));
                helper.succeed();
            });
        });

        r.add("multiblock/augmentation_break_controller_unforms", 80, helper -> {
            placeAugmentationStation(helper);
            helper.runAfterDelay(1, () -> {
                helper.assertTrue(formAugmentationStation(helper), "Augmentation station should form");
                helper.destroyBlock(asPos(2, 2));
            });
            helper.runAfterDelay(10, () -> {
                assertAugmentationUnformed(helper, asPos(2, 2));
                helper.succeed();
            });
        });

        r.add("multiblock/augmentation_break_extension_unforms", 80, helper -> {
            placeAugmentationStation(helper);
            helper.runAfterDelay(1, () -> {
                helper.assertTrue(formAugmentationStation(helper), "Augmentation station should form");
                helper.destroyBlock(asPos(2, 0));
            });
            helper.runAfterDelay(10, () -> {
                assertAugmentationUnformed(helper, asPos(2, 0));
                helper.succeed();
            });
        });

        r.add("multiblock/augmentation_lone_extension_breaks_clean", 60, helper -> {
            BlockPos pos = new BlockPos(4, 1, 4);
            helper.setBlock(pos, NTBlocks.AUGMENTATION_STATION_EXTENSION.get());
            helper.runAfterDelay(1, () -> helper.destroyBlock(pos));
            helper.runAfterDelay(10, () -> {
                helper.assertBlockPresent(Blocks.AIR, pos);
                helper.succeed();
            });
        });
    }
}
