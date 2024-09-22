package com.portingdeadmods.nautec.compat.jade;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.blockentities.LaserJunctionBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum LaserJunctionComponentProvider implements IBlockComponentProvider {
    INSTANCE;
    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getBlockEntity() instanceof LaserJunctionBlockEntity blockEntity) {
            String inputDirections = blockEntity.getLaserInputsAsString();
            iTooltip.add(Component.literal("Inputs: " + inputDirections));
            String outputDirections = blockEntity.getLaserOutputsAsString();
            iTooltip.add(Component.literal("Outputs: " + outputDirections));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "laser_junction");
    }
}
