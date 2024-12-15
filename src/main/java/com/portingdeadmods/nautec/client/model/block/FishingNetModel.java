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

public class FishingNetModel extends Model {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("modid", "fishing_net"), "main");
    public static final Material MATERIAL = new Material(
            InventoryMenu.BLOCK_ATLAS, ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "entity/fishing_net_1")
    );
    private final ModelPart main;
    private final ModelPart fishing_net;

    public FishingNetModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.main = root.getChild("main");
        this.fishing_net = this.main.getChild("fishing_net");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-20.0F, -3.0F, -1.0F, 28.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(26, 4).addBox(-24.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition fishing_net = main.addOrReplaceChild("fishing_net", CubeListBuilder.create(), PartPose.offset(5.0F, 0.25F, 3.75F));

        PartDefinition cube_r1 = fishing_net.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(26, 26).addBox(-2.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(26, 12).addBox(13.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, 0.0F, -3.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r2 = fishing_net.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 4).addBox(-2.0F, -2.0F, -1.0F, 0.0F, 12.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -0.75F, 1.5708F, -1.1781F, -1.5708F));

        return LayerDefinition.create(meshdefinition, 64, 64);
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
