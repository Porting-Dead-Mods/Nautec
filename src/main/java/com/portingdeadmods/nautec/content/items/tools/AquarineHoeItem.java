package com.portingdeadmods.nautec.content.items.tools;

import com.portingdeadmods.nautec.api.items.IPowerItem;
import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.capabilities.power.IPowerStorage;
import com.portingdeadmods.nautec.content.items.tiers.NTToolMaterials;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.data.NTDataComponentsUtils;
import com.portingdeadmods.nautec.data.components.ComponentPowerStorage;
import com.portingdeadmods.nautec.utils.ItemUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class AquarineHoeItem extends HoeItem implements IPowerItem {
    private static final int POWER_PER_BLOCK = 2; // Adjust this value to fit the power requirement per block tilled

    public AquarineHoeItem() {
        super(NTToolMaterials.AQUARINE, new Properties().stacksTo(1).component(NTDataComponents.ABILITY_ENABLED, false)
                .component(NTDataComponents.IS_INFUSED,false).component(NTDataComponents.POWER, ComponentPowerStorage.withCapacity(700)));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        IPowerStorage powerStorage = stack.getCapability(NTCapabilities.PowerStorage.ITEM);
        if (powerStorage.getPowerStored() <= 0) {
            return InteractionResult.FAIL;
        }

        if (context.getPlayer() != null && NTDataComponentsUtils.isAbilityEnabled(stack) && canTill(context.getLevel(), context.getClickedPos(), context.getLevel().getBlockState(context.getClickedPos()))) {
            createFarmland3x3(context.getLevel(), context.getClickedPos(), context.getPlayer(), stack);
            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }

    private void createFarmland3x3(Level level, BlockPos center, Player player, ItemStack stack) {
        IPowerStorage powerStorage = stack.getCapability(NTCapabilities.PowerStorage.ITEM);
        int availablePower = powerStorage.getPowerStored();
        int blocksToTill = availablePower / POWER_PER_BLOCK; // Calculate how many blocks can be tilled

        // Iterate over the 3x3 area
        for (BlockPos targetPos : BlockPos.betweenClosed(center.offset(-1, 0, -1), center.offset(1, 0, 1))) {
            BlockState targetState = level.getBlockState(targetPos);

            if (blocksToTill > 0 && canTill(level, targetPos, targetState)) {
                tillBlock(level, targetPos, player, stack, powerStorage);
                blocksToTill--; // Reduce the block count
            }
        }
        if(level.isClientSide){
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1f, 1f);
        }
    }

    private boolean canTill(Level level, BlockPos pos, BlockState state) {
        return state.is(BlockTags.DIRT) || state.is(Blocks.GRASS_BLOCK); // Can only till dirt or grass blocks
    }

    private void tillBlock(Level level, BlockPos pos, Player player, ItemStack stack, IPowerStorage powerStorage) {
        BlockState currentState = level.getBlockState(pos);

        if (currentState.is(BlockTags.DIRT) || currentState.is(Blocks.GRASS_BLOCK)) {
            level.setBlock(pos, Blocks.FARMLAND.defaultBlockState(), 3); // Convert the block to farmland
            powerStorage.tryDrainPower(POWER_PER_BLOCK, false); // Drain power
        }
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        IPowerStorage powerStorage = miningEntity.getItemInHand(InteractionHand.MAIN_HAND).getCapability(NTCapabilities.PowerStorage.ITEM);
        powerStorage.tryDrainPower(1, false);
        return super.mineBlock(stack, level, state, pos, miningEntity);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        IPowerStorage powerStorage = attacker.getItemInHand(InteractionHand.MAIN_HAND).getCapability(NTCapabilities.PowerStorage.ITEM);
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
        return 100;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        IPowerStorage powerStorage = stack.getCapability(NTCapabilities.PowerStorage.ITEM);
        tooltipComponents.add(Component.literal("Ability: Till 3x3 Farmland").withStyle(ChatFormatting.DARK_PURPLE));
        if(!NTDataComponentsUtils.isInfused(stack)){
            tooltipComponents.add(Component.literal("Infuse in Algae Serum to unlock Abilities").withStyle(ChatFormatting.DARK_GREEN));
        }else{
            tooltipComponents.add(Component.literal("Status: " + ((NTDataComponentsUtils.isAbilityEnabled(stack)) ? "Enabled" : "Shift + Right Click to Enable")).withStyle((NTDataComponentsUtils.isAbilityEnabled(stack)) ? ChatFormatting.GREEN : ChatFormatting.RED));
        }
        tooltipComponents.add(Component.literal("Power: " + powerStorage.getPowerStored() + "/" + powerStorage.getPowerCapacity() + " AP").withStyle(ChatFormatting.DARK_AQUA));
    }
}