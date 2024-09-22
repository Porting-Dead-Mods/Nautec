package com.portingdeadmods.nautec.capabilities.fluid;

import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.utils.Utils;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.IntList;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public record SidedFluidHandler(IFluidHandler innerHandler,
                                IOActions action,
                                IntList tanks) implements IFluidHandler {
    public SidedFluidHandler(IFluidHandler innerHandler, Pair<IOActions, int[]> actionSlotsPair) {
        this(innerHandler, actionSlotsPair != null ? actionSlotsPair.left() : IOActions.NONE, actionSlotsPair != null ? Utils.intArrayToList(actionSlotsPair.right()) : IntList.of());
    }

    @Override
    public int getTanks() {
        return innerHandler.getTanks();
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {
        return innerHandler.getFluidInTank(tank);
    }

    @Override
    public int getTankCapacity(int tank) {
        return innerHandler.getTankCapacity(tank);
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return action == IOActions.INSERT || action == IOActions.BOTH && tanks.contains(tank) && innerHandler.isFluidValid(tank, stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction fAction) {
        return action == IOActions.INSERT || action == IOActions.BOTH ? innerHandler.fill(resource, fAction) : 0;
    }

    @Override
    public @NotNull FluidStack drain(FluidStack resource, FluidAction fAction) {
        return action == IOActions.EXTRACT || action == IOActions.BOTH ? innerHandler.drain(resource, fAction) : FluidStack.EMPTY;
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, FluidAction fAction) {
        return action == IOActions.EXTRACT || action == IOActions.BOTH ? innerHandler.drain(maxDrain, fAction) : FluidStack.EMPTY;
    }
}