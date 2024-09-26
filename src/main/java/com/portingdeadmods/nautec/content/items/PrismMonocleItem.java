package com.portingdeadmods.nautec.content.items;

import com.portingdeadmods.nautec.api.items.IPowerItem;
import com.portingdeadmods.nautec.content.items.tiers.NTArmorMaterials;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.data.components.ComponentPowerStorage;
import com.portingdeadmods.nautec.registries.NTItems;
import com.portingdeadmods.nautec.utils.ItemUtils;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class PrismMonocleItem extends ArmorItem implements IPowerItem {
    public PrismMonocleItem(Properties properties) {
        super(NTArmorMaterials.PRISMARINE, Type.HELMET, properties.component(NTDataComponents.POWER, ComponentPowerStorage.withCapacity(100)));
    }

    public static void registerCapabilities(final RegisterCapabilitiesEvent evt) {
        evt.registerItem(
                CuriosCapability.ITEM,
                (stack, context) -> new ICurio() {

                    @Override
                    public ItemStack getStack() {
                        return NTItems.PRISM_MONOCLE.toStack();
                    }

                    @Override
                    public void curioTick(SlotContext slotContext) {
                        // ticking logic here
                    }
                }, NTItems.PRISM_MONOCLE.get());
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
