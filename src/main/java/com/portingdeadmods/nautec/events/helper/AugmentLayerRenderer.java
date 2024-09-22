package com.portingdeadmods.nautec.events.helper;

import com.mojang.blaze3d.vertex.PoseStack;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentType;
import com.portingdeadmods.nautec.api.client.renderer.augments.AugmentRenderer;
import com.portingdeadmods.nautec.utils.AugmentHelper;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.player.Player;

public class AugmentLayerRenderer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private static final Object2ObjectMap<AugmentType<?>, AugmentRendererProvider<?>> RENDERER_PROVIDERS = new Object2ObjectOpenHashMap<>();
    private static final Object2ObjectMap<AugmentType<?>, AugmentRenderer<?>> RENDERERS = new Object2ObjectOpenHashMap<>();

    public AugmentLayerRenderer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        Iterable<Augment> augments = getAugments(player);
        for (Augment augment : augments) {
            if (augment != null) {
                renderAugmentModel(poseStack, bufferSource, packedLight, augment);
            }
        }
    }

    private void renderAugmentModel(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Augment augment) {
        AugmentRenderer<Augment> renderer = getRenderer(augment);
        if (renderer != null) {
            renderer.render(augment, poseStack, bufferSource, packedLight);
        }
    }

    @SuppressWarnings("unchecked")
    private static AugmentRenderer<Augment> getRenderer(Augment augment) {
        return (AugmentRenderer<Augment>) RENDERERS.get(augment.getAugmentType());
    }

    private Iterable<Augment> getAugments(Player player) {
        return AugmentHelper.getAugments(player).values();
    }

    public static <T extends Augment> void registerRenderer(AugmentType<T> augmentType, AugmentRendererProvider<T> augmentRenderer) {
        RENDERER_PROVIDERS.put(augmentType, augmentRenderer);
    }

    public static void createRenderers() {
        AugmentRenderer.Context ctx = new AugmentRenderer.Context(Minecraft.getInstance().getEntityModels());
        ObjectSet<AugmentType<?>> augmentTypes = RENDERER_PROVIDERS.keySet();
        for (AugmentType<?> key : augmentTypes) {
            RENDERERS.put(key, RENDERER_PROVIDERS.get(key).create(ctx));
        }
    }

    @FunctionalInterface
    public interface AugmentRendererProvider<T extends Augment> {
        AugmentRenderer<T> create(AugmentRenderer.Context ctx);
    }
}