package com.portingdeadmods.modjam.content.items;

import com.portingdeadmods.modjam.api.items.IPowerItem;
import com.portingdeadmods.modjam.content.items.tiers.MJArmorMaterials;
import com.portingdeadmods.modjam.data.MJDataComponents;
import com.portingdeadmods.modjam.data.components.ComponentPowerStorage;
import com.portingdeadmods.modjam.registries.MJItems;
import com.portingdeadmods.modjam.utils.ItemUtils;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class PrismMonocleItem extends ArmorItem implements IPowerItem {
    public PrismMonocleItem(Properties properties) {
        super(MJArmorMaterials.PRISMARINE, Type.HELMET, properties.component(MJDataComponents.POWER, ComponentPowerStorage.withCapacity(100))
                .durability(0)
                .stacksTo(1)
        );
    }

    public static void registerCapabilities(final RegisterCapabilitiesEvent evt) {
        evt.registerItem(
                CuriosCapability.ITEM,
                (stack, context) -> new ICurio() {

                    @Override
                    public ItemStack getStack() {
                        return MJItems.PRISM_MONOCLE.toStack();
                    }

                    @Override
                    public void curioTick(SlotContext slotContext) {
                        // ticking logic here
                    }
                }, MJItems.PRISM_MONOCLE.get());
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
