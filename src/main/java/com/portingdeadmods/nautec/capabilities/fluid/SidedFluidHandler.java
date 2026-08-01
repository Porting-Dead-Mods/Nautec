package com.portingdeadmods.nautec.capabilities.fluid;

import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.utils.Utils;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.IntList;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

public record SidedFluidHandler(ResourceHandler<FluidResource> innerHandler,
                                IOActions action,
                                IntList tanks) implements ResourceHandler<FluidResource> {
    public SidedFluidHandler(ResourceHandler<FluidResource> innerHandler, Pair<IOActions, int[]> actionSlotsPair) {
        this(innerHandler, actionSlotsPair != null ? actionSlotsPair.left() : IOActions.NONE, actionSlotsPair != null ? Utils.intArrayToList(actionSlotsPair.right()) : IntList.of());
    }

    @Override
    public int size() {
        return innerHandler.size();
    }

    @Override
    public FluidResource getResource(int index) {
        return innerHandler.getResource(index);
    }

    @Override
    public long getAmountAsLong(int index) {
        return innerHandler.getAmountAsLong(index);
    }

    @Override
    public long getCapacityAsLong(int index, FluidResource resource) {
        return innerHandler.getCapacityAsLong(index, resource);
    }

    @Override
    public boolean isValid(int index, FluidResource resource) {
        return (action == IOActions.INSERT || action == IOActions.BOTH) && tanks.contains(index) && innerHandler.isValid(index, resource);
    }

    @Override
    public int insert(int index, FluidResource resource, int amount, TransactionContext transaction) {
        return (action == IOActions.INSERT || action == IOActions.BOTH) && tanks.contains(index) ? innerHandler.insert(index, resource, amount, transaction) : 0;
    }

    @Override
    public int extract(int index, FluidResource resource, int amount, TransactionContext transaction) {
        return (action == IOActions.EXTRACT || action == IOActions.BOTH) && tanks.contains(index) ? innerHandler.extract(index, resource, amount, transaction) : 0;
    }
}
