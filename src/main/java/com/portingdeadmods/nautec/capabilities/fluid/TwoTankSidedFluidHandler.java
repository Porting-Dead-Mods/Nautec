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
        return tank == 0 ? primaryHandler.getFluidInTank(0) : secondaryHandler.getFluidInTank(1);
    }

    @Override
    public int getTankCapacity(int tank) {
        return tank == 0 ? primaryHandler.getTankCapacity(0) : secondaryHandler.getTankCapacity(1);
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
        return action == IOActions.EXTRACT || action == IOActions.BOTH ? secondaryHandler.drain(resource, fAction) : FluidStack.EMPTY;
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, FluidAction fAction) {
        return action == IOActions.EXTRACT || action == IOActions.BOTH ? secondaryHandler.drain(maxDrain, fAction) : FluidStack.EMPTY;
    }
}