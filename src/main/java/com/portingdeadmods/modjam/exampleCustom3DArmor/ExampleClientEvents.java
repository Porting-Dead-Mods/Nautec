package com.portingdeadmods.modjam.exampleCustom3DArmor;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.registries.MJItems;
import com.portingdeadmods.modjam.utils.ArmorModelsHandler;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = ModJam.MODID,value = Dist.CLIENT,bus = EventBusSubscriber.Bus.MOD)
public class ExampleClientEvents {

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack
                    itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                return ArmorModelsHandler.armorModel(ArmorModelsHandler.test, equipmentSlot);
            }
        }, ExampleItems.TEST, ExampleItems.TEST_CHEST);

        event.registerItem(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack
                    itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                return ArmorModelsHandler.armorModel(ArmorModelsHandler.divingSuit, equipmentSlot);
            }
        }, MJItems.DIVING_HELMET, MJItems.DIVING_CHESTPLATE);
    }

}
