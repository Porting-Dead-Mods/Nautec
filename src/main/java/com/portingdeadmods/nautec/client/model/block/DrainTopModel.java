package com.portingdeadmods.nautec.client.model.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.portingdeadmods.nautec.Nautec;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public class DrainTopModel extends Model {
    public static final Material DRAIN_TOP_LOCATION = new Material(
            InventoryMenu.BLOCK_ATLAS, ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "entity/drain_top")
    );
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "drain_top"), "main");
    private final ModelPart top;
    private final ModelPart valve;

    public DrainTopModel(ModelPart root) {
        super(RenderType::entityTranslucent);
        this.top = root.getChild("top");
        this.valve = root.getChild("valve");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition top = partdefinition.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 0).addBox(-30.0F, -19.0F, -14.0F, 44.0F, 3.0F, 44.0F, new CubeDeformation(0.0F))
                .texOffs(0, 47).addBox(-20.0F, -21.0F, -4.0F, 24.0F, 2.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 40.0F, -8.0F));

        PartDefinition cube_r1 = top.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -3.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, -18.0042F, -5.9937F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r2 = top.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(14, 0).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-22.0F, -18.0042F, 9.0063F, 0.0F, 0.0F, -0.3927F));

        PartDefinition cube_r3 = top.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(14, 8).addBox(-3.0F, -2.0F, -1.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -18.0042F, 7.0063F, 0.0F, 0.0F, 0.3927F));

        PartDefinition cube_r4 = top.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 8).addBox(-3.0F, -2.0F, -3.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -18.0042F, 22.0063F, -0.3927F, 0.0F, 0.0F));

        PartDefinition valve = partdefinition.addOrReplaceChild("valve", CubeListBuilder.create().texOffs(56, 47).addBox(-8.0F, -9.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-2.0F, -9.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        renderLid(poseStack, buffer, packedLight, packedOverlay);

        renderValve(poseStack, buffer, packedLight, packedOverlay);
    }

    public void renderLid(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        {
            poseStack.translate(1, -1, 1);
            this.top.render(poseStack, buffer, packedLight, packedOverlay, -1);
        }
        poseStack.popPose();
    }

    public void renderValve(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        {
            poseStack.translate(0.5, 0, 0.5);
            this.valve.render(poseStack, buffer, packedLight, packedOverlay, -1);
        }
        poseStack.popPose();
    }

    public void setupAnimation() {
        top.xRot = (float) Math.toRadians(180);
        top.x = 0;
        top.y = 0;
        top.z = 0;
        valve.xRot = (float) Math.toRadians(180);
        valve.x = 0;
        valve.y = 0;
        valve.z = 0;
    }
}
