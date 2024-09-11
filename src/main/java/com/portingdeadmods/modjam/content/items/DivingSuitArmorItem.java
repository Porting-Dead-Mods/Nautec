package com.portingdeadmods.modjam.content.items;

import com.portingdeadmods.modjam.data.MJDataComponentsUtils;
import com.portingdeadmods.modjam.registries.MJItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
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
        if (entity instanceof Player player) {
            if (stack == player.getItemBySlot(EquipmentSlot.CHEST) &&
                    player.getItemBySlot(EquipmentSlot.HEAD).is(MJItems.DIVING_HELMET) &&
                    player.getItemBySlot(EquipmentSlot.LEGS).is(MJItems.DIVING_LEGGINGS) &&
                    player.getItemBySlot(EquipmentSlot.FEET).is(MJItems.DIVING_BOOTS)) {

                if (player.isUnderWater()) {
                    if (level.getGameTime() % 20 == 0) {
                        int currentOxygen = MJDataComponentsUtils.getOxygenLevels(stack);
                        if (currentOxygen > 0) {
                            MJDataComponentsUtils.setOxygenLevels(stack, currentOxygen - 1);
                            player.setAirSupply(player.getMaxAirSupply());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(stack.is(MJItems.DIVING_HELMET.get())) {
            tooltipComponents.add(Component.literal("Allows you to see better underwater.").withStyle(ChatFormatting.GRAY));
        }
        if (stack.is(MJItems.DIVING_CHESTPLATE.get())) {
            int oxygen = MJDataComponentsUtils.getOxygenLevels(stack);
            int minutesRemaining = oxygen / 60;
            int secondsRemaining = oxygen % 60;

            int red = (int) (255 * (1 - (oxygen / 300.0)));
            int green = (int) (255 * (oxygen / 300.0));

            int colorHex = (red << 16) | (green << 8);

            tooltipComponents.add(Component.literal(String.format("Oxygen: %d minutes %d seconds", minutesRemaining, secondsRemaining))
                    .withStyle(style -> style.withColor(TextColor.fromRgb(colorHex))));
        }
    }
}
