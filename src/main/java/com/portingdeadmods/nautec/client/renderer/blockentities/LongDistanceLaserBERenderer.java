package com.portingdeadmods.nautec.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.portingdeadmods.nautec.content.blockentities.LongDistanceLaserBlockEntity;
import com.portingdeadmods.nautec.utils.LaserRendererHelper;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.FastColor;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class LongDistanceLaserBERenderer implements BlockEntityRenderer<LongDistanceLaserBlockEntity> {
    public LongDistanceLaserBERenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(LongDistanceLaserBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockPos originPos = blockEntity.getBlockPos();
        Object2IntMap<Direction> laserDistances = blockEntity.getLaserDistances();
        for (Direction direction : blockEntity.getLaserOutputs()) {
            int laserDistance = laserDistances.getOrDefault(direction, 0);
            BlockPos targetPos = originPos.relative(direction, laserDistance - 1);
            if (laserDistance != 0 && blockEntity.shouldRender(direction)) {
                LaserRendererHelper.renderOuterBeam(blockEntity, originPos, targetPos, direction, poseStack, bufferSource, partialTick);

                poseStack.pushPose();
                {
                    poseStack.mulPose(direction.getRotation());
                    poseStack.scale(0.25f, 1, 0.25f);
                    // These are completely random values that I got from testing ._.
                    switch (direction) {
                        case UP -> poseStack.translate(1.5f, 0, 1.5f);
                        case DOWN, SOUTH, WEST -> poseStack.translate(1.5f, 0, -2.5f);
                        case NORTH, EAST -> poseStack.translate(-2.5f, 0, -2.5f);
                    }
                    LaserRendererHelper.renderInnerBeam(poseStack, bufferSource, partialTick, blockEntity.getLevel().getGameTime(),
                            0, laserDistance, FastColor.ARGB32.color(202, 214, 224));
                }
                poseStack.popPose();
            }
        }
    }

    @Override
    public boolean shouldRenderOffScreen(LongDistanceLaserBlockEntity blockEntity) {
        return true;
    }

    @Override
    public @NotNull AABB getRenderBoundingBox(LongDistanceLaserBlockEntity blockEntity) {
        BlockPos blockPos = blockEntity.getBlockPos();
        AABB box = new AABB(blockPos);
        Object2IntMap<Direction> laserDistances = blockEntity.getLaserDistances();
        for (Direction direction : blockEntity.getLaserOutputs()) {
            int distance = laserDistances.getOrDefault(direction, 0);
            BlockPos pos = blockPos.relative(direction, distance);
            BlockPos relative = blockPos.subtract(pos);
            box = box.expandTowards(-relative.getX(), -relative.getY(), -relative.getZ());
        }
        return box;
    }
}
