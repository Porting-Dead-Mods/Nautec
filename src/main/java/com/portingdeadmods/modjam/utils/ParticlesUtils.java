package com.portingdeadmods.modjam.utils;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;

public class ParticlesUtils {

    private static final int PARTICLE_COUNT = 20; // Number of particles to spawn
    private static final double PARTICLE_RADIUS = 0.5; // Radius of the sphere around the item
    private static final int PARTICLE_DELAY = 5; // Number of ticks between particle spawns

    private static int particleTicks = 0;

    public static void spawnParticles(ItemEntity itemEntity, Level level, ParticleOptions particlesTypes) {
        // Spawn particles around the item entity in a spherical pattern
        if (particleTicks % PARTICLE_DELAY == 0) { // Control particle spawn rate
            for (int i = 0; i < PARTICLE_COUNT; i++) {
                double theta = level.random.nextDouble() * Math.PI * 2; // Random angle
                double phi = level.random.nextDouble() * Math.PI; // Random angle

                // Spherical coordinates to Cartesian coordinates
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

}
