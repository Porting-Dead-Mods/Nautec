package com.portingdeadmods.nautec.api.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LaserBlockEntityRenderer<T extends LaserBlockEntity, S extends LaserRenderState> extends NTBERenderer<T, S> {
    public static final Identifier BEAM_LOCATION = Nautec.rl("textures/entity/laser_beam.png");
    private static final Identifier GUARDIAN_BEAM_LOCATION = Identifier.withDefaultNamespace("textures/entity/guardian_beam.png");
    private static final RenderType BEAM_RENDER_TYPE = RenderTypes.entityCutout(GUARDIAN_BEAM_LOCATION);

    public LaserBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @SuppressWarnings("unchecked")
    @Override
    public S createRenderState() {
        return (S) new LaserRenderState();
    }

    @Override
    public void extractRenderState(T blockEntity, S state, float partialTick, Vec3 cameraPos, ModelFeatureRenderer.@Nullable CrumblingOverlay crumbling) {
        BlockEntityRenderState.extractBase(blockEntity, state, crumbling);
        state.beams.clear();
        state.partialTick = partialTick;
        state.laserTime = blockEntity.getClientLaserTime() + (partialTick * 24);
        state.gameTime = blockEntity.getLevel() == null ? 0L : blockEntity.getLevel().getGameTime();

        Object2IntMap<Direction> laserDistances = blockEntity.getLaserDistances();
        for (Direction direction : blockEntity.getLaserOutputs()) {
            int laserDistance = laserDistances.getOrDefault(direction, 0);

            BlockPos originPos = blockEntity.getBlockPos();
            BlockPos targetPos = originPos.relative(direction, laserDistance - 1);
            BlockState blockState = blockEntity.getLevel().getBlockState(targetPos.relative(direction));
            if (laserDistance > 0 && blockEntity.shouldRender(direction)) {
                VoxelShape shape = blockState.getShape(blockEntity.getLevel(), targetPos.relative(direction), CollisionContext.empty());

                float shapeIndent = (float) switch (direction.getAxisDirection()) {
                    case POSITIVE -> shape.min(direction.getAxis());
                    case NEGATIVE -> shape.max(direction.getAxis());
                };

                state.beams.add(new LaserRenderState.Beam(direction, laserDistance, shapeIndent));
            }
        }
    }

    @Override
    public void submit(S state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        for (LaserRenderState.Beam beam : state.beams) {
            Direction direction = beam.direction();
            int laserDistance = beam.laserDistance();
            float shapeIndent = beam.shapeIndent();

            submitOuterBeam(poseStack, collector, direction, laserDistance, 1 - shapeIndent, state.laserTime, state.gameTime);

            poseStack.pushPose();
            {
                poseStack.mulPose(direction.getRotation());
                poseStack.scale(0.125f, 1, 0.125f);
                switch (direction) {
                    case UP -> poseStack.translate(3.5f, 0, 3.5f);
                    case DOWN, SOUTH, WEST -> poseStack.translate(3.5f, 0, -4.5f);
                    case EAST, NORTH -> poseStack.translate(-4.5f, 0, -4.5f);
                }

                int offset = 0;
                int offset2 = 0;

                if (direction == Direction.EAST || direction == Direction.SOUTH) {
                    offset2 = 1;
                }

                if (direction == Direction.NORTH || direction == Direction.WEST) {
                    offset2 = 1;
                }


                if (direction == Direction.DOWN) {
                    offset2 = 1;
                }

                if (direction == Direction.UP) {
                    offset = 1;
                    offset2 = 1;
                }

                submitInnerBeam(poseStack, collector, state.partialTick, state.gameTime,
                        offset, (laserDistance - offset - offset2 + (1 - shapeIndent)), ARGB.color(202, 214, 224));
            }
            poseStack.popPose();
        }
    }

    public static void submitOuterBeam(PoseStack poseStack, SubmitNodeCollector collector, Direction direction, int laserDistance, float targetOffset, float laserTime, long gameTime) {
        float f1 = laserTime;
        float f2 = f1 * 0.5F % 1.0F;
        float f3 = 0.5f;

        int red = 94;
        int green = 133;
        int blue = 164;

        poseStack.pushPose();
        {
            int offset;
            int offset2 = 0;
            if (direction == Direction.NORTH) {
                poseStack.translate(0.5F, f3, 0.0F);
                offset = 0;
            } else if (direction == Direction.SOUTH) {
                poseStack.translate(0.5F, f3, 1.0F);
                offset = 1;
            } else if (direction == Direction.EAST) {
                poseStack.translate(1.0F, f3, 0.5F);
                offset = 1;
            } else if (direction == Direction.WEST) {
                poseStack.translate(0.0F, f3, 0.5F);
                offset = 0;
            } else if (direction == Direction.DOWN) {
                poseStack.translate(0.5F, 1, 0.5F);
                offset = 0;
            } else {
                poseStack.translate(0.5F, 1, 0.5F);
                offset = 1;
            }
            BlockPos originPos = BlockPos.ZERO;
            BlockPos targetPos = originPos.relative(direction, laserDistance - 1);
            Vec3 vec3 = vec3Relative(targetPos.relative(direction, -offset).getCenter(), direction, targetOffset - (direction != Direction.DOWN ? 0.9d : -0.1d));
            Vec3 vec31 = originPos.relative(direction, offset2).getCenter();
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
            float f29 = -1.0F + f2;
            float f30 = f4 * 2.5F + f29;
            float f31 = gameTime % 2 == 0 ? 0.5F : 0.0F;
            collector.submitCustomGeometry(poseStack, BEAM_RENDER_TYPE, (pose, vertexconsumer) -> {
                outerBeamVertex(red, green, blue, f4, f19, f20, f21, f22, f29, f30, vertexconsumer, pose);
                outerBeamVertex(red, green, blue, f4, f23, f24, f25, f26, f29, f30, vertexconsumer, pose);

                vertex(vertexconsumer, pose, f11, f4, f12, red, green, blue, 0.5F, f31 + 0.5F);
                vertex(vertexconsumer, pose, f13, f4, f14, red, green, blue, 1.0F, f31 + 0.5F);
                vertex(vertexconsumer, pose, f17, f4, f18, red, green, blue, 1.0F, f31);
                vertex(vertexconsumer, pose, f15, f4, f16, red, green, blue, 0.5F, f31);
            });
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

    public static void submitInnerBeam(
            PoseStack poseStack, SubmitNodeCollector collector, float partialTick, long gameTime, float yOffset, float height, int color
    ) {
        submitInnerBeam(poseStack, collector, BEAM_LOCATION, partialTick, 1.0F, gameTime, yOffset, height, color, 0.2F, 0.25F);
    }

    public static void submitInnerBeam(
            PoseStack poseStack,
            SubmitNodeCollector collector,
            Identifier beamLocation,
            float partialTick,
            float textureScale,
            long gameTime,
            float yOffset,
            float height,
            int color,
            float beamRadius,
            float glowRadius
    ) {
        float i = yOffset + height;
        poseStack.pushPose();
        poseStack.translate(0.5, 0.0, 0.5);
        float f = (float) Math.floorMod(gameTime, 40) + partialTick;
        float f1 = height < 0 ? f : -f;
        float f2 = Mth.frac(f1 * 0.2F - (float) Mth.floor(f1 * 0.1F));
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(f * 2.25F - 45.0F));
        float f6 = -beamRadius;
        float f9 = -beamRadius;
        float f12 = -1.0F + f2;
        float f13 = (float) height * textureScale * (0.5F / beamRadius) + f12;
        submitPart(
                poseStack,
                collector,
                RenderTypes.beaconBeam(beamLocation, false),
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
        float f3 = -glowRadius;
        float f4 = -glowRadius;
        float f5 = -glowRadius;
        f6 = -glowRadius;
        f12 = -1.0F + f2;
        f13 = (float) height * textureScale + f12;
        submitPart(
                poseStack,
                collector,
                RenderTypes.beaconBeam(beamLocation, true),
                ARGB.color(32, color),
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

    private static void submitPart(
            PoseStack poseStack,
            SubmitNodeCollector collector,
            RenderType renderType,
            int color,
            float minY,
            float maxY,
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
        collector.submitCustomGeometry(poseStack, renderType, (pose, consumer) -> {
            renderQuad(pose, consumer, color, minY, maxY, x1, z1, x2, z2, minU, maxU, minV, maxV);
            renderQuad(pose, consumer, color, minY, maxY, x4, z4, x3, z3, minU, maxU, minV, maxV);
            renderQuad(pose, consumer, color, minY, maxY, x2, z2, x4, z4, minU, maxU, minV, maxV);
            renderQuad(pose, consumer, color, minY, maxY, x3, z3, x1, z1, minU, maxU, minV, maxV);
        });
    }

    private static void renderQuad(
            PoseStack.Pose pose,
            VertexConsumer consumer,
            int color,
            float minY,
            float maxY,
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
            PoseStack.Pose pose, VertexConsumer consumer, int color, float y, float x, float z, float u, float v
    ) {
        consumer.addVertex(pose, x, y, z)
                .setColor(color)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(15728880)
                .setNormal(pose, 0.0F, 1.0F, 0.0F);
    }

    public static Vec3 vec3Relative(Vec3 self, Direction direction, double distance) {
        return distance == 0
                ? self
                : new Vec3(
                self.x() + direction.getStepX() * distance, self.y() + direction.getStepY() * distance, self.z() + direction.getStepZ() * distance
        );
    }

    @Override
    public boolean shouldRenderOffScreen() {
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
