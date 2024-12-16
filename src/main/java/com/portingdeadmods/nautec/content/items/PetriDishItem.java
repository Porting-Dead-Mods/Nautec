package com.portingdeadmods.nautec.content.items;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.items.IBacteriaItem;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.registries.NTBacterias;
import com.portingdeadmods.nautec.utils.BacteriaHelper;
import com.portingdeadmods.nautec.utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
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
        ResourceKey<Bacteria> bacteriaType = stack.get(NTDataComponents.BACTERIA).bacteria();
        Bacteria bacteria = BacteriaHelper.getBacteria(context.registries(), bacteriaType);
        if (bacteria != null) {
            tooltipComponents.add(Component.literal("Name: ").append(Utils.registryTranslation(bacteriaType)).withStyle(ChatFormatting.GRAY));
            if (bacteriaType != NTBacterias.EMPTY) {
                MutableComponent statsCaption = Component.literal("Stats: ");
                if (Boolean.TRUE.equals(stack.get(NTDataComponents.ANALYZED))) {
                    tooltipComponents.add(statsCaption.withStyle(ChatFormatting.GRAY));
                    for (Component tooltipComponent : bacteria.stats().statsTooltip()) {
                        tooltipComponents.add(Component.literal(" ".repeat(2)).append(tooltipComponent).withStyle(ChatFormatting.GRAY));
                    }
                } else {
                    statsCaption.append("???");
                    tooltipComponents.add(statsCaption.withStyle(ChatFormatting.GRAY));
                }
            }
        }
    }
}
