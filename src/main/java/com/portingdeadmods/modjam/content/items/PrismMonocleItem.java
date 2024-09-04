package com.portingdeadmods.modjam.content.items;

import com.portingdeadmods.modjam.api.items.IPowerItem;
import com.portingdeadmods.modjam.data.MJDataComponents;
import com.portingdeadmods.modjam.data.components.ComponentPowerStorage;
import com.portingdeadmods.modjam.utils.ItemUtils;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class PrismMonocleItem extends ArmorItem implements IPowerItem {
    public PrismMonocleItem(Holder<ArmorMaterial> material, Properties properties) {
        super(material, Type.HELMET, properties.component(MJDataComponents.POWER, ComponentPowerStorage.withCapacity(100))
                .durability(0)
                .stacksTo(1)
        );
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
