package com.portingdeadmods.nautec.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public final class ParticleUtils {

    private static final int PARTICLE_COUNT = 20; // Number of particles to spawn
    private static final double PARTICLE_RADIUS = 0.5; // Radius of the sphere around the item
    private static final int PARTICLE_DELAY = 5; // Number of ticks between particle spawns

    private static int particleTicks = 0;

    // Method for spawning particles around an item entity
    public static void spawnParticlesAroundItem(ItemEntity itemEntity, Level level, ParticleOptions particlesTypes) {
        assert level.isClientSide;
        if (particleTicks % PARTICLE_DELAY == 0) {
            for (int i = 0; i < PARTICLE_COUNT; i++) {
                double theta = level.random.nextDouble() * Math.PI * 2;
                double phi = level.random.nextDouble() * Math.PI;

                double xOffset = PARTICLE_RADIUS * Math.sin(phi) * Math.cos(theta);
                double yOffset = PARTICLE_RADIUS * Math.cos(phi);
                double zOffset = PARTICLE_RADIUS * Math.sin(phi) * Math.sin(theta);

                level.addParticle(particlesTypes,
                        itemEntity.getX() + xOffset,
                        itemEntity.getY() + yOffset,
                        itemEntity.getZ() + zOffset,
                        0, 0, 0);
            }
        }

        particleTicks++;
    }

    // Method for spawning particles around a block
    public static void spawnParticlesAroundBlock(BlockPos blockPos, Level level, ParticleOptions particlesTypes) {
        assert level.isClientSide;

        if (particleTicks % PARTICLE_DELAY == 0) {
            for (int i = 0; i < PARTICLE_COUNT; i++) {
                double theta = level.random.nextDouble() * Math.PI * 2;
                double phi = level.random.nextDouble() * Math.PI;

                double xOffset = PARTICLE_RADIUS * Math.sin(phi) * Math.cos(theta);
                double yOffset = PARTICLE_RADIUS * Math.cos(phi);
                double zOffset = PARTICLE_RADIUS * Math.sin(phi) * Math.sin(theta);

                level.addParticle(particlesTypes,
                        blockPos.getX() + 0.5 + xOffset, // Center of the block
                        blockPos.getY() + 0.5 + yOffset,
                        blockPos.getZ() + 0.5 + zOffset,
                        0, 0, 0);
            }
        }

        particleTicks++;
    }

    public static void spawnBreakParticle(BlockPos pos, Block block, int count) {
        assert Minecraft.getInstance().level != null && Minecraft.getInstance().level.isClientSide;
        for (int i = 0; i < count; i++) {
            Minecraft.getInstance().particleEngine.add(new TerrainParticle(Minecraft.getInstance().level, pos.getX() + 0.5f, pos.above().getY(), pos.getZ() + 0.5f,
                    0 + ((double) i / 10), 0 + ((double) i / 10), 0 + ((double) i / 10), block.defaultBlockState()));
        }
    }
}
