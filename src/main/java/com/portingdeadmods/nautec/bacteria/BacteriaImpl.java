package com.portingdeadmods.nautec.bacteria;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public record BacteriaImpl(
        ResourceLocation id,
        Item type,
        float growthRate,
        float mutationResistance,
        float productionRate,
        int lifespan,
        int color) implements Bacteria {

    @Override
    public byte getAlpha() {
        return (byte) ((color >> 24) & 0xFF);
    }

    @Override
    public byte getRed() {
        return (byte) ((color >> 16) & 0xFF);
    }

    @Override
    public byte getGreen() {
        return (byte) ((color >> 8) & 0xFF);
    }

    @Override
    public byte getBlue() {
        return (byte) (color & 0xFF);
    }
}
