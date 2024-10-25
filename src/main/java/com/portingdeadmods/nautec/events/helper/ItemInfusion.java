package com.portingdeadmods.nautec.events.helper;

import com.portingdeadmods.nautec.api.items.IPowerItem;
import com.portingdeadmods.nautec.data.NTDataComponentsUtils;
import com.portingdeadmods.nautec.utils.ParticleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;

import java.util.HashMap;
import java.util.Map;

public class ItemInfusion {

    private int infusionProgress;
    private final BlockPos originalFluidPos;

    public ItemInfusion(BlockPos originalFluidPos) {
        this.infusionProgress = 0;
        this.originalFluidPos = originalFluidPos;
    }

    public int getInfusionProgress() {
        return infusionProgress;
    }

    public void incrementInfusionProgress() {
        this.infusionProgress++;
    }

    public BlockPos getOriginalFluidPos() {
        return originalFluidPos;
    }

    // Define infusion constants
    private static final int MAX_INFUSION_TIME = 150;  // Max time for the infusion process
    private static final int PARTICLE_INTERVAL = 5;    // Particle spawning interval
    private static final int SOUND_INTERVAL = 50;      // Sound effect interval
    private static final float SOUND_VOLUME = 1.0F;
    private static final float SOUND_PITCH = 1.0F;

    // Active infusion map: stores InfusionData for each itemEntity
    private static final Map<ItemEntity, ItemInfusion> activeInfusions = new HashMap<>();

    public static void processPowerItemInfusion(ItemEntity itemEntity, Level level) {
        // Check if the item is an IPowerItem
        ItemStack stack = itemEntity.getItem();
        if (!(stack.getItem() instanceof IPowerItem)) {
            return;
        }

        // Only start infusion if the item is NOT already infused
        if (NTDataComponentsUtils.isInfused(stack)) {
            return;
        }

        // Start infusion if not already in progress
        if (!activeInfusions.containsKey(itemEntity)) {
            BlockPos originalFluidPos = itemEntity.blockPosition();
            activeInfusions.put(itemEntity, new ItemInfusion(originalFluidPos));
        } else {
            // Get current infusion data
            ItemInfusion infusionData = activeInfusions.get(itemEntity);

            // Check if the infusion is complete
            if (infusionData.getInfusionProgress() >= MAX_INFUSION_TIME) {
                NTDataComponentsUtils.setInfusedStatus(stack, true);

                // Play final effects and sound
                spawnCompletionEffects(itemEntity, level);

                // Remove from active infusions
                activeInfusions.remove(itemEntity);

                // Remove fluid from original position
                BlockPos originalFluidPos = infusionData.getOriginalFluidPos();
                if (level.getBlockState(originalFluidPos).getFluidState().isSource()) {
                    level.setBlock(itemEntity.getOnPos(), Blocks.AIR.defaultBlockState(), 11);
                }
            } else {
                // Continue the infusion process
                infusionData.incrementInfusionProgress();

                // Spawn particles at regular intervals
                if (infusionData.getInfusionProgress() % PARTICLE_INTERVAL == 0) {
                    ParticleUtils.spawnParticlesAroundItem(itemEntity, level, ParticleTypes.ENCHANT);  // Infusion particles
                }

                // Play sound at intervals
                if (infusionData.getInfusionProgress() % SOUND_INTERVAL == 0) {
                    level.playSound(null, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(),
                            SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, SOUND_VOLUME, SOUND_PITCH);
                }
            }
        }
    }

    private static void spawnCompletionEffects(ItemEntity itemEntity, Level level) {
        // Completion particle effects
        ParticleUtils.spawnParticlesAroundItem(itemEntity, level, ParticleTypes.DRAGON_BREATH);

        // Play completion sound
        level.playSound(null, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(),
                SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, SOUND_VOLUME, SOUND_PITCH);
    }
}
