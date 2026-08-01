package com.portingdeadmods.nautec.api.client.model.augments;

import com.mojang.blaze3d.vertex.PoseStack;
import com.portingdeadmods.nautec.api.augments.Augment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;

import java.util.function.Function;

public abstract class AugmentModel<T extends Augment> extends Model<Unit> {
    public AugmentModel(ModelPart root, Function<Identifier, RenderType> renderType) {
        super(root, renderType);
    }

    @Override
    public void setupAnim(Unit state) {
    }

    public void submit(PoseStack poseStack, SubmitNodeCollector collector, RenderType renderType, int packedLight, int packedOverlay) {
        collector.submitModel(this, Unit.INSTANCE, poseStack, renderType, packedLight, packedOverlay, -1, null);
    }
}
