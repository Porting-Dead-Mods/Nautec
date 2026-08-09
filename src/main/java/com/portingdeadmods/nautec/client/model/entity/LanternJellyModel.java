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
public class LanternJellyModel extends EntityModel<LivingEntityRenderState> {
    private static final int TENDRIL_COUNT = 4;

    private final ModelPart bell;
    private final ModelPart skirt;
    private final ModelPart[] tendrils = new ModelPart[TENDRIL_COUNT];

    public LanternJellyModel(ModelPart root) {
        super(root);
        this.bell = root.getChild("bell");
        this.skirt = root.getChild("skirt");
        for (int i = 0; i < TENDRIL_COUNT; i++) {
            this.tendrils[i] = root.getChild("tendril_" + i);
        }
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("bell", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-4.0F, -6.0F, -4.0F, 8.0F, 6.0F, 8.0F), PartPose.offset(0.0F, 18.0F, 0.0F));
        root.addOrReplaceChild("skirt", CubeListBuilder.create().texOffs(32, 0)
                .addBox(-3.0F, 0.0F, -3.0F, 6.0F, 2.0F, 6.0F), PartPose.offset(0.0F, 18.0F, 0.0F));

        float[][] offsets = {{-2.5F, -2.5F}, {2.5F, -2.5F}, {-2.5F, 2.5F}, {2.5F, 2.5F}};
        for (int i = 0; i < TENDRIL_COUNT; i++) {
            root.addOrReplaceChild("tendril_" + i, CubeListBuilder.create().texOffs(i * 4, 14)
                            .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F),
                    PartPose.offset(offsets[i][0], 20.0F, offsets[i][1]));
        }
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(LivingEntityRenderState state) {
        super.setupAnim(state);
        float pulse = Mth.sin(0.09F * state.ageInTicks);
        this.bell.y = 18.0F + pulse * 0.7F;
        this.bell.xScale = 1.0F - pulse * 0.06F;
        this.bell.zScale = 1.0F - pulse * 0.06F;
        this.bell.yScale = 1.0F + pulse * 0.08F;
        this.skirt.y = 18.0F + pulse * 0.7F;

        for (int i = 0; i < TENDRIL_COUNT; i++) {
            this.tendrils[i].xRot = pulse * 0.24F * (i < 2 ? 1.0F : -1.0F);
            this.tendrils[i].zRot = pulse * 0.24F * (i % 2 == 0 ? 1.0F : -1.0F);
        }
    }
}
