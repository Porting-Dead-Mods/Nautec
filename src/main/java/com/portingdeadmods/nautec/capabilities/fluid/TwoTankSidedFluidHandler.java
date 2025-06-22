package com.portingdeadmods.nautec.capabilities.fluid;

import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.utils.Utils;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.IntList;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public record TwoTankSidedFluidHandler(IFluidHandler primaryHandler,
                                       IFluidHandler secondaryHandler,
                                       IOActions action,
                                       IntList tanks) implements IFluidHandler {
    public TwoTankSidedFluidHandler(IFluidHandler primaryHandler, IFluidHandler secondaryHandler, Pair<IOActions, int[]> actionSlotsPair) {
        this(primaryHandler, secondaryHandler, actionSlotsPair != null ? actionSlotsPair.left() : IOActions.NONE, actionSlotsPair != null ? Utils.intArrayToList(actionSlotsPair.right()) : IntList.of());
    }

    @Override
    public int getTanks() {
        return 2;
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {
        return tank == 0 ? primaryHandler.getFluidInTank(0) : secondaryHandler.getFluidInTank(0);
    }

    @Override
    public int getTankCapacity(int tank) {
        return tank == 0 ? primaryHandler.getTankCapacity(0) : secondaryHandler.getTankCapacity(0);
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return (action == IOActions.INSERT || action == IOActions.BOTH) && tanks.contains(tank)
                && (tank == 0 ? primaryHandler.isFluidValid(0, stack) : secondaryHandler.isFluidValid(0, stack));
    }

    @Override
    public int fill(FluidStack resource, FluidAction fAction) {
        return (action == IOActions.INSERT || action == IOActions.BOTH) ? primaryHandler.fill(resource, fAction) : 0;
    }

    @Override
    public @NotNull FluidStack drain(FluidStack resource, FluidAction fAction) {
        if (action != IOActions.EXTRACT && action != IOActions.BOTH) {
            return FluidStack.EMPTY;
        }
        
        // Check if we have enough fluid to drain the requested amount
        FluidStack currentFluid = secondaryHandler.getFluidInTank(0);
        if (currentFluid.isEmpty() || !currentFluid.is(resource.getFluid()) || 
            currentFluid.getAmount() < resource.getAmount()) {
            return FluidStack.EMPTY;
        }
        
        return secondaryHandler.drain(resource, fAction);
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, FluidAction fAction) {
        if (action != IOActions.EXTRACT && action != IOActions.BOTH) {
            return FluidStack.EMPTY;
        }
        
        // Check if we have enough fluid to drain the requested amount
        FluidStack currentFluid = secondaryHandler.getFluidInTank(0);
        if (currentFluid.isEmpty() || currentFluid.getAmount() < maxDrain) {
            return FluidStack.EMPTY;
        }
        
        return secondaryHandler.drain(maxDrain, fAction);
    }
}