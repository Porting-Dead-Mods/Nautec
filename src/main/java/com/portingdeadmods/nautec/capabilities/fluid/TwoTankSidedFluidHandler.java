package com.portingdeadmods.nautec.capabilities.fluid;

import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.utils.Utils;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.IntList;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

public record TwoTankSidedFluidHandler(ResourceHandler<FluidResource> primaryHandler,
                                       ResourceHandler<FluidResource> secondaryHandler,
                                       IOActions action,
                                       IntList tanks) implements ResourceHandler<FluidResource> {
    public TwoTankSidedFluidHandler(ResourceHandler<FluidResource> primaryHandler, ResourceHandler<FluidResource> secondaryHandler, Pair<IOActions, int[]> actionSlotsPair) {
        this(primaryHandler, secondaryHandler, actionSlotsPair != null ? actionSlotsPair.left() : IOActions.NONE, actionSlotsPair != null ? Utils.intArrayToList(actionSlotsPair.right()) : IntList.of());
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public FluidResource getResource(int index) {
        return index == 0 ? primaryHandler.getResource(0) : secondaryHandler.getResource(0);
    }

    @Override
    public long getAmountAsLong(int index) {
        return index == 0 ? primaryHandler.getAmountAsLong(0) : secondaryHandler.getAmountAsLong(0);
    }

    @Override
    public long getCapacityAsLong(int index, FluidResource resource) {
        return index == 0 ? primaryHandler.getCapacityAsLong(0, resource) : secondaryHandler.getCapacityAsLong(0, resource);
    }

    @Override
    public boolean isValid(int index, FluidResource resource) {
        return (action == IOActions.INSERT || action == IOActions.BOTH) && tanks.contains(index)
                && (index == 0 ? primaryHandler.isValid(0, resource) : secondaryHandler.isValid(0, resource));
    }

    @Override
    public int insert(int index, FluidResource resource, int amount, TransactionContext transaction) {
        if (action != IOActions.INSERT && action != IOActions.BOTH) {
            return 0;
        }
        return index == 0 ? primaryHandler.insert(0, resource, amount, transaction) : 0;
    }

    @Override
    public int extract(int index, FluidResource resource, int amount, TransactionContext transaction) {
        if (action != IOActions.EXTRACT && action != IOActions.BOTH) {
            return 0;
        }
        return index == 1 ? secondaryHandler.extract(0, resource, amount, transaction) : 0;
    }
}
