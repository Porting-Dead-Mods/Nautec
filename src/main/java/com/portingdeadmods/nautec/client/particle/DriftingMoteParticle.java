package com.portingdeadmods.nautec.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DriftingMoteParticle extends SingleQuadParticle {
    private final float wobble;

    protected DriftingMoteParticle(ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite,
                                   float rise, float size, int lifetime) {
        super(level, x, y, z, 0.0, 0.0, 0.0, sprite);
        this.setSize(0.02F, 0.02F);
        this.quadSize = size * (this.random.nextFloat() * 0.4F + 0.8F);
        this.lifetime = lifetime;
        this.hasPhysics = false;
        this.xd = (this.random.nextFloat() - 0.5F) * 0.005F;
        this.yd = rise * (this.random.nextFloat() * 0.5F + 0.75F);
        this.zd = (this.random.nextFloat() - 0.5F) * 0.005F;
        this.wobble = this.random.nextFloat() * 6.2831855F;
    }

    @Override
    public SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.lifetime-- <= 0) {
            this.remove();
            return;
        }

        double sway = Math.cos(this.wobble + this.age * 0.1) * 0.0015;
        this.move(this.xd + sway, this.yd, this.zd);
        this.xd *= 0.96;
        this.zd *= 0.96;
        this.alpha = Math.min(1.0F, this.lifetime / 12.0F);
    }

    @OnlyIn(Dist.CLIENT)
    public record Provider(SpriteSet sprites, float red, float green, float blue,
                           float rise, float size, int minLifetime, int maxLifetime) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType options, ClientLevel level,
                                       double x, double y, double z, double xa, double ya, double za, RandomSource random) {
            int lifetime = minLifetime + random.nextInt(Math.max(1, maxLifetime - minLifetime));
            DriftingMoteParticle particle = new DriftingMoteParticle(level, x, y, z, sprites.get(random), rise, size, lifetime);
            particle.setColor(red, green, blue);
            return particle;
        }
    }
}
