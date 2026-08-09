package com.portingdeadmods.nautec.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.client.model.entity.AbyssalMawModel;
import com.portingdeadmods.nautec.client.model.entity.LanternJellyModel;
import com.portingdeadmods.nautec.client.model.entity.SiltSkipperModel;
import com.portingdeadmods.nautec.client.model.entity.VentCrawlerModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public final class NTMobRenderers {
    public static final ModelLayerLocation SILT_SKIPPER_LAYER = layer("silt_skipper");
    public static final ModelLayerLocation LANTERN_JELLY_LAYER = layer("lantern_jelly");
    public static final ModelLayerLocation VENT_CRAWLER_LAYER = layer("vent_crawler");
    public static final ModelLayerLocation ABYSSAL_MAW_LAYER = layer("abyssal_maw");

    private static ModelLayerLocation layer(String name) {
        return new ModelLayerLocation(Nautec.rl(name), "main");
    }

    private static Identifier texture(String name) {
        return Nautec.rl("textures/entity/" + name + ".png");
    }

    public static class SiltSkipperRenderer extends SimpleMobRenderer<Mob, SiltSkipperModel> {
        public SiltSkipperRenderer(EntityRendererProvider.Context context) {
            super(context, new SiltSkipperModel(context.bakeLayer(SILT_SKIPPER_LAYER)), 0.3F, texture("silt_skipper"));
        }

        @Override
        protected void setupRotations(LivingEntityRenderState state, PoseStack poseStack, float bodyRot, float entityScale) {
            super.setupRotations(state, poseStack, bodyRot, entityScale);
            poseStack.mulPose(Axis.YP.rotationDegrees(4.0F * Mth.sin(0.6F * state.ageInTicks)));
            if (!state.isInWater) {
                poseStack.translate(0.1F, 0.1F, -0.1F);
                poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
            }
        }
    }

    public static class LanternJellyRenderer extends SimpleMobRenderer<Mob, LanternJellyModel> {
        public LanternJellyRenderer(EntityRendererProvider.Context context) {
            super(context, new LanternJellyModel(context.bakeLayer(LANTERN_JELLY_LAYER)), 0.5F, texture("lantern_jelly"));
            this.addLayer(new GlowOverlayLayer<>(this, texture("lantern_jelly_glow")));
        }
    }

    public static class VentCrawlerRenderer extends SimpleMobRenderer<Mob, VentCrawlerModel> {
        public VentCrawlerRenderer(EntityRendererProvider.Context context) {
            super(context, new VentCrawlerModel(context.bakeLayer(VENT_CRAWLER_LAYER)), 0.5F, texture("vent_crawler"));
            this.addLayer(new GlowOverlayLayer<>(this, texture("vent_crawler_glow")));
        }
    }

    public static class AbyssalMawRenderer extends SimpleMobRenderer<Mob, AbyssalMawModel> {
        public AbyssalMawRenderer(EntityRendererProvider.Context context) {
            super(context, new AbyssalMawModel(context.bakeLayer(ABYSSAL_MAW_LAYER)), 0.8F, texture("abyssal_maw"));
            this.addLayer(new GlowOverlayLayer<>(this, texture("abyssal_maw_glow")));
        }
    }

    public static class SimpleMobRenderer<T extends Mob, M extends EntityModel<LivingEntityRenderState>>
            extends MobRenderer<T, LivingEntityRenderState, M> {
        private final Identifier texture;

        public SimpleMobRenderer(EntityRendererProvider.Context context, M model, float shadowRadius, Identifier texture) {
            super(context, model, shadowRadius);
            this.texture = texture;
        }

        @Override
        public @NotNull Identifier getTextureLocation(@NotNull LivingEntityRenderState state) {
            return this.texture;
        }

        @Override
        public @NotNull LivingEntityRenderState createRenderState() {
            return new LivingEntityRenderState();
        }
    }

    private NTMobRenderers() {
    }
}
