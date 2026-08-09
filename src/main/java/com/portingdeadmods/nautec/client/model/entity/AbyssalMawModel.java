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
public class AbyssalMawModel extends EntityModel<LivingEntityRenderState> {
    private final ModelPart lowerJaw;
    private final ModelPart tail;
    private final ModelPart lureRod;
    private final ModelPart lureBulb;

    public AbyssalMawModel(ModelPart root) {
        super(root);
        this.lowerJaw = root.getChild("lower_jaw");
        this.tail = root.getChild("tail");
        this.lureRod = root.getChild("lure_rod");
        this.lureBulb = root.getChild("lure_bulb");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-4.0F, -5.0F, -6.0F, 8.0F, 10.0F, 12.0F), PartPose.offset(0.0F, 18.0F, 0.0F));
        root.addOrReplaceChild("upper_jaw", CubeListBuilder.create().texOffs(0, 22)
                .addBox(-4.0F, -3.0F, -6.0F, 8.0F, 3.0F, 6.0F), PartPose.offset(0.0F, 16.0F, -6.0F));
        root.addOrReplaceChild("lower_jaw", CubeListBuilder.create().texOffs(0, 31)
                .addBox(-4.0F, 0.0F, -6.0F, 8.0F, 3.0F, 6.0F), PartPose.offset(0.0F, 16.0F, -6.0F));
        root.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(40, 0)
                .addBox(0.0F, -4.0F, 0.0F, 0.0F, 8.0F, 7.0F), PartPose.offset(0.0F, 18.0F, 6.0F));
        root.addOrReplaceChild("lure_rod", CubeListBuilder.create().texOffs(40, 15)
                .addBox(-0.5F, -7.0F, -0.5F, 1.0F, 7.0F, 1.0F), PartPose.offset(0.0F, 13.0F, -7.0F));
        root.addOrReplaceChild("lure_bulb", CubeListBuilder.create().texOffs(44, 15)
                .addBox(-1.0F, -9.0F, -3.0F, 2.0F, 2.0F, 2.0F), PartPose.offset(0.0F, 13.0F, -7.0F));

        return LayerDefinition.create(mesh, 64, 48);
    }

    @Override
    public void setupAnim(LivingEntityRenderState state) {
        super.setupAnim(state);
        this.tail.yRot = -0.45F * Mth.sin(0.2F * state.ageInTicks);
        this.lowerJaw.xRot = 0.16F + 0.16F * Mth.sin(0.09F * state.ageInTicks);
        float sway = 0.22F * Mth.sin(0.13F * state.ageInTicks);
        this.lureRod.xRot = sway;
        this.lureBulb.xRot = sway;
    }
}
