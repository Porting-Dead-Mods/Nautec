package com.portingdeadmods.nautec.compat.jade;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.blockentities.AquaticCatalystBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum AquaticCatalystComponentProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getBlockEntity() instanceof AquaticCatalystBlockEntity blockEntity) {
            if (blockEntity.isActive()) {
                iTooltip.add(Component.literal("Status: Active"));
               if (blockEntity.getCurrentRecipe() != null) {
                    iTooltip.add(Component.literal("Processing: ").append(Component.literal(blockEntity.getItemStackHandler().getStackInSlot(0).getCount() +"x ").append(blockEntity.getProcessingItem().getHoverName())));
                    iTooltip.add(Component.literal("Remaining Duration: " + blockEntity.getRemainingDuration() + " ticks"));
                    iTooltip.add(Component.literal("Transferring: " + blockEntity.getPowerToTransfer() + "AP/T"));
                }
            } else {
                iTooltip.add(Component.literal("Status: Inactive"));
            }
        }
    }


    @Override
    public Identifier getUid() {
        return Nautec.rl("aquatic_catalyst");
    }
}
