package com.portingdeadmods.nautec.compat.jade;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.blockentities.MixerBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.fluids.FluidStack;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum MixerComponentProvider implements IBlockComponentProvider {
    INSTANCE;


    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getBlockEntity() instanceof MixerBlockEntity blockEntity) {
            FluidStack inputFluid = blockEntity.getInputFluid();
            if (!inputFluid.isEmpty()) {
                iTooltip.add(Component.literal("Fluid Input: ")
                        .append(inputFluid.getHoverName())
                        .append(" - " + blockEntity.getInputFluidAmount() + " mB"));
            }

            FluidStack outputFluid = blockEntity.getOutputFluid();
            if (!outputFluid.isEmpty()) {
                iTooltip.add(Component.literal("Fluid Output: ")
                        .append(outputFluid.getHoverName())
                        .append(" - " + blockEntity.getOutputFluidAmount() + " mB"));
            }

            int duration = blockEntity.getDuration();
            int maxDuration = blockEntity.getMaxDuration();
            if (duration > 0 && maxDuration > 0) {
                iTooltip.add(Component.literal("Mixing Progress: " + duration + " / " + maxDuration + " ticks"));
            }

            iTooltip.add(Component.literal("Energy: " + blockEntity.getPower() + " AP"));
        }
    }

    @Override
    public Identifier getUid() {
        return Nautec.rl("mixer");
    }
}
