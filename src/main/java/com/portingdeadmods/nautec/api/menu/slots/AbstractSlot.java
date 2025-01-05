package com.portingdeadmods.nautec.api.menu.slots;

import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public abstract class AbstractSlot {
    protected final int slot;
    public int index;
    protected final int x;
    protected final int y;

    public AbstractSlot(int index, int x, int y) {
        this.slot = index;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
