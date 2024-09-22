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

public class WhiskModel extends Model {
    public static final Material WHISK_LOCATION = new Material(
            InventoryMenu.BLOCK_ATLAS, ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "entity/whisk")
    );
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "custommodel"), "main");
    private final ModelPart main;

    public WhiskModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.main = root.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, 3.0F, 2.0F, 21.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 16).addBox(1.0F, 17.0F, 3.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(20, 8).addBox(4.0F, 11.0F, 3.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 20).addBox(1.0F, 9.0F, 3.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -4.0F));

        PartDefinition cube_r1 = main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 4).addBox(-2.0F, 17.0F, 0.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 0).addBox(-2.0F, 25.0F, 0.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -8.0F, 5.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r2 = main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(8, 16).addBox(0.0F, 15.0F, 0.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -4.0F, 5.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r3 = main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(8, 0).addBox(0.0F, 15.0F, 0.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -4.0F, -2.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r4 = main.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(12, 24).addBox(0.0F, 15.0F, 0.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -4.0F, 8.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r5 = main.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(8, 12).addBox(-2.0F, 17.0F, 0.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 8).addBox(-2.0F, 9.0F, 0.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 1.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r6 = main.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 24).addBox(-2.0F, 17.0F, 0.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(20, 24).addBox(-2.0F, 25.0F, 0.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -8.0F, 7.0F, 0.0F, -1.5708F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    public void setupAnim() {
        main.xRot = (float) Math.toRadians(180);
        main.x = 0;
        main.y = 0;
        main.z = 0;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        main.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}
