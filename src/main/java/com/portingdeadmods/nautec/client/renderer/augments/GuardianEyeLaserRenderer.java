package com.portingdeadmods.nautec.client.renderer.augments;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.world.phys.Vec3;

public class GuardianEyeLaserRenderer {
    public static void renderLaser(Vec3 playerPos, Vec3 targetPos, PoseStack poseStack, MultiBufferSource bufferSource) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.lineWidth(8.0F);

        float playerX = (float) playerPos.x;
        float playerY = (float) playerPos.y + 1.5F;
        float playerZ = (float) playerPos.z;

        float targetX = (float) targetPos.x;
        float targetY = (float) targetPos.y;
        float targetZ = (float) targetPos.z;

        BufferBuilder buffer = Tesselator.getInstance().begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR);
        buffer.addVertex(poseStack.last().pose(),playerX,playerY,playerZ);
        buffer.setColor(1, 0,1,1);

        buffer.addVertex(poseStack.last().pose(),targetX,targetY,targetZ);
        buffer.setColor(1, 0,1,1);

        BufferUploader.draw(buffer.build());

        RenderSystem.disableBlend();
    }
}
