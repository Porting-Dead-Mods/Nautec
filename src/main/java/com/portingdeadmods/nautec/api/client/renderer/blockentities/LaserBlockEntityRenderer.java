package com.portingdeadmods.nautec.api.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
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

public class LaserBlockEntityRenderer<T extends LaserBlockEntity> implements BlockEntityRenderer<T> {
    public LaserBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Object2IntMap<Direction> laserDistances = blockEntity.getLaserDistances();
        for (Direction direction : blockEntity.getLaserOutputs()) {
            int laserDistance = laserDistances.getOrDefault(direction, 0);

            BlockPos originPos = blockEntity.getBlockPos();
            BlockPos targetPos = originPos.relative(direction, laserDistance - 1);
            if (laserDistance > 0 && blockEntity.shouldRender(direction)) {
                LaserRendererHelper.renderOuterBeam(blockEntity, originPos, targetPos, direction, poseStack, bufferSource, partialTick);

                poseStack.pushPose();
                {
                    poseStack.mulPose(direction.getRotation());
                    poseStack.scale(0.125f, 1, 0.125f);
                    // These are completely random values that I got from testing ._.
                    switch (direction) {
                        case UP -> poseStack.translate(3.5f, 0, 3.5f);
                        case DOWN, SOUTH, WEST -> poseStack.translate(3.5f, 0, -4.5f);
                        case EAST, NORTH -> poseStack.translate(-4.5f, 0, -4.5f);
                    }

                    int offset = 0;
                    int offset2 = 0;

                    if (direction == Direction.EAST || direction == Direction.SOUTH) {
                        offset = 1;
                    }

                    if (direction == Direction.NORTH || direction == Direction.WEST || direction == Direction.DOWN) {
                        offset2 = 1;
                    }

                    if (direction == Direction.UP) {
                        offset = 1;
                    }

                    LaserRendererHelper.renderInnerBeam(poseStack, bufferSource, partialTick, blockEntity.getLevel().getGameTime(),
                            offset, laserDistance - offset - offset2, FastColor.ARGB32.color(202, 214, 224));
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
