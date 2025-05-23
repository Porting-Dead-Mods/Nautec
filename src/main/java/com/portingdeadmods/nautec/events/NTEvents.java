package com.portingdeadmods.nautec.events;

import com.portingdeadmods.nautec.NTConfig;
import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.api.items.IPowerItem;
import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.capabilities.power.IPowerStorage;
import com.portingdeadmods.nautec.compat.modonomicon.ModonomiconCompat;
import com.portingdeadmods.nautec.content.blockentities.multiblock.semi.PrismarineCrystalBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.multiblock.semi.PrismarineCrystalPartBlockEntity;
import com.portingdeadmods.nautec.content.blocks.multiblock.semi.PrismarineCrystalBlock;
import com.portingdeadmods.nautec.data.NTDataAttachments;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.data.NTDataComponentsUtils;
import com.portingdeadmods.nautec.events.helper.ItemEtching;
import com.portingdeadmods.nautec.events.helper.ItemInfusion;
import com.portingdeadmods.nautec.network.SyncAugmentPayload;
import com.portingdeadmods.nautec.registries.NTAttachmentTypes;
import com.portingdeadmods.nautec.registries.NTFluids;
import com.portingdeadmods.nautec.registries.NTItems;
import com.portingdeadmods.nautec.utils.AugmentHelper;
import com.portingdeadmods.nautec.utils.ParticleUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Map;

public final class NTEvents {
    @EventBusSubscriber(modid = Nautec.MODID, bus = EventBusSubscriber.Bus.GAME)
    public static class Game {
        @SubscribeEvent
        public static void onItemEntityTick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof ItemEntity itemEntity) {
                Level level = itemEntity.level();

                if (itemEntity.isInFluidType(NTFluids.ETCHING_ACID.getFluidType().get())) {
                    ItemEtching.processItemEtching(itemEntity, level);
                }

                if (itemEntity.isInFluidType(NTFluids.EAS.getFluidType().get()) || level.getBlockState(itemEntity.blockPosition().below()).getFluidState().is(NTFluids.EAS.getStillFluid())) {
                    ItemInfusion.processPowerItemInfusion(itemEntity, level);
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
            Player player = event.getEntity();
            Map<AugmentSlot, Augment> augments = player.getData(NTDataAttachments.AUGMENTS);
            Map<AugmentSlot, CompoundTag> augmentsExtraData = player.getData(NTDataAttachments.AUGMENTS_EXTRA_DATA);
            for (AugmentSlot augmentSlot : augments.keySet()) {
                Augment augment = augments.get(augmentSlot);
                augment.setPlayer(player);
                CompoundTag nbt = augmentsExtraData.get(augmentSlot);
                if (nbt != null) {
                    augment.deserializeNBT(player.level().registryAccess(), nbt);
                }
                PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncAugmentPayload(augment, nbt != null ? nbt : new CompoundTag()));
            }

            // Add the Nautec Guide attachment to the player
            if (ModList.get().isLoaded("modonomicon")) {
                if (!player.getData(NTAttachmentTypes.HAS_NAUTEC_GUIDE.get()) && NTConfig.spawnBookInInventory) {
                    ItemHandlerHelper.giveItemToPlayer(player, ModonomiconCompat.getItemStack());
                    player.setData(NTAttachmentTypes.HAS_NAUTEC_GUIDE.get(), true);
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerLoggedIn(PlayerInteractEvent.LeftClickBlock event) {
            Player player = event.getEntity();

            Level level = player.level();
            BlockPos pos = event.getPos();
            BlockEntity blockEntity = level.getBlockEntity(pos);

            ItemStack mainHandItem = player.getMainHandItem();
            if (!player.hasInfiniteMaterials() && mainHandItem.is(NTItems.AQUARINE_PICKAXE) && mainHandItem.get(NTDataComponents.ABILITY_ENABLED)) {
                PrismarineCrystalBlockEntity be = null;
                if (blockEntity instanceof PrismarineCrystalPartBlockEntity partBlockEntity) {
                    be = (PrismarineCrystalBlockEntity) level.getBlockEntity(partBlockEntity.getCrystalPos());
                } else if (blockEntity instanceof PrismarineCrystalBlockEntity blockEntity1) {
                    be = blockEntity1;
                }

                if (be != null && !be.isBreaking()) {
                    be.playBreakAnimation();
                    ItemHandlerHelper.giveItemToPlayer(player, NTItems.PRISMARINE_CRYSTAL_SHARD.toStack(level.random.nextInt(1, 3)));
                    if (level.random.nextInt(0, 4) == 0) {
                        PrismarineCrystalBlock.removeCrystal(level, player, be.getBlockPos());
                        if (level.isClientSide) {
                            ParticleUtils.spawnBreakParticle(be.getBlockPos(), be.getBlockState().getBlock(), 50);
                        }
                        level.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 4, 0.75f);
                    } else {
                        level.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1, 0.5f);
                    }
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Post event) {
            Player player = event.getEntity();
            int changedIndex = player.getData(NTDataAttachments.AUGMENT_DATA_CHANGED);
            if (changedIndex != -1) {
                Map<AugmentSlot, Augment> augments = AugmentHelper.getAugments(player);
                Map<AugmentSlot, CompoundTag> augmentsExtraData = AugmentHelper.getAugmentsData(player);
                AugmentSlot changedSlot = NTRegistries.AUGMENT_SLOT.byId(changedIndex);
                CompoundTag tag = augments.get(changedSlot).serializeNBT(player.level().registryAccess());
                AugmentHelper.setAugmentExtraData(player, changedSlot, tag);
                player.setData(NTDataAttachments.AUGMENT_DATA_CHANGED, -1);
            }
        }

        @SubscribeEvent
        public static void onBreakBlock(PlayerEvent.BreakSpeed event) {
            Player player = event.getEntity();
            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() instanceof IPowerItem powerItem) {
                IPowerStorage powerStorage = stack.getCapability(NTCapabilities.PowerStorage.ITEM);
                if (powerStorage.getPowerStored() <= 0) {
                    event.setCanceled(true);
                }
            }
        }

        @SubscribeEvent
        public static void onHitEntity(AttackEntityEvent event) {
            if (event.getEntity().getMainHandItem().getItem() instanceof IPowerItem powerItem) {
                IPowerStorage powerStorage = event.getEntity().getMainHandItem().getCapability(NTCapabilities.PowerStorage.ITEM);
                if (powerStorage.getPowerStored() <= 0 && event.getTarget() instanceof LivingEntity) {
                    event.setCanceled(true);
                    event.getEntity().displayClientMessage(Component.literal("Not Enough Power !"), true);
                }
            }
        }

        @SubscribeEvent
        public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
            if (event.getItemStack().getItem() instanceof IPowerItem powerItem) {
                ItemStack stack = event.getItemStack();
                if (stack.has(NTDataComponents.ABILITY_ENABLED) && event.getEntity().isShiftKeyDown()) {
                    if (stack.is(NTItems.PRISMATIC_BATTERY)) {
                        NTDataComponentsUtils.setAbilityStatus(stack, !NTDataComponentsUtils.isAbilityEnabled(stack));
                        return;
                    }
                    if (NTDataComponentsUtils.isInfused(stack)) {
                        boolean enabled = NTDataComponentsUtils.isAbilityEnabled(stack);
                        NTDataComponentsUtils.setAbilityStatus(stack, !enabled);
                        event.getEntity().displayClientMessage(Component.literal("Ability " + (enabled ? "Disabled" : "Enabled")).withStyle((enabled ? ChatFormatting.RED : ChatFormatting.GREEN)), true);
                        if (event.getLevel().isClientSide) {
                            Player player = event.getEntity();
                            Level level = event.getLevel();
                            if (enabled) {
                                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.PLAYERS, 0.4f, 0.01f);
                            } else {
                                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.PLAYERS, 0.4f, 0.09f);
                            }
                        }
                    } else {
                        if (event.getLevel().isClientSide) {
                            event.getEntity().sendSystemMessage(Component.literal("Infuse in Algae Serum to unlock Abilities").withStyle(ChatFormatting.RED));
                        }
                    }
                    event.setCanceled(true);
                }
            }
        }
    }

}
