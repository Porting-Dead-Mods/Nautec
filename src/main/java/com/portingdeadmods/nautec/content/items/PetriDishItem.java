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
        ResourceKey<Bacteria> bacteriaType = stack.get(NTDataComponents.BACTERIA).bacteriaInstance().getBacteria();
        BacteriaInstance bacteria = stack.get(NTDataComponents.BACTERIA).bacteriaInstance();
        if (bacteria != null) {
            tooltipComponents.add(Component.literal("Name: ").append(Utils.registryTranslation(bacteriaType)).withStyle(ChatFormatting.WHITE));
            if (bacteriaType != NTBacterias.EMPTY) {
                MutableComponent statsCaption = Component.literal("Stats: ").withStyle(ChatFormatting.WHITE);
                if (Boolean.TRUE.equals(stack.get(NTDataComponents.ANALYZED))) {
                    if (!tooltipFlag.hasShiftDown()) {
                        statsCaption
                                .append(Component.literal("<").withStyle(ChatFormatting.WHITE))
                                .append(Component.literal("Shift").withStyle(ChatFormatting.YELLOW))
                                .append(Component.literal(">").withStyle(ChatFormatting.WHITE))
                                .append(Component.literal(" and ").withStyle(ChatFormatting.WHITE))
                                .append(Component.literal("<").withStyle(ChatFormatting.WHITE))
                                .append(Component.literal("Control").withStyle(ChatFormatting.YELLOW))
                                .append(Component.literal(">").withStyle(ChatFormatting.WHITE));
                    }
                    tooltipComponents.add(statsCaption);
                    if (tooltipFlag.hasShiftDown() && tooltipFlag.hasControlDown()) {
                        for (Component tooltipComponent : bacteria.getStats().statsTooltipWithMutatorValues()) {
                            tooltipComponents.add(Component.literal(" ".repeat(2)).append(tooltipComponent));
                        }
                    }
                    if (tooltipFlag.hasShiftDown() && !tooltipFlag.hasControlDown()) {
                        for (Component tooltipComponent : bacteria.getStats().statsTooltip()) {
                            tooltipComponents.add(Component.literal(" ".repeat(2)).append(tooltipComponent));
                        }
                    }
                } else {
                    statsCaption.append(Component.literal("???").withStyle(ChatFormatting.YELLOW));
                    tooltipComponents.add(statsCaption);
                }
            }
        }
    }
}
