package com.portingdeadmods.nautec.client.model.augment;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.client.model.augments.AugmentModel;
import com.portingdeadmods.nautec.content.augments.DolphinFinAugment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;

public class DolphinFinModel extends AugmentModel<DolphinFinAugment> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Nautec.rl("dolphin_fin"), "main");
    public static final RenderType RENDER_TYPE = RenderTypes.entitySolid(Nautec.rl("textures/augments/dolphin_fin.png"));

    private final ModelPart main;

    public DolphinFinModel(ModelPart root) {
        super(root, RenderTypes::entitySolid);
        this.main = root.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -6.0F, -1.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, RenderType renderType, int packedLight, int packedOverlay) {
        this.main.x = 0;
        this.main.y = 0;
        this.main.z = 0;
        poseStack.pushPose();
        {
            poseStack.translate(0, 0.5, 0.25);
            poseStack.translate(0.5, 0.5, 0.5);
            poseStack.mulPose(Axis.XP.rotationDegrees(-115));
            poseStack.translate(-0.5, -0.5, -0.5);
            collector.submitModelPart(this.main, poseStack, renderType, packedLight, packedOverlay, null);
        }
        poseStack.popPose();
    }
}
