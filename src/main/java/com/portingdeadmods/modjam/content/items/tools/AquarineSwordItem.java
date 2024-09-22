package com.portingdeadmods.modjam.content.items.tools;

import com.portingdeadmods.modjam.api.items.IPowerItem;
import com.portingdeadmods.modjam.capabilities.MJCapabilities;
import com.portingdeadmods.modjam.capabilities.power.IPowerStorage;
import com.portingdeadmods.modjam.content.items.tiers.MJToolMaterials;
import com.portingdeadmods.modjam.data.MJDataComponents;
import com.portingdeadmods.modjam.data.components.ComponentPowerStorage;
import com.portingdeadmods.modjam.utils.ItemUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class AquarineSwordItem extends SwordItem implements IPowerItem {
    public AquarineSwordItem() {
        super(MJToolMaterials.AQUARINE,
                new Properties().attributes(
                        SwordItem.createAttributes(
                                MJToolMaterials.AQUARINE,
                                3,
                                -2.4f
                        )
                ).component(MJDataComponents.IS_INFUSED,false).component(MJDataComponents.ABILITY_ENABLED,false).component(MJDataComponents.POWER, ComponentPowerStorage.withCapacity(700)));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        IPowerStorage powerStorage = stack.getCapability(MJCapabilities.PowerStorage.ITEM);
        if(powerStorage.getPowerStored() <= 0) {
            return InteractionResult.FAIL;
        }
        return super.useOn(context);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        IPowerStorage powerStorage = miningEntity.getItemInHand(InteractionHand.MAIN_HAND).getCapability(MJCapabilities.PowerStorage.ITEM);
        powerStorage.tryDrainPower(1, false);
        return super.mineBlock(stack, level, state, pos, miningEntity);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        IPowerStorage powerStorage = attacker.getItemInHand(InteractionHand.MAIN_HAND).getCapability(MJCapabilities.PowerStorage.ITEM);
        powerStorage.tryDrainPower(1, false);
        return super.hurtEnemy(stack, target, attacker);
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

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        IPowerStorage powerStorage = stack.getCapability(MJCapabilities.PowerStorage.ITEM);
        tooltipComponents.add(Component.literal("Power: " + powerStorage.getPowerStored() + "/" + powerStorage.getPowerCapacity() + " AP").withStyle(ChatFormatting.DARK_AQUA));
    }
}
