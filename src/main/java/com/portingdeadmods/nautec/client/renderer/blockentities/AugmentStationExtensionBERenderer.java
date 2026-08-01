package com.portingdeadmods.nautec.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.portingdeadmods.nautec.api.client.renderer.robotArms.RobotArmRenderState;
import com.portingdeadmods.nautec.api.client.renderer.robotArms.RobotArmRenderer;
import com.portingdeadmods.nautec.content.blockentities.multiblock.part.AugmentationStationExtensionBlockEntity;
import com.portingdeadmods.nautec.content.items.RobotArmItem;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class AugmentStationExtensionBERenderer implements BlockEntityRenderer<AugmentationStationExtensionBlockEntity, RobotArmRenderState> {
    private static final Object2ObjectMap<RobotArmItem, Function<EntityModelSet, ? extends RobotArmRenderer>> RENDERER_PROVIDERS = new Object2ObjectOpenHashMap<>();
    private static final Object2ObjectMap<RobotArmItem, RobotArmRenderer> RENDERERS = new Object2ObjectOpenHashMap<>();

    public AugmentStationExtensionBERenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public RobotArmRenderState createRenderState() {
        return new RobotArmRenderState();
    }

    @Override
    public void extractRenderState(AugmentationStationExtensionBlockEntity blockEntity, RobotArmRenderState state, float partialTick, Vec3 cameraPos, ModelFeatureRenderer.@Nullable CrumblingOverlay crumbling) {
        BlockEntityRenderState.extractBase(blockEntity, state, crumbling);
        state.armItem = blockEntity.getItemStackHandler().getStackInSlot(1).getItem() instanceof RobotArmItem robotArmItem ? robotArmItem : null;
        if (state.armItem != null) {
            RobotArmRenderer robotArmRenderer = RENDERERS.get(state.armItem);
            if (robotArmRenderer != null) {
                robotArmRenderer.extractRenderState(blockEntity, state, partialTick);
            }
        }
    }

    @Override
    public void submit(RobotArmRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        if (state.armItem != null) {
            RobotArmRenderer robotArmRenderer = RENDERERS.get(state.armItem);
            if (robotArmRenderer != null) {
                robotArmRenderer.submit(state, poseStack, collector);
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
