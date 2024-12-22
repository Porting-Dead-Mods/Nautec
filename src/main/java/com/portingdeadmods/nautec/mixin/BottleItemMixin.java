package com.portingdeadmods.nautec.mixin;

import com.portingdeadmods.nautec.NTConfig;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BottleItem.class)
public abstract class BottleItemMixin extends Item {

    public BottleItemMixin(Item.Properties properties) {
        super(properties);
    }

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getFluidState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/material/FluidState;", ordinal = 0), cancellable = true)
    private void onUse(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (NTConfig.collectAirWithBottle) {
            BlockHitResult blockHitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
            if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = blockHitResult.getBlockPos();

                if (level.getBlockState(blockPos).getBlock() == Blocks.BUBBLE_COLUMN) {
                    ItemStack airBottleStack = NTItems.AIR_BOTTLE.get().getDefaultInstance();

                    level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
                    level.gameEvent(player, GameEvent.FLUID_PICKUP, blockPos);
                    com.portingdeadmods.nautec.utils.ItemUtils.giveItemToPlayerNoSound(player, airBottleStack);
                    ItemStack itemInHand = player.getItemInHand(hand);
                    itemInHand.shrink(1);
                    cir.setReturnValue(InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide()));
                }
            }
        }
    }
}
