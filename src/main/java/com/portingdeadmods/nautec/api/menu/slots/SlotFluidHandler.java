package com.portingdeadmods.nautec.api.menu.slots;

import com.portingdeadmods.nautec.api.client.screen.FluidTankRenderer;
import com.portingdeadmods.nautec.capabilities.fluid.FluidTank;
import net.neoforged.neoforge.fluids.FluidStack;

public class SlotFluidHandler extends AbstractSlot {
    private final FluidTank fluidHandler;
    private final int width;
    private final int height;
    private final FluidTankRenderer renderer;

    public SlotFluidHandler(FluidTank fluidHandler, int index, int x, int y, int width, int height) {
        super(index, x, y);
        this.fluidHandler = fluidHandler;
        this.width = width;
        this.height = height;
        this.renderer = new FluidTankRenderer(fluidHandler.getTankCapacity(0), true, width, height);
    }

    public FluidTankRenderer getRenderer() {
        return renderer;
    }

    public FluidStack getFluidStack() {
        return fluidHandler.getFluidInTank(slot);
    }

    public int getFluidCapacity() {
        return fluidHandler.getTankCapacity(slot);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public FluidTank getFluidHandler() {
        return fluidHandler;
    }
}
