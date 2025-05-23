package com.portingdeadmods.nautec.content.items.tools;

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
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class AquarineShovelItem extends ShovelItem implements IPowerItem {
    private static final int POWER_PER_BLOCK = 2;

    public AquarineShovelItem() {
        super(NTToolMaterials.AQUARINE, new Properties()
                .stacksTo(1)
                .component(NTDataComponents.IS_INFUSED,false)
                .component(NTDataComponents.ABILITY_ENABLED, false)
                .component(NTDataComponents.POWER, ComponentPowerStorage.withCapacity(1200))
                .attributes(ShovelItem.createAttributes(NTToolMaterials.AQUARINE, 1.5f, -3.0f))
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
        if (miningEntity instanceof Player player) {
            BlockHitResult hitResult = (BlockHitResult) player.pick(20.0D, 0.0F, false);
            Direction hitFace = hitResult.getDirection();

            for (BlockPos targetPos : get3x3MiningArea(pos, hitFace)) {
                BlockState targetState = level.getBlockState(targetPos);

                if (canMine(level, targetPos, targetState) && NTDataComponentsUtils.isAbilityEnabled(stack)) {
                    level.destroyBlock(targetPos, true);
                }
            }
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
        return 100;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return NTDataComponentsUtils.isAbilityEnabled(stack) || stack.isEnchanted();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        Tooltips.trans(tooltipComponents, "nautec.tool.shovel.ability", ChatFormatting.DARK_PURPLE);
        if(!NTDataComponentsUtils.isInfused(stack)){
            Tooltips.trans(tooltipComponents, "nautec.tool.infuse-me", ChatFormatting.DARK_GREEN);
        }else{
            Tooltips.transtrans(tooltipComponents, "nautec.tool.status", NTDataComponentsUtils.isAbilityEnabled(stack) ? "nautec.tool.enabled" : "nautec.tool.disabled", NTDataComponentsUtils.isAbilityEnabled(stack) ? ChatFormatting.GREEN : ChatFormatting.RED);
        }
        IPowerStorage powerStorage = stack.getCapability(NTCapabilities.PowerStorage.ITEM);
        Tooltips.transInsert(tooltipComponents, "nautec.tool.power", powerStorage.getPowerStored() + "/" + powerStorage.getPowerCapacity(), ChatFormatting.DARK_AQUA);
    }

    private void mine3x3(Level level, BlockPos pos, LivingEntity player, ItemStack stack) {
        IPowerStorage powerStorage = stack.getCapability(NTCapabilities.PowerStorage.ITEM);

        if (powerStorage.getPowerStored() > 0) {
            int blocksToBreak = powerStorage.getPowerStored() / POWER_PER_BLOCK; // Calculate how many blocks we can break
            Direction faceDirection = player.getDirection(); // Get the direction the player is facing

            // Adjust the 3x3 mining area based on the player's facing direction
            Iterable<BlockPos> blocksToMine = get3x3MiningArea(pos, faceDirection);

            for (BlockPos targetPos : blocksToMine) {
                if (blocksToBreak > 0 && canMine(level,pos,level.getBlockState(targetPos))) {
                    blocksToBreak = breakBlock(level, targetPos, stack, player, powerStorage, blocksToBreak);
                }
            }
        }
    }

    // Method to get the 3x3 mining area based on the face the player hit
    private Iterable<BlockPos> get3x3MiningArea(BlockPos center, Direction hitFace) {
        return switch (hitFace) {
            case UP, DOWN ->
                // If the player is facing up or down, mine the horizontal plane (X-Z plane)
                    BlockPos.betweenClosed(center.offset(-1, 0, -1), center.offset(1, 0, 1));
            case NORTH, SOUTH ->
                // If the player is facing north or south, mine in the X-Y plane
                    BlockPos.betweenClosed(center.offset(-1, -1, 0), center.offset(1, 1, 0));
            case EAST, WEST ->
                // If the player is facing east or west, mine in the Z-Y plane
                    BlockPos.betweenClosed(center.offset(0, -1, -1), center.offset(0, 1, 1));
            default ->
                // Default to the horizontal plane if somehow no direction is provided
                    BlockPos.betweenClosed(center.offset(-1, 0, -1), center.offset(1, 0, 1));
        };
    }



    private boolean canMine(Level level, BlockPos pos, BlockState state) {
        return state.is(BlockTags.MINEABLE_WITH_SHOVEL) && level.getBlockEntity(pos) == null;
    }

    private int breakBlock(Level level, BlockPos pos, ItemStack stack, LivingEntity player, IPowerStorage powerStorage, int blocksToBreak) {
        BlockState state = level.getBlockState(pos);

        if (!canMine(level,pos,state) || blocksToBreak <= 0 || powerStorage.getPowerStored() < POWER_PER_BLOCK) {
            return blocksToBreak;
        }

        level.destroyBlock(pos, true); // Break the block
        powerStorage.tryDrainPower(POWER_PER_BLOCK, false); // Drain power for this block
        return --blocksToBreak;
    }
}
