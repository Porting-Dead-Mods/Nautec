package com.portingdeadmods.nautec.content.items;

import com.portingdeadmods.nautec.api.items.ICurioItem;
import com.portingdeadmods.nautec.api.items.IPowerItem;
import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.capabilities.power.IPowerStorage;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.data.NTDataComponentsUtils;
import com.portingdeadmods.nautec.data.components.ComponentPowerStorage;
import com.portingdeadmods.nautec.utils.ItemUtils;
import com.portingdeadmods.nautec.utils.Tooltips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class BatteryItem extends Item implements IPowerItem, ICurioItem {
    public BatteryItem(Properties properties) {
        super(properties
                .component(NTDataComponents.POWER, ComponentPowerStorage.withCapacity(10000))
                .component(NTDataComponents.ABILITY_ENABLED,false));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return NTDataComponentsUtils.isAbilityEnabled(stack);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return ItemUtils.POWER_BAR_COLOR;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return ItemUtils.powerForDurabilityBar(stack);
    }

    @Override
    public int getMaxInput() {
        return ItemUtils.ITEM_POWER_INPUT;
    }

    @Override
    public int getMaxOutput() {
        return 100;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        IPowerStorage powerStorage = stack.getCapability(NTCapabilities.PowerStorage.ITEM);
        Tooltips.transtrans(tooltipComponents, "nautec.armor.status", NTDataComponentsUtils.isAbilityEnabled(stack)? "nautec.armor.enabled" : "nautec.armor.disabled", NTDataComponentsUtils.isAbilityEnabled(stack)?ChatFormatting.GREEN:ChatFormatting.RED);
        Tooltips.transInsert(tooltipComponents, "nautec.armor.power", " " + powerStorage.getPowerStored() + "/" + powerStorage.getPowerCapacity() , ChatFormatting.DARK_AQUA);
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public void curioTick(ItemStack stack, SlotContext slotContext) {
        IPowerStorage powerStorage = stack.getCapability(NTCapabilities.PowerStorage.ITEM);
        Player player = (Player) slotContext.entity();
        if (NTDataComponentsUtils.isAbilityEnabled(stack)) {
            for (ItemStack itemStack : player.getInventory().items) {
                if (itemStack.getCapability(NTCapabilities.PowerStorage.ITEM) != null) {
                    IPowerStorage itemPowerStorage = itemStack.getCapability(NTCapabilities.PowerStorage.ITEM);
                    if (itemPowerStorage.getPowerStored() < itemPowerStorage.getPowerCapacity()) {
                        int powerToTransfer = Math.min(powerStorage.getPowerStored(), itemPowerStorage.getPowerCapacity() - itemPowerStorage.getPowerStored());
                        powerStorage.tryDrainPower(powerToTransfer,false);
                        itemPowerStorage.tryFillPower(powerToTransfer,false);
                    }
                }
            }
        }
    }
}
