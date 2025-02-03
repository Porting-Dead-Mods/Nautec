package com.portingdeadmods.nautec.api.menu.slots;

import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.capabilities.bacteria.IBacteriaStorage;

public class SlotBacteriaStorage extends AbstractSlot {
    private static final int WIDTH = 18;
    private static final int HEIGHT = 18;
    private final IBacteriaStorage bacteriaStorage;

    public SlotBacteriaStorage(IBacteriaStorage bacteriaStorage, int index, int x, int y) {
        super(index, x, y);
        this.bacteriaStorage = bacteriaStorage;
    }

    public BacteriaInstance getBacteriaInstance() {
        return bacteriaStorage.getBacteria(slot);
    }

    public IBacteriaStorage getBacteriaStorage() {
        return bacteriaStorage;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }
}
