package com.portingdeadmods.nautec.content.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class AirBottleItem extends Item {
    public AirBottleItem(Properties properties) {
        super(properties
                .food(new FoodProperties.Builder().alwaysEdible().build()));
    }

    @Override
    public SoundEvent getEatingSound() {
        return Items.HONEY_BOTTLE.getEatingSound();
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide) {
            entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 200, 0));
            stack.shrink(1);
            if (entity instanceof Player player) {
                player.addItem(new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("Right click a glass bottle on a bubble column to fill with pressurized air").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.literal("Edible").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return Items.GLASS_BOTTLE.getDefaultInstance();
    }
}
