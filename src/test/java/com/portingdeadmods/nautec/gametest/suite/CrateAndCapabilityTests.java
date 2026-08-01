package com.portingdeadmods.nautec.gametest.suite;

import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.capabilities.item.ItemStackHandler;
import com.portingdeadmods.nautec.capabilities.item.SidedItemHandler;
import com.portingdeadmods.nautec.content.blockentities.CrateBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.MixerBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.multiblock.controller.DrainBlockEntity;
import com.portingdeadmods.nautec.registries.NTBlocks;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.List;

public final class CrateAndCapabilityTests {
    private CrateAndCapabilityTests() {
    }

    private static final BlockPos TARGET = new BlockPos(4, 1, 4);

    public static void register(NTTestRegistrar r) {
        r.add("crate/break_keeps_items_in_drop", 60, helper -> {
            helper.setBlock(TARGET, NTBlocks.CRATE.get());
            helper.runAfterDelay(1, () -> {
                CrateBlockEntity crate = helper.getBlockEntity(TARGET, CrateBlockEntity.class);
                helper.assertTrue(crate != null, "Crate BE missing");
                crate.setItem(0, new ItemStack(Items.DIAMOND, 5));
                crate.setItem(13, new ItemStack(Items.EMERALD, 3));
                helper.getLevel().destroyBlock(helper.absolutePos(TARGET), true);
            });
            helper.runAfterDelay(10, () -> {
                List<ItemEntity> drops = helper.getEntities(EntityType.ITEM, TARGET, 4.0);
                List<ItemEntity> crateDrops = drops.stream()
                        .filter(entity -> entity.getItem().is(NTBlocks.CRATE.get().asItem()))
                        .toList();
                helper.assertValueEqual(1, crateDrops.size(), "crate item entity count");
                long looseContents = drops.stream()
                        .filter(entity -> entity.getItem().is(Items.DIAMOND) || entity.getItem().is(Items.EMERALD))
                        .count();
                helper.assertValueEqual(0L, looseContents, "loose spilled content item entities");
                ItemStack dropStack = crateDrops.getFirst().getItem();
                ItemContainerContents contents = dropStack.get(DataComponents.CONTAINER);
                helper.assertTrue(contents != null, "Dropped crate should carry a CONTAINER component");
                helper.assertTrue(contents.getStackInSlot(0).is(Items.DIAMOND), "Slot 0 of dropped crate should be diamonds");
                helper.assertValueEqual(5, contents.getStackInSlot(0).getCount(), "diamond count in dropped crate");
                helper.assertTrue(contents.getStackInSlot(13).is(Items.EMERALD), "Slot 13 of dropped crate should be emeralds");
                helper.assertValueEqual(3, contents.getStackInSlot(13).getCount(), "emerald count in dropped crate");
                helper.succeed();
            });
        });

        r.add("crate/replaced_crate_restores_contents", 80, helper -> {
            BlockPos second = new BlockPos(2, 1, 2);
            helper.setBlock(TARGET, NTBlocks.CRATE.get());
            helper.runAfterDelay(1, () -> {
                CrateBlockEntity crate = helper.getBlockEntity(TARGET, CrateBlockEntity.class);
                helper.assertTrue(crate != null, "Crate BE missing");
                crate.setItem(0, new ItemStack(Items.DIAMOND, 5));
                helper.getLevel().destroyBlock(helper.absolutePos(TARGET), true);
            });
            helper.runAfterDelay(10, () -> {
                List<ItemEntity> crateDrops = helper.getEntities(EntityType.ITEM, TARGET, 4.0).stream()
                        .filter(entity -> entity.getItem().is(NTBlocks.CRATE.get().asItem()))
                        .toList();
                helper.assertValueEqual(1, crateDrops.size(), "crate item entity count");
                ItemStack dropStack = crateDrops.getFirst().getItem();
                helper.setBlock(second, NTBlocks.CRATE.get());
                CrateBlockEntity placed = helper.getBlockEntity(second, CrateBlockEntity.class);
                helper.assertTrue(placed != null, "Replaced crate BE missing");
                placed.applyComponentsFromItemStack(dropStack);
                helper.assertTrue(placed.getItem(0).is(Items.DIAMOND), "Replaced crate slot 0 should be diamonds");
                helper.assertValueEqual(5, placed.getItem(0).getCount(), "diamond count after re-placing crate");
                helper.succeed();
            });
        });

        r.add("capability/mixer_item_sided_insert", 60, helper -> {
            helper.setBlock(TARGET, NTBlocks.MIXER.get());
            helper.runAfterDelay(2, () -> {
                BlockPos abs = helper.absolutePos(TARGET);
                ResourceHandler<ItemResource> north = helper.getLevel().getCapability(Capabilities.Item.BLOCK, abs, Direction.NORTH);
                helper.assertTrue(north != null, "Mixer should expose an item handler on its north side");
                ItemResource cobble = ItemResource.of(new ItemStack(Items.COBBLESTONE));
                try (Transaction tx = Transaction.openRoot()) {
                    helper.assertValueEqual(4, north.insert(0, cobble, 4, tx), "inserted via north side");
                    tx.commit();
                }
                MixerBlockEntity mixer = helper.getBlockEntity(TARGET, MixerBlockEntity.class);
                helper.assertTrue(mixer != null, "Mixer BE missing");
                helper.assertTrue(mixer.getItemStackHandler().getStackInSlot(0).is(Items.COBBLESTONE), "Slot 0 should hold cobblestone");
                helper.assertValueEqual(4, mixer.getItemStackHandler().getStackInSlot(0).getCount(), "slot 0 count");
                try (Transaction tx = Transaction.openRoot()) {
                    helper.assertValueEqual(0, north.extract(0, cobble, 4, tx), "extract via insert-only north side");
                }
                ResourceHandler<ItemResource> up = helper.getLevel().getCapability(Capabilities.Item.BLOCK, abs, Direction.UP);
                helper.assertTrue(up == null, "Mixer should not expose an item handler on top");
                helper.succeed();
            });
        });

        r.add("capability/mixer_item_null_direction_unfiltered", 60, helper -> {
            helper.setBlock(TARGET, NTBlocks.MIXER.get());
            helper.runAfterDelay(2, () -> {
                BlockPos abs = helper.absolutePos(TARGET);
                ResourceHandler<ItemResource> handler = helper.getLevel().getCapability(Capabilities.Item.BLOCK, abs, null);
                helper.assertTrue(handler != null, "Null direction should return the base item handler");
                ItemResource cobble = ItemResource.of(new ItemStack(Items.COBBLESTONE));
                try (Transaction tx = Transaction.openRoot()) {
                    helper.assertValueEqual(8, handler.insert(0, cobble, 8, tx), "insert into slot 0 via base handler");
                    helper.assertValueEqual(0, handler.insert(4, cobble, 8, tx), "insert into output slot 4 blocked by validator");
                    tx.commit();
                }
                try (Transaction tx = Transaction.openRoot()) {
                    helper.assertValueEqual(8, handler.extract(0, cobble, 8, tx), "extract via unfiltered base handler");
                    tx.commit();
                }
                helper.succeed();
            });
        });

        r.add("capability/mixer_fluid_two_tank", 60, helper -> {
            helper.setBlock(TARGET, NTBlocks.MIXER.get());
            helper.runAfterDelay(2, () -> {
                BlockPos abs = helper.absolutePos(TARGET);
                ResourceHandler<FluidResource> north = helper.getLevel().getCapability(Capabilities.Fluid.BLOCK, abs, Direction.NORTH);
                helper.assertTrue(north != null, "Mixer should expose a fluid handler on its north side");
                FluidResource water = FluidResource.of(Fluids.WATER);
                try (Transaction tx = Transaction.openRoot()) {
                    helper.assertValueEqual(250, north.insert(0, water, 250, tx), "insert into input tank");
                    helper.assertValueEqual(0, north.insert(1, water, 250, tx), "insert into output tank blocked");
                    tx.commit();
                }
                MixerBlockEntity mixer = helper.getBlockEntity(TARGET, MixerBlockEntity.class);
                helper.assertTrue(mixer != null, "Mixer BE missing");
                helper.assertValueEqual(250, mixer.getFluidTank().getFluidAmount(), "input tank amount");
                try (Transaction tx = Transaction.openRoot()) {
                    helper.assertValueEqual(0, north.extract(0, water, 100, tx), "extract from input tank blocked");
                }
                mixer.getSecondaryFluidTank().setFluid(new FluidStack(Fluids.WATER, 500));
                try (Transaction tx = Transaction.openRoot()) {
                    helper.assertValueEqual(400, north.extract(1, water, 400, tx), "extract from output tank");
                    tx.commit();
                }
                helper.assertValueEqual(100, mixer.getSecondaryFluidTank().getFluidAmount(), "output tank amount after extract");
                ResourceHandler<FluidResource> up = helper.getLevel().getCapability(Capabilities.Fluid.BLOCK, abs, Direction.UP);
                helper.assertTrue(up == null, "Mixer should not expose a fluid handler on top");
                helper.succeed();
            });
        });

        r.add("capability/drain_bottom_extract_only", 60, helper -> {
            helper.setBlock(TARGET, NTBlocks.DRAIN.get());
            helper.runAfterDelay(2, () -> {
                DrainBlockEntity drain = helper.getBlockEntity(TARGET, DrainBlockEntity.class);
                helper.assertTrue(drain != null, "Drain BE missing");
                drain.getFluidTank().setFluid(new FluidStack(Fluids.WATER, 1000));
                BlockPos abs = helper.absolutePos(TARGET);
                ResourceHandler<FluidResource> down = helper.getLevel().getCapability(Capabilities.Fluid.BLOCK, abs, Direction.DOWN);
                helper.assertTrue(down != null, "Drain should expose a fluid handler on the bottom");
                FluidResource water = FluidResource.of(Fluids.WATER);
                try (Transaction tx = Transaction.openRoot()) {
                    helper.assertValueEqual(0, down.insert(0, water, 100, tx), "insert via extract-only bottom side");
                    helper.assertValueEqual(400, down.extract(0, water, 400, tx), "extract via bottom side");
                    tx.commit();
                }
                helper.assertValueEqual(600, drain.getFluidTank().getFluidAmount(), "drain tank after extraction");
                ResourceHandler<FluidResource> north = helper.getLevel().getCapability(Capabilities.Fluid.BLOCK, abs, Direction.NORTH);
                helper.assertTrue(north == null, "Drain should not expose a fluid handler on its side");
                helper.succeed();
            });
        });

        r.add("capability/charger_null_direction_only", 60, helper -> {
            helper.setBlock(TARGET, NTBlocks.CHARGER.get());
            helper.runAfterDelay(2, () -> {
                BlockPos abs = helper.absolutePos(TARGET);
                ResourceHandler<ItemResource> base = helper.getLevel().getCapability(Capabilities.Item.BLOCK, abs, null);
                helper.assertTrue(base != null, "Charger should expose its item handler for a null direction");
                for (Direction direction : Direction.values()) {
                    ResourceHandler<ItemResource> sided = helper.getLevel().getCapability(Capabilities.Item.BLOCK, abs, direction);
                    helper.assertTrue(sided == null, "Charger has no sided IO, expected null handler for " + direction);
                }
                helper.succeed();
            });
        });

        r.add("capability/bacteria_storage_lookup", 60, helper -> {
            BlockPos mixerPos = new BlockPos(2, 1, 2);
            helper.setBlock(TARGET, NTBlocks.INCUBATOR.get());
            helper.setBlock(mixerPos, NTBlocks.MIXER.get());
            helper.runAfterDelay(2, () -> {
                var incubatorStorage = helper.getLevel().getCapability(NTCapabilities.BacteriaStorage.BLOCK, helper.absolutePos(TARGET), null);
                helper.assertTrue(incubatorStorage != null, "Incubator should expose a bacteria storage");
                var mixerStorage = helper.getLevel().getCapability(NTCapabilities.BacteriaStorage.BLOCK, helper.absolutePos(mixerPos), null);
                helper.assertTrue(mixerStorage == null, "Mixer should not expose a bacteria storage");
                helper.succeed();
            });
        });

        r.add("capability/power_storage_laser_view", 60, helper -> {
            BlockPos chargerPos = new BlockPos(2, 1, 2);
            BlockPos cratePos = new BlockPos(6, 1, 2);
            helper.setBlock(TARGET, NTBlocks.INCUBATOR.get());
            helper.setBlock(chargerPos, NTBlocks.CHARGER.get());
            helper.setBlock(cratePos, NTBlocks.CRATE.get());
            helper.runAfterDelay(2, () -> {
                var incubatorPower = helper.getLevel().getCapability(NTCapabilities.PowerStorage.BLOCK, helper.absolutePos(TARGET), null);
                helper.assertTrue(incubatorPower != null, "Incubator should expose a read-only laser power view");
                var chargerPower = helper.getLevel().getCapability(NTCapabilities.PowerStorage.BLOCK, helper.absolutePos(chargerPos), null);
                helper.assertTrue(chargerPower != null, "Charger should expose a read-only laser power view");
                helper.assertValueEqual(0, chargerPower.getPowerStored(), "idle charger laser power");
                helper.assertTrue(!chargerPower.canFillPower() && !chargerPower.canDrainPower(),
                        "Laser power view should be read-only");
                helper.assertValueEqual(0, chargerPower.tryFillPower(100, false), "read-only view fill attempt");
                var cratePower = helper.getLevel().getCapability(NTCapabilities.PowerStorage.BLOCK, helper.absolutePos(cratePos), null);
                helper.assertTrue(cratePower == null, "Crate should not expose a power storage");
                helper.succeed();
            });
        });

        r.add("capability/hopper_pushes_into_mixer", 200, helper -> {
            BlockPos hopperPos = new BlockPos(3, 1, 4);
            helper.setBlock(TARGET, NTBlocks.MIXER.get());
            helper.setBlock(hopperPos, Blocks.HOPPER.defaultBlockState()
                    .setValue(BlockStateProperties.FACING_HOPPER, Direction.EAST));
            helper.runAfterDelay(1, () -> {
                HopperBlockEntity hopper = helper.getBlockEntity(hopperPos, HopperBlockEntity.class);
                helper.assertTrue(hopper != null, "Hopper BE missing");
                hopper.setItem(0, new ItemStack(Items.COBBLESTONE, 3));
            });
            helper.runAfterDelay(120, () -> {
                MixerBlockEntity mixer = helper.getBlockEntity(TARGET, MixerBlockEntity.class);
                helper.assertTrue(mixer != null, "Mixer BE missing");
                int total = 0;
                for (int i = 0; i < mixer.getItemStackHandler().getSlots(); i++) {
                    ItemStack stack = mixer.getItemStackHandler().getStackInSlot(i);
                    if (stack.is(Items.COBBLESTONE)) {
                        total += stack.getCount();
                    }
                }
                helper.assertValueEqual(3, total, "cobblestone pushed into mixer by hopper");
                HopperBlockEntity hopper = helper.getBlockEntity(hopperPos, HopperBlockEntity.class);
                helper.assertTrue(hopper != null && hopper.isEmpty(), "Hopper should be empty after pushing");
                helper.succeed();
            });
        });

        r.add("capability/sided_handler_respects_slot_filter", 20, helper -> {
            ItemStackHandler inner = new ItemStackHandler(2);
            SidedItemHandler insertOnly = new SidedItemHandler(inner, IOActions.INSERT, IntList.of(0));
            ItemResource cobble = ItemResource.of(Items.COBBLESTONE);
            try (Transaction tx = Transaction.openRoot()) {
                helper.assertValueEqual(0, insertOnly.insert(1, cobble, 4, tx), "insert into unlisted slot");
                helper.assertValueEqual(4, insertOnly.insert(0, cobble, 4, tx), "insert into listed slot");
                helper.assertValueEqual(0, insertOnly.extract(0, cobble, 4, tx), "extract via insert-only handler");
                tx.commit();
            }
            inner.setStackInSlot(1, new ItemStack(Items.COBBLESTONE, 8));
            SidedItemHandler extractOnly = new SidedItemHandler(inner, IOActions.EXTRACT, IntList.of(0));
            try (Transaction tx = Transaction.openRoot()) {
                helper.assertValueEqual(0, extractOnly.extract(1, cobble, 4, tx), "extract from unlisted slot");
                helper.assertValueEqual(4, extractOnly.extract(0, cobble, 4, tx), "extract from listed slot");
                helper.assertValueEqual(0, extractOnly.insert(0, cobble, 4, tx), "insert via extract-only handler");
            }
            helper.succeed();
        });
    }
}
