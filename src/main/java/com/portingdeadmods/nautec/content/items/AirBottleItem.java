package com.portingdeadmods.nautec.content.items;

import com.portingdeadmods.nautec.data.NTDataComponentsUtils;
import com.portingdeadmods.nautec.utils.Tooltips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
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
                int currentLevel = NTDataComponentsUtils.getOxygenLevels(player.getItemBySlot(EquipmentSlot.CHEST));
                if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof DivingSuitArmorItem && currentLevel < 100) {
                    NTDataComponentsUtils.setOxygenLevels(player.getItemBySlot(EquipmentSlot.CHEST), currentLevel + 20);
                }
            }
        }
        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Tooltips.trans(tooltipComponents, "nautec.air_bottle.fill", ChatFormatting.GRAY);
        Tooltips.trans(tooltipComponents,"nautec.air_bottle.craft_msg", ChatFormatting.GRAY);
        Tooltips.trans(tooltipComponents,"nautec.edible",ChatFormatting.GRAY, ChatFormatting.ITALIC);
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return Items.GLASS_BOTTLE.getDefaultInstance();
    }
}
