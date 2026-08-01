package com.portingdeadmods.nautec.api.client.renderer.items;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.client.model.block.AnchorModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.util.Unit;
import org.joml.Vector3fc;

import java.util.function.Consumer;

public class AnchorItemRenderer implements NoDataSpecialModelRenderer {
    private final AnchorModel model;

    public AnchorItemRenderer(AnchorModel model) {
        this.model = model;
        this.model.setupAnim();
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int packedLight, int packedOverlay, boolean hasFoil, int outlineColor) {
        collector.submitModel(this.model, Unit.INSTANCE, poseStack, AnchorModel.RENDER_TYPE, packedLight, packedOverlay, -1, null);
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
        public AnchorItemRenderer bake(SpecialModelRenderer.BakingContext context) {
            return new AnchorItemRenderer(new AnchorModel(context.entityModelSet().bakeLayer(AnchorModel.LAYER_LOCATION)));
        }
    }
}
