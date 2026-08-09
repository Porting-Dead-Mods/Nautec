package com.portingdeadmods.nautec.client.model.entity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SiltSkipperModel extends EntityModel<LivingEntityRenderState> {
    private final ModelPart tail;
    private final ModelPart leftFin;
    private final ModelPart rightFin;

    public SiltSkipperModel(ModelPart root) {
        super(root);
        this.tail = root.getChild("tail");
        this.leftFin = root.getChild("left_fin");
        this.rightFin = root.getChild("right_fin");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-1.5F, -2.0F, -3.0F, 3.0F, 4.0F, 6.0F), PartPose.offset(0.0F, 22.0F, 0.0F));
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 10)
                .addBox(-2.0F, -2.5F, -4.0F, 4.0F, 5.0F, 4.0F), PartPose.offset(0.0F, 22.0F, -3.0F));
        root.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(18, 0)
                .addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 5.0F), PartPose.offset(0.0F, 22.0F, 3.0F));
        root.addOrReplaceChild("dorsal_fin", CubeListBuilder.create().texOffs(18, 10)
                .addBox(0.0F, -4.0F, -2.5F, 0.0F, 4.0F, 5.0F), PartPose.offset(0.0F, 20.0F, 0.0F));
        root.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(18, 19)
                .addBox(0.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F),
                PartPose.offsetAndRotation(1.5F, 22.5F, -1.0F, 0.0F, 0.0F, (float) (Math.PI / 5)));
        root.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(18, 21)
                .addBox(-2.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F),
                PartPose.offsetAndRotation(-1.5F, 22.5F, -1.0F, 0.0F, 0.0F, (float) (-Math.PI / 5)));

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(LivingEntityRenderState state) {
        super.setupAnim(state);
        float amplitude = state.isInWater ? 1.0F : 1.6F;
        float wave = Mth.sin(0.7F * state.ageInTicks);
        this.tail.yRot = -amplitude * 0.55F * wave;
        this.leftFin.zRot = 0.6283F + 0.25F * wave;
        this.rightFin.zRot = -0.6283F - 0.25F * wave;
    }
}
