package com.portingdeadmods.nautec.client.model.augment;

import com.mojang.blaze3d.vertex.PoseStack;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.client.model.augments.AugmentModel;
import com.portingdeadmods.nautec.content.augments.GuardianEyeAugment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;

public class GuardianEyeModel extends AugmentModel<GuardianEyeAugment> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Nautec.rl("guardian_eye"), "main");
    public static final RenderType RENDER_TYPE = RenderTypes.entitySolid(Nautec.rl("textures/augments/guardian_eye.png"));
    private final ModelPart main;

    public GuardianEyeModel(ModelPart root) {
        super(root, RenderTypes::entitySolid);
        this.main = root.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 13.0F, 1.0F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -6.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, RenderType renderType, int packedLight, int packedOverlay) {
        collector.submitModelPart(this.main, poseStack, renderType, packedLight, packedOverlay, null);
    }
}
