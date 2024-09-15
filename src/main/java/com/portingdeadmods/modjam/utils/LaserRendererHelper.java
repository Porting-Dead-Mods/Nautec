package com.portingdeadmods.modjam.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.portingdeadmods.modjam.api.blockentities.LaserBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public final class LaserRendererHelper {
    public static final ResourceLocation BEAM_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/beacon_beam.png");
    private static final ResourceLocation GUARDIAN_BEAM_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/guardian_beam.png");
    private static final RenderType BEAM_RENDER_TYPE = RenderType.entityCutoutNoCull(GUARDIAN_BEAM_LOCATION);

    public static <T extends LaserBlockEntity> void renderOuterBeam(T blockEntity, BlockPos originPos, BlockPos targetPos, Direction direction, PoseStack poseStack, MultiBufferSource bufferSource, float partialTicks) {
        float f = blockEntity.getLaserScale(partialTicks);
        float f1 = blockEntity.getClientLaserTime() + (partialTicks * 24);
        float f2 = f1 * 0.5F % 1.0F;
        float f3 = 0.5f;

        int red = 94;
        int green = 133;
        int blue = 164;

        poseStack.pushPose();
        {
            if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                poseStack.translate(0.5F, f3, 0.0F);
            } else if (direction == Direction.EAST || direction == Direction.WEST) {
                poseStack.translate(0.0F, f3, 0.5F);
            } else {
                poseStack.translate(0.5F, 0, 0.5F);
            }
            Vec3 vec3 = targetPos.getCenter();
            Vec3 vec31 = originPos.getCenter();
            Vec3 vec32 = vec3.subtract(vec31);
            float f4 = (float) (vec32.length() + 1.0);
            vec32 = vec32.normalize();
            float f5 = (float) Math.acos(vec32.y);
            float f6 = (float) Math.atan2(vec32.z, vec32.x);
            poseStack.mulPose(Axis.YP.rotationDegrees(((float) (Math.PI / 2) - f6) * (180.0F / (float) Math.PI)));
            poseStack.mulPose(Axis.XP.rotationDegrees(f5 * (180.0F / (float) Math.PI)));
            float f7 = f1 * 0.05F * -1.5F;
            float f11 = Mth.cos(f7 + (float) (Math.PI * 3.0 / 4.0)) * 0.282F;
            float f12 = Mth.sin(f7 + (float) (Math.PI * 3.0 / 4.0)) * 0.282F;
            float f13 = Mth.cos(f7 + (float) (Math.PI / 4)) * 0.282F;
            float f14 = Mth.sin(f7 + (float) (Math.PI / 4)) * 0.282F;
            float f15 = Mth.cos(f7 + ((float) Math.PI * 5.0F / 4.0F)) * 0.282F;
            float f16 = Mth.sin(f7 + ((float) Math.PI * 5.0F / 4.0F)) * 0.282F;
            float f17 = Mth.cos(f7 + ((float) Math.PI * 7.0F / 4.0F)) * 0.282F;
            float f18 = Mth.sin(f7 + ((float) Math.PI * 7.0F / 4.0F)) * 0.282F;
            float f19 = Mth.cos(f7 + (float) Math.PI) * 0.2F;
            float f20 = Mth.sin(f7 + (float) Math.PI) * 0.2F;
            float f21 = Mth.cos(f7 + 0.0F) * 0.2F;
            float f22 = Mth.sin(f7 + 0.0F) * 0.2F;
            float f23 = Mth.cos(f7 + (float) (Math.PI / 2)) * 0.2F;
            float f24 = Mth.sin(f7 + (float) (Math.PI / 2)) * 0.2F;
            float f25 = Mth.cos(f7 + (float) (Math.PI * 3.0 / 2.0)) * 0.2F;
            float f26 = Mth.sin(f7 + (float) (Math.PI * 3.0 / 2.0)) * 0.2F;
            float f27 = 0.0F;
            float f28 = 0.4999F;
            float f29 = -1.0F + f2;
            float f30 = f4 * 2.5F + f29;
            VertexConsumer vertexconsumer = bufferSource.getBuffer(BEAM_RENDER_TYPE);
            PoseStack.Pose posestack$pose = poseStack.last();
            outerBeamVertex(red, green, blue, f4, f19, f20, f21, f22, f29, f30, vertexconsumer, posestack$pose);
            outerBeamVertex(red, green, blue, f4, f23, f24, f25, f26, f29, f30, vertexconsumer, posestack$pose);
            float f31 = 0.0F;
            if (blockEntity.getLevel().getGameTime() % 2 == 0) {
                f31 = 0.5F;
            }

            vertex(vertexconsumer, posestack$pose, f11, f4, f12, red, green, blue, 0.5F, f31 + 0.5F);
            vertex(vertexconsumer, posestack$pose, f13, f4, f14, red, green, blue, 1.0F, f31 + 0.5F);
            vertex(vertexconsumer, posestack$pose, f17, f4, f18, red, green, blue, 1.0F, f31);
            vertex(vertexconsumer, posestack$pose, f15, f4, f16, red, green, blue, 0.5F, f31);
        }
        poseStack.popPose();
    }

    private static void outerBeamVertex(int red, int green, int blue, float f4, float f19, float f20, float f21, float f22, float f29, float f30, VertexConsumer vertexconsumer, PoseStack.Pose posestack$pose) {
        vertex(vertexconsumer, posestack$pose, f19, f4, f20, red, green, blue, 0.4999F, f30);
        vertex(vertexconsumer, posestack$pose, f19, 0.0F, f20, red, green, blue, 0.4999F, f29);
        vertex(vertexconsumer, posestack$pose, f21, 0.0F, f22, red, green, blue, 0.0F, f29);
        vertex(vertexconsumer, posestack$pose, f21, f4, f22, red, green, blue, 0.0F, f30);
    }

    private static void vertex(
            VertexConsumer consumer,
            PoseStack.Pose pose,
            float x,
            float y,
            float z,
            int red,
            int green,
            int blue,
            float u,
            float v
    ) {
        consumer.addVertex(pose, x, y, z)
                .setColor(red, green, blue, 255)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(15728880)
                .setNormal(pose, 0.0F, 1.0F, 0.0F);
    }

    public static void renderInnerBeam(
            PoseStack poseStack, MultiBufferSource bufferSource, float partialTick, long gameTime, int yOffset, int height, int color
    ) {
        renderInnerBeam(poseStack, bufferSource, BEAM_LOCATION, partialTick, 1.0F, gameTime, yOffset, height, color, 0.2F, 0.25F);
    }

    public static void renderInnerBeam(
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            ResourceLocation beamLocation,
            float partialTick,
            float textureScale,
            long gameTime,
            int yOffset,
            int height,
            int color,
            float beamRadius,
            float glowRadius
    ) {
        int i = yOffset + height;
        poseStack.pushPose();
        poseStack.translate(0.5, 0.0, 0.5);
        float f = (float) Math.floorMod(gameTime, 40) + partialTick;
        float f1 = height < 0 ? f : -f;
        float f2 = Mth.frac(f1 * 0.2F - (float) Mth.floor(f1 * 0.1F));
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(f * 2.25F - 45.0F));
        float f3;
        float f5;
        float f6 = -beamRadius;
        float f9 = -beamRadius;
        float f12 = -1.0F + f2;
        float f13 = (float) height * textureScale * (0.5F / beamRadius) + f12;
        renderPart(
                poseStack,
                bufferSource.getBuffer(RenderType.beaconBeam(beamLocation, false)),
                color,
                yOffset,
                i,
                0.0F,
                beamRadius,
                beamRadius,
                0.0F,
                f6,
                0.0F,
                0.0F,
                f9,
                0.0F,
                1.0F,
                f13,
                f12
        );
        poseStack.popPose();
        f3 = -glowRadius;
        float f4 = -glowRadius;
        f5 = -glowRadius;
        f6 = -glowRadius;
        f12 = -1.0F + f2;
        f13 = (float) height * textureScale + f12;
        renderPart(
                poseStack,
                bufferSource.getBuffer(RenderType.beaconBeam(beamLocation, true)),
                FastColor.ARGB32.color(32, color),
                yOffset,
                i,
                f3,
                f4,
                glowRadius,
                f5,
                f6,
                glowRadius,
                glowRadius,
                glowRadius,
                0.0F,
                1.0F,
                f13,
                f12
        );
        poseStack.popPose();
    }

    private static void renderPart(
            PoseStack poseStack,
            VertexConsumer consumer,
            int color,
            int minY,
            int maxY,
            float x1,
            float z1,
            float x2,
            float z2,
            float x3,
            float z3,
            float x4,
            float z4,
            float minU,
            float maxU,
            float minV,
            float maxV
    ) {
        PoseStack.Pose posestack$pose = poseStack.last();
        renderQuad(
                posestack$pose, consumer, color, minY, maxY, x1, z1, x2, z2, minU, maxU, minV, maxV
        );
        renderQuad(
                posestack$pose, consumer, color, minY, maxY, x4, z4, x3, z3, minU, maxU, minV, maxV
        );
        renderQuad(
                posestack$pose, consumer, color, minY, maxY, x2, z2, x4, z4, minU, maxU, minV, maxV
        );
        renderQuad(
                posestack$pose, consumer, color, minY, maxY, x3, z3, x1, z1, minU, maxU, minV, maxV
        );
    }

    private static void renderQuad(
            PoseStack.Pose pose,
            VertexConsumer consumer,
            int color,
            int minY,
            int maxY,
            float minX,
            float minZ,
            float maxX,
            float maxZ,
            float minU,
            float maxU,
            float minV,
            float maxV
    ) {
        addVertex(pose, consumer, color, maxY, minX, minZ, maxU, minV);
        addVertex(pose, consumer, color, minY, minX, minZ, maxU, maxV);
        addVertex(pose, consumer, color, minY, maxX, maxZ, minU, maxV);
        addVertex(pose, consumer, color, maxY, maxX, maxZ, minU, minV);
    }

    private static void addVertex(
            PoseStack.Pose pose, VertexConsumer consumer, int color, int y, float x, float z, float u, float v
    ) {
        consumer.addVertex(pose, x, (float) y, z)
                .setColor(color)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(15728880)
                .setNormal(pose, 0.0F, 1.0F, 0.0F);
    }
}
