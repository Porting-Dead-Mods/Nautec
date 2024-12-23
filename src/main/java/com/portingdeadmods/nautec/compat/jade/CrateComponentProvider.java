package com.portingdeadmods.nautec.compat.jade;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.blockentities.CrateBlockEntity;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElementHelper;

public enum CrateComponentProvider implements IBlockComponentProvider {
    INSTANCE;


    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getBlockEntity() instanceof CrateBlockEntity) {
            IElementHelper helper = IElementHelper.get();
            iTooltip.append(helper.item(new ItemStack(NTItems.CROWBAR.get())));
            iTooltip.add(Component.literal("Locked"));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "crate");
    }
}
