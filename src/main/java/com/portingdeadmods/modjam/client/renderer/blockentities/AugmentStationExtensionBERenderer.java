package com.portingdeadmods.modjam.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.portingdeadmods.modjam.api.client.renderer.augments.AugmentRenderer;
import com.portingdeadmods.modjam.api.client.renderer.robotArms.RobotArmRenderer;
import com.portingdeadmods.modjam.client.model.block.RobotArmModel;
import com.portingdeadmods.modjam.content.blockentities.multiblock.part.AugmentationStationExtensionBlockEntity;
import com.portingdeadmods.modjam.content.items.RobotArmItem;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.Function;

public class AugmentStationExtensionBERenderer implements BlockEntityRenderer<AugmentationStationExtensionBlockEntity> {
    private static final Object2ObjectMap<RobotArmItem, Function<AugmentRenderer.Context, ? extends RobotArmRenderer>> RENDERER_PROVIDERS = new Object2ObjectOpenHashMap<>();
    private static final Object2ObjectMap<RobotArmItem, RobotArmRenderer> RENDERERS = new Object2ObjectOpenHashMap<>();

    public AugmentStationExtensionBERenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(AugmentationStationExtensionBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getItemHandler().getStackInSlot(1).getItem() instanceof RobotArmItem robotArmItem) {
            RENDERERS.get(robotArmItem).render(blockEntity, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
        }
    }

    public static void createRenderers() {
        Iterable<RobotArmItem> items = RENDERER_PROVIDERS.keySet();
        AugmentRenderer.Context ctx = new AugmentRenderer.Context(Minecraft.getInstance().getEntityModels());
        for (RobotArmItem item : items) {
            RENDERERS.put(item, RENDERER_PROVIDERS.get(item).apply(ctx));
        }
    }

    public static void registerRenderer(RobotArmItem item, Function<AugmentRenderer.Context, ? extends RobotArmRenderer> rendererConstructor) {
        RENDERER_PROVIDERS.put(item, rendererConstructor);
    }
}
