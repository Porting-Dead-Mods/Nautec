package com.portingdeadmods.nautec.capabilities.fluid;

import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.NotNull;

public class FluidTank extends FluidStacksResourceHandler {
    public FluidTank(int capacity) {
        super(1, capacity);
    }

    public @NotNull FluidStack getFluid() {
        return this.stacks.get(0);
    }

    public void setFluid(@NotNull FluidStack stack) {
        this.stacks.set(0, stack);
        onContentsChanged(0, stack);
    }

    public int getFluidAmount() {
        return getFluid().getAmount();
    }

    public int getCapacity() {
        return this.capacity;
    }

    public boolean isEmpty() {
        return getFluid().isEmpty();
    }

    public int getSpace() {
        return Math.max(0, this.capacity - getFluidAmount());
    }

    public int getTanks() {
        return 1;
    }

    public @NotNull FluidStack getFluidInTank(int tank) {
        return getFluid();
    }

    public int getTankCapacity(int tank) {
        return this.capacity;
    }

    public boolean isFluidValid(@NotNull FluidStack stack) {
        return isValid(0, FluidResource.of(stack));
    }

    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return isFluidValid(stack);
    }

    public int fill(@NotNull FluidStack resource, IFluidHandler.FluidAction action) {
        if (resource.isEmpty()) {
            return 0;
        }
        try (Transaction tx = Transaction.openRoot()) {
            int filled = insert(0, FluidResource.of(resource), resource.getAmount(), tx);
            if (action.execute()) {
                tx.commit();
            }
            return filled;
        }
    }

    public @NotNull FluidStack drain(@NotNull FluidStack resource, IFluidHandler.FluidAction action) {
        if (resource.isEmpty()) {
            return FluidStack.EMPTY;
        }
        try (Transaction tx = Transaction.openRoot()) {
            FluidResource fluidResource = FluidResource.of(resource);
            int drained = extract(0, fluidResource, resource.getAmount(), tx);
            if (action.execute()) {
                tx.commit();
            }
            return drained <= 0 ? FluidStack.EMPTY : fluidResource.toStack(drained);
        }
    }

    public @NotNull FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        FluidResource resource = getResource(0);
        if (resource.isEmpty() || maxDrain <= 0) {
            return FluidStack.EMPTY;
        }
        try (Transaction tx = Transaction.openRoot()) {
            int drained = extract(0, resource, maxDrain, tx);
            if (action.execute()) {
                tx.commit();
            }
            return drained <= 0 ? FluidStack.EMPTY : resource.toStack(drained);
        }
    }
}
