package com.portingdeadmods.nautec.client.renderer.augments.helper;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.portingdeadmods.nautec.content.augments.GuardianEyeAugment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class GuardianEyeRenderHelper {
    private static final ResourceLocation GUARDIAN_BEAM_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/guardian_beam.png");
    private static final RenderType BEAM_RENDER_TYPE = RenderType.entityCutoutNoCull(GUARDIAN_BEAM_LOCATION);

    public static void render(Player entity, GuardianEyeAugment augment, float partialTicks, PoseStack poseStack, MultiBufferSource buffer) {
        Entity targetEntity = augment.getTargetEntity();
        if (targetEntity == null) {
            return; // No target, skip rendering.
        }

        float laserScale = augment.getLaserScale(partialTicks);
        float laserTime = augment.getClientLaserTime() + partialTicks;
        float laserProgress = laserTime * 0.5F % 1.0F;
        float eyeHeight = entity.getEyeHeight();

        poseStack.pushPose();
        poseStack.translate(0.0F, eyeHeight, 0.0F);

        Vec3 targetPos = getPosition(targetEntity, targetEntity.getBbHeight() * 0.5, partialTicks);
        Vec3 entityPos = getPosition(entity, eyeHeight, partialTicks);
        Vec3 direction = targetPos.subtract(entityPos).normalize();

        float distance = (float) (targetPos.subtract(entityPos).length() + 1.0);
        float pitch = (float) Math.acos(direction.y);
        float yaw = (float) Math.atan2(direction.z, direction.x);

        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F - (yaw * (180.0F / (float) Math.PI))));
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch * (180.0F / (float) Math.PI)));

        float wobble = laserTime * 0.05F * -1.5F;
        float intensity = laserScale * laserScale;

        // Precomputed color values for performance
        int red = 64 + (int) (intensity * 191.0F);
        int green = 32 + (int) (intensity * 191.0F);
        int blue = 128 - (int) (intensity * 64.0F);

        // Simplify vertex positions
        float halfPi = (float) Math.PI / 2;
        float[] cosines = {Mth.cos(wobble), Mth.cos(wobble + halfPi), Mth.cos((float) (wobble + Math.PI)), Mth.cos(wobble + 1.5F * (float) Math.PI)};
        float[] sines = {Mth.sin(wobble), Mth.sin(wobble + halfPi), Mth.sin((float) (wobble + Math.PI)), Mth.sin(wobble + 1.5F * (float) Math.PI)};

        VertexConsumer vertexConsumer = buffer.getBuffer(BEAM_RENDER_TYPE);
        PoseStack.Pose pose = poseStack.last();

        for (int i = 0; i < 4; i++) {
            int next = (i + 1) % 4;
            float u1 = i < 2 ? 0.0F : 0.5F;
            float u2 = next < 2 ? 0.0F : 0.5F;

            vertex(vertexConsumer, pose, cosines[i] * 0.2F, distance, sines[i] * 0.2F, red, green, blue, u1, laserProgress + distance);
            vertex(vertexConsumer, pose, cosines[i] * 0.2F, 0.0F, sines[i] * 0.2F, red, green, blue, u1, laserProgress);
            vertex(vertexConsumer, pose, cosines[next] * 0.2F, 0.0F, sines[next] * 0.2F, red, green, blue, u2, laserProgress);
            vertex(vertexConsumer, pose, cosines[next] * 0.2F, distance, sines[next] * 0.2F, red, green, blue, u2, laserProgress + distance);
        }

        poseStack.popPose();
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

    private static Vec3 getPosition(Entity livingEntity, double yOffset, float partialTick) {
        double d0 = Mth.lerp(partialTick, livingEntity.xOld, livingEntity.getX());
        double d1 = Mth.lerp(partialTick, livingEntity.yOld, livingEntity.getY()) + yOffset;
        double d2 = Mth.lerp(partialTick, livingEntity.zOld, livingEntity.getZ());
        return new Vec3(d0, d1, d2);
    }
}
