package com.portingdeadmods.nautec.content.items;

import com.portingdeadmods.nautec.content.items.tiers.NTArmorMaterials;
import com.portingdeadmods.nautec.data.NTDataComponentsUtils;
import com.portingdeadmods.nautec.registries.NTItems;
import com.portingdeadmods.nautec.utils.Tooltips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class DivingSuitArmorItem extends Item {

    public DivingSuitArmorItem(ArmorType type, Properties properties) {
        super(applyArmor(type, properties));
    }

    private static Properties applyArmor(ArmorType type, Properties properties) {
        properties = properties.stacksTo(1).humanoidArmor(NTArmorMaterials.DIVING_SUIT, type);
        if (type == ArmorType.HELMET) {
            properties = properties.component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.HEAD)
                    .setEquipSound(NTArmorMaterials.DIVING_SUIT.equipSound())
                    .setAsset(NTArmorMaterials.DIVING_SUIT_HELMET_ASSET)
                    .build());
        }
        return properties;
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot) {
        if (entity instanceof Player player) {
            if (hasFullArmorSet(stack, player)) {
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

    private static boolean hasFullArmorSet(ItemStack stack, Player player) {
        return stack == player.getItemBySlot(EquipmentSlot.CHEST) &&
                player.getItemBySlot(EquipmentSlot.HEAD).is(NTItems.DIVING_HELMET) &&
                player.getItemBySlot(EquipmentSlot.LEGS).is(NTItems.DIVING_LEGGINGS) &&
                player.getItemBySlot(EquipmentSlot.FEET).is(NTItems.DIVING_BOOTS);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (stack.is(NTItems.DIVING_HELMET.get())) {
            Tooltips.trans(tooltipComponents, "nautec.helm.desc", ChatFormatting.GRAY);
        }

        if (stack.is(NTItems.DIVING_CHESTPLATE.get())) {
            int oxygen = NTDataComponentsUtils.getOxygenLevels(stack);
            int minutesRemaining = oxygen / 60;
            int secondsRemaining = oxygen % 60;

            int red = (int) (255 * (1 - (oxygen / 600.0)));
            int green = (int) (255 * (oxygen / 600.0));

            int colorHex = (red << 16) | (green << 8);

            tooltipComponents.accept(Component.literal(String.format("Oxygen: %d minutes %d seconds", minutesRemaining, secondsRemaining))
                    .withStyle(style -> style.withColor(TextColor.fromRgb(colorHex))));
            tooltipComponents.accept(Component.literal("Can be filled up using Bottles of pressurized air").withStyle(ChatFormatting.GRAY));
        }
    }
}
