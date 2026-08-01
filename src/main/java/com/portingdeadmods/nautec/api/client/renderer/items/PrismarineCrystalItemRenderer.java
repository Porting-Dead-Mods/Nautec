package com.portingdeadmods.nautec.api.client.renderer.items;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.client.model.block.PrismarineCrystalModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import org.joml.Vector3fc;

import java.util.function.Consumer;

public class PrismarineCrystalItemRenderer implements NoDataSpecialModelRenderer {
    private final PrismarineCrystalModel model;

    public PrismarineCrystalItemRenderer(PrismarineCrystalModel model) {
        this.model = model;
        this.model.setupAnim();
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int packedLight, int packedOverlay, boolean hasFoil, int outlineColor) {
        this.model.submit(poseStack, collector, packedLight, packedOverlay);
    }

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
        this.model.root().getExtentsForGui(new PoseStack(), output);
    }

    public record Unbaked() implements NoDataSpecialModelRenderer.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(new Unbaked());

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public PrismarineCrystalItemRenderer bake(SpecialModelRenderer.BakingContext context) {
            return new PrismarineCrystalItemRenderer(new PrismarineCrystalModel(context.entityModelSet().bakeLayer(PrismarineCrystalModel.LAYER_LOCATION)));
        }
    }
}
