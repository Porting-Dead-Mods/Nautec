package com.portingdeadmods.nautec.api.client.model.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.function.Consumer;

public class NTArmorModel extends HumanoidModel<HumanoidRenderState> {

    private final EquipmentSlot slot;

    public NTArmorModel(ModelPart root, EquipmentSlot slot) {
        super(root);
        this.slot = slot;
        setPartVisibility(slot);
    }

    public static LayerDefinition createLayer(int textureWidth, int textureHeight, Consumer<PartsDefinition> partsConsumer) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.ZERO);

        partsConsumer.accept(new PartsDefinition(root));

        return LayerDefinition.create(mesh, textureWidth, textureHeight);
    }

    public EquipmentSlot getSlot() {
        return slot;
    }

    protected void setPartVisibility(EquipmentSlot slot) {
        head.visible = false;
        hat.visible = false;
        body.visible = false;
        leftArm.visible = false;
        rightArm.visible = false;
        leftLeg.visible = false;
        rightLeg.visible = false;
        switch (slot) {
            case HEAD -> {
                head.visible = true;
                hat.visible = true;
            }
            case CHEST -> {
                body.visible = true;
                rightArm.visible = true;
                leftArm.visible = true;
            }
            case LEGS -> {
                body.visible = true;
                rightLeg.visible = true;
                leftLeg.visible = true;
            }
            case FEET -> {
                rightLeg.visible = true;
                leftLeg.visible = true;
            }
        }
    }

    public record PartsDefinition(PartDefinition root) {

        public PartDefinition getHat() {
            return root().addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        }

        public PartDefinition getHead() {
            return root().addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
        }

        public PartDefinition getBody() {
            return root().addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
        }

        public PartDefinition getLeftArm() {
            return root().addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);
        }

        public PartDefinition getRightArm() {
            return root().addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);
        }

        public PartDefinition getLeftLeg() {
            return root().addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.ZERO);
        }

        public PartDefinition getRightLeg() {
            return root().addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.ZERO);
        }

    }
}
