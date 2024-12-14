package com.portingdeadmods.nautec.content.items;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.items.IPowerItem;
import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.capabilities.power.IPowerStorage;
import com.portingdeadmods.nautec.content.items.tiers.NTArmorMaterials;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.data.components.ComponentPowerStorage;
import com.portingdeadmods.nautec.utils.ItemUtils;
import com.portingdeadmods.nautec.utils.Tooltips;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.function.Consumer;

public class AquarineArmorItem extends ArmorItem implements IPowerItem {
    public AquarineArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(NTArmorMaterials.AQUARINE_STEEL, type, properties
                .durability(100)
                .component(NTDataComponents.POWER, ComponentPowerStorage.withCapacity(512)));
    }
    public static final AttributeModifier ENABLED_ARMOR_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"armor"),10,AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier DISABLED_ARMOR_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"armor"),0,AttributeModifier.Operation.ADD_VALUE);

    public static final AttributeModifier ENABLED_TOUGHNESS_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"toughness"),5,AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier DISABLED_TOUGHNESS_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"toughness"),0,AttributeModifier.Operation.ADD_VALUE);

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        IPowerStorage powerStorage = stack.getCapability(NTCapabilities.PowerStorage.ITEM);
        ItemAttributeModifiers attributes = stack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
        boolean hasEnergy = powerStorage.getPowerStored() > 0;
        EquipmentSlot slotType = this.getEquipmentSlot();
        attributes = attributes.withModifierAdded(Attributes.ARMOR, hasEnergy ? ENABLED_ARMOR_MODIFIER : DISABLED_ARMOR_MODIFIER, EquipmentSlotGroup.bySlot(slotType));
        attributes = attributes.withModifierAdded(Attributes.ARMOR_TOUGHNESS, hasEnergy ? ENABLED_TOUGHNESS_MODIFIER : DISABLED_TOUGHNESS_MODIFIER, EquipmentSlotGroup.bySlot(slotType));
        return attributes;
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<Item> onBroken) {
        if (stack.getItem() instanceof IPowerItem poweredTool) {
            IPowerStorage energyStorage = stack.getCapability(NTCapabilities.PowerStorage.ITEM);
            if (energyStorage == null) return amount;
            double reductionFactor = 0;
            if (entity != null) {
                HolderLookup.RegistryLookup<Enchantment> registrylookup = entity.level().getServer().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
                int unbreakingLevel = stack.getEnchantmentLevel(registrylookup.getOrThrow(Enchantments.UNBREAKING));
                reductionFactor = Math.min(4.0, unbreakingLevel * 0.1);
            }
            int finalEnergyCost = (int) Math.max(0, amount - (amount * reductionFactor));
            energyStorage.tryDrainPower(finalEnergyCost, false);
            return 0;
        }
        return amount;
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
        Tooltips.trans(tooltipComponents, "nautec.armor.ability.desc", ChatFormatting.DARK_PURPLE);
        Tooltips.transInsert(tooltipComponents, "nautec.armor.power", powerStorage.getPowerStored() + "/" + powerStorage.getPowerCapacity() , ChatFormatting.DARK_AQUA);
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
