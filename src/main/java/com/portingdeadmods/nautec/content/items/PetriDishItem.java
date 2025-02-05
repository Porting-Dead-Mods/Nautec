package com.portingdeadmods.nautec.content.items;

import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.api.items.IBacteriaItem;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.registries.NTBacterias;
import com.portingdeadmods.nautec.utils.BacteriaHelper;
import com.portingdeadmods.nautec.utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class PetriDishItem extends Item implements IBacteriaItem {
    public PetriDishItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        BacteriaInstance bacteria = stack.get(NTDataComponents.BACTERIA).bacteriaInstance();
        tooltipComponents.addAll(bacteria.getExpandableTooltip(tooltipFlag.hasShiftDown(), tooltipFlag.hasControlDown()));
    }
}
