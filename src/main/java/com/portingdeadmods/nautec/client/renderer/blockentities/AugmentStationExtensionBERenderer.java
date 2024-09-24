package com.portingdeadmods.nautec.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.portingdeadmods.nautec.api.client.renderer.augments.AugmentRenderer;
import com.portingdeadmods.nautec.api.client.renderer.robotArms.RobotArmRenderer;
import com.portingdeadmods.nautec.content.blockentities.multiblock.part.AugmentationStationExtensionBlockEntity;
import com.portingdeadmods.nautec.content.items.RobotArmItem;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import java.util.function.Function;

public class AugmentStationExtensionBERenderer implements BlockEntityRenderer<AugmentationStationExtensionBlockEntity> {
    private static final Object2ObjectMap<RobotArmItem, Function<EntityModelSet, ? extends RobotArmRenderer>> RENDERER_PROVIDERS = new Object2ObjectOpenHashMap<>();
    private static final Object2ObjectMap<RobotArmItem, RobotArmRenderer> RENDERERS = new Object2ObjectOpenHashMap<>();

    public AugmentStationExtensionBERenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(AugmentationStationExtensionBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getItemHandler().getStackInSlot(1).getItem() instanceof RobotArmItem robotArmItem) {
            RobotArmRenderer robotArmRenderer = RENDERERS.get(robotArmItem);
            if (robotArmRenderer != null) {
                robotArmRenderer.render(blockEntity, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
            }
        }
    }

    public static void createRenderers() {
        Iterable<RobotArmItem> items = RENDERER_PROVIDERS.keySet();
        EntityModelSet ctx = Minecraft.getInstance().getEntityModels();
        for (RobotArmItem item : items) {
            RENDERERS.put(item, RENDERER_PROVIDERS.get(item).apply(ctx));
        }
    }

    public static void registerRenderer(RobotArmItem item, Function<EntityModelSet, ? extends RobotArmRenderer> rendererConstructor) {
        RENDERER_PROVIDERS.put(item, rendererConstructor);
    }
}
