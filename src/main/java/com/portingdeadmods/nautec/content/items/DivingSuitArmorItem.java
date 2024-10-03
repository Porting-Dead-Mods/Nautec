package com.portingdeadmods.nautec.content.items;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.data.NTDataComponentsUtils;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DivingSuitArmorItem extends ArmorItem {

    public DivingSuitArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties.durability(type.getDurability(5)));
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player) {
            if (stack == player.getItemBySlot(EquipmentSlot.CHEST) &&
                    player.getItemBySlot(EquipmentSlot.HEAD).is(NTItems.DIVING_HELMET) &&
                    player.getItemBySlot(EquipmentSlot.LEGS).is(NTItems.DIVING_LEGGINGS) &&
                    player.getItemBySlot(EquipmentSlot.FEET).is(NTItems.DIVING_BOOTS)) {

                if (player.isUnderWater() && !player.isCreative() && !player.isSpectator()) {
                    if (level.getGameTime() % 20 == 0) {
                        int currentOxygen = NTDataComponentsUtils.getOxygenLevels(stack);
                        if (currentOxygen > 0) {
                            NTDataComponentsUtils.setOxygenLevels(stack, currentOxygen - 1);
                            player.setAirSupply(player.getMaxAirSupply());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(stack.is(NTItems.DIVING_HELMET.get())) {
            tooltipComponents.add(Component.literal("Allows you to see better underwater.").withStyle(ChatFormatting.GRAY));
        }

        if (stack.is(NTItems.DIVING_CHESTPLATE.get())) {
            int oxygen = NTDataComponentsUtils.getOxygenLevels(stack);
            int minutesRemaining = oxygen / 60;
            int secondsRemaining = oxygen % 60;

            int red = (int) (255 * (1 - (oxygen / 600.0)));
            int green = (int) (255 * (oxygen / 600.0));

            int colorHex = (red << 16) | (green << 8);

            tooltipComponents.add(Component.literal(String.format("Oxygen: %d minutes %d seconds", minutesRemaining, secondsRemaining))
                    .withStyle(style -> style.withColor(TextColor.fromRgb(colorHex))));
        }
    }

    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return stack.is(NTItems.DIVING_HELMET.get()) ? ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"textures/example/diving_suit.png") : super.getArmorTexture(stack, entity, slot, layer, innerModel);
    }
}
