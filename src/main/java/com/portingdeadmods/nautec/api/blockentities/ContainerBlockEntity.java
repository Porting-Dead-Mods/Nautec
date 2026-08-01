package com.portingdeadmods.nautec.api.blockentities;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.capabilities.bacteria.BacteriaStorage;
import com.portingdeadmods.nautec.capabilities.bacteria.IBacteriaStorage;
import com.portingdeadmods.nautec.capabilities.fluid.FluidTank;
import com.portingdeadmods.nautec.capabilities.fluid.SidedFluidHandler;
import com.portingdeadmods.nautec.capabilities.item.ItemStackHandler;
import com.portingdeadmods.nautec.capabilities.item.SidedItemHandler;
import com.portingdeadmods.nautec.capabilities.power.IPowerStorage;
import com.portingdeadmods.nautec.capabilities.power.PowerStorage;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public abstract class ContainerBlockEntity extends BlockEntity {
    private @Nullable ItemStackHandler itemHandler;
    private @Nullable FluidTank fluidTank;
    private @Nullable FluidTank secondaryFluidTank;
    private @Nullable PowerStorage powerStorage;
    private @Nullable BacteriaStorage bacteriaStorage;

    public ContainerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public void commonTick() {
    }

    public ResourceHandler<ItemResource> getItemHandler() {
        return itemHandler;
    }

    public ResourceHandler<FluidResource> getFluidHandler() {
        return fluidTank;
    }

    public ResourceHandler<FluidResource> getSecondaryFluidHandler() {
        return secondaryFluidTank;
    }

    public IPowerStorage getPowerStorage() {
        return powerStorage;
    }

    public IBacteriaStorage getBacteriaStorage() {
        return bacteriaStorage;
    }

    public ItemStackHandler getItemStackHandler() {
        return itemHandler;
    }

    public FluidTank getFluidTank() {
        return fluidTank;
    }

    public FluidTank getSecondaryFluidTank() {
        return secondaryFluidTank;
    }

    protected PowerStorage getPowerStorageImpl() {
        return powerStorage;
    }

    protected BacteriaStorage getBacteriaStorageImpl() {
        return bacteriaStorage;
    }

    @Override
    protected final void loadAdditional(@NotNull ValueInput in) {
        super.loadAdditional(in);
        if (this.getFluidTank() != null)
            in.child("fluid_tank").ifPresent(this.getFluidTank()::deserialize);
        if (this.getSecondaryFluidTank() != null)
            in.child("secondary_fluid").ifPresent(this.getSecondaryFluidTank()::deserialize);
        if (this.getItemStackHandler() != null)
            in.child("itemhandler").ifPresent(this.getItemStackHandler()::deserialize);
        if (this.getPowerStorageImpl() != null)
            in.child("power_storage").ifPresent(this.getPowerStorageImpl()::deserialize);
        if (this.getBacteriaStorageImpl() != null)
            in.child("bacteria_storage").ifPresent(this.getBacteriaStorageImpl()::deserialize);
        loadData(in);
    }

    @Override
    protected final void saveAdditional(@NotNull ValueOutput out) {
        super.saveAdditional(out);
        if (getFluidTank() != null)
            getFluidTank().serialize(out.child("fluid_tank"));
        if (getSecondaryFluidTank() != null)
            getSecondaryFluidTank().serialize(out.child("secondary_fluid"));
        if (getItemStackHandler() != null)
            getItemStackHandler().serialize(out.child("itemhandler"));
        if (getPowerStorageImpl() != null)
            getPowerStorageImpl().serialize(out.child("power_storage"));
        if (getBacteriaStorageImpl() != null)
            getBacteriaStorageImpl().serialize(out.child("bacteria_storage"));
        saveData(out);
    }

    protected void loadData(ValueInput in) {
    }

    protected void saveData(ValueOutput out) {
    }

    protected final void addItemHandler(int slots) {
        addItemHandler(slots, (slot, itemStack) -> true);
    }

    protected final void addItemHandler(int slots, int slotLimit) {
        addItemHandler(slots, slotLimit, (slot, itemStack) -> true);
    }

    protected final void addItemHandler(int slots, BiPredicate<Integer, ItemStack> validation) {
        addItemHandler(slots, 64, validation);
    }

    protected final void addItemHandler(int slots, UnaryOperator<Integer> slotLimit, BiPredicate<Integer, ItemStack> validation) {
        addItemHandlerInternal(slots, slotLimit, validation);
    }

    protected final void addItemHandler(int slots, int slotLimit, BiPredicate<Integer, ItemStack> validation) {
        addItemHandlerInternal(slots, slot -> slotLimit, validation);
    }

    private void addItemHandlerInternal(int slots, UnaryOperator<Integer> slotLimit, BiPredicate<Integer, ItemStack> validation) {
        this.itemHandler = new ItemStackHandler(slots) {
            @Override
            protected void onContentsChanged(int slot, ItemStack stack) {
                super.onContentsChanged(slot, stack);
                update();
                onItemsChanged(slot);
                invalidateCapabilities();
            }

            @Override
            public boolean isValid(int slot, @NotNull ItemResource resource) {
                return validation.test(slot, resource.toStack());
            }

            @Override
            protected int getCapacity(int slot, ItemResource resource) {
                int limit = slotLimit.apply(slot);
                return resource.isEmpty() ? limit : Math.min(limit, resource.getMaxStackSize());
            }
        };
    }

    public ItemStack forceExtractItem(int slot, int amount, boolean simulate) {
        if (amount == 0 || itemHandler == null)
            return ItemStack.EMPTY;

        ItemStack existing = itemHandler.getStackInSlot(slot);

        if (existing.isEmpty())
            return ItemStack.EMPTY;

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.getCount() <= toExtract) {
            if (!simulate) {
                itemHandler.setStackInSlot(slot, ItemStack.EMPTY);
                onItemsChanged(slot);
                return existing;
            } else {
                return existing.copy();
            }
        } else {
            if (!simulate) {
                itemHandler.setStackInSlot(slot, existing.copyWithCount(existing.getCount() - toExtract));
                onItemsChanged(slot);
            }

            return existing.copyWithCount(toExtract);
        }
    }

    public ItemStack forceInsertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty() || itemHandler == null)
            return ItemStack.EMPTY;

        ItemStack existing = itemHandler.getStackInSlot(slot);

        int limit = Math.min(itemHandler.getSlotLimit(slot), stack.getMaxStackSize());

        if (!existing.isEmpty()) {
            if (!ItemStack.isSameItemSameComponents(stack, existing))
                return stack;

            limit -= existing.getCount();
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate) {
            if (existing.isEmpty()) {
                itemHandler.setStackInSlot(slot, reachedLimit ? stack.copyWithCount(limit) : stack);
            } else {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }
            onItemsChanged(slot);
        }

        return reachedLimit ? stack.copyWithCount(stack.getCount() - limit) : ItemStack.EMPTY;
    }


    protected final void addFluidTank(int capacityInMb) {
        addFluidTank(capacityInMb, ignored -> true);
    }

    protected final void addSecondaryFluidTank(int capacityInMb) {
        addSecondaryFluidTank(capacityInMb, ignored -> true);
    }

    protected final void addSecondaryFluidTank(int capacityInMb, Predicate<FluidStack> validation) {
        addFluidTank(capacityInMb, validation, true);
    }

    protected final void addFluidTank(int capacityInMn, Predicate<FluidStack> validation) {
        addFluidTank(capacityInMn, validation, false);
    }

    protected final void addFluidTank(int capacityInMb, Predicate<FluidStack> validation, boolean secondary) {
        FluidTank tank = new FluidTank(capacityInMb) {
            @Override
            protected void onContentsChanged(int index, FluidStack stack) {
                super.onContentsChanged(index, stack);
                update();
                onFluidChanged();
            }

            @Override
            public boolean isValid(int index, @NotNull FluidResource resource) {
                return validation.test(resource.toStack(1));
            }
        };
        if (!secondary) {
            this.fluidTank = tank;
        } else {
            this.secondaryFluidTank = tank;
        }
    }

    protected final void addPowerStorage(int powerCapacity) {
        this.powerStorage = new PowerStorage() {
            @Override
            public void onEnergyChanged() {
                update();
                ContainerBlockEntity.this.onPowerChanged();
            }
        };
        this.powerStorage.setPowerCapacity(powerCapacity);
    }

    protected final void addBacteriaStorage(int slots) {
        this.bacteriaStorage = new BacteriaStorage(slots) {
            @Override
            public void onBacteriaChanged(int slot) {
                update();
                ContainerBlockEntity.this.onBacteriaChanged(slot);
            }
        };
    }

    public void update() {
        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    protected void onItemsChanged(int slot) {
    }

    protected void onFluidChanged() {
    }

    public void onPowerChanged() {
    }

    public void onBacteriaChanged(int slot) {
    }

    public void drop() {
        ItemStack[] stacks = getItemHandlerStacks();
        if (stacks != null) {
            SimpleContainer inventory = new SimpleContainer(stacks);
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
    }

    public @Nullable ItemStack[] getItemHandlerStacks() {
        ItemStackHandler itemStackHandler = getItemStackHandler();

        if (itemStackHandler == null) return null;

        ItemStack[] itemStacks = new ItemStack[itemStackHandler.getSlots()];
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemStacks[i] = itemStackHandler.getStackInSlot(i);
        }
        return itemStacks;
    }

    public List<ItemStack> getItemHandlerStacksList() {
        ItemStackHandler itemStackHandler = getItemStackHandler();

        if (itemStackHandler == null) return null;

        int slots = itemStackHandler.getSlots();
        ObjectList<ItemStack> itemStacks = new ObjectArrayList<>(slots);
        for (int i = 0; i < slots; i++) {
            ItemStack stack = itemStackHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                itemStacks.add(stack);
            }
        }
        return itemStacks;
    }

    public <T> T getHandlerOnSide(BlockCapability<T, @Nullable Direction> capability, SidedHandlerSupplier<T> handlerSupplier, Direction direction, T baseHandler) {
        if (direction == null) {
            return baseHandler;
        }

        Map<Direction, Pair<IOActions, int[]>> ioPorts = getSidedInteractions(capability);
        if (ioPorts.containsKey(direction)) {

            if (direction == Direction.UP || direction == Direction.DOWN) {
                return handlerSupplier.get(baseHandler, ioPorts.get(direction));
            }

            if (this.getBlockState().hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
                Direction localDir = this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);

                return getCapOnSide(handlerSupplier, direction, baseHandler, ioPorts, localDir);
            }

            if (getBlockState().hasProperty(BlockStateProperties.FACING)) {
                Direction localDir = this.getBlockState().getValue(BlockStateProperties.FACING);

                return getCapOnSide(handlerSupplier, direction, baseHandler, ioPorts, localDir);
            }

            Nautec.LOGGER.warn("Sided io for non facing block");
        }

        return null;
    }

    @Nullable
    private <T> T getCapOnSide(SidedHandlerSupplier<T> handlerSupplier, Direction direction, T baseHandler, Map<Direction, Pair<IOActions, int[]>> ioPorts, Direction localDir) {
        return switch (localDir) {
            case NORTH -> handlerSupplier.get(baseHandler, ioPorts.get(direction.getOpposite()));
            case EAST -> handlerSupplier.get(baseHandler, ioPorts.get(direction.getClockWise()));
            case SOUTH -> handlerSupplier.get(baseHandler, ioPorts.get(direction));
            case WEST -> handlerSupplier.get(baseHandler, ioPorts.get(direction.getCounterClockWise()));
            default -> null;
        };
    }

    public ResourceHandler<ItemResource> getItemHandlerOnSide(Direction direction) {
        return getHandlerOnSide(
                Capabilities.Item.BLOCK,
                SidedItemHandler::new,
                direction,
                getItemHandler()
        );
    }

    public ResourceHandler<FluidResource> getFluidHandlerOnSide(Direction direction) {
        return getHandlerOnSide(
                Capabilities.Fluid.BLOCK,
                SidedFluidHandler::new,
                direction,
                getFluidHandler()
        );
    }

    /**
     * Get the input/output config for the blockenitity.
     * If directions are not defined in the map, they are assumed to be {@link IOActions#NONE} and do not affect any slot.
     *
     * @return Map of directions that each map to a pair that defines the IOAction as well as the tanks that are affected. Return an empty map if you do not have an itemhandler
     */
    public abstract <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability);

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return saveWithoutMetadata(provider);
    }

    @FunctionalInterface
    public interface SidedHandlerSupplier<T> {
        T get(T handler, Pair<IOActions, int[]> supportedActions);
    }
}
