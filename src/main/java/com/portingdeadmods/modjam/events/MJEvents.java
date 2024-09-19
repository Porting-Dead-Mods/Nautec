package com.portingdeadmods.modjam.events;

import com.portingdeadmods.modjam.MJRegistries;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.augments.Augment;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.api.augments.AugmentType;
import com.portingdeadmods.modjam.api.items.IPowerItem;
import com.portingdeadmods.modjam.capabilities.MJCapabilities;
import com.portingdeadmods.modjam.capabilities.power.IPowerStorage;
import com.portingdeadmods.modjam.content.commands.arguments.AugmentSlotArgumentType;
import com.portingdeadmods.modjam.content.commands.arguments.AugmentTypeArgumentType;
import com.portingdeadmods.modjam.content.recipes.ItemEtchingRecipe;
import com.portingdeadmods.modjam.data.MJDataAttachments;
import com.portingdeadmods.modjam.registries.MJFluidTypes;
import com.portingdeadmods.modjam.utils.AugmentHelper;
import com.portingdeadmods.modjam.utils.ParticlesUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public final class MJEvents {

    @EventBusSubscriber(modid = ModJam.MODID, bus = EventBusSubscriber.Bus.GAME)
    public static class Game {
        private static final Object2IntMap<ItemEntity> activeTransformations = new Object2IntOpenHashMap<>();

        @SubscribeEvent
        public static void onItemEntityTick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof ItemEntity itemEntity) {
                Level level = itemEntity.level();

                if (itemEntity.isInFluidType(MJFluidTypes.ETCHING_ACID_FLUID_TYPE.get())) {
                    processItemEtching(itemEntity, level);
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
            Player player = event.getEntity();
            Map<AugmentSlot, Augment> augments = player.getData(MJDataAttachments.AUGMENTS);
            Map<AugmentSlot, CompoundTag> augmentsExtraData = player.getData(MJDataAttachments.AUGMENTS_EXTRA_DATA);
            for (AugmentSlot augmentSlot : augments.keySet()) {
                Augment augment = augments.get(augmentSlot);
                augment.setPlayer(player);
                CompoundTag nbt = augmentsExtraData.get(augmentSlot);
                if (nbt != null) {
                    augment.deserializeNBT(player.level().registryAccess(), nbt);
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Post event) {
            Player player = event.getEntity();
            int changedIndex = player.getData(MJDataAttachments.AUGMENT_DATA_CHANGED);
            if (changedIndex != -1) {
                Map<AugmentSlot, Augment> augments = AugmentHelper.getAugments(player);
                Map<AugmentSlot, CompoundTag> augmentsExtraData = AugmentHelper.getAugmentsData(player);
                AugmentSlot changedSlot = MJRegistries.AUGMENT_SLOT.byId(changedIndex);
                CompoundTag tag = augments.get(changedSlot).serializeNBT(player.level().registryAccess());
                AugmentHelper.setAugmentExtraData(player, changedSlot, tag);
                player.setData(MJDataAttachments.AUGMENT_DATA_CHANGED, -1);
            }
        }

        @SubscribeEvent
        public static void onBreakBlock(PlayerEvent.BreakSpeed event) {
            Player player = event.getEntity();
            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() instanceof IPowerItem powerItem) {
                IPowerStorage powerStorage = stack.getCapability(MJCapabilities.PowerStorage.ITEM);
                if (powerStorage.getPowerStored() <= 0) {
                    event.setCanceled(true);
                }
            }
        }
        //TODO remove this shit
        @SubscribeEvent
        public static void onPowerItemTick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof ItemEntity itemEntity) {
                ItemStack stack = itemEntity.getItem();
                if (stack.getItem() instanceof IPowerItem powerItem) {
                    IPowerStorage powerStorage = stack.getCapability(MJCapabilities.PowerStorage.ITEM);
                    if(powerStorage.getPowerStored() < powerStorage.getPowerCapacity()) {
                        powerStorage.tryFillPower(1,false);
                    }
                }
            }
        }

        private static void processItemEtching(ItemEntity itemEntity, Level level) {
            ItemStack stack = itemEntity.getItem();

            if (!activeTransformations.containsKey(itemEntity)) {
                Optional<ItemEtchingRecipe> optionalRecipe = getEtchingRecipe(stack, level);
                if (optionalRecipe.isPresent()) {
                    activeTransformations.put(itemEntity, 0);
                }
            } else {
                int etchingTime = activeTransformations.getInt(itemEntity);

                if (etchingTime >= 100) {
                    Optional<ItemEtchingRecipe> optionalRecipe = getEtchingRecipe(stack, level);
                    if (optionalRecipe.isPresent()) {
                        transformItem(itemEntity, optionalRecipe.get(), level);
                    }
                    activeTransformations.remove(itemEntity);
                } else {
                    activeTransformations.put(itemEntity, etchingTime + 1);
                    // Optionally spawn particles while etching
                    ParticlesUtils.spawnParticlesAroundItem(itemEntity, level, ParticleTypes.FLAME);
                }
            }
        }

        private static Optional<ItemEtchingRecipe> getEtchingRecipe(ItemStack stack, Level level) {
            return level.getRecipeManager()
                    .getRecipeFor(ItemEtchingRecipe.Type.INSTANCE, new SingleRecipeInput(stack), level)
                    .map(RecipeHolder::value);
        }

        private static void transformItem(ItemEntity itemEntity, ItemEtchingRecipe recipe, Level level) {
            Vec3 position = itemEntity.position();

            ItemStack resultStack = recipe.getResultItem(level.registryAccess()).copy();
            resultStack.setCount(itemEntity.getItem().getCount());

            itemEntity.discard();

            ItemEntity newItemEntity = new ItemEntity(level, position.x, position.y, position.z, resultStack);
            level.addFreshEntity(newItemEntity);
        }
    }

    @EventBusSubscriber(modid = ModJam.MODID, bus = EventBusSubscriber.Bus.MOD)
    public static class Mod {
        @SubscribeEvent
        public static void onRegisterAugments(RegisterEvent event) {
            Registry<AugmentSlot> slotRegistry = event.getRegistry(MJRegistries.AUGMENT_SLOT.key());
            if (slotRegistry != null) {
                AugmentSlotArgumentType.suggestions = slotRegistry.keySet().stream().map(Objects::toString).collect(Collectors.toSet());
            }

            Registry<AugmentType<?>> augmentRegistry = event.getRegistry(MJRegistries.AUGMENT_TYPE.key());
            if (augmentRegistry != null) {
                AugmentTypeArgumentType.suggestions = augmentRegistry.keySet().stream().map(Objects::toString).collect(Collectors.toSet());
            }
        }
    }
}
