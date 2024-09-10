package com.portingdeadmods.modjam.content.items;

import com.portingdeadmods.modjam.registries.MJItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class DivingSuitArmorItem extends ArmorItem {

    public DivingSuitArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(entity instanceof Player player) {
           if(player.getItemBySlot(EquipmentSlot.HEAD).is(MJItems.DIVING_HELMET) && player.getItemBySlot(EquipmentSlot.CHEST).is(MJItems.DIVING_CHESTPLATE) && player.getItemBySlot(EquipmentSlot.LEGS).is(MJItems.DIVING_LEGGINGS) && player.getItemBySlot(EquipmentSlot.FEET).is(MJItems.DIVING_BOOTS)) {
               if(player.isUnderWater()) {
                   player.setAirSupply(player.getMaxAirSupply());
               }
           }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(stack.is(MJItems.DIVING_HELMET.get())) {
            tooltipComponents.add(Component.literal("Allows you to see better underwater.").withStyle(ChatFormatting.GRAY));
        }
    }
}
