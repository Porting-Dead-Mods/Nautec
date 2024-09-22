package com.portingdeadmods.modjam.api.blockentities;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.capabilities.IOActions;
import com.portingdeadmods.modjam.capabilities.fluid.SidedFluidHandler;
import com.portingdeadmods.modjam.capabilities.item.SidedItemHandler;
import com.portingdeadmods.modjam.capabilities.power.IPowerStorage;
import com.portingdeadmods.modjam.capabilities.power.PowerStorage;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
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
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class ContainerBlockEntity extends BlockEntity {
    private @Nullable ItemStackHandler itemHandler;
    private @Nullable FluidTank fluidTank;
    private @Nullable FluidTank secondaryFluidTank;
    private @Nullable PowerStorage powerStorage;

    public ContainerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public void commonTick() {
    }

    public IItemHandler getItemHandler() {
        return itemHandler;
    }

    public IFluidHandler getFluidHandler() {
        return fluidTank;
    }

    public IFluidHandler getSecondaryFluidHandler() {
        return secondaryFluidTank;
    }

    public IPowerStorage getPowerStorage() {
        return powerStorage;
    }

    protected ItemStackHandler getItemStackHandler() {
        return itemHandler;
    }

    protected FluidTank getFluidTank() {
        return fluidTank;
    }

    protected FluidTank getSecondaryFluidTank() {
        return fluidTank;
    }

    protected PowerStorage getEnergyStorageImpl() {
        return powerStorage;
    }

    @Override
    protected final void loadAdditional(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider provider) {
        super.loadAdditional(nbt, provider);
        if (this.getFluidTank() != null)
            this.getFluidTank().readFromNBT(provider, nbt);
        if (this.getSecondaryFluidTank() != null)
            this.getSecondaryFluidTank().readFromNBT(provider, nbt.getCompound("secondary_fluid"));
        if (this.getItemStackHandler() != null)
            this.getItemStackHandler().deserializeNBT(provider, nbt.getCompound("itemhandler"));
        if (this.getEnergyStorageImpl() != null)
            this.getEnergyStorageImpl().deserializeNBT(provider, nbt.getCompound("power_storage"));
        loadData(nbt, provider);
    }

    @Override
    protected final void saveAdditional(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider provider) {
        super.saveAdditional(nbt, provider);
        if (getFluidTank() != null)
            getFluidTank().writeToNBT(provider, nbt);
        if (getSecondaryFluidTank() != null) {
            CompoundTag tag = new CompoundTag();
            getSecondaryFluidTank().writeToNBT(provider, tag);
            nbt.put("secondary_fluid", tag);
        }
        if (getItemStackHandler() != null)
            nbt.put("itemhandler", getItemStackHandler().serializeNBT(provider));
        if (getEnergyStorageImpl() != null)
            nbt.put("power_storage", getEnergyStorageImpl().serializeNBT(provider));
        saveData(nbt, provider);
    }

    protected void loadData(CompoundTag tag, HolderLookup.Provider provider) {
    }

    protected void saveData(CompoundTag tag, HolderLookup.Provider provider) {
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

    protected final void addItemHandler(int slots, int slotLimit, BiPredicate<Integer, ItemStack> validation) {
        ModJam.LOGGER.debug("itemhandler slots: {}", slots);
        this.itemHandler = new ItemStackHandler(slots) {
            @Override
            protected void onContentsChanged(int slot) {
                update();
                setChanged();
                onItemsChanged(slot);
                invalidateCapabilities();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return validation.test(slot, stack);
            }

            @Override
            public int getSlotLimit(int slot) {
                return slotLimit;
            }

            @Override
            public int getSlots() {
                return slots;
            }
        };
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
            protected void onContentsChanged() {
                update();
                setChanged();
                onFluidChanged();
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return validation.test(stack);
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
                setChanged();
                ContainerBlockEntity.this.onPowerChanged();
            }
        };
        this.powerStorage.setPowerCapacity(powerCapacity);
    }

    private void update() {
        if (!level.isClientSide()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    protected void onItemsChanged(int slot) {
    }

    protected void onFluidChanged() {
    }

    public void onPowerChanged() {
    }

    public void onHeatChanged() {
    }

    public void drop() {
        ItemStack[] stacks = getItemHandlerStacks();
        SimpleContainer inventory = new SimpleContainer(stacks);
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public @Nullable ItemStack[] getItemHandlerStacks() {
        IItemHandler itemStackHandler = getItemHandler();

        if (itemStackHandler == null) return null;

        ItemStack[] itemStacks = new ItemStack[itemStackHandler.getSlots()];
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemStacks[i] = itemStackHandler.getStackInSlot(i);
        }
        return itemStacks;
    }

    public List<ItemStack> getItemHandlerStacksList() {
        IItemHandler itemStackHandler = getItemHandler();

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

            ModJam.LOGGER.warn("Sided io for non facing block");
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

    public IItemHandler getItemHandlerOnSide(Direction direction) {
        return getHandlerOnSide(
                Capabilities.ItemHandler.BLOCK,
                SidedItemHandler::new,
                direction,
                getItemHandler()
        );
    }

    public IFluidHandler getFluidHandlerOnSide(Direction direction) {
        return getHandlerOnSide(
                Capabilities.FluidHandler.BLOCK,
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

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        super.onDataPacket(net, pkt, lookupProvider);
    }

    @FunctionalInterface
    public interface SidedHandlerSupplier<T> {
        T get(T handler, Pair<IOActions, int[]> supportedActions);
    }
}
