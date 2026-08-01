package com.portingdeadmods.nautec.client.model.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.portingdeadmods.nautec.Nautec;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;

public class RobotArmModel extends Model.Simple {
    public static final RenderType RENDER_TYPE = RenderTypes.entitySolid(Nautec.rl("textures/entity/robot_arm.png"));
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Nautec.rl("robot_arm"), "main");
    private final ModelPart main;
    private final ModelPart bottom;
    private final ModelPart middle;
    private final ModelPart tip;

    public RobotArmModel(ModelPart root) {
        super(root, RenderTypes::entitySolid);
        this.main = root.getChild("main");
        this.bottom = main.getChild("bottom");
        this.middle = main.getChild("middle");
        this.tip = main.getChild("tip");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bottom = main.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(8, 0).addBox(-1.0F, -16.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition middle = main.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -30.0F, -1.0F, 2.0F, 24.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 0).addBox(-1.0F, -6.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.0F, 0.0F, 0.0021F, -0.0007F, -0.0001F));

        PartDefinition tip = main.addOrReplaceChild("tip", CubeListBuilder.create().texOffs(16, 9).addBox(-2.0F, -32.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 18).addBox(1.0F, -32.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 6).addBox(-2.0F, -29.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 14).addBox(-1.0F, -28.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    public void submitPart(RobotArmParts part, PoseStack poseStack, SubmitNodeCollector collector, int packedLight, int packedOverlay) {
        collector.submitModelPart((switch (part) {
            case BOTTOM -> bottom;
            case MIDDLE -> middle;
            case TIP -> tip;
        }), poseStack, RENDER_TYPE, packedLight, packedOverlay, null);
    }

    public enum RobotArmParts {
        TIP,
        MIDDLE,
        BOTTOM,
    }
}
