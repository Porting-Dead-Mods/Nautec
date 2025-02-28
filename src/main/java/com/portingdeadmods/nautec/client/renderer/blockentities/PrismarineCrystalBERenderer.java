package com.portingdeadmods.nautec.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.portingdeadmods.nautec.api.client.renderer.blockentities.LaserBlockEntityRenderer;
import com.portingdeadmods.nautec.client.model.block.PrismarineCrystalModel;
import com.portingdeadmods.nautec.content.blockentities.multiblock.semi.PrismarineCrystalBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

public class PrismarineCrystalBERenderer extends LaserBlockEntityRenderer<PrismarineCrystalBlockEntity> {
    private final PrismarineCrystalModel model;

    public PrismarineCrystalBERenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
        this.model = new PrismarineCrystalModel(ctx.bakeLayer(PrismarineCrystalModel.LAYER_LOCATION));
        model.setupAnim();
    }

    @Override
    public void render(PrismarineCrystalBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        super.render(blockEntity, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
        VertexConsumer consumer = PrismarineCrystalModel.PRISMARINE_CRYSTAL_LOCATION.buffer(bufferSource, RenderType::entitySolid);
        poseStack.pushPose();
        {
            poseStack.translate(0, -2, 0);

            if (blockEntity.isBreaking()) {
                float f = ((float) (blockEntity.getLevel().getGameTime() - blockEntity.getStartTick()) + partialTick) / (float) blockEntity.getDuration();
                if (f >= 0.0F && f <= 1.0F) {
                    float f2;
                    f2 = f * 6.2831855F;
                    float f3 = -1.5F * (Mth.cos(f2) + 0.5F) * Mth.sin(f2 / 2.0F);
                    poseStack.rotateAround(Axis.XP.rotation(f3 * 0.015625F), 0.5F, 0.0F, 0.5F);
                    float f4 = Mth.sin(f2);
                    poseStack.rotateAround(Axis.ZP.rotation(f4 * 0.015625F), 0.5F, 0.0F, 0.5F);
                }
            }
            model.render(poseStack, consumer, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos().above()), packedOverlay);
        }
        poseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        return LevelRenderer.getLightColor(level, pos);
    }

    @Override
    public @NotNull AABB getRenderBoundingBox(PrismarineCrystalBlockEntity blockEntity) {
        return blockEntity.shouldRender(Direction.UP)
                ? super.getRenderBoundingBox(blockEntity)
                : new AABB(blockEntity.getBlockPos().below(3)).expandTowards(0, 6, 0);
    }
}
