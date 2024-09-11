package com.portingdeadmods.modjam.api.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.modjam.utils.LaserRendererHelper;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.FastColor;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class LaserBlockEntityRenderer<T extends LaserBlockEntity> implements BlockEntityRenderer<T> {
    public LaserBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
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
                    poseStack.scale(0.125f, 1, 0.125f);
                    // These are completely random values that I got from testing ._.
                    switch (direction) {
                        case UP -> poseStack.translate(3.375f, 0, 3.375f);
                        case DOWN, SOUTH, WEST -> poseStack.translate(3.375f, 0, -4.5f);
                        case NORTH, EAST -> poseStack.translate(-4.5f, 0, -4.5f);
                    }
                    LaserRendererHelper.renderInnerBeam(poseStack, bufferSource, partialTick, blockEntity.getLevel().getGameTime(),
                            0, laserDistance, FastColor.ARGB32.color(202, 214, 224));
                }
                poseStack.popPose();
            }
        }
    }

    @Override
    public boolean shouldRenderOffScreen(T blockEntity) {
        return true;
    }

    @Override
    public @NotNull AABB getRenderBoundingBox(T blockEntity) {
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
