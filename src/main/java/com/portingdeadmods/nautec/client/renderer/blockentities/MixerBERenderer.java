package com.portingdeadmods.nautec.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.portingdeadmods.nautec.api.client.renderer.blockentities.LaserBlockEntityRenderer;
import com.portingdeadmods.nautec.api.utils.HorizontalDirection;
import com.portingdeadmods.nautec.client.model.block.WhiskModel;
import com.portingdeadmods.nautec.content.blockentities.MixerBlockEntity;
import com.portingdeadmods.nautec.content.blocks.MixerBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MixerBERenderer extends LaserBlockEntityRenderer<MixerBlockEntity> {
    private static final float SIDE_MARGIN = (float) MixerBlock.SHAPE.min(Direction.Axis.X) + 0.075f;
    private static final float MIN_Y = 2 / 16f;
    private static final float MAX_Y = 1 - MIN_Y;
    private final WhiskModel model;

    public MixerBERenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
        this.model = new WhiskModel(ctx.bakeLayer(WhiskModel.LAYER_LOCATION));
    }

    @Override
    public void render(MixerBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        super.render(blockEntity, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
        this.model.setupAnim();
        poseStack.pushPose();
        {
            poseStack.translate(0.5, 0, 0.5);
            poseStack.mulPose(Axis.YP.rotation(blockEntity.getIndependentAngle(partialTick)));
            poseStack.translate(-0.5, 0, -0.5);
            poseStack.translate(0.5, 1.425, 0.75);
            this.model.renderToBuffer(
                    poseStack,
                    WhiskModel.WHISK_LOCATION.buffer(bufferSource, RenderType::entityCutout),
                    packedLight,
                    packedOverlay
            );
        }
        poseStack.popPose();

        IItemHandler handler = blockEntity.getItemHandler();
        for (int i = 0; i < handler.getSlots() - 1; i++) {
            renderItem(handler.getStackInSlot(i), blockEntity, i, poseStack, bufferSource, packedLight, packedOverlay);
        }

        renderFluid(blockEntity, poseStack, bufferSource, packedLight);
    }

    private static void renderItem(ItemStack itemStack, MixerBlockEntity mixerBE, int index, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        Direction direction = HorizontalDirection.values()[index].toRegularDirection();

        poseStack.pushPose();
        {
            Vector3f normal = direction.step();
            poseStack.translate(normal.x / 5, normal.y / 5, normal.z / 5);
            poseStack.translate(0.5, 0.5, 0.5);
            poseStack.mulPose(Axis.YP.rotation((float) Math.toRadians(index * 90)));
            poseStack.scale(0.25f, 0.25f, 0.25f);
            itemRenderer.renderStatic(
                    itemStack,
                    ItemDisplayContext.NONE,
                    packedLight,
                    packedOverlay,
                    poseStack,
                    bufferSource,
                    mixerBE.getLevel(),
                    1
            );
        }
        poseStack.popPose();
    }

    private static int getLightLevel(Level level, BlockPos pos) {
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        int skyLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(skyLight, blockLight);
    }

    private static void renderFluid(MixerBlockEntity blockEntity, PoseStack poseStack, MultiBufferSource pBufferSource, int combinedLight) {
        IFluidHandler fluidHandler = blockEntity.getFluidHandler();
        FluidStack fluidStack = fluidHandler.getFluidInTank(0);
        int fluidCapacity = fluidHandler.getTankCapacity(0);
        if (fluidStack.isEmpty())
            return;

        float fillPercentage = Math.min(1, (float) fluidStack.getAmount() / fluidCapacity) / 2;
        if (fluidStack.getFluid().getFluidType().isLighterThanAir())
            renderFluid(poseStack, pBufferSource, fluidStack, fillPercentage, 1, combinedLight);
        else
            renderFluid(poseStack, pBufferSource, fluidStack, 1, fillPercentage, combinedLight);
    }

    private static void renderFluid(PoseStack poseStack, MultiBufferSource bufferSource, FluidStack fluidStack, float alpha, float heightPercentage, int combinedLight) {
        VertexConsumer vertexBuilder = bufferSource.getBuffer(RenderType.translucent());
        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidTypeExtensions.getStillTexture(fluidStack));
        int color = fluidTypeExtensions.getTintColor();
        alpha *= (color >> 24 & 255) / 255f;
        float red = (color >> 16 & 255) / 255f;
        float green = (color >> 8 & 255) / 255f;
        float blue = (color & 255) / 255f;

        renderQuads(poseStack.last().pose(), vertexBuilder, sprite, red, green, blue, alpha, heightPercentage, combinedLight);
    }

    private static void renderQuads(Matrix4f matrix, VertexConsumer buffer, TextureAtlasSprite sprite, float r, float g, float b, float alpha, float heightPercentage, int light) {
        float height = MIN_Y + (MAX_Y - MIN_Y) * heightPercentage;
        float minU = sprite.getU(SIDE_MARGIN), maxU = sprite.getU((1 - SIDE_MARGIN));
        float minV = sprite.getV(MIN_Y), maxV = sprite.getV(height);
        // min z
        buffer.addVertex(matrix, SIDE_MARGIN, MIN_Y, SIDE_MARGIN).setColor(r, g, b, alpha).setUv(minU, minV).setLight(light).setNormal(0, 0, -1);
        buffer.addVertex(matrix, SIDE_MARGIN, height, SIDE_MARGIN).setColor(r, g, b, alpha).setUv(minU, maxV).setLight(light).setNormal(0, 0, -1);
        buffer.addVertex(matrix, 1 - SIDE_MARGIN, height, SIDE_MARGIN).setColor(r, g, b, alpha).setUv(maxU, maxV).setLight(light).setNormal(0, 0, -1);
        buffer.addVertex(matrix, 1 - SIDE_MARGIN, MIN_Y, SIDE_MARGIN).setColor(r, g, b, alpha).setUv(maxU, minV).setLight(light).setNormal(0, 0, -1);
        // max z
        buffer.addVertex(matrix, SIDE_MARGIN, MIN_Y, 1 - SIDE_MARGIN).setColor(r, g, b, alpha).setUv(minU, minV).setLight(light).setNormal(0, 0, 1);
        buffer.addVertex(matrix, 1 - SIDE_MARGIN, MIN_Y, 1 - SIDE_MARGIN).setColor(r, g, b, alpha).setUv(maxU, minV).setLight(light).setNormal(0, 0, 1);
        buffer.addVertex(matrix, 1 - SIDE_MARGIN, height, 1 - SIDE_MARGIN).setColor(r, g, b, alpha).setUv(maxU, maxV).setLight(light).setNormal(0, 0, 1);
        buffer.addVertex(matrix, SIDE_MARGIN, height, 1 - SIDE_MARGIN).setColor(r, g, b, alpha).setUv(minU, maxV).setLight(light).setNormal(0, 0, 1);
        // min x
        buffer.addVertex(matrix, SIDE_MARGIN, MIN_Y, SIDE_MARGIN).setColor(r, g, b, alpha).setUv(minU, minV).setLight(light).setNormal(-1, 0, 0);
        buffer.addVertex(matrix, SIDE_MARGIN, MIN_Y, 1 - SIDE_MARGIN).setColor(r, g, b, alpha).setUv(maxU, minV).setLight(light).setNormal(-1, 0, 0);
        buffer.addVertex(matrix, SIDE_MARGIN, height, 1 - SIDE_MARGIN).setColor(r, g, b, alpha).setUv(maxU, maxV).setLight(light).setNormal(-1, 0, 0);
        buffer.addVertex(matrix, SIDE_MARGIN, height, SIDE_MARGIN).setColor(r, g, b, alpha).setUv(minU, maxV).setLight(light).setNormal(-1, 0, 0);
        // max x
        buffer.addVertex(matrix, 1 - SIDE_MARGIN, MIN_Y, SIDE_MARGIN).setColor(r, g, b, alpha).setUv(minU, minV).setLight(light).setNormal(1, 0, 0);
        buffer.addVertex(matrix, 1 - SIDE_MARGIN, height, SIDE_MARGIN).setColor(r, g, b, alpha).setUv(minU, maxV).setLight(light).setNormal(1, 0, 0);
        buffer.addVertex(matrix, 1 - SIDE_MARGIN, height, 1 - SIDE_MARGIN).setColor(r, g, b, alpha).setUv(maxU, maxV).setLight(light).setNormal(1, 0, 0);
        buffer.addVertex(matrix, 1 - SIDE_MARGIN, MIN_Y, 1 - SIDE_MARGIN).setColor(r, g, b, alpha).setUv(maxU, minV).setLight(light).setNormal(1, 0, 0);
        // top
        if (heightPercentage < 1) {
            minV = sprite.getV(SIDE_MARGIN);
            maxV = sprite.getV(1 - SIDE_MARGIN);
            buffer.addVertex(matrix, SIDE_MARGIN, height, SIDE_MARGIN).setColor(r, g, b, alpha).setUv(minU, minV).setLight(light).setNormal(0, 1, 0);
            buffer.addVertex(matrix, SIDE_MARGIN, height, 1 - SIDE_MARGIN).setColor(r, g, b, alpha).setUv(minU, maxV).setLight(light).setNormal(0, 1, 0);
            buffer.addVertex(matrix, 1 - SIDE_MARGIN, height, 1 - SIDE_MARGIN).setColor(r, g, b, alpha).setUv(maxU, maxV).setLight(light).setNormal(0, 1, 0);
            buffer.addVertex(matrix, 1 - SIDE_MARGIN, height, SIDE_MARGIN).setColor(r, g, b, alpha).setUv(maxU, minV).setLight(light).setNormal(0, 1, 0);
        }
    }
}
