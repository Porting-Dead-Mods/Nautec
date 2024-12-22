package com.portingdeadmods.nautec.compat.jade;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.blockentities.MixerBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
            // Display the input fluid and its amount
            FluidStack inputFluid = blockEntity.getInputFluid();
            iTooltip.add(Component.literal("Fluid Input: ")
                    .append(Component.translatable(inputFluid.getFluid().getFluidType().getDescriptionId()))
                    .append(" - " + blockEntity.getInputFluidAmount() + " mB"));

            // Display the output fluid and its amount
            FluidStack outputFluid = blockEntity.getOutputFluid();
            iTooltip.add(Component.literal("Fluid Output: ")
                    .append(Component.translatable(outputFluid.getFluid().getFluidType().getDescriptionId()))
                    .append(" - " + blockEntity.getOutputFluidAmount() + " mB"));


            // Display the current duration and max duration of the mixing process
            int duration = blockEntity.getDuration();
            int maxDuration = blockEntity.getMaxDuration();
            iTooltip.add(Component.literal("Mixing Progress: " + duration + " / " + maxDuration + " ticks"));

            iTooltip.add(Component.literal("Energy: " + blockEntity.getPower() + " AP"));

        }
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "mixer");
    }
}
