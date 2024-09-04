package com.portingdeadmods.modjam.content.items;

import com.portingdeadmods.modjam.api.items.IPowerItem;
import com.portingdeadmods.modjam.capabilities.MJCapabilities;
import com.portingdeadmods.modjam.capabilities.power.IPowerStorage;
import com.portingdeadmods.modjam.data.MJDataComponents;
import com.portingdeadmods.modjam.data.components.ComponentPowerStorage;
import com.portingdeadmods.modjam.utils.ItemUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ExamplePowerItem extends Item implements IPowerItem {
    public ExamplePowerItem(Properties properties) {
        super(properties.component(MJDataComponents.POWER, ComponentPowerStorage.withCapacity(100))
                .durability(0)
                .stacksTo(1)
        );
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        IPowerStorage powerStorage = itemInHand.getCapability(MJCapabilities.PowerStorage.ITEM);
        powerStorage.setPowerStored(powerStorage.getPowerStored() + 1);
        return InteractionResultHolder.success(itemInHand);
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
        return 100;
    }

    @Override
    public int getMaxOutput() {
        return 100;
    }
}
