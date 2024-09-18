package com.portingdeadmods.modjam.compat.jade;

import com.portingdeadmods.modjam.content.blockentities.CrateBlockEntity;
import com.portingdeadmods.modjam.data.MJDataComponents;
import com.portingdeadmods.modjam.registries.MJItems;
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
        if(blockAccessor.getBlockEntity() instanceof CrateBlockEntity blockEntity) {
            if(!blockEntity.components().has(MJDataComponents.OPEN.get())) {
                IElementHelper helper = IElementHelper.get();
                iTooltip.append(helper.item(new ItemStack(MJItems.CROWBAR.get())));
                iTooltip.add(Component.literal("Locked"));
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath("modjam", "crate");
    }
}
