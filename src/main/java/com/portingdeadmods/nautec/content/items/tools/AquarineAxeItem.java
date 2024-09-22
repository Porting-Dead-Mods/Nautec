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
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class AquarineAxeItem extends AxeItem implements IPowerItem {
    private static final int POWER_PER_BLOCK = 2;

    public AquarineAxeItem() {
        super(NTToolMaterials.AQUARINE, new Properties()
                .stacksTo(1)
                .component(NTDataComponents.IS_INFUSED,false)
                .component(NTDataComponents.POWER, ComponentPowerStorage.withCapacity(700))
                .component(NTDataComponents.ABILITY_ENABLED,false)
        );
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        IPowerStorage powerStorage = stack.getCapability(NTCapabilities.PowerStorage.ITEM);
        if (powerStorage.getPowerStored() <= 0) {
            return InteractionResult.FAIL;
        }
        return super.useOn(context);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        IPowerStorage powerStorage = miningEntity.getItemInHand(InteractionHand.MAIN_HAND).getCapability(NTCapabilities.PowerStorage.ITEM);
        powerStorage.tryDrainPower(1, false);

        if (NTDataComponentsUtils.isAbilityEnabled(stack)) {
            chopTree(level, pos, miningEntity, stack);
            return true;
        }

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
        return 0;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.literal("Ability: Chop Down Entire Trees").withStyle(ChatFormatting.DARK_PURPLE));
        if(!NTDataComponentsUtils.isInfused(stack)){
            tooltipComponents.add(Component.literal("Infuse in Algae Serum to unlock Abilities").withStyle(ChatFormatting.DARK_GREEN));
        }else{
            tooltipComponents.add(Component.literal("Status: " + ((NTDataComponentsUtils.isAbilityEnabled(stack)) ? "Enabled" : "Shift + Right Click to Enable")).withStyle((NTDataComponentsUtils.isAbilityEnabled(stack)) ? ChatFormatting.GREEN : ChatFormatting.RED));
        }
        IPowerStorage powerStorage = stack.getCapability(NTCapabilities.PowerStorage.ITEM);
        tooltipComponents.add(Component.literal("Power: " + powerStorage.getPowerStored() + "/" + powerStorage.getPowerCapacity() + " AP").withStyle(ChatFormatting.DARK_AQUA));
    }

    // Tree chopping logic
    private void chopTree(Level level, BlockPos pos, LivingEntity player, ItemStack stack) {
        IPowerStorage powerStorage = stack.getCapability(NTCapabilities.PowerStorage.ITEM);

        if (isLog(level,pos,level.getBlockState(pos)) && powerStorage.getPowerStored() > 0) {
            int blocksToBreak = powerStorage.getPowerStored() / POWER_PER_BLOCK; // Calculate how many blocks we can break
            breakTree(level, pos, stack, player, powerStorage, blocksToBreak);
        }
    }

    // Helper function to detect logs
    private boolean isLog(Level level, BlockPos pos, BlockState state) {
        return state.is(BlockTags.LOGS) && level.getBlockEntity(pos) == null;
    }

    // Recursive function to break the tree
    private int breakTree(Level level, BlockPos pos, ItemStack stack, LivingEntity player, IPowerStorage powerStorage, int blocksToBreak) {
        BlockState state = level.getBlockState(pos);

        // Stop if it's not a log or if we've reached the block limit or run out of power
        if (!isLog(level,pos,state) || blocksToBreak <= 0 || powerStorage.getPowerStored() < POWER_PER_BLOCK) {
            return blocksToBreak;
        }

        // Break the block
        level.destroyBlock(pos, true);

        // Drain power for this block
        powerStorage.tryDrainPower(POWER_PER_BLOCK, false);
        blocksToBreak--;

        // Recursively break adjacent logs
        for (BlockPos adjacentPos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
            if (!adjacentPos.equals(pos) && blocksToBreak > 0) {
                blocksToBreak = breakTree(level, adjacentPos, stack, player, powerStorage, blocksToBreak);
            }
        }

        return blocksToBreak;
    }
}
