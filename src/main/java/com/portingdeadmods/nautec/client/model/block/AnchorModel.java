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

public class AnchorModel extends Model {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "anchor"), "main");
    public static final Material MATERIAL = new Material(InventoryMenu.BLOCK_ATLAS, ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "entity/anchor"));
    private final ModelPart anchor;
    private final ModelPart rod;
    private final ModelPart bottom;

    public AnchorModel(ModelPart root) {
        super(RenderType::entitySolid);
        this.anchor = root.getChild("anchor");
        this.rod = this.anchor.getChild("rod");
        this.bottom = this.anchor.getChild("bottom");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition anchor = partdefinition.addOrReplaceChild("anchor", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, 21.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition rod = anchor.addOrReplaceChild("rod", CubeListBuilder.create().texOffs(16, 8).addBox(-10.0F, -37.0F, -2.0F, 8.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 15).addBox(2.0F, -37.0F, -2.0F, 8.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 36).addBox(-4.0F, -43.0F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 43).addBox(-4.0F, -47.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 8).addBox(-4.0F, -49.0F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 44).addBox(2.0F, -47.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.0F, -41.0F, -2.0F, 4.0F, 39.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.0F, 0.0F));

        PartDefinition bottom = anchor.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(16, 0).addBox(-7.0F, -2.0F, -2.0F, 14.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 14).addBox(7.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 22).addBox(11.0F, -12.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 42).addBox(11.0F, -16.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(28, 42).addBox(-13.0F, -16.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 36).addBox(-11.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(32, 22).addBox(-15.0F, -12.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public void setupAnim() {
        anchor.xRot = (float) Math.toRadians(180);
        anchor.x = 0;
        anchor.y = 0;
        anchor.z = 0;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        anchor.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}
