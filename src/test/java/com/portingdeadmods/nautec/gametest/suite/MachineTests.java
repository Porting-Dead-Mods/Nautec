package com.portingdeadmods.nautec.gametest.suite;

import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.capabilities.power.IPowerStorage;
import com.portingdeadmods.nautec.content.blockentities.AquaticCatalystBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.ChargerBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.FishingStationBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.MixerBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.OilBarrelBlockEntity;
import com.portingdeadmods.nautec.content.blocks.OilBarrelBlock;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTFluids;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import java.util.Set;

public final class MachineTests {
    private MachineTests() {
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

    private static int batteryPower(GameTestHelper helper, ItemStack stack) {
        IPowerStorage storage = stack.getCapability(NTCapabilities.PowerStorage.ITEM);
        if (storage == null) {
            throw helper.assertionException("Battery stack lost its power capability");
        }
        return storage.getPowerStored();
    }

    public static void register(NTTestRegistrar r) {
        r.add("machine/charger_charges_battery", 140, helper -> {
            BlockPos sourcePos = new BlockPos(4, 4, 4);
            BlockPos chargerPos = new BlockPos(4, 1, 4);
            placeShieldedSource(helper, sourcePos, Direction.DOWN);
            helper.setBlock(chargerPos, NTBlocks.CHARGER.get().defaultBlockState());

            ChargerBlockEntity charger = helper.getBlockEntity(chargerPos, ChargerBlockEntity.class);
            if (charger == null) {
                helper.fail("Expected ChargerBlockEntity");
                return;
            }
            charger.getItemStackHandler().setStackInSlot(0, new ItemStack(NTItems.PRISMATIC_BATTERY.get()));

            int[] baseline = new int[1];
            helper.runAfterDelay(50, () ->
                    baseline[0] = batteryPower(helper, charger.getItemStackHandler().getStackInSlot(0)));

            helper.runAfterDelay(100, () -> {
                helper.assertValueEqual(100, charger.getPower(), "charger power while charging");
                int delta = batteryPower(helper, charger.getItemStackHandler().getStackInSlot(0)) - baseline[0];
                helper.assertTrue(delta >= 196 && delta <= 204,
                        "Battery should charge 4 power per tick over 50 ticks, delta was " + delta);
                helper.succeed();
            });
        });

        r.add("machine/charger_idle_without_power", 100, helper -> {
            BlockPos chargerPos = new BlockPos(4, 1, 4);
            helper.setBlock(chargerPos, NTBlocks.CHARGER.get().defaultBlockState());

            ChargerBlockEntity charger = helper.getBlockEntity(chargerPos, ChargerBlockEntity.class);
            if (charger == null) {
                helper.fail("Expected ChargerBlockEntity");
                return;
            }
            charger.getItemStackHandler().setStackInSlot(0, new ItemStack(NTItems.PRISMATIC_BATTERY.get()));

            helper.runAfterDelay(60, () -> {
                helper.assertValueEqual(0, charger.getPower(), "charger power without a laser source");
                helper.assertValueEqual(0, batteryPower(helper, charger.getItemStackHandler().getStackInSlot(0)),
                        "battery power without a laser source");
                helper.succeed();
            });
        });

        r.add("machine/aquatic_catalyst_channels_power", 120, helper -> {
            BlockPos catalystPos = new BlockPos(2, 1, 4);
            BlockPos mixerPos = new BlockPos(5, 1, 4);
            helper.setBlock(catalystPos, NTBlocks.AQUATIC_CATALYST.get().defaultBlockState()
                    .setValue(BlockStateProperties.FACING, Direction.WEST));
            helper.setBlock(mixerPos, NTBlocks.MIXER.get().defaultBlockState());

            AquaticCatalystBlockEntity catalyst = helper.getBlockEntity(catalystPos, AquaticCatalystBlockEntity.class);
            if (catalyst == null) {
                helper.fail("Expected AquaticCatalystBlockEntity");
                return;
            }
            helper.runAfterDelay(1, () ->
                    catalyst.getItemStackHandler().setStackInSlot(0, new ItemStack(NTItems.PRISMARINE_CRYSTAL_SHARD.get())));

            helper.runAfterDelay(60, () -> {
                helper.assertTrue(catalyst.isActive(), "Catalyst should be actively channeling");
                helper.assertTrue(catalyst.getItemStackHandler().getStackInSlot(0).isEmpty(),
                        "Catalyst should have consumed the crystal shard");
                MixerBlockEntity receiver = helper.getBlockEntity(mixerPos, MixerBlockEntity.class);
                if (receiver == null) {
                    helper.fail("Expected MixerBlockEntity");
                    return;
                }
                helper.assertValueEqual(12, receiver.getPower(), "power channeled per tick (2400 / 200)");
                helper.assertValueEqual(1.2f, receiver.getPurity(), "recipe purity carried by the beam");
                helper.succeed();
            });
        });

        r.add("machine/aquatic_catalyst_keeps_recipeless_items", 80, helper -> {
            BlockPos catalystPos = new BlockPos(4, 1, 4);
            helper.setBlock(catalystPos, NTBlocks.AQUATIC_CATALYST.get().defaultBlockState()
                    .setValue(BlockStateProperties.FACING, Direction.WEST));

            AquaticCatalystBlockEntity catalyst = helper.getBlockEntity(catalystPos, AquaticCatalystBlockEntity.class);
            if (catalyst == null) {
                helper.fail("Expected AquaticCatalystBlockEntity");
                return;
            }
            helper.runAfterDelay(1, () ->
                    catalyst.getItemStackHandler().setStackInSlot(0, new ItemStack(Items.STICK, 3)));

            helper.runAfterDelay(50, () -> {
                helper.assertTrue(!catalyst.isActive(), "Catalyst should not activate for a recipe-less item");
                ItemStack slot = catalyst.getItemStackHandler().getStackInSlot(0);
                helper.assertTrue(slot.is(Items.STICK), "Recipe-less item should remain in the slot");
                helper.assertValueEqual(3, slot.getCount(), "recipe-less item count after idling");
                helper.succeed();
            });
        });

        r.add("machine/mixer_crafts_aquarine_steel_compound", 300, helper -> {
            BlockPos mixerPos = new BlockPos(4, 1, 4);
            BlockPos sourcePos = new BlockPos(4, 4, 4);
            helper.setBlock(mixerPos, NTBlocks.MIXER.get().defaultBlockState());
            placeShieldedSource(helper, sourcePos, Direction.DOWN);

            MixerBlockEntity mixer = helper.getBlockEntity(mixerPos, MixerBlockEntity.class);
            if (mixer == null) {
                helper.fail("Expected MixerBlockEntity");
                return;
            }
            int filled = mixer.getFluidTank().fill(
                    new FluidStack(NTFluids.SALT_WATER.getStillFluid(), 1000), IFluidHandler.FluidAction.EXECUTE);
            helper.assertValueEqual(1000, filled, "salt water filled into mixer input tank");
            mixer.getItemStackHandler().setStackInSlot(0, new ItemStack(Items.RAW_IRON, 2));
            mixer.getItemStackHandler().setStackInSlot(1, new ItemStack(Items.PRISMARINE_CRYSTALS));

            helper.succeedWhen(() -> {
                ItemStack output = mixer.getItemStackHandler().getStackInSlot(MixerBlockEntity.OUTPUT_SLOT);
                helper.assertTrue(output.is(NTItems.AQUARINE_STEEL_COMPOUND.get()),
                        "Mixer output should be aquarine steel compound, was " + output);
                helper.assertValueEqual(5, output.getCount(), "mixer output count");
                helper.assertTrue(mixer.getItemStackHandler().getStackInSlot(0).isEmpty(), "raw iron consumed");
                helper.assertTrue(mixer.getItemStackHandler().getStackInSlot(1).isEmpty(), "prismarine crystals consumed");
                helper.assertValueEqual(0, mixer.getInputFluidAmount(), "salt water consumed");
            });
        });

        r.add("machine/mixer_produces_output_fluid", 350, helper -> {
            BlockPos mixerPos = new BlockPos(4, 1, 4);
            BlockPos sourcePos = new BlockPos(4, 4, 4);
            helper.setBlock(mixerPos, NTBlocks.MIXER.get().defaultBlockState());
            placeShieldedSource(helper, sourcePos, Direction.DOWN);

            MixerBlockEntity mixer = helper.getBlockEntity(mixerPos, MixerBlockEntity.class);
            if (mixer == null) {
                helper.fail("Expected MixerBlockEntity");
                return;
            }
            mixer.getFluidTank().fill(
                    new FluidStack(NTFluids.SALT_WATER.getStillFluid(), 1000), IFluidHandler.FluidAction.EXECUTE);
            mixer.getItemStackHandler().setStackInSlot(0, new ItemStack(Items.PUFFERFISH));
            mixer.getItemStackHandler().setStackInSlot(1, new ItemStack(Items.GUNPOWDER));
            mixer.getItemStackHandler().setStackInSlot(2, new ItemStack(Items.BONE_MEAL));

            helper.succeedWhen(() -> {
                FluidStack output = mixer.getSecondaryFluidTank().getFluid();
                helper.assertTrue(output.is(NTFluids.ETCHING_ACID.getStillFluid()),
                        "Mixer output tank should contain etching acid, was " + output);
                helper.assertValueEqual(1000, output.getAmount(), "etching acid amount");
                helper.assertValueEqual(0, mixer.getInputFluidAmount(), "salt water consumed");
            });
        });

        r.add("machine/mixer_requires_power", 120, helper -> {
            BlockPos mixerPos = new BlockPos(4, 1, 4);
            helper.setBlock(mixerPos, NTBlocks.MIXER.get().defaultBlockState());

            MixerBlockEntity mixer = helper.getBlockEntity(mixerPos, MixerBlockEntity.class);
            if (mixer == null) {
                helper.fail("Expected MixerBlockEntity");
                return;
            }
            mixer.getFluidTank().fill(
                    new FluidStack(NTFluids.SALT_WATER.getStillFluid(), 1000), IFluidHandler.FluidAction.EXECUTE);
            mixer.getItemStackHandler().setStackInSlot(0, new ItemStack(Items.RAW_IRON, 2));
            mixer.getItemStackHandler().setStackInSlot(1, new ItemStack(Items.PRISMARINE_CRYSTALS));

            helper.runAfterDelay(80, () -> {
                helper.assertTrue(!mixer.isActive(), "Mixer should not run without laser power");
                helper.assertValueEqual(0, mixer.getDuration(), "mixer progress without power");
                helper.assertTrue(mixer.getItemStackHandler().getStackInSlot(MixerBlockEntity.OUTPUT_SLOT).isEmpty(),
                        "Mixer output should be empty without power");
                helper.assertValueEqual(1000, mixer.getInputFluidAmount(), "input fluid untouched without power");
                helper.succeed();
            });
        });

        r.add("machine/oil_barrel_capacity_and_validation", 20, helper -> {
            BlockPos barrelPos = new BlockPos(4, 1, 4);
            helper.setBlock(barrelPos, NTBlocks.OIL_BARREL.get().defaultBlockState()
                    .setValue(OilBarrelBlock.OPEN, true));

            OilBarrelBlockEntity barrel = helper.getBlockEntity(barrelPos, OilBarrelBlockEntity.class);
            if (barrel == null) {
                helper.fail("Expected OilBarrelBlockEntity");
                return;
            }

            FluidStack oil5000 = new FluidStack(NTFluids.OIL.getStillFluid(), 5000);
            helper.assertValueEqual(5000, barrel.getFluidTank().fill(oil5000, IFluidHandler.FluidAction.EXECUTE), "first oil fill");
            helper.assertValueEqual(5000, barrel.getFluidTank().getFluidAmount(), "amount after first fill");

            helper.assertValueEqual(3000, barrel.getFluidTank().fill(oil5000, IFluidHandler.FluidAction.SIMULATE), "simulated overfill");
            helper.assertValueEqual(5000, barrel.getFluidTank().getFluidAmount(), "amount unchanged by simulation");

            helper.assertValueEqual(3000, barrel.getFluidTank().fill(oil5000, IFluidHandler.FluidAction.EXECUTE), "overfill clamped to capacity");
            helper.assertValueEqual(8000, barrel.getFluidTank().getFluidAmount(), "amount at capacity");
            helper.assertValueEqual(0, barrel.getFluidTank().fill(oil5000, IFluidHandler.FluidAction.EXECUTE), "fill when full");

            FluidStack drained = barrel.getFluidTank().drain(3000, IFluidHandler.FluidAction.EXECUTE);
            helper.assertTrue(drained.is(NTFluids.OIL.getStillFluid()), "drained fluid should be oil");
            helper.assertValueEqual(3000, drained.getAmount(), "drained amount");
            helper.assertValueEqual(5000, barrel.getFluidTank().getFluidAmount(), "amount after drain");

            helper.assertValueEqual(0, barrel.getFluidTank().fill(
                    new FluidStack(Fluids.WATER, 1000), IFluidHandler.FluidAction.EXECUTE), "water rejected by oil barrel");

            helper.succeed();
        });

        r.add("machine/oil_barrel_open_state_gates_handler", 20, helper -> {
            BlockPos closedPos = new BlockPos(2, 1, 4);
            BlockPos openPos = new BlockPos(6, 1, 4);
            helper.setBlock(closedPos, NTBlocks.OIL_BARREL.get().defaultBlockState()
                    .setValue(OilBarrelBlock.OPEN, false));
            helper.setBlock(openPos, NTBlocks.OIL_BARREL.get().defaultBlockState()
                    .setValue(OilBarrelBlock.OPEN, true));

            OilBarrelBlockEntity closed = helper.getBlockEntity(closedPos, OilBarrelBlockEntity.class);
            OilBarrelBlockEntity open = helper.getBlockEntity(openPos, OilBarrelBlockEntity.class);
            if (closed == null || open == null) {
                helper.fail("Expected OilBarrelBlockEntity at both positions");
                return;
            }

            helper.assertTrue(closed.getFluidHandler() == null, "Closed barrel should not expose a fluid handler");
            helper.assertTrue(open.getFluidHandler() != null, "Open barrel should expose a fluid handler");
            helper.assertTrue(closed.getFluidTank() != null, "Closed barrel still has an internal tank");

            helper.succeed();
        });

        r.add("machine/fishing_station_produces_loot", 400, helper -> {
            BlockPos stationPos = new BlockPos(4, 3, 4);
            BlockPos sourcePos = new BlockPos(4, 5, 4);

            for (int x = 1; x <= 7; x++) {
                for (int z = 1; z <= 7; z++) {
                    boolean rim = x == 1 || x == 7 || z == 1 || z == 7;
                    for (int y = 1; y <= 2; y++) {
                        helper.setBlock(new BlockPos(x, y, z),
                                rim ? Blocks.GLASS.defaultBlockState() : Blocks.WATER.defaultBlockState());
                    }
                }
            }

            helper.setBlock(stationPos, NTBlocks.FISHING_STATION.get().defaultBlockState());
            placeShieldedSource(helper, sourcePos, Direction.DOWN);

            FishingStationBlockEntity station = helper.getBlockEntity(stationPos, FishingStationBlockEntity.class);
            if (station == null) {
                helper.fail("Expected FishingStationBlockEntity");
                return;
            }

            helper.succeedWhen(() -> {
                boolean anyLoot = false;
                for (int i = 0; i < station.getItemStackHandler().getSlots(); i++) {
                    if (!station.getItemStackHandler().getStackInSlot(i).isEmpty()) {
                        anyLoot = true;
                        break;
                    }
                }
                helper.assertTrue(anyLoot, "Fishing station should have produced loot");
            });
        });

        r.add("machine/fishing_station_requires_water", 200, helper -> {
            BlockPos stationPos = new BlockPos(4, 3, 4);
            BlockPos sourcePos = new BlockPos(4, 5, 4);
            helper.setBlock(stationPos, NTBlocks.FISHING_STATION.get().defaultBlockState());
            placeShieldedSource(helper, sourcePos, Direction.DOWN);

            FishingStationBlockEntity station = helper.getBlockEntity(stationPos, FishingStationBlockEntity.class);
            if (station == null) {
                helper.fail("Expected FishingStationBlockEntity");
                return;
            }

            helper.runAfterDelay(150, () -> {
                helper.assertTrue(station.getPower() >= 1, "Station should still receive laser power");
                helper.assertTrue(!station.isRunning(), "Station should not run without water below");
                for (int i = 0; i < station.getItemStackHandler().getSlots(); i++) {
                    helper.assertTrue(station.getItemStackHandler().getStackInSlot(i).isEmpty(),
                            "Station inventory should stay empty without water, slot " + i);
                }
                helper.succeed();
            });
        });
    }
}
