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
public class VentCrawlerModel extends EntityModel<LivingEntityRenderState> {
    private static final int LEG_COUNT = 6;
    private static final int[][] LEG_UV = {{0, 16}, {12, 16}, {24, 16}, {36, 16}, {0, 20}, {12, 20}};

    private final ModelPart[] legs = new ModelPart[LEG_COUNT];
    private final ModelPart head;

    public VentCrawlerModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        for (int i = 0; i < LEG_COUNT; i++) {
            this.legs[i] = root.getChild("leg_" + i);
        }
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-5.0F, -4.0F, -6.0F, 10.0F, 4.0F, 12.0F), PartPose.offset(0.0F, 22.0F, 0.0F));
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(44, 0)
                .addBox(-3.0F, -3.0F, -3.0F, 6.0F, 3.0F, 3.0F), PartPose.offset(0.0F, 22.0F, -6.0F));

        for (int i = 0; i < LEG_COUNT; i++) {
            boolean left = i >= 3;
            float x = left ? 5.0F : -5.0F;
            float z = -4.0F + (i % 3) * 4.0F;
            root.addOrReplaceChild("leg_" + i, CubeListBuilder.create().texOffs(LEG_UV[i][0], LEG_UV[i][1])
                    .addBox(left ? 0.0F : -4.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F), PartPose.offset(x, 22.0F, z));
        }
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(LivingEntityRenderState state) {
        super.setupAnim(state);
        for (int i = 0; i < LEG_COUNT; i++) {
            float phase = (i % 3) * 1.0471976F + (i < 3 ? 0.0F : 3.1415927F);
            float swing = Mth.cos(state.walkAnimationPos * 0.6F + phase) * 0.45F * state.walkAnimationSpeed;
            this.legs[i].zRot = swing + (i < 3 ? -0.22F : 0.22F);
            this.legs[i].yRot = Mth.sin(state.walkAnimationPos * 0.6F + phase) * 0.2F * state.walkAnimationSpeed;
        }
        this.head.xRot = 0.06F * Mth.sin(0.08F * state.ageInTicks);
    }
}
