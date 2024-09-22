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

public class PrismarineCrystalModel extends Model {
    public static final Material PRISMARINE_CRYSTAL_LOCATION = new Material(
            InventoryMenu.BLOCK_ATLAS, ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "entity/prismarine_crystal")
    );
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "prismarine_crystal"), "main");
    private final ModelPart main;

    public PrismarineCrystalModel(ModelPart root) {
        super(RenderType::entitySolid);
        this.main = root.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -61.0F, -8.0F, 16.0F, 56.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(85, 93).addBox(-8.0F, -5.0F, -8.0F, 14.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(86, 38).addBox(-6.0F, -65.0F, -6.0F, 14.0F, 4.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 72).addBox(-6.0F, -55.0F, -14.0F, 12.0F, 40.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(64, 0).addBox(-14.0F, -52.0F, -6.0F, 6.0F, 40.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(53, 61).addBox(0.0F, -50.0F, 2.0F, 12.0F, 35.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(106, 110).addBox(2.0F, -55.0F, 4.0F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(99, 75).addBox(2.0F, -15.0F, 4.0F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(100, 0).addBox(6.0F, -47.0F, -11.0F, 5.0F, 28.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(36, 107).addBox(-11.0F, -42.0F, -11.0F, 5.0F, 21.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(99, 56).addBox(-6.0F, -73.0F, -6.0F, 12.0F, 8.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(48, 0).addBox(-3.0F, -78.0F, -4.0F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(66, 110).addBox(-5.0F, -2.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 118).addBox(-3.0F, 8.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 25.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    public void setupAnim() {
        main.xRot = (float) Math.toRadians(180);
        main.x = 0;
        main.y = 0;
        main.z = 0;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        main.render(poseStack, buffer, packedLight, packedOverlay, color);
    }

    public void render(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        {
            poseStack.translate(0.5, 0, 0.5);
            this.main.render(poseStack, buffer, packedLight, packedOverlay, -1);
        }
        poseStack.popPose();
    }
}
