package com.portingdeadmods.nautec.mixin;

import com.portingdeadmods.nautec.NTConfig;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.item.Item.getPlayerPOVHitResult;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void onUse(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (NTConfig.collectSaltWater) {

            ItemStack itemStack = player.getItemInHand(hand);
            BlockHitResult blockHitResult = getPlayerPOVHitResult(
                    level, player, ClipContext.Fluid.SOURCE_ONLY
            );

            if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = blockHitResult.getBlockPos();
                BlockState blockState = level.getBlockState(blockPos);

                if (blockState.getBlock() instanceof BucketPickup bucketPickup) {

                    if (level.getFluidState(blockPos).is(FluidTags.WATER) && level.getBiome(blockPos).getRegisteredName().contains("ocean")) {

                        ItemStack filledBucket = bucketPickup.pickupBlock(player, level, blockPos, blockState);
                        if (!filledBucket.isEmpty()) {

                            filledBucket = new ItemStack(NTItems.SALT_WATER_BUCKET.asItem());
                            player.awardStat(Stats.ITEM_USED.get(Items.BUCKET));

                            bucketPickup.getPickupSound(blockState).ifPresent(soundEvent ->
                                    level.playSound(player, blockPos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F)
                            );

                            if (!level.isClientSide) {
                                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, filledBucket);
                            }

                            cir.setReturnValue(InteractionResultHolder.sidedSuccess(ItemUtils.createFilledResult(itemStack, player, filledBucket), level.isClientSide()));
                            cir.cancel();
                        }
                    }
                }
            }
        }
    }
}