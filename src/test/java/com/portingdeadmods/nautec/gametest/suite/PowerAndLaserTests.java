package com.portingdeadmods.nautec.gametest.suite;

import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.capabilities.power.IPowerStorage;
import com.portingdeadmods.nautec.content.blockentities.CreativePowerSourceBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.LaserJunctionBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.MixerBlockEntity;
import com.portingdeadmods.nautec.content.blocks.LongDistanceLaserBlock;
import com.portingdeadmods.nautec.content.blocks.PrismarineLaserRelayBlock;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.Set;

public final class PowerAndLaserTests {
    private static final BlockPos SOURCE_POS = new BlockPos(2, 1, 4);

    private PowerAndLaserTests() {
    }

    private static void placeShieldedSource(GameTestHelper helper, BlockPos pos, Direction... openDirections) {
        helper.setBlock(pos, NTBlocks.CREATIVE_POWER_SOURCE.get().defaultBlockState());
        Set<Direction> open = Set.of(openDirections);
        for (Direction direction : Direction.values()) {
            if (direction == Direction.DOWN || open.contains(direction)) {
                continue;
            }
            helper.setBlock(pos.relative(direction, 2), Blocks.STONE.defaultBlockState());
        }
    }

    private static MixerBlockEntity mixer(GameTestHelper helper, BlockPos pos) {
        MixerBlockEntity mixer = helper.getBlockEntity(pos, MixerBlockEntity.class);
        if (mixer == null) {
            throw helper.assertionException("Expected MixerBlockEntity at " + pos);
        }
        return mixer;
    }

    private static IPowerStorage batteryStorage(GameTestHelper helper, ItemStack stack) {
        IPowerStorage storage = stack.getCapability(NTCapabilities.PowerStorage.ITEM);
        if (storage == null) {
            throw helper.assertionException("Prismatic battery did not expose item power capability");
        }
        return storage;
    }

    public static void register(NTTestRegistrar r) {
        r.add("power/item_fill_respects_max_input_and_capacity", 20, helper -> {
            ItemStack stack = new ItemStack(NTItems.PRISMATIC_BATTERY.get());
            IPowerStorage storage = batteryStorage(helper, stack);

            helper.assertValueEqual(0, storage.getPowerStored(), "initial stored power");
            helper.assertValueEqual(10000, storage.getPowerCapacity(), "battery capacity");
            helper.assertValueEqual(128, storage.getMaxInput(), "battery max input");

            helper.assertValueEqual(128, storage.tryFillPower(500, false), "fill clamped to max input");
            helper.assertValueEqual(128, storage.getPowerStored(), "stored after clamped fill");

            helper.assertValueEqual(0, storage.tryFillPower(0, false), "fill of zero");
            helper.assertValueEqual(0, storage.tryFillPower(-5, false), "fill of negative");

            storage.setPowerStored(9990);
            helper.assertValueEqual(10, storage.tryFillPower(500, false), "fill clamped to remaining capacity");
            helper.assertValueEqual(10000, storage.getPowerStored(), "stored at capacity");
            helper.assertValueEqual(0, storage.tryFillPower(1, false), "fill when full");

            helper.succeed();
        });

        r.add("power/item_drain_respects_max_output", 20, helper -> {
            ItemStack stack = new ItemStack(NTItems.PRISMATIC_BATTERY.get());
            IPowerStorage storage = batteryStorage(helper, stack);
            helper.assertValueEqual(100, storage.getMaxOutput(), "battery max output");

            storage.setPowerStored(500);
            helper.assertValueEqual(40, storage.tryDrainPower(40, false), "partial drain");
            helper.assertValueEqual(460, storage.getPowerStored(), "stored after partial drain");

            helper.assertValueEqual(100, storage.tryDrainPower(500, false), "drain clamped to max output");
            helper.assertValueEqual(360, storage.getPowerStored(), "stored after clamped drain");

            storage.setPowerStored(30);
            helper.assertValueEqual(30, storage.tryDrainPower(500, false), "drain clamped to stored power");
            helper.assertValueEqual(0, storage.getPowerStored(), "stored after emptying");
            helper.assertValueEqual(0, storage.tryDrainPower(5, false), "drain when empty");

            helper.succeed();
        });

        r.add("power/simulate_does_not_mutate", 20, helper -> {
            ItemStack stack = new ItemStack(NTItems.PRISMATIC_BATTERY.get());
            IPowerStorage storage = batteryStorage(helper, stack);

            helper.assertValueEqual(128, storage.tryFillPower(500, true), "simulated fill result");
            helper.assertValueEqual(0, storage.getPowerStored(), "stored unchanged after simulated fill");

            storage.setPowerStored(250);
            helper.assertValueEqual(100, storage.tryDrainPower(500, true), "simulated drain result");
            helper.assertValueEqual(250, storage.getPowerStored(), "stored unchanged after simulated drain");

            helper.succeed();
        });

        r.add("power/purity_get_set_persists", 20, helper -> {
            ItemStack stack = new ItemStack(NTItems.PRISMATIC_BATTERY.get());
            IPowerStorage storage = batteryStorage(helper, stack);

            helper.assertValueEqual(0.0f, storage.getPurity(), "initial purity");
            storage.setPurity(0.5f);
            helper.assertValueEqual(0.5f, storage.getPurity(), "purity after set");

            storage.setPowerStored(64);
            helper.assertValueEqual(0.5f, storage.getPurity(), "purity preserved after power change");
            helper.assertValueEqual(64, storage.getPowerStored(), "stored preserved after purity set");

            IPowerStorage freshWrapper = batteryStorage(helper, stack);
            helper.assertValueEqual(0.5f, freshWrapper.getPurity(), "purity persisted in data component");

            helper.succeed();
        });

        r.add("power/item_capability_exposure", 20, helper -> {
            ItemStack battery = new ItemStack(NTItems.PRISMATIC_BATTERY.get());
            helper.assertTrue(battery.getCapability(NTCapabilities.PowerStorage.ITEM) != null,
                    "Prismatic battery should expose the item power capability");

            ItemStack stick = new ItemStack(Items.STICK);
            helper.assertTrue(stick.getCapability(NTCapabilities.PowerStorage.ITEM) == null,
                    "Plain stick should not expose the item power capability");

            helper.succeed();
        });

        r.add("laser/source_transmits_power_to_mixer", 100, helper -> {
            BlockPos mixerPos = new BlockPos(5, 1, 4);
            placeShieldedSource(helper, SOURCE_POS, Direction.EAST);
            helper.setBlock(mixerPos, NTBlocks.MIXER.get().defaultBlockState());

            helper.runAfterDelay(60, () -> {
                CreativePowerSourceBlockEntity source = helper.getBlockEntity(SOURCE_POS, CreativePowerSourceBlockEntity.class);
                if (source == null) {
                    helper.fail("Expected CreativePowerSourceBlockEntity");
                    return;
                }
                helper.assertValueEqual(3, source.getLaserDistances().getInt(Direction.EAST), "laser distance to mixer");
                helper.assertValueEqual(100, mixer(helper, mixerPos).getPower(), "mixer received power");
                helper.succeed();
            });
        });

        r.add("laser/beam_blocked_by_obstruction", 140, helper -> {
            BlockPos mixerPos = new BlockPos(6, 1, 4);
            BlockPos obstructionPos = new BlockPos(4, 1, 4);
            placeShieldedSource(helper, SOURCE_POS, Direction.EAST);
            helper.setBlock(mixerPos, NTBlocks.MIXER.get().defaultBlockState());

            helper.runAfterDelay(50, () -> {
                helper.assertValueEqual(100, mixer(helper, mixerPos).getPower(), "mixer powered before obstruction");
                helper.setBlock(obstructionPos, Blocks.STONE.defaultBlockState());
            });

            helper.runAfterDelay(110, () -> {
                CreativePowerSourceBlockEntity source = helper.getBlockEntity(SOURCE_POS, CreativePowerSourceBlockEntity.class);
                if (source == null) {
                    helper.fail("Expected CreativePowerSourceBlockEntity");
                    return;
                }
                helper.assertValueEqual(0, source.getLaserDistances().getInt(Direction.EAST), "laser distance after obstruction");
                helper.assertValueEqual(0, mixer(helper, mixerPos).getPower(), "mixer power after obstruction");
                helper.succeed();
            });
        });

        r.add("laser/relay_passes_power_through", 100, helper -> {
            BlockPos relayPos = new BlockPos(4, 1, 4);
            BlockPos mixerPos = new BlockPos(6, 1, 4);
            placeShieldedSource(helper, SOURCE_POS, Direction.EAST);
            helper.setBlock(relayPos, NTBlocks.PRISMARINE_RELAY.get().defaultBlockState()
                    .setValue(PrismarineLaserRelayBlock.FACING, Direction.EAST));
            helper.setBlock(mixerPos, NTBlocks.MIXER.get().defaultBlockState());

            helper.runAfterDelay(60, () -> {
                helper.assertValueEqual(100, mixer(helper, mixerPos).getPower(), "mixer power through relay");
                helper.succeed();
            });
        });

        r.add("laser/relay_facing_gates_input", 100, helper -> {
            BlockPos relayPos = new BlockPos(4, 1, 4);
            BlockPos mixerPos = new BlockPos(6, 1, 4);
            placeShieldedSource(helper, SOURCE_POS, Direction.EAST);
            helper.setBlock(relayPos, NTBlocks.PRISMARINE_RELAY.get().defaultBlockState()
                    .setValue(PrismarineLaserRelayBlock.FACING, Direction.NORTH));
            helper.setBlock(new BlockPos(4, 1, 2), Blocks.STONE.defaultBlockState());
            helper.setBlock(mixerPos, NTBlocks.MIXER.get().defaultBlockState());

            helper.runAfterDelay(60, () -> {
                CreativePowerSourceBlockEntity source = helper.getBlockEntity(SOURCE_POS, CreativePowerSourceBlockEntity.class);
                if (source == null) {
                    helper.fail("Expected CreativePowerSourceBlockEntity");
                    return;
                }
                helper.assertValueEqual(0, source.getLaserDistances().getInt(Direction.EAST), "no connection into wrongly-faced relay");
                helper.assertValueEqual(0, mixer(helper, mixerPos).getPower(), "mixer unpowered behind wrongly-faced relay");
                helper.succeed();
            });
        });

        r.add("laser/junction_routes_power", 100, helper -> {
            BlockPos junctionPos = new BlockPos(4, 1, 4);
            BlockPos mixerPos = new BlockPos(6, 1, 4);
            placeShieldedSource(helper, SOURCE_POS, Direction.EAST);
            helper.setBlock(junctionPos, NTBlocks.LASER_JUNCTION.get().defaultBlockState());
            helper.setBlock(mixerPos, NTBlocks.MIXER.get().defaultBlockState());

            LaserJunctionBlockEntity junction = helper.getBlockEntity(junctionPos, LaserJunctionBlockEntity.class);
            if (junction == null) {
                helper.fail("Expected LaserJunctionBlockEntity");
                return;
            }
            junction.getLaserInputs().add(Direction.WEST);
            junction.getLaserOutputs().add(Direction.EAST);

            helper.runAfterDelay(60, () -> {
                helper.assertValueEqual(100, junction.getPower(), "junction received power");
                helper.assertValueEqual(100, mixer(helper, mixerPos).getPower(), "mixer power through junction");
                helper.succeed();
            });
        });

        r.add("laser/long_distance_laser_passes_power", 100, helper -> {
            BlockPos ldlPos = new BlockPos(4, 1, 4);
            BlockPos mixerPos = new BlockPos(6, 1, 4);
            placeShieldedSource(helper, SOURCE_POS, Direction.EAST);
            helper.setBlock(ldlPos, NTBlocks.LONG_DISTANCE_LASER.get().defaultBlockState()
                    .setValue(LongDistanceLaserBlock.FACING, Direction.EAST));
            helper.setBlock(mixerPos, NTBlocks.MIXER.get().defaultBlockState());

            helper.runAfterDelay(60, () -> {
                helper.assertValueEqual(100, mixer(helper, mixerPos).getPower(), "mixer power through long distance laser");
                helper.succeed();
            });
        });
    }
}
