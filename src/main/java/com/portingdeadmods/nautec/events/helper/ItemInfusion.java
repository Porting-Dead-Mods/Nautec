package com.portingdeadmods.nautec.events.helper;

import com.portingdeadmods.nautec.api.items.IPowerItem;
import com.portingdeadmods.nautec.data.NTDataComponentsUtils;
import com.portingdeadmods.nautec.utils.ParticleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.PowerParticleOption;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

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

    private static final int MAX_INFUSION_TIME = 150;
    private static final int PARTICLE_INTERVAL = 5;
    private static final int SOUND_INTERVAL = 50;
    private static final float SOUND_VOLUME = 1.0F;
    private static final float SOUND_PITCH = 1.0F;

    private static final Map<ItemEntity, ItemInfusion> activeInfusions = new HashMap<>();

    public static void processPowerItemInfusion(ItemEntity itemEntity, Level level) {
        ItemStack stack = itemEntity.getItem();
        if (!(stack.getItem() instanceof IPowerItem)) {
            return;
        }

        if (NTDataComponentsUtils.isInfused(stack)) {
            return;
        }

        if (!activeInfusions.containsKey(itemEntity)) {
            BlockPos originalFluidPos = itemEntity.blockPosition();
            activeInfusions.put(itemEntity, new ItemInfusion(originalFluidPos));
        } else {
            ItemInfusion infusionData = activeInfusions.get(itemEntity);

            if (infusionData.getInfusionProgress() >= MAX_INFUSION_TIME) {
                NTDataComponentsUtils.setInfusedStatus(stack, true);

                spawnCompletionEffects(itemEntity, level);

                activeInfusions.remove(itemEntity);

                BlockPos originalFluidPos = infusionData.getOriginalFluidPos();
                if (level.getBlockState(originalFluidPos).getFluidState().isSource()) {
                    level.setBlock(itemEntity.getOnPos(), Blocks.AIR.defaultBlockState(), 11);
                }
            } else {
                infusionData.incrementInfusionProgress();

                if (infusionData.getInfusionProgress() % PARTICLE_INTERVAL == 0 && level.isClientSide()) {
                    ParticleUtils.spawnParticlesAroundItem(itemEntity, level, ParticleTypes.ENCHANT);
                }

                if (infusionData.getInfusionProgress() % SOUND_INTERVAL == 0) {
                    level.playSound(null, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(),
                            SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, SOUND_VOLUME, SOUND_PITCH);
                }
            }
        }
    }

    private static void spawnCompletionEffects(ItemEntity itemEntity, Level level) {
        if (level.isClientSide()) {
            ParticleUtils.spawnParticlesAroundItem(itemEntity, level, PowerParticleOption.create(ParticleTypes.DRAGON_BREATH, 1.0F));
        }

        level.playSound(null, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(),
                SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, SOUND_VOLUME, SOUND_PITCH);
    }
}
