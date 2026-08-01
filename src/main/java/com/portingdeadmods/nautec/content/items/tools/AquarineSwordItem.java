package com.portingdeadmods.nautec.content.items.tools;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.items.IPowerItem;
import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.capabilities.power.IPowerStorage;
import com.portingdeadmods.nautec.content.items.tiers.NTToolMaterials;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.data.NTDataComponentsUtils;
import com.portingdeadmods.nautec.data.components.ComponentPowerStorage;
import com.portingdeadmods.nautec.utils.ItemUtils;
import com.portingdeadmods.nautec.utils.Tooltips;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import net.minecraft.sounds.SoundEvents;

public class AquarineSwordItem extends Item implements IPowerItem {
    public AquarineSwordItem(Properties properties) {
        super(properties
                .sword(NTToolMaterials.AQUARINE, 3, -2.4f)
                .component(NTDataComponents.IS_INFUSED,false)
                .component(NTDataComponents.ABILITY_ENABLED,false)
                .component(NTDataComponents.POWER, ComponentPowerStorage.withCapacity(1200)));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        IPowerStorage powerStorage = stack.getCapability(NTCapabilities.PowerStorage.ITEM);
        if(powerStorage.getPowerStored() <= 0) {
            return InteractionResult.FAIL;
        }
        return super.useOn(context);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        IPowerStorage powerStorage = miningEntity.getItemInHand(InteractionHand.MAIN_HAND).getCapability(NTCapabilities.PowerStorage.ITEM);
        powerStorage.tryDrainPower(1, false);
        return super.mineBlock(stack, level, state, pos, miningEntity);
    }

    public static final AttributeModifier ENABLED_DAMAGE = new AttributeModifier(Identifier.fromNamespaceAndPath(Nautec.MODID,"damage"),0.7,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    public static final AttributeModifier DISABLED_DAMAGE = new AttributeModifier(Identifier.fromNamespaceAndPath(Nautec.MODID,"damage"),0,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot) {
        if(NTDataComponentsUtils.isAbilityEnabled(stack)){
            IPowerStorage powerStorage = stack.getCapability(NTCapabilities.PowerStorage.ITEM);
            ItemAttributeModifiers attributes = stack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
            boolean hasEnergy = powerStorage.getPowerStored() > 0;
            attributes = attributes.withModifierAdded(Attributes.ATTACK_DAMAGE, hasEnergy ? ENABLED_DAMAGE : DISABLED_DAMAGE, EquipmentSlotGroup.MAINHAND);
            stack.set(DataComponents.ATTRIBUTE_MODIFIERS, attributes);
        } else {
            ItemAttributeModifiers attributes = stack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
            attributes = attributes.withModifierAdded(Attributes.ATTACK_DAMAGE, DISABLED_DAMAGE, EquipmentSlotGroup.MAINHAND);
            stack.set(DataComponents.ATTRIBUTE_MODIFIERS, attributes);
        }
        super.inventoryTick(stack, level, entity, slot);
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        IPowerStorage powerStorage = attacker.getItemInHand(InteractionHand.MAIN_HAND).getCapability(NTCapabilities.PowerStorage.ITEM);
        if(NTDataComponentsUtils.isAbilityEnabled(stack)){
            powerStorage.tryDrainPower(10, false);
            if (!target.level().isClientSide()) {
                LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(target.level(), EntitySpawnReason.TRIGGERED);
                if (lightningBolt != null) {
                    lightningBolt.setPos(target.getX(), target.getY(), target.getZ());
                    target.level().addFreshEntity(lightningBolt);
                    target.level().playSound(null, target.blockPosition(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.HOSTILE, 0.3F, 1.0F);
                }
            }
        }else{
            powerStorage.tryDrainPower(1, false);
        }
        super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return NTDataComponentsUtils.isAbilityEnabled(stack) || stack.isEnchanted();
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
        return 100;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, display, tooltipComponents, tooltipFlag);
        IPowerStorage powerStorage = stack.getCapability(NTCapabilities.PowerStorage.ITEM);
        Tooltips.trans(tooltipComponents, "nautec.tool.sword.ability", ChatFormatting.DARK_PURPLE);
        if(!NTDataComponentsUtils.isInfused(stack)){
            Tooltips.trans(tooltipComponents, "nautec.tool.infuse-me", ChatFormatting.DARK_GREEN);
        }else{
            Tooltips.transtrans(tooltipComponents, "nautec.tool.status", NTDataComponentsUtils.isAbilityEnabled(stack) ? "nautec.tool.enabled" : "nautec.tool.disabled", NTDataComponentsUtils.isAbilityEnabled(stack) ? ChatFormatting.GREEN : ChatFormatting.RED);
        }
        Tooltips.transInsert(tooltipComponents, "nautec.tool.power", powerStorage.getPowerStored() + "/" + powerStorage.getPowerCapacity(), ChatFormatting.DARK_AQUA);
    }
}
