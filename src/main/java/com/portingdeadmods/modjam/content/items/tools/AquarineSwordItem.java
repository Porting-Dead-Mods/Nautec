package com.portingdeadmods.modjam.content.items.tools;

import com.portingdeadmods.modjam.api.items.IPowerItem;
import com.portingdeadmods.modjam.capabilities.MJCapabilities;
import com.portingdeadmods.modjam.capabilities.power.IPowerStorage;
import com.portingdeadmods.modjam.content.items.tiers.MJToolMaterials;
import com.portingdeadmods.modjam.data.MJDataComponents;
import com.portingdeadmods.modjam.data.components.ComponentPowerStorage;
import com.portingdeadmods.modjam.utils.ItemUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;

public class AquarineSwordItem extends SwordItem implements IPowerItem {
    public AquarineSwordItem() {
        super(MJToolMaterials.AQUARINE,
                new Properties().attributes(
                        SwordItem.createAttributes(
                                MJToolMaterials.AQUARINE,
                                3,
                                -2.4f
                        )
                ).component(MJDataComponents.POWER, ComponentPowerStorage.withCapacity(700)));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide) {
            ItemStack stack = player.getItemInHand(usedHand);
            IPowerStorage powerStorage = stack.getCapability(MJCapabilities.PowerStorage.ITEM);
            if(powerStorage.getPowerStored() <= 0) {
                return InteractionResultHolder.fail(stack);
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }
    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return false;
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
        return 0;
    }
}
